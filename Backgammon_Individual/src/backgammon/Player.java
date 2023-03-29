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
    
    public String getNamewithColor () {
        return pieceEntity.getDisplay() + name + DisplayColour.RESET;
    }
    
    public PieceEntity getPieceEntity () {
        return pieceEntity;
    }
    
    public String getColourName () {
        return pieceEntity.getColour();
    }
    
    public int getPips () {
        return pips;
    }
    
    public void setPips (int pips) {
        this.pips = pips;
    }
    
    public String toString () {
        return pieceEntity.getDisplay() + name + DisplayColour.RESET + "<" + this.pips + ">pips";
    }
}
