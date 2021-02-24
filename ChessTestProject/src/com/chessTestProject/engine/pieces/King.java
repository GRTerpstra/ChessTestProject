// User-defined package.
package com.chessTestProject.engine.pieces;

// Imported user-defined packages.
import com.chessTestProject.engine.Alliance;
import com.chessTestProject.engine.board.Board;
import com.chessTestProject.engine.board.BoardUtils;
import com.chessTestProject.engine.board.Move;
import com.chessTestProject.engine.board.Tile;
import com.chessTestProject.engine.board.Move.AttackMove;
import com.chessTestProject.engine.board.Move.MajorMove;
import com.chessTestProject.engine.pieces.Piece.PieceType;

// Imported built-in packages.
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Class TODO
 * @author Gerwin Terpstra.
 * @version 1.0
 * @since 02-22-2021
 */
public class King extends Piece{
	
	// Declare and initialize constant member variable.
	private final static int[] CANDIDATE_MOVE_COORDINATE = {-9, -8, -7, -1, 1, 7, 8, 9};

	// Constructor.
	public King(final int piecePosition,final Alliance pieceAlliance) {
		super(PieceType.KING, piecePosition, pieceAlliance);
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
	
	@Override
	public King movePiece(Move move) {
		return new King(move.getDestinationCoordinate(), move.getMovedPiece().getPieceAlliance());
	}
	
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
