package backgammon;

/**
 * This class enumerates the suits in a deck of playing cards.
 */

public enum Suit {
	
	R (DisplayColour.RED + " ●" + DisplayColour.RESET, SuitColour.RED, DisplayColour.RED),
	W (DisplayColour.WHITE + " ●" + DisplayColour.RESET, SuitColour.WHITE, DisplayColour.WHITE);
	
	private String symbol;
	private SuitColour colour;
	private String display;
	
	Suit (String symbol, SuitColour colour, String display) {
		this.symbol = symbol;
		this.colour = colour;
		this.display = display;
	}
	
	public SuitColour getColour () {
		return colour;
	}
	
	public String getDisplay () {
		return display;
	}
	
	public String toString () {
		return symbol;
	}
}
