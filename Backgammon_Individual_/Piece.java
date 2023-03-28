package backgammon;

/**
 * This class models a single card.
 */

public class Piece {
	
	public Suit suit;
	
	Piece () {
		suit = Suit.R;
	}
	
	Piece (Suit suit) {
		this.suit = suit;
	}
	
	private boolean isSameSuit  (Piece piece) {
		return suit == piece.getSuit();
	}
	
	private boolean isDifferentColour (Piece piece) {
		return this.suit.getColour() != piece.suit.getColour();
	}
	
	
	
	public Suit getSuit () {
		return suit;
	}
	
	public boolean isNextInLane (Piece piece) {
		return this.isDifferentColour(piece);
	}
	
	public boolean isNextInSuit (Piece piece) {
		return this.isSameSuit(piece);
	}
	
	public String toString() {
		return suit.toString();
	}
}
