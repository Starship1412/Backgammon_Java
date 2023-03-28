package backgammon;

/**
 * This is the main class for playing the Backgammon game.
 */

public class Backgammon {
		
	public static void main (String... args) {
		Board board = new Board();
		View view = new View();
		Command command;
		view.displayWelcome();
		board.initializeBoard();
		int once = 0;
		do {
			boolean commandDone = false;
			do {
				command = view.getUserInput(board);
				if (once == 1) {
					if (command.isRoll()) {
						board.die.roll();
						view.displayPiece(board);
						commandDone = true;
					} else if (command.isMove()) {
						if (board.moveIsPossible(command)) {
							board.move(command);
							board.calculatePips();
							view.displayPiece(board);
							if (board.die.moveNumber == 0)
								board.endTurn();
							commandDone = true;
						} else
							view.displayCommandNotPossible();
					} else if (command.isStart()) {
						view.displayCommandTemporarilyInvalid();
					} else if (command.isQuit()) {
						commandDone = true;
					} else if (command.isWaive()) {
						view.displayWaive(board.currentPlayer);
						board.endTurn();
						board.die.setZero();
						commandDone = true;
					} else if (command.isShowPips()) {
						view.displayPips(board.currentPlayer);
					} else if (command.isShowHint()) {
						view.showHint();
					}
				}
				if (once == 0) {
					if (command.isStart()) {
						view.getUserStartInput(board);
						once++;
						board.calculatePips();
						view.displayPiece(board);
						commandDone = true;
					} else if (command.isRoll() || command.isMove() || command.isWaive() || command.isShowPips() || command.isRollNumber()) {
						view.displayCommandTemporarilyInvalid();
					} else if (command.isShowHint()) {
						view.showHint();
					} else if (command.isQuit())
						commandDone = true;
				}
			} while (!commandDone);
		} while (!command.isQuit() && !board.isGameOver());
		if (board.isGameOver()) {
			view.displayGameOver(board.currentPlayer);
		} else
			view.displayQuit();
	}
}
/////