package backgammon;

public class Player {
	
    private int pips;
    private String name;
    private PieceEntity pieceEntity;
    
    Player (String name, PieceEntity pieceEntity) {
        this.name = name;
        this.pieceEntity = pieceEntity;
        this.pips = 167;
    }
    
    Player (String name) {
        this.name = name;
    }
    
    // Return player name
    public String getName () {
        return name;
    }
    
    public String getNamewithColor () {
        return pieceEntity.getDisplay() + name + DisplayColour.RESET;
    }
    
    // Return player suit
    public PieceEntity getPieceEntity () {
        return pieceEntity;
    }
    
    public ColourName getColourName () {
        return pieceEntity.getColour();
    }
    
    // Return player pips
    public int getPips () {
        return pips;
    }
    
    // Set player pips.
    public void setPips (int pips) {
        this.pips = pips;
    }
    
    // Change pips by change (positive or negative)
    public void incrementPips (int change) {
        this.pips += change;
    }
    
    public String toString () {
        return pieceEntity.getDisplay() + name + DisplayColour.RESET + "<" + this.pips + ">pips";
    }
}
