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
	
	private boolean isSamePieceEntity  (Piece piece) {
		return pieceEntity == piece.getPieceEntity();
	}
	
	private boolean isDifferentColour (Piece piece) {
		return this.pieceEntity.getColour() != piece.pieceEntity.getColour();
	}
	
	
	
	public PieceEntity getPieceEntity () {
		return pieceEntity;
	}
	
	public boolean isNextInLane (Piece piece) {
		return this.isDifferentColour(piece);
	}
	
	public boolean isNextInSuit (Piece piece) {
		return this.isSamePieceEntity(piece);
	}
	
	public String toString() {
		return pieceEntity.toString();
	}
}
