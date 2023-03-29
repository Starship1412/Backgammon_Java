package backgammon;

/**
 * This class models a single card.
 */

public class Piece {
	
	private PieceEntity pieceEntity;
	
	Piece () {
		pieceEntity = PieceEntity.R;
	}
	
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
