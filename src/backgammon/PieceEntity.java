package backgammon;

/**
 * This class enumerates the hues in pieces.
 */

public enum PieceEntity {
	
	R (DisplayColour.RED + " ●" + DisplayColour.RESET, DisplayColour.RED, "RED"),
	W (DisplayColour.WHITE + " ●" + DisplayColour.RESET, DisplayColour.WHITE, "WHITE");
	
	private String symbol, display, colour;
	
	PieceEntity (String symbol, String display, String colour) { // Constructor: Initializes a new piece entity with a symbol, display, and color.
		this.symbol = symbol;
		this.display = display;
		this.colour = colour;
	}
	
	public String getDisplay () { // Returns the display value of the piece entity.
		return display;
	}
	
	public String getColour () { // Returns the color value of the piece entity.
		return colour;
	}
	
	public String toString () { // Returns a string representation of the piece entity, including its symbol.
		return symbol;
	}
}
