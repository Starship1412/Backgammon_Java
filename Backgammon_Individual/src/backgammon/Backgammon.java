package backgammon;

/**
 * This is the main class for playing the Backgammon game.
 */

public class Backgammon {
	
	public static void main (String... args) {
		Board board = new Board();
		View view = new View();
		Command command;
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
						commandDone = true;
					} else if (command.isMove()) {
						if (board.moveIsPossible(command)) {
							board.move(command);
							board.calculateSetPips();
							view.displayPiece(board);
							if (board.getDiceMoveNumber() == 0)
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
						commandDone = true;
					} else if (command.isShowHint())
						view.showHint();
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
							oneMatchOverDisplayed = false;
						} else if (command.isRoll() || command.isMove() || command.isWaive() || command.isShowPips() || command.isSetFace()) {
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
						view.FirstDiceRoll(board);
						view.showDice(board.getDiceFace(1), board.getDiceFace(2));
						view.displayPiece(board);
						startControl++;
						commandDone = true;
					} else if (command.isRoll() || command.isMove() || command.isWaive() || command.isShowPips() || command.isSetFace() || command.isJump()) {
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
}
