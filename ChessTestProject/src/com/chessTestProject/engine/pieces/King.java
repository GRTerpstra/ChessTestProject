// User-defined package.
package com.chessTestProject.engine.pieces;

// Imported user-defined classes.
import com.chessTestProject.engine.Alliance;
import com.chessTestProject.engine.board.Board;
import com.chessTestProject.engine.board.BoardUtils;
import com.chessTestProject.engine.board.Move;
import com.chessTestProject.engine.board.Tile;
import com.chessTestProject.engine.board.Move.AttackMove;
import com.chessTestProject.engine.board.Move.MajorMove;
import com.chessTestProject.engine.pieces.Piece.PieceType;

// Imported built-in classes.
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Class that creates a king and defines all the legal moves a king can make.
 * @author Gerwin Terpstra.
 * @version 1.0
 * @since 02-22-2021
 */
public class King extends Piece{
	// Declare and initialize constant member variables.
	private final static int[] CANDIDATE_MOVE_COORDINATE = {-9, -8, -7, -1, 1, 7, 8, 9};

	// Constructor.
	public King(final Alliance pieceAlliance,
				final int piecePosition) {
		super(PieceType.KING, pieceAlliance, piecePosition, true);
	}	
	public King(final Alliance pieceAlliance,
				final int piecePosition,
				final boolean isFirstMove) {
		super(PieceType.KING, pieceAlliance, piecePosition, isFirstMove);
}
	
	/**
	 * Method that defines and returns a list of all the legal moves a king can make distinguished by a normal move and an attacking move.
	 * @param final Board board the board where the piece is going to play on.
	 * @return List<Move> collection of all the legal moves a king can make.
	 */
	@Override
	public Collection<Move> calculateLegalMoves(Board board) {
		final List<Move> legalMoves = new ArrayList<>();	
		for(final int currentCandidateOffset : CANDIDATE_MOVE_COORDINATE) {			
			final int candidateDestinationCoordinate = this.piecePosition + currentCandidateOffset;
			if(isFirstColumnExclusion(this.piecePosition, currentCandidateOffset) || 
					isEightColumnExclusion(this.piecePosition, currentCandidateOffset)) {
				continue;
			}			
			if(BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)) {
				final Tile candidateDestinationTile = board.getTile(candidateDestinationCoordinate);			
				if(!candidateDestinationTile.isTileOccupied()) {
					legalMoves.add(new MajorMove(board, this, candidateDestinationCoordinate));					
				} else {					
					final Piece pieceAtDestination = candidateDestinationTile.getPiece();
					final Alliance pieceAlliance = pieceAtDestination.getPieceAlliance();					
					if(this.pieceAlliance != pieceAlliance) {
						legalMoves.add(new AttackMove(board, this, candidateDestinationCoordinate, 
								pieceAtDestination));
					}
				}
			}
		}
		return legalMoves;
	}	
	
	/**
	 * Overridden method that creates a new king from the given alliance and on the given coordinate.
	 * @param Move move an instance of Move.
	 * @return King a new instance of King.
	 */
	@Override
	public King movePiece(Move move) {
		return new King(move.getMovedPiece().getPieceAlliance(), move.getDestinationCoordinate());
	}
	
	/**
	 * Overridden method that returns a String representing a king piece.
	 * The value of the String is defined in the PieceType enum.
	 * @return String representing a king piece.
	 */
	@Override
	public String toString() {
		return PieceType.KING.toString();
	}
	
	/**
	 * Method that checks if the king is on the first column or not, and if the move that it wants to do is invalid from the first column.
	 * @param int currentPosition the coordinate of the current position of the king.
	 * @param int candidateOffset the number with which you indicate/calculate where the king has to move to.
	 * @return boolean true if the king wants to do an invalid move while being on the first column of the board, false otherwise.
	 */
	private static boolean isFirstColumnExclusion(final int currentPosition, final int candidateOffset) {		
		return BoardUtils.FIRST_COLUMN[currentPosition] && (candidateOffset == -9 || candidateOffset == -1 || 
				candidateOffset == 7); 
	}
		
	/**
	 * Method that checks if the king is on the eight column or not, and if the move that it wants to do is invalid from the eight column.
	 * @param int currentPosition the coordinate of the current position of the king.
	 * @param int candidateOffset the number with which you indicate/calculate where the king has to move to.
	 * @return boolean true if the king wants to do an invalid move while being on the eight column of the board, false otherwise.
	 */
	private static boolean isEightColumnExclusion(final int currentPosition, final int candidateOffset) {
		return BoardUtils.EIGHT_COLUMN[currentPosition] && (candidateOffset == -7 || candidateOffset == 1 ||
				candidateOffset == 9);
	}
}
