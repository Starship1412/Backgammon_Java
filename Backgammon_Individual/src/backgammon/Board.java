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
	private Die die;
	
	Board () {
		view = new View();
		in = new Scanner(System.in);
		die = new Die();
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
        } else {
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
				if ( (bar.peek().getPieceEntity() == players[0].getPieceEntity()) && ((lane.empty()) || lane.size() == 1 || bar.peek().getPieceEntity() == lane.peek().getPieceEntity()) && (die.getMoveNumber() != 0) ) {
					if (die.getFace(1) != die.getFace(2)) {
						if ( (players[0] == players[1]) && (die.getMoveStep(1) != 0) && (command.getFromIndex() + 24 - die.getFace(1) == command.getToIndex()) || (players[0] == players[2]) && (die.getMoveStep(1) != 0) && (command.getFromIndex() + die.getFace(1) - 2 == command.getToIndex()) ) {
							isPossible = true;
							die.minusMoveStep(1);
						}
						if ( (players[0] == players[1]) && (die.getMoveStep(2) != 0) && (command.getFromIndex() + 24 - die.getFace(2) == command.getToIndex()) || (players[0] == players[2]) && (die.getMoveStep(2) != 0) && (command.getFromIndex() + die.getFace(2) - 2 == command.getToIndex()) ) {
							isPossible = true;
							die.minusMoveStep(2);
						}
					}
					if (die.getFace(1) == die.getFace(2)) {
						if ( (players[0] == players[1]) && (die.getMoveStep(1) != 0) && (command.getFromIndex() + 24 - die.getFace(1) == command.getToIndex()) || (players[0] == players[2]) && (die.getMoveStep(1) != 0) && (command.getFromIndex() + die.getFace(1) - 2 == command.getToIndex()) ) {
							isPossible = true;
							die.minusMoveStep(1);
						}
					}
				}
		} else if (command.isMoveFromLane() && command.isMoveToEndpoint()) {
			Stack<Piece> lane = lanes.get(command.getFromIndex());
			Stack<Piece> endpoint = endpoints.get(command.getToIndex());
			int finalStage = endpoint.size();
			for (int i = 0; i < 6; i++) {
				if (!lanes.get(i).empty()) {
					if ( (players[0] == players[1]) && (lanes.get(i).peek().getPieceEntity() == players[0].getPieceEntity()) ) {
						finalStage += lanes.get(i).size();
					}
				}
				if (!lanes.get(i+18).empty()) {
					if ( (players[0] == players[2]) && (lanes.get(i+18).peek().getPieceEntity() == players[0].getPieceEntity()) ) {
						finalStage += lanes.get(i+18).size();
					}
				}
			}
			if (!lane.empty())
				if ( (getPlayerNumber(players[0]) == command.getToIndex()) && (lane.peek().getPieceEntity() == players[0].getPieceEntity()) && (finalStage == 15) && (die.getMoveNumber() != 0) ) {
					if (die.getFace(1) != die.getFace(2)) {
						int compare = die.getFace(1) - die.getFace(2);
						if ( (players[0] == players[1]) && (die.getMoveStep(1) != 0) && (command.getFromIndex() + 1 <= command.getToIndex() + die.getFace(1)) || (players[0] == players[2]) && (die.getMoveStep(1) != 0) && (command.getFromIndex() + die.getFace(1) >= command.getToIndex() + 23) ) {
							isPossible = true;
							die.minusMoveStep(1);
						}
						if ( (players[0] == players[1]) && (die.getMoveStep(2) != 0) && (command.getFromIndex() + 1 <= command.getToIndex() + die.getFace(2)) || (players[0] == players[2]) && (die.getMoveStep(2) != 0) && (command.getFromIndex() + die.getFace(2) >= command.getToIndex() + 23) ) {
							isPossible = true;
							die.minusMoveStep(2);
						}
						if (die.getMoveStep(1) == 0 && die.getMoveStep(2) == 0) {
							if (compare > 0) {
								die.addMoveStep(1);
							} else if (compare < 0)
								die.addMoveStep(2);
						}
					}
					if (die.getFace(1) == die.getFace(2)) {
						if ( (players[0] == players[1]) && (die.getMoveStep(1) != 0) && (command.getFromIndex() + 1 <= command.getToIndex() + die.getFace(1)) || (players[0] == players[2]) && (die.getMoveStep(1) != 0) && (command.getFromIndex() + die.getFace(1) >= command.getToIndex() + 23) ) {
							isPossible = true;
							die.minusMoveStep(1);
						}
					}
				}
		} else if (command.isMoveFromLane() && command.isMoveToLane()) {
			Stack<Piece> fromLane = lanes.get(command.getFromIndex());
			Stack<Piece> toLane = lanes.get(command.getToIndex());
			if (!fromLane.empty())
				if ( (bars.get(getPlayerNumber(players[0])).empty()) && (fromLane.peek().getPieceEntity() == players[0].getPieceEntity()) && ((toLane.empty()) || toLane.size() == 1 || fromLane.peek().getPieceEntity() == toLane.peek().getPieceEntity()) && (die.getMoveNumber() != 0) ) {
					if (die.getFace(1) != die.getFace(2)) {
						if ( (players[0] == players[1]) && (die.getMoveStep(1) != 0) && (command.getFromIndex() == command.getToIndex() + die.getFace(1)) || (players[0] == players[2]) && (die.getMoveStep(1) != 0) && (command.getFromIndex() + die.getFace(1) == command.getToIndex()) ) {
							isPossible = true;
							die.minusMoveStep(1);
						}
						if ( (players[0] == players[1]) && (die.getMoveStep(2) != 0) && (command.getFromIndex() == command.getToIndex() + die.getFace(2)) || (players[0] == players[2]) && (die.getMoveStep(2) != 0) && (command.getFromIndex() + die.getFace(2) == command.getToIndex()) ) {
							isPossible = true;
							die.minusMoveStep(2);
						}
					}
					if (die.getFace(1) == die.getFace(2)) {
						if ( (players[0] == players[1]) && (die.getMoveStep(1) != 0) && (command.getFromIndex() == command.getToIndex() + die.getFace(1)) || (players[0] == players[2]) && (die.getMoveStep(1) != 0) && (command.getFromIndex() + die.getFace(1) == command.getToIndex()) ) {
							isPossible = true;
							die.minusMoveStep(1);
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
			}
			else {
				Piece barPiece = bar.pop();
				lane.push(barPiece);
			}
			die.minusMoveNumber();
		} else if (command.isMoveFromLane() && command.isMoveToEndpoint()) {
			Stack<Piece> lane = lanes.get(command.getFromIndex());
			Stack<Piece> endpoint = endpoints.get(command.getToIndex());
			Piece lanePiece = lane.pop();
			endpoint.push(lanePiece);
			die.minusMoveNumber();
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
			die.minusMoveNumber();
		}
	}
	
	public void setFace (Command command) {
		die.setFace(command.getFaceInput(1), command.getFaceInput(2));
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
	
	public int getDieFace (int index) {
		return switch (index) {
			case 1 -> die.getFace(1);
			case 2 -> die.getFace(2);
			default -> 0;
		};
	}
	
	public void makeDieRoll () {
		die.roll();
	}
	
	public int getDieMoveNumber () {
		return die.getMoveNumber();
	}
	
	public void makeDieSetZero () {
		die.setZero();
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
