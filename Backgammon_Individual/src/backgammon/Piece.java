package backgammon;

/**
 * This class models a single piece.
 */

public class Piece {
	
	private PieceEntity pieceEntity;
	
	Piece (PieceEntity pieceEntity) { // Constructor: Initializes a new piece with the specified piece entity.
		this.pieceEntity = pieceEntity;
	}
	
	public PieceEntity getPieceEntity () { // Returns the piece entity associated with this piece.
		return pieceEntity;
	}
	
	public String toString() { // Returns a string representation of the piece, including its piece entity symbol.
		return pieceEntity.toString();
	}
}
