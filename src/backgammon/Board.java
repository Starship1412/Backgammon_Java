package backgammon;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

/**
 * This class models the board.
 */

public class Board {
	
	public static final int NUM_LANES = 24;
	public static final int NUM_BARS = PieceEntity.values().length; // = 2
	public static final int NUM_TERMINUS = PieceEntity.values().length; // = 2
	
	private List<Stack<Piece>> lanes;
	private List<Stack<Piece>> bars;
	private List<Stack<Piece>> endpoints;
	
	private Player[] players;
	private View view;
	private Scanner in;
	private Dice dice;
	private int matchNumber;
	private int matchRoundNumber = 1;
	
	Board () { // Constructor: Initializes a new board object by arranging the game board with displays, inputs, dice, players and the kinds of positions of the pieces.
		view = new View();
		in = new Scanner(System.in);
		dice = new Dice();
		this.players = new Player[3]; // players[0] is current player. players[1] is RED player. players[2] is WHITE player.
		
		lanes = new ArrayList<>(NUM_LANES);
		bars = new ArrayList<>(NUM_BARS);
		endpoints = new ArrayList<>(NUM_TERMINUS);
		
		for (int i=0; i<NUM_LANES; i++)
			lanes.add(new Stack<>());
		for (int i=0; i<NUM_BARS; i++)
			bars.add(new Stack<>());
		for (int i=0; i<NUM_TERMINUS; i++)
			endpoints.add(new Stack<>());
	}
	
	private int getMaxLaneOnInnerTable (Command command, List<Stack<Piece>> lanes, int playerIndex) { // Get the maximum lane on the inner table
		int maxLane = -1;
		if (playerIndex == 1) {
			for (int i = 0; i <= 5; i++) {
	            Stack<Piece> lanei = lanes.get(i);
	            if (!lanei.isEmpty())
	                maxLane = i;
	        }
			if (command.getFromIndex() >= 6)
            	maxLane = command.getFromIndex();
		} else if (playerIndex == 2) {
			for (int i = 23; i >= 18; i--) {
	            Stack<Piece> lanei = lanes.get(i);
	            if (!lanei.isEmpty())
	                maxLane = i;
	        }
			if (command.getFromIndex() <= 17)
            	maxLane = command.getFromIndex();
		}
		return maxLane;
	}
	
	private boolean isPathClear (int start, int end) { // Check if the path is clear for a move
		if (start < 0)
	        start = 0;
		if (end > 23)
			end = 23;
	    for (int i = start + 1; i < end; i++) {
	        if (!lanes.get(i).empty() && lanes.get(i).peek().getPieceEntity() == players[0].getPieceEntity()) {
	            return false;
	        }
	    }
	    return true;
	}
	
	private int getPlayerNumber () { // Get the player number
    	if (players[0].getPieceEntity() == PieceEntity.W) {
    		return 1;
    	} else // players[0].getPieceEntity() == PieceEntity.R
    		return 0;
    }
	
	
	
	public Board (InputStream inputStream) { // Only for Test.
        this.in = new Scanner(inputStream);
        this.players = new Player[3];
    }
	
	public void initializePlayer (int playerIndex) { // Initialize a player with the given index
		String playerName = in.nextLine();
		if (Command.isText(playerName))
			playerName = view.readContentFromFile(playerName, in, "Please enter a new player name: ");
		if (playerIndex == 1) {
			players[playerIndex] = new Player(playerName, PieceEntity.R);
		} else if (playerIndex == 2)
			players[playerIndex] = new Player(playerName, PieceEntity.W);
	}
	
	public void endTurn () { // End the current player's turn
		if (!isOneMatchOver()) {
	        if (players[0] == players[1]) {
	            players[0] = players[2];
	            view.playerTurnCurrent(players[1]);
	            view.playerTurnNext(players[2]);
	        } else if (players[0] == players[2]) {
	        	players[0] = players[1];
	        	view.playerTurnCurrent(players[2]);
	            view.playerTurnNext(players[1]);
	        }
		} else if (isOneMatchOver()) {
			if (players[0] == players[1]) {
				view.playerTurnCurrent(players[1]);
			} else if (players[0] == players[2])
				view.playerTurnCurrent(players[2]);
		}
    }
	
	public void initializeBoard () { // Initialize the board
		for (int i = 0; i < 24; i++)
	        lanes.get(i).clear();
	    for (int i = 0; i < 2; i++)
	    	bars.get(i).clear();
	    for (int i = 0; i < 2; i++)
	    	endpoints.get(i).clear();
		for (int i = 0; i < 2; i++) {
			lanes.get(0).push(new Piece(PieceEntity.W));
			lanes.get(23).push(new Piece(PieceEntity.R));
		}
		for (int i = 0; i < 3; i++) {
			lanes.get(16).push(new Piece(PieceEntity.W));
			lanes.get(7).push(new Piece(PieceEntity.R));
		}
		for (int i = 0; i < 5; i++) {
			lanes.get(11).push(new Piece(PieceEntity.W));
			lanes.get(18).push(new Piece(PieceEntity.W));
			lanes.get(5).push(new Piece(PieceEntity.R));
			lanes.get(12).push(new Piece(PieceEntity.R));
		}
	}
	
	public boolean moveIsPossible (Command command) { // Check if a move is possible after giving a command
		boolean isPossible = false;
		if (command.isMoveFromBar() && command.isMoveToLane()) {
			Stack<Piece> bar = bars.get(command.getFromIndex());
			Stack<Piece> lane = lanes.get(command.getToIndex());
			if (!bar.empty())
				if (bar.peek().getPieceEntity() == players[0].getPieceEntity() && (lane.empty() || lane.size() == 1 || bar.peek().getPieceEntity() == lane.peek().getPieceEntity()) && dice.getMoveNumber() != 0) {
					if (dice.getFace(1) != dice.getFace(2)) {
						for (int i = 1; i <= 2; i++)
							if (dice.getMoveStep(i) != 0 && (players[0] == players[1] && command.getFromIndex() + 24 == command.getToIndex() + dice.getFace(i) || players[0] == players[2] && command.getFromIndex() + dice.getFace(i) == command.getToIndex() + 2)) {
								isPossible = true;
								dice.subtractMoveStep(i);
							}
						if (dice.getMoveStep(1) != 0 && dice.getMoveStep(2) != 0 && bar.size() == 1
						&& (players[0] == players[1] && command.getFromIndex() + 24 == command.getToIndex() + dice.getFace(1) + dice.getFace(2) && (lanes.get(24 - dice.getFace(1)).empty() || lanes.get(24 - dice.getFace(2)).empty() || lanes.get(24 - dice.getFace(1)).peek().getPieceEntity() == players[1].getPieceEntity() || lanes.get(24 - dice.getFace(2)).peek().getPieceEntity() == players[1].getPieceEntity())
						|| players[0] == players[2] && command.getFromIndex() + dice.getFace(1) + dice.getFace(2) == command.getToIndex() + 2 && (lanes.get(dice.getFace(1) - 1).empty() || lanes.get(dice.getFace(2) - 1).empty() || lanes.get(dice.getFace(1) - 1).peek().getPieceEntity() == players[2].getPieceEntity() || lanes.get(dice.getFace(2) - 1).peek().getPieceEntity() == players[2].getPieceEntity()))) {
							isPossible = true;
							dice.subtractMoveStep(1);
							dice.subtractMoveStep(2);
						}
					}
					if (dice.getFace(1) == dice.getFace(2) && dice.getMoveStep(1) != 0) {
					boolean allConditionsMet = true;
					boolean currentCondition = true;
						if (players[0] == players[1]) {
							if (command.getFromIndex() + 24 == command.getToIndex() + dice.getFace(1) && (lanes.get(24 - dice.getFace(1)).empty() || lanes.get(24 - dice.getFace(1)).size() == 1 || lanes.get(24 - dice.getFace(1)).peek().getPieceEntity() == players[1].getPieceEntity())) {
								isPossible = true;
								dice.subtractMoveStep(1);
							}
							for (int i = 2; i <= dice.getMoveStep(1); i++)
								if (command.getFromIndex() + 24 == command.getToIndex() + dice.getFace(1) * i && bar.size() == 1) {
									for (int j = 1; j <= i - 1; j++) {
										currentCondition = lanes.get(24 - dice.getFace(1) * j).empty() || lanes.get(24 - dice.getFace(1) * j).peek().getPieceEntity() == players[1].getPieceEntity();
										allConditionsMet = allConditionsMet && currentCondition;
									}
									currentCondition = lanes.get(24 - dice.getFace(1) * i).empty() || lanes.get(24 - dice.getFace(1) * i).peek().getPieceEntity() == players[1].getPieceEntity() || lanes.get(24 - dice.getFace(1) * i).size() == 1;
									allConditionsMet = allConditionsMet && currentCondition;
									if (allConditionsMet) {
										isPossible = true;
										for (int j = 0; j < i; j++)
											dice.subtractMoveStep(1);
									}
								}
						}
						if (players[0] == players[2]) {
							if (command.getFromIndex() + dice.getFace(1) == command.getToIndex() + 2 && (lanes.get(dice.getFace(1) - 1).empty() || lanes.get(dice.getFace(1) - 1).size() == 1 || lanes.get(dice.getFace(1) - 1).peek().getPieceEntity() == players[2].getPieceEntity())) {
								isPossible = true;
								dice.subtractMoveStep(1);
							}
							for (int i = 2; i <= dice.getMoveStep(1); i++)
								if (command.getFromIndex() + dice.getFace(1) * i == command.getToIndex() + 2 && bar.size() == 1) {
									for (int j = 1; j <= i - 1; j++) {
										currentCondition = lanes.get(dice.getFace(1) * j - 1).empty() || lanes.get(dice.getFace(1) * j - 1).peek().getPieceEntity() == players[2].getPieceEntity();
										allConditionsMet = allConditionsMet && currentCondition;
									}
									currentCondition = lanes.get(dice.getFace(1) * i - 1).empty() || lanes.get(dice.getFace(1) * i - 1).peek().getPieceEntity() == players[2].getPieceEntity() || lanes.get(dice.getFace(1) * i - 1).size() == 1;
									allConditionsMet = allConditionsMet && currentCondition;
									if (allConditionsMet) {
										isPossible = true;
										for (int j = 0; j < i; j++)
											dice.subtractMoveStep(1);
									}
								}
						}
					}
				}
		} else if (command.isMoveFromLane() && command.isMoveToEndpoint()) {
			Stack<Piece> lane = lanes.get(command.getFromIndex());
			Stack<Piece> endpoint = endpoints.get(command.getToIndex());
			int finalStage = endpoint.size();
			for (int i = 0; i < 6; i++) {
				if (!lanes.get(i).empty())
					if (players[0] == players[1] && lanes.get(i).peek().getPieceEntity() == players[0].getPieceEntity()) {
						finalStage += lanes.get(i).size();
						if (command.getFromIndex() >= 6)
							finalStage += 1;
					}
				if (!lanes.get(i+18).empty())
					if (players[0] == players[2] && lanes.get(i+18).peek().getPieceEntity() == players[0].getPieceEntity()) {
						finalStage += lanes.get(i+18).size();
						if (command.getFromIndex() <= 17)
							finalStage += 1;
					}
			}
			if (!lane.empty())
				if (getPlayerNumber() == command.getToIndex() && lane.peek().getPieceEntity() == players[0].getPieceEntity() && finalStage == 15 && dice.getMoveNumber() != 0) {
					int maxLane = -1;
					if (dice.getFace(1) != dice.getFace(2)) {
						int diceIndexToDecrement = -1;
						if (players[0] == players[1]) {
							maxLane = getMaxLaneOnInnerTable(command, lanes, 1);
							for (int i = 1; i <= 2; i++) {
								if (dice.getMoveStep(i) != 0 && command.getFromIndex() == maxLane && command.getFromIndex() + 1 < command.getToIndex() + dice.getFace(i) || dice.getMoveStep(i) != 0 && command.getFromIndex() + 1 == command.getToIndex() + dice.getFace(i)) {
									isPossible = true;
									if (diceIndexToDecrement == -1 || dice.getFace(i) > dice.getFace(diceIndexToDecrement))
							            diceIndexToDecrement = i;
								}
							}
							if (diceIndexToDecrement != -1)
							    dice.subtractMoveStep(diceIndexToDecrement);
							if (dice.getMoveStep(1) != 0 && dice.getMoveStep(2) != 0 && command.getFromIndex() + 1 > command.getToIndex() + dice.getFace(1) && command.getFromIndex() + 1 > command.getToIndex() + dice.getFace(2))
								if (command.getFromIndex() == maxLane && command.getFromIndex() + 1 < command.getToIndex() + dice.getFace(1) + dice.getFace(2)
								&& (lanes.get(command.getFromIndex() - dice.getFace(1)).empty() || lanes.get(command.getFromIndex() - dice.getFace(2)).empty() || lanes.get(command.getFromIndex() - dice.getFace(1)).peek().getPieceEntity() == players[1].getPieceEntity() || lanes.get(command.getFromIndex() - dice.getFace(2)).peek().getPieceEntity() == players[1].getPieceEntity())
								&& lane.size() == 1 && (isPathClear(command.getFromIndex() - dice.getFace(1), command.getFromIndex()) || isPathClear(command.getFromIndex() - dice.getFace(2), command.getFromIndex()))
								|| command.getFromIndex() + 1 == command.getToIndex() + dice.getFace(1) + dice.getFace(2)
								&& (lanes.get(command.getFromIndex() - dice.getFace(1)).empty() || lanes.get(command.getFromIndex() - dice.getFace(2)).empty() || lanes.get(command.getFromIndex() - dice.getFace(1)).peek().getPieceEntity() == players[1].getPieceEntity() || lanes.get(command.getFromIndex() - dice.getFace(2)).peek().getPieceEntity() == players[1].getPieceEntity())) {
									isPossible = true;
									dice.subtractMoveStep(1);
									dice.subtractMoveStep(2);
								}
						}
						if (players[0] == players[2]) {
							maxLane = getMaxLaneOnInnerTable(command, lanes, 2);
							for (int i = 1; i <= 2; i++) {
								if (dice.getMoveStep(i) != 0 && command.getFromIndex() == maxLane && command.getFromIndex() + dice.getFace(i) > command.getToIndex() + 23 || dice.getMoveStep(i) != 0 && command.getFromIndex() + dice.getFace(i) == command.getToIndex() + 23) {
									isPossible = true;
									if (diceIndexToDecrement == -1 || dice.getFace(i) > dice.getFace(diceIndexToDecrement))
							            diceIndexToDecrement = i;
								}
							}
							if (diceIndexToDecrement != -1)
							    dice.subtractMoveStep(diceIndexToDecrement);
							if (dice.getMoveStep(1) != 0 && dice.getMoveStep(2) != 0 && command.getFromIndex() + dice.getFace(1) < command.getToIndex() + 23 && command.getFromIndex() + dice.getFace(2) < command.getToIndex() + 23)
								if (command.getFromIndex() == maxLane && command.getFromIndex() + dice.getFace(1) + dice.getFace(2) > command.getToIndex() + 23
								&& (lanes.get(command.getFromIndex() + dice.getFace(1)).empty() || lanes.get(command.getFromIndex() + dice.getFace(2)).empty() || lanes.get(command.getFromIndex() + dice.getFace(1)).peek().getPieceEntity() == players[2].getPieceEntity() || lanes.get(command.getFromIndex() + dice.getFace(2)).peek().getPieceEntity() == players[2].getPieceEntity())
								&& lane.size() == 1 && (isPathClear(command.getFromIndex(), command.getFromIndex() + dice.getFace(1)) || isPathClear(command.getFromIndex(), command.getFromIndex() + dice.getFace(2)))
								|| command.getFromIndex() + dice.getFace(1) + dice.getFace(2) == command.getToIndex() + 23
								&& (lanes.get(command.getFromIndex() + dice.getFace(1)).empty() || lanes.get(command.getFromIndex() + dice.getFace(2)).empty() || lanes.get(command.getFromIndex() + dice.getFace(1)).peek().getPieceEntity() == players[2].getPieceEntity() || lanes.get(command.getFromIndex() + dice.getFace(2)).peek().getPieceEntity() == players[2].getPieceEntity())) {
									isPossible = true;
									dice.subtractMoveStep(1);
									dice.subtractMoveStep(2);
								}
						}
					}
					if (dice.getFace(1) == dice.getFace(2) && dice.getMoveStep(1) != 0) {
						boolean allConditionsMet = true;
						boolean currentCondition = true;
						boolean shouldBreak = false;
						if (players[0] == players[1]) {
							maxLane = getMaxLaneOnInnerTable(command, lanes, 1);
							if (command.getFromIndex() == maxLane && command.getFromIndex() + 1 < command.getToIndex() + dice.getFace(1) || command.getFromIndex() + 1 == command.getToIndex() + dice.getFace(1)) {
								isPossible = true;
								dice.subtractMoveStep(1);
							}
							for (int i = 2; i <= dice.getMoveStep(1) && !shouldBreak; i++)
								if (command.getFromIndex() == maxLane && command.getFromIndex() + 1 < command.getToIndex() + dice.getFace(1) * i) {
									for (int j = 1; j <= i - 1 && command.getFromIndex() - dice.getFace(1) * j >= 0; j++) {
										currentCondition = lanes.get(command.getFromIndex() - dice.getFace(1) * j).empty() || lanes.get(command.getFromIndex() - dice.getFace(1) * j).peek().getPieceEntity() == players[1].getPieceEntity();
										allConditionsMet = allConditionsMet && currentCondition;
									}
									currentCondition = lane.size() == 1 && isPathClear(command.getFromIndex() - dice.getFace(1) * (i - 1), command.getFromIndex());
									allConditionsMet = allConditionsMet && currentCondition;
									if (allConditionsMet) {
										isPossible = true;
										for (int j = 0; j < i; j++)
											dice.subtractMoveStep(1);
									}
									shouldBreak = true;
								} else if (command.getFromIndex() + 1 == command.getToIndex() + dice.getFace(1) * i) {
									for (int j = 1; j <= i - 1; j++) {
										currentCondition = lanes.get(command.getFromIndex() - dice.getFace(1) * j).empty() || lanes.get(command.getFromIndex() - dice.getFace(1) * j).peek().getPieceEntity() == players[1].getPieceEntity();
										allConditionsMet = allConditionsMet && currentCondition;
									}
									if (allConditionsMet) {
										isPossible = true;
										for (int j = 0; j < i; j++)
											dice.subtractMoveStep(1);
									}
								}
						}
						if (players[0] == players[2]) {
							maxLane = getMaxLaneOnInnerTable(command, lanes, 2);
							if (command.getFromIndex() == maxLane && command.getFromIndex() + dice.getFace(1) > command.getToIndex() + 23 || command.getFromIndex() + dice.getFace(1) == command.getToIndex() + 23) {
								isPossible = true;
								dice.subtractMoveStep(1);
							}
							for (int i = 2; i <= dice.getMoveStep(1) && !shouldBreak; i++)
								if (command.getFromIndex() == maxLane && command.getFromIndex() + dice.getFace(1) * i > command.getToIndex() + 23) {
									for (int j = 1; j <= i - 1 && command.getFromIndex() + dice.getFace(1) * j <= 23; j++) {
										currentCondition = lanes.get(command.getFromIndex() + dice.getFace(1) * j).empty() || lanes.get(command.getFromIndex() + dice.getFace(1) * j).peek().getPieceEntity() == players[2].getPieceEntity();
										allConditionsMet = allConditionsMet && currentCondition;
									}
									currentCondition = lane.size() == 1 && isPathClear(command.getFromIndex(), command.getFromIndex() + dice.getFace(1) * (i - 1));
									allConditionsMet = allConditionsMet && currentCondition;
									if (allConditionsMet) {
										isPossible = true;
										for (int j = 0; j < i; j++)
											dice.subtractMoveStep(1);
									}
									shouldBreak = true;
								} else if (command.getFromIndex() + dice.getFace(1) * i == command.getToIndex() + 23) {
									for (int j = 1; j <= i - 1; j++) {
										currentCondition = lanes.get(command.getFromIndex() + dice.getFace(1) * j).empty() || lanes.get(command.getFromIndex() + dice.getFace(1) * j).peek().getPieceEntity() == players[2].getPieceEntity();
										allConditionsMet = allConditionsMet && currentCondition;
									}
									if (allConditionsMet) {
										isPossible = true;
										for (int j = 0; j < i; j++)
											dice.subtractMoveStep(1);
									}
								}
						}
					}
				}
		} else if (command.isMoveFromLane() && command.isMoveToLane()) {
			Stack<Piece> fromLane = lanes.get(command.getFromIndex());
			Stack<Piece> toLane = lanes.get(command.getToIndex());
			if (!fromLane.empty())
				if (bars.get(getPlayerNumber()).empty() && fromLane.peek().getPieceEntity() == players[0].getPieceEntity() && (toLane.empty() || toLane.size() == 1 || fromLane.peek().getPieceEntity() == toLane.peek().getPieceEntity()) && dice.getMoveNumber() != 0) {
					if (dice.getFace(1) != dice.getFace(2)) {
						for (int i = 1; i <= 2; i++)
							if (dice.getMoveStep(i) != 0 && (players[0] == players[1] && command.getFromIndex() == command.getToIndex() + dice.getFace(i) || players[0] == players[2] && command.getFromIndex() + dice.getFace(i) == command.getToIndex())) {
								isPossible = true;
								dice.subtractMoveStep(i);
							}
						if (dice.getMoveStep(1) != 0 && dice.getMoveStep(2) != 0
						&& (players[0] == players[1] && command.getFromIndex() == command.getToIndex() + dice.getFace(1) + dice.getFace(2) && (lanes.get(command.getFromIndex() - dice.getFace(1)).empty() || lanes.get(command.getFromIndex() - dice.getFace(2)).empty() || lanes.get(command.getFromIndex() - dice.getFace(1)).peek().getPieceEntity() == players[1].getPieceEntity() || lanes.get(command.getFromIndex() - dice.getFace(2)).peek().getPieceEntity() == players[1].getPieceEntity())
						|| players[0] == players[2] && command.getFromIndex() + dice.getFace(1) + dice.getFace(2) == command.getToIndex() && (lanes.get(command.getFromIndex() + dice.getFace(1)).empty() || lanes.get(command.getFromIndex() + dice.getFace(2)).empty() || lanes.get(command.getFromIndex() + dice.getFace(1)).peek().getPieceEntity() == players[2].getPieceEntity() || lanes.get(command.getFromIndex() + dice.getFace(2)).peek().getPieceEntity() == players[2].getPieceEntity()))) {
							isPossible = true;
							dice.subtractMoveStep(1);
							dice.subtractMoveStep(2);
						}
					}
					if (dice.getFace(1) == dice.getFace(2) && dice.getMoveStep(1) != 0) {
						boolean allConditionsMet = true;
						boolean currentCondition = true;
						if (players[0] == players[1]) {
							if (command.getFromIndex() == command.getToIndex() + dice.getFace(1) && (lanes.get(command.getFromIndex() - dice.getFace(1)).empty() || lanes.get(command.getFromIndex() - dice.getFace(1)).size() == 1 || lanes.get(command.getFromIndex() - dice.getFace(1)).peek().getPieceEntity() == players[1].getPieceEntity())) {
								isPossible = true;
								dice.subtractMoveStep(1);
							}
							for (int i = 2; i <= dice.getMoveStep(1); i++) {
								if (command.getFromIndex() == command.getToIndex() + dice.getFace(1) * i) {
									for (int j = 1; j <= i - 1; j++) {
										currentCondition = lanes.get(command.getFromIndex() - dice.getFace(1) * j).empty() || lanes.get(command.getFromIndex() - dice.getFace(1) * j).peek().getPieceEntity() == players[1].getPieceEntity();
										allConditionsMet = allConditionsMet && currentCondition;
									}
									currentCondition = lanes.get(command.getFromIndex() - dice.getFace(1) * i).empty() || lanes.get(command.getFromIndex() - dice.getFace(1) * i).peek().getPieceEntity() == players[1].getPieceEntity() || lanes.get(command.getFromIndex() - dice.getFace(1) * i).size() == 1;
									allConditionsMet = allConditionsMet && currentCondition;
									if (allConditionsMet) {
										isPossible = true;
										for (int j = 0; j < i; j++)
											dice.subtractMoveStep(1);
									}
								}
							}
						}
						if (players[0] == players[2]) {
							if (command.getFromIndex() + dice.getFace(1) == command.getToIndex() && (lanes.get(command.getFromIndex() + dice.getFace(1)).empty() || lanes.get(command.getFromIndex() + dice.getFace(1)).size() == 1 || lanes.get(command.getFromIndex() + dice.getFace(1)).peek().getPieceEntity() == players[2].getPieceEntity())) {
								isPossible = true;
								dice.subtractMoveStep(1);
							}
							for (int i = 2; i <= dice.getMoveStep(1); i++) {
								if (command.getFromIndex() + dice.getFace(1) * i == command.getToIndex()) {
									for (int j = 1; j <= i - 1; j++) {
										currentCondition = lanes.get(command.getFromIndex() + dice.getFace(1) * j).empty() || lanes.get(command.getFromIndex() + dice.getFace(1) * j).peek().getPieceEntity() == players[2].getPieceEntity();
										allConditionsMet = allConditionsMet && currentCondition;
									}
									currentCondition = lanes.get(command.getFromIndex() + dice.getFace(1) * i).empty() || lanes.get(command.getFromIndex() + dice.getFace(1) * i).peek().getPieceEntity() == players[2].getPieceEntity() || lanes.get(command.getFromIndex() + dice.getFace(1) * i).size() == 1;
									allConditionsMet = allConditionsMet && currentCondition;
									if (allConditionsMet) {
										isPossible = true;
										for (int j = 0; j < i; j++)
											dice.subtractMoveStep(1);
									}
								}
							}
						}
					}
				}
		}
		return isPossible;
	}
	
	public void move (Command command) { // Move a piece on the board according to the given command
		if (command.isMoveFromBar() && command.isMoveToLane()) {
			Stack<Piece> bar = bars.get(command.getFromIndex());
			Stack<Piece> lane = lanes.get(command.getToIndex());
			if (lane.size() == 1 && bar.peek().getPieceEntity() != lane.peek().getPieceEntity()) {
				Piece barPiece = bar.pop();
				if (lane.peek().getPieceEntity() == PieceEntity.W) {
					Piece lanePiece = lane.pop();
					bars.get(1).push(lanePiece);
				} else if (lane.peek().getPieceEntity() == PieceEntity.R) {
					Piece lanePiece = lane.pop();
					bars.get(0).push(lanePiece);
				}
				lane.push(barPiece);
			} else {
				Piece barPiece = bar.pop();
				lane.push(barPiece);
			}
		} else if (command.isMoveFromLane() && command.isMoveToEndpoint()) {
			Stack<Piece> lane = lanes.get(command.getFromIndex());
			Stack<Piece> endpoint = endpoints.get(command.getToIndex());
			Piece lanePiece = lane.pop();
			endpoint.push(lanePiece);
		} else if (command.isMoveFromLane() && command.isMoveToLane()) {
			Stack<Piece> fromLane = lanes.get(command.getFromIndex());
			Stack<Piece> toLane = lanes.get(command.getToIndex());
			if (toLane.size() == 1 && fromLane.peek().getPieceEntity() != toLane.peek().getPieceEntity()) {
				Piece fromLanePiece = fromLane.pop();
				if (toLane.peek().getPieceEntity() == PieceEntity.W) {
					Piece toLanePiece = toLane.pop();
					bars.get(1).push(toLanePiece);
				} else if (toLane.peek().getPieceEntity() == PieceEntity.R) {
					Piece toLanePiece = toLane.pop();
					bars.get(0).push(toLanePiece);
				}
				toLane.push(fromLanePiece);
			} else {
				Piece fromLanePiece = fromLane.pop();
				toLane.push(fromLanePiece);
			}
		}
	}
	
	public void setDiceFace (Command command) { // Set the dice face according to the given command
		dice.setFace(command.getFaceInput(1), command.getFaceInput(2));
	}
	
	public int getDiceFace (int index) { // Get the dice face for the given index
		return switch (index) {
			case 1 -> dice.getFace(1);
			case 2 -> dice.getFace(2);
			default -> 0;
		};
	}
	
	public boolean isOneMatchOver () { // Check if one match in the game is over
		for (Stack<Piece> endpoint : endpoints)
			if (endpoint.size() == 15)
				return true;
		return false;
	}
	
	public boolean isWholeMatchOver () { // Check if the whole match in the game is over
		return matchNumber + 1 == matchRoundNumber;
	}
	
	public int getSize (String index) { // Get the size of the largest stack of pieces for the specified index (upLane or downLane)
		int upLaneSize = 0;
		int downLaneSize = 0;
		List<Stack<Piece>> up12Lanes = lanes.subList(0, 12);
		List<Stack<Piece>> down12Lanes = lanes.subList(12, 24);
		for (Stack<Piece> lane : up12Lanes)
		    if (lane.size() > upLaneSize)
		    	upLaneSize = lane.size();
		if (bars.get(1).size() > upLaneSize)
	    	upLaneSize = bars.get(1).size();
		for (Stack<Piece> lane : down12Lanes)
		    if (lane.size() > downLaneSize)
		    	downLaneSize = lane.size();
		if (bars.get(0).size() > downLaneSize)
	    	downLaneSize = bars.get(0).size();
		return switch(index) {
			case "upLane" -> upLaneSize;
			case "downLane" -> downLaneSize;
			default -> 0;
		};
	}
	
	public void calculateSetPips () { // Calculate the number of pips for each player
		int pip1 = 0;
		int pip2 = 0;
   		for (int i=0; i<24; i++) {
   			if (!lanes.get(i).empty())
   				if (lanes.get(i).peek().getPieceEntity() == PieceEntity.R) {
   					pip1 += (i+1)*lanes.get(i).size();
   				} else if (lanes.get(i).peek().getPieceEntity() == PieceEntity.W)
   					pip2 += (24-i)*lanes.get(i).size();
   			if (endpoints.get(0).size() == 15)
   				pip1 = 0;
   			if (endpoints.get(1).size() == 15)
   				pip2 = 0;
   			players[1].setPips(pip1);
   			players[2].setPips(pip2);
   		}
	}
	
	public void makeDiceRoll () { // Roll the dice
		dice.roll();
	}
	
	public int getDiceMoveNumber () { // Get the total distance number that pieces can be moved
		return dice.getMoveNumber();
	}
	
	public void makeDiceSetZero () { // Set the dice values to zero
		dice.setZero();
	}
    
    public Player getPlayer (int index) { // Get the player object for the given index
    	return switch (index) {
			case 0 -> players[0];
			case 1 -> players[1];
			case 2 -> players[2];
			default -> players[0];
    	};
    }
    
    public void setCurrentPlayer (int playerIndex) { // Set the current player using the given player index
    	players[0] = players[playerIndex];
    }
	
	public Stack<Piece> getLane (int index) { // Get the lane stack at the specified index
		return lanes.get(index);
	}
	
	public Stack<Piece> getBar (int index) { // Get the bar stack at the specified index
		return bars.get(index);
	}
	
	public Stack<Piece> getEndpoint (int index) { // Get the endpoint stack at the specified index
		return endpoints.get(index);
	}
	
	public int getMatchNumber () { // Get the current match number
		return matchNumber;
	}
	
	public void setMatchNumber (int matchNumber) { // Set the match number
		this.matchNumber = matchNumber;
	}
	
	public int getMatchRoundNumber () { // Get the match round number
		return matchRoundNumber;
	}
	
	public void setMatchRoundNumber (int matchRoundNumber) { // Set the match round number
		this.matchRoundNumber = matchRoundNumber;
	}
	
	public void addMatchRoundNumber () { // Increment the match round number
		matchRoundNumber++;
	}
	
	public void setPlayersScoreToZero () { // Set both players' scores to zero
		players[1].setScore(0);
		players[2].setScore(0);
	}
	
	public void addCurrentPlayerScore () { // Add score to the current player
		players[0].addScore(10);
	}
	
	public int getDiceMoveStep (int index) { // Get the move step of the dice for the given index
    	return switch (index) {
			case 1 -> dice.getMoveStep(1);
			case 2 -> dice.getMoveStep(2);
			default -> 0;
		};
    }
	
	public void setDiceMoveStep (int moveStep1, int moveStep2) { // Set the move steps by the given number of dice
		dice.setMoveStep(moveStep1, moveStep2);
	}
	
	public void setPlayer(int index, Player player) { // Only for Test. Set the player object at the given index.
	    if (index >= 0 && index < players.length) {
	        players[index] = player;
	    }
	}
	
	public int getPieceCountAtLane(int laneIndex) { // Get the number of pieces at the specified lane index
	    return lanes.get(laneIndex).size();
	}
	
	@Override
	public boolean equals(Object o) { // Only for Test. Check if two board objects are equal.
	    if (this == o) return true;
	    if (o == null || getClass() != o.getClass()) return false;
	    Board board = (Board) o;
	    boolean lanesEqual = true;
	    boolean barsEqual = true;
	    boolean endpointsEqual = true;
	    for (int i = 0; i < 24; i++) {
	        if (!this.getLane(i).equals(board.getLane(i))) {
	            lanesEqual = false;
	            break;
	        }
	    }
	    for (int i = 0; i < 2; i++) {
	        if (!this.getBar(i).equals(board.getBar(i))) {
	            barsEqual = false;
	            break;
	        }
	    }
	    for (int i = 0; i < 2; i++) {
	        if (!this.getEndpoint(i).equals(board.getEndpoint(i))) {
	            endpointsEqual = false;
	            break;
	        }
	    }
	    return lanesEqual && barsEqual && endpointsEqual;
	}
}
