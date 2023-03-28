package backgammon;

public class Player {
	
    private int pips;
    private String name;
    private Suit suit;
    private Board board;
    Die die = new Die();
    
    Player (String name, Suit suit) {
        this.name = name;
        this.suit = suit;
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
        return suit.getDisplay() + name + DisplayColour.RESET;
    }
    
    // Return player suit
    public Suit getSuit () {
        return suit;
    }
    
    public SuitColour getColourName () {
        return suit.getColour();
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
        return suit.getDisplay() + name + DisplayColour.RESET + "<" + this.pips + ">pips";
    }
}
