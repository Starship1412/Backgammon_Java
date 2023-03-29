package backgammon;

/**
 * This class models a single piece.
 */

public class Piece {
	
	private PieceEntity pieceEntity;
	
	Piece (PieceEntity pieceEntity) {
		this.pieceEntity = pieceEntity;
	}
	
	public PieceEntity getPieceEntity () {
		return pieceEntity;
	}
	
	public String toString() {
		return pieceEntity.toString();
	}
}
