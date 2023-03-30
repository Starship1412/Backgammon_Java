package backgammon;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * This class displays to the console and gets user input from the keyboard.
 */

public class View {
	
	private final static String BLANK2 = "  ";
	private final static String BLANK3 = "   ";
	private Scanner in;
	private Command command;
	
	View () {
		in = new Scanner(System.in);
	}
	
	public void displayWelcome () {
		System.out.println("Welcome to Backgammon");
		System.out.println("Enter S to start the game or restart the game. Enter Q to exit the game. Enter H to view all allowed commands.\n"
				+ "Note that some commands are temporarily invalid in some cases.");
	}
	
	public void displayStart () {
		System.out.println("Enter S to start the game or restart the game. Enter Q to exit the game. Enter H to view all allowed commands.\n"
				+ "Note that some commands are temporarily invalid in some cases.");
	}
	
	public void displayPiece (Board board) {
		String numberString = Integer.toString(board.getPlayer(0).getPips());
		int numberSpaces = 4 - numberString.length();
		int numberUpLane = Math.max(board.getSize("upLane"),1);
		int numberDownLane = Math.max(board.getSize("downLane"),1);
		System.out.println("|---------------------------------------------------------------------|");
		if (board.getPlayer(0).getColourName() == "RED") {
			System.out.print("| Current player's color: " + DisplayColour.BOLD_RED + board.getPlayer(0).getColourName() + DisplayColour.RESET + "                               pips: " + board.getPlayer(0).getPips());
		} else if (board.getPlayer(0).getColourName() == "WHITE")
			System.out.print("| Current player's color: " + DisplayColour.BOLD_WHITE + board.getPlayer(0).getColourName() + DisplayColour.RESET + "                             pips: " + board.getPlayer(0).getPips());
		for (int i = 0; i < numberSpaces; i++)
            System.out.print(" ");
		System.out.println("|");
		System.out.println("|---------------------------------------------------------------------|");
		if (board.getDieFace(1) != board.getDieFace(2)) {
			System.out.println("| Dice:                     " + board.getDieFace(1) + "              " + board.getDieFace(2) + "                          |");
		}
		if (board.getDieFace(1) == board.getDieFace(2)) {
			System.out.println("| Dice:      " + board.getDieFace(1) + "              " + board.getDieFace(1) + "              " + board.getDieFace(1) + "              " + board.getDieFace(1)  + "           |");
		}
		System.out.println("|---------------------------------------------------------------------|");
		System.out.println("| " + DisplayColour.WHITE + "13   14   15   16   17   18" + DisplayColour.RESET + " | " + DisplayColour.WHITE + "B2" + DisplayColour.RESET + " | " + DisplayColour.WHITE + "19   20   21   22   23   24" + DisplayColour.RESET + " | " + DisplayColour.RED + "E1" + DisplayColour.RESET + " |");
		System.out.print("| " + DisplayColour.RED + "12   11   10   09   08   07" + DisplayColour.RESET);
		System.out.print(" | " + DisplayColour.WHITE + "B2"  + DisplayColour.RESET + " | ");
		System.out.println(DisplayColour.RED + "06   05   04   03   02   01" + DisplayColour.RESET + " | " + DisplayColour.RED + "E1" + DisplayColour.RESET + " |");
		
		for (int row = 0; row < numberUpLane; row++) {
			System.out.print("| ");
			for (int up = 11; up > 6; up--) {
				Stack<Piece> lane = board.getLane(up);
				if (row < lane.size()) {
					System.out.print(lane.get(row) + BLANK3);
		        } else
		            System.out.print(BLANK2 + BLANK3);
			}
			Stack<Piece> lane6 = board.getLane(6);
			if (row < lane6.size()) {
				System.out.print(lane6.get(row) + " | ");
	        } else
	            System.out.print(BLANK2 + " | ");
			Stack<Piece> bar = board.getBar(1);
				if (row < bar.size()) {
					System.out.print(bar.get(row) + " | ");
		        } else
		        	System.out.print(BLANK2 + " | ");
			for (int up = 5; up > 0; up--) {
				Stack<Piece> lane = board.getLane(up);
				if (row < lane.size()) {
					System.out.print(lane.get(row) + BLANK3);
		        } else
		            System.out.print(BLANK2 + BLANK3);
			}
			Stack<Piece> lane0 = board.getLane(0);
			if (row < lane0.size()) {
				System.out.print(lane0.get(row));
	        } else
	            System.out.print(BLANK2);
			if (row==0) {
				if (board.getEndpointSize(0) < 10)
			        System.out.print(" | " + DisplayColour.BOLD_RED + "0" + board.getEndpointSize(0) + DisplayColour.RESET + " |");
			    else
			    	System.out.print(" | " + DisplayColour.BOLD_RED + board.getEndpointSize(0) + DisplayColour.RESET + " |");
			} else
				System.out.print(" |    |");
			System.out.println();
		}
		System.out.println("|-----------------------------|----|-----------------------------|----|");
		for (int row = 0; row < numberDownLane; row++) {
			System.out.print("| ");
			for (int up = 12; up < 17; up++) {
				Stack<Piece> lane = board.getLane(up);
				if (row < numberDownLane - lane.size()) {
					System.out.print(BLANK2 + BLANK3);
		        } else
		            System.out.print(lane.get(numberDownLane - row - 1) + BLANK3);
			}
			Stack<Piece> lane17 = board.getLane(17);
			if (row < numberDownLane - lane17.size()) {
				System.out.print(BLANK2 + " | ");
	        } else
	            System.out.print(lane17.get(numberDownLane - row - 1) + " | ");
			Stack<Piece> bar = board.getBar(0);
				if (row < numberDownLane - bar.size()) {
					System.out.print(BLANK2 + " | ");
		        } else
		        	System.out.print(bar.get(numberDownLane - row - 1) + " | ");
			for (int up = 18; up < 23; up++) {
				Stack<Piece> lane = board.getLane(up);
				if (row < numberDownLane - lane.size()) {
					System.out.print(BLANK2 + BLANK3);
		        } else
		        	System.out.print(lane.get(numberDownLane - row - 1) + BLANK3);
			}
			Stack<Piece> lane23 = board.getLane(23);
			if (row < numberDownLane - lane23.size()) {
				System.out.print(BLANK2);
	        } else
	            System.out.print(lane23.get(numberDownLane - row - 1));
			if (row == numberDownLane - 1) {
				if (board.getEndpointSize(1) < 10)
			        System.out.print(" | " + DisplayColour.BOLD_WHITE + "0" + board.getEndpointSize(1) + DisplayColour.RESET + " |");
			    else
			    	System.out.print(" | " + DisplayColour.BOLD_WHITE + board.getEndpointSize(1) + DisplayColour.RESET + " |");
			} else
				System.out.print(" |    |");
			System.out.println();
		}
		
		System.out.print("| " + DisplayColour.WHITE + "12   11   10   09   08   07" + DisplayColour.RESET);
		System.out.print(" | " + DisplayColour.RED + "B1" + DisplayColour.RESET + " | ");
		System.out.println(DisplayColour.WHITE + "06   05   04   03   02   01" + DisplayColour.RESET + " | " + DisplayColour.WHITE + "E2" + DisplayColour.RESET + " |");
		System.out.println("| " + DisplayColour.RED + "13   14   15   16   17   18" + DisplayColour.RESET + " | " + DisplayColour.RED + "B1" + DisplayColour.RESET + " | " + DisplayColour.RED + "19   20   21   22   23   24" + DisplayColour.RESET + " | " + DisplayColour.WHITE + "E2" + DisplayColour.RESET + " |");
		System.out.println("|---------------------------------------------------------------------|");
	}
	
	public Command getCommand () {
		return command;
	}
	
	public Command getUserInput (Board board) {
		boolean commandEntered = false;
		do {
			System.out.print("Enter command: ");
			String input = in.nextLine();
			if (Command.isValid(input)) {
				if (Command.isText(input)) {
	                String fileName = Command.getText(input);
	                File dir = new File(".");
	                File[] exactMatches = dir.listFiles((dir1, name) -> name.equals(fileName));
	                File[] caseInsensitiveMatches = dir.listFiles((dir1, name) -> name.equalsIgnoreCase(fileName));
	                if ( !( (caseInsensitiveMatches != null && caseInsensitiveMatches.length > 0) && !(exactMatches != null && exactMatches.length > 0) ) ) {
		                try {
		                    BufferedReader br = new BufferedReader(new FileReader(fileName));
		                    StringBuilder fileContent = new StringBuilder();
		                    String line;
		                    while ((line = br.readLine()) != null)
		                        fileContent.append(line).append("\n");
		                    br.close();
		                    input = fileContent.toString();
		                } catch (FileNotFoundException e) {
		                    System.out.println("Error: File not found - " + e.getMessage());
		                } catch (IOException e) {
		                    System.out.println("Error reading file: " + e.getMessage());   
		                }
	                } else
	                	System.out.println("A file exists with only the case of the file name different from the input file name.");
                }
				command = new Command(input);
				if (board.getPlayer(0) == board.getPlayer(2) && command.isMove()) {
					String inputFormatted = input.trim();
					String[] inputs = new String[2];
					inputs[0] = inputFormatted.substring(0, 2);
					inputs[1] = inputFormatted.substring(2, 4);
					if (inputs[0].matches("\\d+")) {
						int number1 = Integer.parseInt(inputs[0]);
						int result1 = 25 - number1;
						inputs[0] = String.format("%02d", result1);
					}
					if (inputs[1].matches("\\d+")) {
						int number2 = Integer.parseInt(inputs[1]);
						int result2 = 25 - number2;
						inputs[1] = String.format("%02d", result2);
					}
					input = inputs[0] + inputs[1];
				}
				command = new Command(input);
				commandEntered = true;
			} else
				System.out.println("The command is invalid. Try again.");
		} while (!commandEntered);
		return command;
	}
	
	public void getUserName (Board board) {
		System.out.print("Enter Name of Player Red: ");
		board.initializePlayer1();
		System.out.println("The Name of Player Red is " + board.getPlayer(1));
		System.out.print("Enter Name of Player White: ");
		board.initializePlayer2();
		System.out.println("The Name of Player White is " + board.getPlayer(2));
		do {
			board.makeDieRoll();
			if (board.getDieFace(1) > board.getDieFace(2)) {
				System.out.println("Die 1 is " + board.getDieFace(1) + ". Die 2 is " + board.getDieFace(2)+ ". The number in Die 1 is bigger than the number in Die 2. So Red goes first.");
				board.setCurrentPlayer(1);
			} else if (board.getDieFace(1) < board.getDieFace(2)) {
				System.out.println("Die 1 is " + board.getDieFace(1) + ". Die 2 is " + board.getDieFace(2)+ ". The number in Die 2 is bigger than the number in Die 1. So White goes first.");
				board.setCurrentPlayer(2);
			} else if (board.getDieFace(1) == board.getDieFace(2)) {
				System.out.println("Die 1 is " + board.getDieFace(1) + ". Die 2 is " + board.getDieFace(2)+ ". The number in Die 1 is equal to the number in Die 2. So Reroll the dies.");
			}
		} while (board.getDieFace(1) == board.getDieFace(2));
	}
	
	public void displayCommandNotPossible () {
		System.out.println("That play is not possible. Try again.");
	}
	
	public void displayCommandTemporarilyInvalid () {
		System.out.println("The command is temporarily invalid. Try again.");
	}
	
	public void displayGameOver (Player player) {
		System.out.println(player.getNamewithColor() + " wins.");
		System.out.println("Game over.");
	}
	
	public void displayQuit () {
		System.out.println("Quit.");
	}
	
	public void playerTurn (Player player1, Player player2) {
		System.out.println(player1 + "(" + player1.getColourName() + ") finishes moving.");
		System.out.println("Now it's the " + player2 + "(" + player2.getColourName() + ")'s turn to play.");
	}
	
	public void showDice (int face1, int face2) {
		if (face1 != face2) {
			System.out.println("The number of 2 dice thrown are " + face1 + " and " + face2 + ". So please move 2 times.");
		} else if (face1 == face2)
			System.out.println("The number of 2 dice thrown are " + face1 + " and " + face2 + ", which means that the number of moveable dice is doubled because 2 dice rolled show the same number. So please move 4 times.");
	}
	
	public void displayWaive (Player player) {
		System.out.println(player + " forfeits the turn as he has no pieces to play legally or he doesn't know how to move the pieces further. Now skip to the other player's turn.");
	}
	
	public void displayPips (Board board) {
		System.out.println(board.getPlayer(1).getNamewithColor() + "'s current pips are " + board.getPlayer(1).getPips() + ".");
		System.out.println(board.getPlayer(2).getNamewithColor() + "'s current pips are " + board.getPlayer(2).getPips() + ".");
	}
	
	public void showHint () {
		System.out.println("S: Start Backgammon or restart Backgammon.");
		System.out.println("R: Roll the dice.");
		System.out.println("R + 2 digits: roll the specified number of dice.");
		System.out.println("W: Abandon the turn.");
		System.out.println("P: Check the current player's pips.");
		System.out.println("2 digits + 2 digits: Move a piece on Lane.");
		System.out.println("B + 1 digit + 2 digits: move pieces from Bar to outside.");
		System.out.println("2 digits + E + 1 digit: Move a piece to Terminus.");
		System.out.println("H: View all allowed commands.");
		System.out.println("Q: Quit the game.");
		System.out.println("If you type \"test:fileName.txt\", the game will read the commands in that file.");
	}
}
