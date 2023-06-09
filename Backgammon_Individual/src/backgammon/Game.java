package backgammon;

public class Game {
	
	Board board = new Board();
	View view = new View();
	Command command;
	public void playGame () {
		int startControl = 0;
		boolean oneMatchOverDisplayed = false;
		view.displayWelcome();
		view.displayStart();
		do {
			boolean commandDone = false;
			do {
				command = view.getUserInput(board);
				if (startControl == 1 && !board.isOneMatchOver())
					if (command.isRoll()) {
						board.makeDiceRoll();
						view.showDice(board.getDiceFace(1), board.getDiceFace(2));
						view.displayPiece(board);
						view.showAllAllowedMoves(board);
						commandDone = true;
					} else if (command.isMove()) {
						if (board.moveIsPossible(command)) {
							board.move(command);
							board.calculateSetPips();
							if (board.isOneMatchOver())
								board.addCurrentPlayerScore();
							view.displayPiece(board);
							if (board.getDiceMoveNumber() != 0) {
								view.showAllAllowedMoves(board);
							} else if (board.getDiceMoveNumber() == 0)
								board.endTurn();
							commandDone = true;
						} else
							view.displayCommandNotPossible();
					} else if (command.isStart()) {
						view.displayRestart();
						startControl--;
						commandDone = true;
					} else if (command.isQuit()) {
						commandDone = true;
					} else if (command.isJump()) {
						view.displayForceJump(board);
						board.addMatchRoundNumber();
						if (!board.isWholeMatchOver()) {
							board.initializeBoard();
							board.calculateSetPips();
							view.FirstDiceRoll(board);
							view.showDice(board.getDiceFace(1), board.getDiceFace(2));
							view.displayPiece(board);
							view.showAllAllowedMoves(board);
						}
						commandDone = true;
					} else if (command.isWaive()) {
						view.displayWaive(board.getPlayer(0));
						board.endTurn();
						board.makeDiceSetZero();
						commandDone = true;
					} else if (command.isShowPips()) {
						view.displayPips(board);
					} else if (command.isSetFace()) {
						board.setDiceFace(command);
						view.displayPiece(board);
						view.showAllAllowedMoves(board);
						commandDone = true;
					} else if (command.isShowHint()) {
						view.showHint();
					} else if (command.isShowAllAllowedMoves())
						view.showAllAllowedMoves(board);
				if (board.isOneMatchOver()) {
					if (!oneMatchOverDisplayed) {
						view.displayOneMatchOver(board);
						board.addMatchRoundNumber();
					}
					if (!board.isWholeMatchOver() && oneMatchOverDisplayed)
						if (command.isJump()) {
							board.initializeBoard();
							board.calculateSetPips();
							view.FirstDiceRoll(board);
							view.showDice(board.getDiceFace(1), board.getDiceFace(2));
							view.displayPiece(board);
							view.showAllAllowedMoves(board);
							oneMatchOverDisplayed = false;
						} else if (command.isRoll() || command.isMove() || command.isWaive() || command.isShowPips() || command.isSetFace() || command.isShowAllAllowedMoves()) {
							view.displayCommandTemporarilyInvalid();
						} else if (command.isShowHint()) {
							view.showHint();
						} else if (command.isStart()) {
							view.displayRestart();
							startControl--;
							oneMatchOverDisplayed = false;
							commandDone = true;
						} else if (command.isQuit())
							commandDone = true;
					if (!oneMatchOverDisplayed && !command.isJump() && !command.isStart())
						oneMatchOverDisplayed = true;
				}
				if (startControl == 0) {
					if (command.isStart()) {
						view.getStartInformation(board);
						board.initializeBoard();
						board.calculateSetPips();
						board.setPlayersScoreToZero();
						view.FirstDiceRoll(board);
						view.showDice(board.getDiceFace(1), board.getDiceFace(2));
						view.displayPiece(board);
						view.showAllAllowedMoves(board);
						startControl++;
						commandDone = true;
					} else if (command.isRoll() || command.isMove() || command.isWaive() || command.isShowPips() || command.isSetFace() || command.isJump() || command.isShowAllAllowedMoves()) {
						view.displayCommandTemporarilyInvalid();
					} else if (command.isShowHint()) {
						view.showHint();
					} else if (command.isQuit())
						commandDone = true;
				}
			} while (!commandDone);
		} while (!command.isQuit() && !board.isWholeMatchOver());
		if (board.isWholeMatchOver()) {
			view.displayWholeMatchOver(board);
		} else
			view.displayQuit();
	}
	
	public Board getBoard () {
		return board;
	}
}
