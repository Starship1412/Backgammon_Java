package backgammon;

public class Player {
	
    private int pips, score;
    private String name;
    private PieceEntity pieceEntity;
    
    Player (String name, PieceEntity pieceEntity) { // Constructor: Initializes a new player with a name, piece entity, and default pips and score values.
        this.name = name;
        this.pieceEntity = pieceEntity;
        this.pips = 167;
        this.score = 0;
    }
    
    public String getNamewithColor () { // Returns the player's name with the associated piece color.
        return pieceEntity.getDisplay() + name + DisplayColour.RESET;
    }
    
    public PieceEntity getPieceEntity () { // Returns the player's piece entity.
        return pieceEntity;
    }
    
    public String getColourName () { // Returns the player's piece color name.
        return pieceEntity.getColour();
    }
    
    public int getPips () { // Returns the player's current pips value.
        return pips;
    }
    
    public void setPips (int pips) { // Sets the player's pips value.
        this.pips = pips;
    }
    
    public int getScore () { // Returns the player's current score.
    	return score;
    }
    
    public void addScore (int score) { // Adds the specified score to the player's current score.
    	this.score += score;
    }
    
    public void setScore (int score) { // Sets the player's score.
    	this.score = score;
    }
    
    public String toString () { // Returns a string representation of the player including their name, piece color, and pips.
        return pieceEntity.getDisplay() + name + DisplayColour.RESET + "<" + this.pips + ">pips";
    }
}
