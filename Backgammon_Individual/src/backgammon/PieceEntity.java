package backgammon;

/**
 * This class enumerates the suits in a deck of playing cards.
 */

public enum PieceEntity {
	
	R (DisplayColour.RED + " ●" + DisplayColour.RESET, ColourName.RED, DisplayColour.RED),
	W (DisplayColour.WHITE + " ●" + DisplayColour.RESET, ColourName.WHITE, DisplayColour.WHITE);
	
	private String symbol, display;
	private ColourName colour;
	
	PieceEntity (String symbol, ColourName colour, String display) {
		this.symbol = symbol;
		this.colour = colour;
		this.display = display;
	}
	
	public ColourName getColour () {
		return colour;
	}
	
	public String getDisplay () {
		return display;
	}
	
	public String toString () {
		return symbol;
	}
}
