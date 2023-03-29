package backgammon;

/**
 * This class enumerates the suits in a deck of playing cards.
 */

public enum PieceEntity {
	
	R (DisplayColour.RED + " ●" + DisplayColour.RESET, DisplayColour.RED, "RED"),
	W (DisplayColour.WHITE + " ●" + DisplayColour.RESET, DisplayColour.WHITE, "WHITE");
	
	private String symbol, display, colour;
	
	PieceEntity (String symbol, String display, String colour) {
		this.symbol = symbol;
		this.display = display;
		this.colour = colour;
	}
	
	public String getDisplay () {
		return display;
	}
	
	public String getColour () {
		return colour;
	}
	
	public String toString () {
		return symbol;
	}
}
