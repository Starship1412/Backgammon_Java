package backgammon;

import java.util.*;

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
	
	Board () {
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
	
	private int getMaxLaneOnInnerTable (List<Stack<Piece>> lanes, int index) {
		int maxLane = -1;
		if (index == 1) {
			for (int i = 0; i <= 5; i++) {
	            Stack<Piece> lanei = lanes.get(i);
	            if (!lanei.isEmpty()) {
	                maxLane = i;
	            }
	        }
		} else if (index == 2) {
			for (int i = 23; i <= 18; i--) {
	            Stack<Piece> lanei = lanes.get(i);
	            if (!lanei.isEmpty()) {
	                maxLane = i;
	            }
	        }
		}
		return maxLane;
	}
	
	
	
	public void initializePlayer (int index) {
		String playerName = in.nextLine();
		if (Command.isText(playerName))
			playerName = view.readContentFromFile(playerName, in, "Please enter a new player name: ");
		if (index == 1) {
			players[index] = new Player(playerName, PieceEntity.R);
		} else if (index == 2)
			players[index] = new Player(playerName, PieceEntity.W);
	}
	
	public void endTurn () {
        if (players[0] == players[1]) {
            players[0] = players[2];
            view.playerTurn(players[1], players[2]);
        } else if (players[0] == players[2]) {
        	players[0] = players[1];
            view.playerTurn(players[2], players[1]);
        }
    }
	
	public void initializeBoard () {
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
	
	public boolean moveIsPossible (Command command) {
		boolean isPossible = false;
		if (command.isMoveFromBar() && command.isMoveToLane()) {
			Stack<Piece> bar = bars.get(command.getFromIndex());
			Stack<Piece> lane = lanes.get(command.getToIndex());
			if (!bar.empty())
				if (bar.peek().getPieceEntity() == players[0].getPieceEntity() && (lane.empty() || lane.size() == 1 || bar.peek().getPieceEntity() == lane.peek().getPieceEntity()) && dice.getMoveNumber() != 0) {
					if (dice.getFace(1) != dice.getFace(2)) {
						for (int i = 1; i <= 2; i++) {
							if (dice.getMoveStep(i) != 0 && (players[0] == players[1] && command.getFromIndex() + 24 == command.getToIndex() + dice.getFace(i) || players[0] == players[2] && command.getFromIndex() + dice.getFace(i) == command.getToIndex() + 2)) {
								isPossible = true;
								dice.minusMoveStep(i);
							}
						}
						if (dice.getMoveStep(1) != 0 && dice.getMoveStep(2) != 0 && bar.size() == 1 &&
							(players[0] == players[1] && command.getFromIndex() + 24 == command.getToIndex() + dice.getFace(1) + dice.getFace(2) && (lanes.get(24 - dice.getFace(1)).empty() || lanes.get(24 - dice.getFace(2)).empty() || lanes.get(24 - dice.getFace(1)).peek().getPieceEntity() == players[1].getPieceEntity() || lanes.get(24 - dice.getFace(2)).peek().getPieceEntity() == players[1].getPieceEntity())
							|| players[0] == players[2] && command.getFromIndex() + dice.getFace(1) + dice.getFace(2) == command.getToIndex() + 2 && (lanes.get(dice.getFace(1) - 1).empty() || lanes.get(dice.getFace(2) - 1).empty() || lanes.get(dice.getFace(1) - 1).peek().getPieceEntity() == players[2].getPieceEntity() || lanes.get(dice.getFace(2) - 1).peek().getPieceEntity() == players[2].getPieceEntity()))) {
							isPossible = true;
							dice.minusMoveStep(1);
							dice.minusMoveStep(2);
						}
					}
					if (dice.getFace(1) == dice.getFace(2)) {
						boolean allConditionsMet = true;
						boolean currentCondition = true;
						if (dice.getMoveStep(1) != 0) {
							if (players[0] == players[1]) {
								if (command.getFromIndex() + 24 == command.getToIndex() + dice.getFace(1))
									if (lanes.get(24 - dice.getFace(1)).empty() || lanes.get(24 - dice.getFace(1)).size() == 1 || lanes.get(24 - dice.getFace(1)).peek().getPieceEntity() == players[1].getPieceEntity()) {
										isPossible = true;
										dice.minusMoveStep(1);
									}
								for (int i = 2; i <= dice.getMoveStep(1); i++) {
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
												dice.minusMoveStep(1);
										}
									}
								}
							}
							if (players[0] == players[2]) {
								if (command.getFromIndex() + dice.getFace(1) == command.getToIndex() + 2)
									if (lanes.get(dice.getFace(1) - 1).empty() || lanes.get(dice.getFace(1) - 1).size() == 1 || lanes.get(dice.getFace(1) - 1).peek().getPieceEntity() == players[2].getPieceEntity()) {
										isPossible = true;
										dice.minusMoveStep(1);
									}
								for (int i = 2; i <= dice.getMoveStep(1); i++) {
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
												dice.minusMoveStep(1);
										}
									}
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
				if (!lanes.get(i).empty()) {
					if (players[0] == players[1] && lanes.get(i).peek().getPieceEntity() == players[0].getPieceEntity()) {
						finalStage += lanes.get(i).size();
					}
				}
				if (!lanes.get(i+18).empty()) {
					if (players[0] == players[2] && lanes.get(i+18).peek().getPieceEntity() == players[0].getPieceEntity()) {
						finalStage += lanes.get(i+18).size();
					}
				}
			}
			if (!lane.empty())
				if (getPlayerNumber(players[0]) == command.getToIndex() && lane.peek().getPieceEntity() == players[0].getPieceEntity() && finalStage == 15 && dice.getMoveNumber() != 0) {
					int maxLane = -1;
					if (dice.getFace(1) != dice.getFace(2)) {
						if (players[0] == players[1]) {
							maxLane = getMaxLaneOnInnerTable(lanes, 1);
							for (int i = 1; i <= 2; i++) {
								if (command.getFromIndex() == maxLane && dice.getMoveStep(i) != 0 && command.getFromIndex() + 1 <= command.getToIndex() + dice.getFace(i)) {
									isPossible = true;
									dice.minusMoveStep(i);
								}
								if (command.getFromIndex() != maxLane && dice.getMoveStep(i) != 0 && command.getFromIndex() + 1 == command.getToIndex() + dice.getFace(i)) {
									isPossible = true;
									dice.minusMoveStep(i);
								}
							}
						}
						if (players[0] == players[2]) {
							maxLane = getMaxLaneOnInnerTable(lanes, 2);
							for (int i = 1; i <= 2; i++) {
								if (command.getFromIndex() == maxLane && dice.getMoveStep(i) != 0 && command.getFromIndex() + dice.getFace(i) >= command.getToIndex() + 23) {
									isPossible = true;
									dice.minusMoveStep(i);
								}
								if (command.getFromIndex() != maxLane && dice.getMoveStep(i) != 0 && command.getFromIndex() + dice.getFace(i) == command.getToIndex() + 23) {
									isPossible = true;
									dice.minusMoveStep(i);
								}
							}
						}
						if (dice.getMoveStep(1) == 0 && dice.getMoveStep(2) == 0) {
							if (dice.getFace(1) > dice.getFace(2)) {
								dice.addMoveStep(2);
							} else if (dice.getFace(1) < dice.getFace(2))
								dice.addMoveStep(1);
						}
					}
					if (dice.getFace(1) == dice.getFace(2)) {
						if (players[0] == players[1]) {
							maxLane = getMaxLaneOnInnerTable(lanes, 1);
							if (command.getFromIndex() == maxLane && dice.getMoveStep(1) != 0 && command.getFromIndex() + 1 <= command.getToIndex() + dice.getFace(1)) {
								isPossible = true;
								dice.minusMoveStep(1);
							}
							if (command.getFromIndex() != maxLane && dice.getMoveStep(1) != 0 && command.getFromIndex() + 1 == command.getToIndex() + dice.getFace(1)) {
								isPossible = true;
								dice.minusMoveStep(1);
							}
						}
						if (players[0] == players[2]) {
							maxLane = getMaxLaneOnInnerTable(lanes, 2);
							if (command.getFromIndex() == maxLane && dice.getMoveStep(1) != 0 && command.getFromIndex() + dice.getFace(1) >= command.getToIndex() + 23) {
								isPossible = true;
								dice.minusMoveStep(1);
							}
							if (command.getFromIndex() != maxLane && dice.getMoveStep(1) != 0 && command.getFromIndex() + dice.getFace(1) == command.getToIndex() + 23) {
								isPossible = true;
								dice.minusMoveStep(1);
							}
						}
					}
				}
		} else if (command.isMoveFromLane() && command.isMoveToLane()) {
			Stack<Piece> fromLane = lanes.get(command.getFromIndex());
			Stack<Piece> toLane = lanes.get(command.getToIndex());
			if (!fromLane.empty())
				if (bars.get(getPlayerNumber(players[0])).empty() && fromLane.peek().getPieceEntity() == players[0].getPieceEntity() && (toLane.empty() || toLane.size() == 1 || fromLane.peek().getPieceEntity() == toLane.peek().getPieceEntity()) && dice.getMoveNumber() != 0) {
					if (dice.getFace(1) != dice.getFace(2)) {
						for (int i = 1; i <= 2; i++) {
							if (dice.getMoveStep(i) != 0 && (players[0] == players[1] && command.getFromIndex() == command.getToIndex() + dice.getFace(i) || players[0] == players[2] && command.getFromIndex() + dice.getFace(i) == command.getToIndex())) {
								isPossible = true;
								dice.minusMoveStep(i);
							}
						}
						if (dice.getMoveStep(1) != 0 && dice.getMoveStep(2) != 0 &&
							(players[0] == players[1] && command.getFromIndex() == command.getToIndex() + dice.getFace(1) + dice.getFace(2) && (lanes.get(command.getFromIndex() - dice.getFace(1)).empty() || lanes.get(command.getFromIndex() - dice.getFace(2)).empty() || lanes.get(command.getFromIndex() - dice.getFace(1)).peek().getPieceEntity() == players[1].getPieceEntity() || lanes.get(command.getFromIndex() - dice.getFace(2)).peek().getPieceEntity() == players[1].getPieceEntity())
							|| players[0] == players[2] && command.getFromIndex() + dice.getFace(1) + dice.getFace(2) == command.getToIndex() && (lanes.get(command.getFromIndex() + dice.getFace(1)).empty() || lanes.get(command.getFromIndex() + dice.getFace(2)).empty() || lanes.get(command.getFromIndex() + dice.getFace(1)).peek().getPieceEntity() == players[2].getPieceEntity() || lanes.get(command.getFromIndex() + dice.getFace(2)).peek().getPieceEntity() == players[2].getPieceEntity()))) {
							isPossible = true;
							dice.minusMoveStep(1);
							dice.minusMoveStep(2);
						}
					}
					if (dice.getFace(1) == dice.getFace(2)) {
						boolean allConditionsMet = true;
						boolean currentCondition = true;
						if (dice.getMoveStep(1) != 0) {
							if (players[0] == players[1]) {
								if (command.getFromIndex() == command.getToIndex() + dice.getFace(1))
									if (lanes.get(command.getFromIndex() - dice.getFace(1)).empty() || lanes.get(command.getFromIndex() - dice.getFace(1)).size() == 1 || lanes.get(command.getFromIndex() - dice.getFace(1)).peek().getPieceEntity() == players[1].getPieceEntity()) {
										isPossible = true;
										dice.minusMoveStep(1);
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
												dice.minusMoveStep(1);
										}
									}
								}
							}
							if (players[0] == players[2]) {
								if (command.getFromIndex() + dice.getFace(1) == command.getToIndex())
									if (lanes.get(command.getFromIndex() + dice.getFace(1)).empty() || lanes.get(command.getFromIndex() + dice.getFace(1)).size() == 1 || lanes.get(command.getFromIndex() + dice.getFace(1)).peek().getPieceEntity() == players[2].getPieceEntity()) {
										isPossible = true;
										dice.minusMoveStep(1);
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
												dice.minusMoveStep(1);
										}
									}
								}
							}
						}
					}
				}
		}
		return isPossible;
	}
	
	public void move (Command command) {
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
	
	public void setFace (Command command) {
		dice.setFace(command.getFaceInput(1), command.getFaceInput(2));
	}
	
	public boolean isGameOver () {
		for (Stack<Piece> endpoint : endpoints)
			if (endpoint.size() == 15)
				return true;
		return false;
	}
	
	public int getSize (String index) {
		int upLaneSize = 0;
		int downLaneSize = 0;
		List<Stack<Piece>> up12Lanes = lanes.subList(0, 12);
		List<Stack<Piece>> down12Lanes = lanes.subList(12, 24);
		for (Stack<Piece> lane : up12Lanes) {
		    if (lane.size() > upLaneSize) {
		    	upLaneSize = lane.size();
		    }
		}
		if (bars.get(1).size() > upLaneSize) {
	    	upLaneSize = bars.get(1).size();
	    }
		for (Stack<Piece> lane : down12Lanes) {
		    if (lane.size() > downLaneSize) {
		    	downLaneSize = lane.size();
		    }
		}
		if (bars.get(0).size() > downLaneSize) {
	    	downLaneSize = bars.get(0).size();
	    }
		return switch(index) {
			case "upLane" -> upLaneSize;
			case "downLane" -> downLaneSize;
			default -> 0;
		};
	}
	
	public void calculatePips () {
		int pip1 = 0;
		int pip2 = 0;
   		for (int i=0; i<24; i++) {
   			if (!lanes.get(i).empty())
   				if (lanes.get(i).peek().getPieceEntity() == PieceEntity.R)
   					pip1 += (i+1)*lanes.get(i).size();
   			if (!lanes.get(i).empty())
   				if (lanes.get(i).peek().getPieceEntity() == PieceEntity.W)
   					pip2 += (24-i)*lanes.get(i).size();
   			if (endpoints.get(0).size() == 15)
   				pip1 = 0;
   			if (endpoints.get(1).size() == 15)
   				pip2 = 0;
   			players[1].setPips(pip1);
   			players[2].setPips(pip2);
   		}
	}
	
	public int getDiceFace (int index) {
		return switch (index) {
			case 1 -> dice.getFace(1);
			case 2 -> dice.getFace(2);
			default -> 0;
		};
	}
	
	public void makeDiceRoll () {
		dice.roll();
	}
	
	public int getDiceMoveNumber () {
		return dice.getMoveNumber();
	}
	
	public void makeDiceSetZero () {
		dice.setZero();
	}
	
    public int getPlayerNumber (Player player) {
    	if (players[0].getPieceEntity() == PieceEntity.W) {
    		return 1;
    	} else // players[0].getPieceEntity() == PieceEntity.R
    		return 0;
    }
    
    public Player getPlayer (int index) {
    	return switch (index) {
			case 0 -> players[0];
			case 1 -> players[1];
			case 2 -> players[2];
			default -> players[0];
    	};
    }
    
    public void setCurrentPlayer (int index) {
    	players[0] = players[index];
    }
	
	public Stack<Piece> getLane (int index) {
		return lanes.get(index);
	}
	
	public Stack<Piece> getBar (int index) {
		return bars.get(index);
	}
	
	public int getEndpointSize (int index) {
		return endpoints.get(index).size();
	}
}
