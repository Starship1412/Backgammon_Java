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
		int restartControl = 0;
		board.initializeBoard();
		view.displayWelcome();
		view.displayStart();
		do {
			boolean commandDone = false;
			do {
				command = view.getUserInput(board);
				if (startControl == 1 && restartControl == 0) {
					if (command.isRoll()) {
						board.makeDieRoll();
						view.displayPiece(board);
						commandDone = true;
					} else if (command.isMove()) {
						if (board.moveIsPossible(command)) {
							board.move(command);
							board.calculatePips();
							view.displayPiece(board);
							if (board.getDieMoveNumber() == 0)
								board.endTurn();
							commandDone = true;
						} else
							view.displayCommandNotPossible();
					} else if (command.isStart()) {
						view.displayStart();
						startControl--;
						restartControl++;
						commandDone = true;
					} else if (command.isQuit()) {
						commandDone = true;
					} else if (command.isWaive()) {
						view.displayWaive(board.getPlayer(0));
						board.endTurn();
						board.makeDieSetZero();
						commandDone = true;
					} else if (command.isShowPips()) {
						view.displayPips(board);
					} else if (command.isSetFace()) {
						board.setFace(command);
						view.displayPiece(board);
						commandDone = true;
					} else if (command.isShowHint())
						view.showHint();
				}
				if (startControl == 0 && restartControl == 0) {
					if (command.isStart()) {
						view.getUserName(board);
						startControl++;
						board.calculatePips();
						view.displayPiece(board);
						commandDone = true;
					} else if (command.isRoll() || command.isMove() || command.isWaive() || command.isShowPips() || command.isSetFace()) {
						view.displayCommandTemporarilyInvalid();
					} else if (command.isShowHint()) {
						view.showHint();
					} else if (command.isQuit())
						commandDone = true;
				}
				if (restartControl == 1)
					restartControl--;
			} while (!commandDone);
		} while (!command.isQuit() && !board.isGameOver());
		if (board.isGameOver()) {
			view.displayGameOver(board.getPlayer(0));
		} else
			view.displayQuit();
	}
}
