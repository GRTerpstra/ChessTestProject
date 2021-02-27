// User-defined package.
package com.chessTestProject.engine.pieces;

// Imported user-defined classes.
import com.chessTestProject.engine.board.BoardUtils;
import com.chessTestProject.engine.Alliance;
import com.chessTestProject.engine.board.Board;
import com.chessTestProject.engine.board.Move;
// !!! Have to use '*' because it's a nested class !!!
import com.chessTestProject.engine.board.Move.*;
import com.chessTestProject.engine.pieces.Piece.PieceType;
import com.chessTestProject.engine.board.Tile;

// Imported built-in classes.
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Class that creates a knight and defines all the legal moves a knight can make.
 * @author Gerwin Terpstra.
 * @version 1.0.
 * @since 02-19-2021.
 */
public class Knight extends Piece {	
	
	// Declare and initialize constant member variable.
	private final static int[] CANDIDATE_MOVE_COORDINATES = { -17, -15, -10, -6, 6, 10, 15, 17};

	// Constructor
	public Knight(final Alliance pieceAlliance,
					final int piecePosition) {
		super(PieceType.KNIGHT, pieceAlliance, piecePosition, true);
	}	
	public Knight(final Alliance pieceAlliance,
					final int piecePosition,
					final boolean isFirstMove) {
		super(PieceType.KNIGHT, pieceAlliance, piecePosition, isFirstMove);
	}
	
	/**
	 * Overriden method that defines and returns a list of all the legal moves a knight can make distinguished by a normal move and an attacking move.
	 * @param final Board board the board where the piece is going to play on.
	 * @return List<Move> collection of all the legal moves a knight can make.
	 */
	@Override
	public Collection<Move> calculateLegalMoves(final Board board) {		
		final List<Move> legalMoves = new ArrayList<>();		
		for(final int currentCandidateOffset : CANDIDATE_MOVE_COORDINATES) {			
			final int candidateDestinationCoordinate = this.piecePosition + currentCandidateOffset;			
			if(BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)) {				
				if(isFirstColumnExclusion(this.piecePosition, currentCandidateOffset) ||
					isSecondColumnExclusion(this.piecePosition, currentCandidateOffset) ||
					isSeventhColumnExclusion(this.piecePosition, currentCandidateOffset) ||
					isEightColumnExclusion(this.piecePosition, currentCandidateOffset)) {					
					continue;
				}				
				final Tile candidateDestinationTile = board.getTile(candidateDestinationCoordinate);				
				if(!candidateDestinationTile.isTileOccupied()) {
					legalMoves.add(new MajorMove(board, this, candidateDestinationCoordinate));					
				} else {					
					final Piece pieceAtDestination = candidateDestinationTile.getPiece();
					final Alliance pieceAlliance = pieceAtDestination.getPieceAlliance();					
					if(this.pieceAlliance != pieceAlliance) {
						legalMoves.add(new AttackMove(board, this, candidateDestinationCoordinate, pieceAtDestination));
					}
				}
			}
		}		
		// !!! Maybe immutable instead see video 4 !!!		
		return legalMoves;		
	}	
	
	/**
	 * Overridden method that creates a new knight from the given alliance and on the given coordinate.
	 * @param Move move an instance of Move.
	 * @return Knight a new instance of Knight.
	 */
	@Override
	public Knight movePiece(Move move) {
		return new Knight(move.getMovedPiece().getPieceAlliance(), move.getDestinationCoordinate());
	}
	
	/**
	 * Overridden method that returns a String representing a knight piece.
	 * The value of the String is defined in the PieceType enum.
	 * @return String representing a knight piece.
	 */
	@Override
	public String toString() {
		return PieceType.KNIGHT.toString();
	}
	
	/**
	 * Method that checks if the knight is on the second column or not, and if the move that it wants to do is invalid from the second column.
	 * @param int currentPosition the coordinate of the current position of the knight.
	 * @param int candidateOffset the number with which you indicate/calculate where the knight has to move to.
	 * @return boolean true if the knight wants to do an invalid move while being on the second column of the board, false otherwise.
	 */
	private static boolean isFirstColumnExclusion(final int currentPosition, final int candidateOffset) {		
		return BoardUtils.FIRST_COLUMN[currentPosition] && (candidateOffset == -17 || candidateOffset == -10 || 
				candidateOffset == 6 || candidateOffset == 15); 
	}
	
	/**
	 * Method that checks if the knight is on the second column or not, and if the move that it wants to do is invalid from the second column.
	 * @param int currentPosition the coordinate of the current position of the knight.
	 * @param int candidateOffset the number with which you indicate/calculate where the knight has to move to.
	 * @return boolean true if the knight wants to do an invalid move while being on the second column of the board, false otherwise.
	 */
	private static boolean isSecondColumnExclusion(final int currentPosition, final int candidateOffset) {
		return BoardUtils.SECOND_COLUMN[currentPosition] && (candidateOffset == -10 || candidateOffset == 6);
	}
	
	/**
	 * Method that checks if the knight is on the seventh column or not, and if the move that it wants to do is invalid from the seventh column.
	 * @param int currentPosition the coordinate of the current position of the knight.
	 * @param int candidateOffset the number with which you indicate/calculate where the knight has to move to.
	 * @return boolean true if the knight wants to do an invalid move while being on the seventh column of the board, false otherwise.
	 */
	private static boolean isSeventhColumnExclusion(final int currentPosition, final int candidateOffset) {
		return BoardUtils.SEVENTH_COLUMN[currentPosition] && (candidateOffset == -6 || candidateOffset == 10);
	}
	
	/**
	 * Method that checks if the knight is on the eight column or not, and if the move that it wants to do is invalid from the eight column.
	 * @param int currentPosition the coordinate of the current position of the knight.
	 * @param int candidateOffset the number with which you indicate/calculate where the knight has to move to.
	 * @return boolean true if the knight wants to do an invalid move while being on the eight column of the board, false otherwise.
	 */
	private static boolean isEightColumnExclusion(final int currentPosition, final int candidateOffset) {
		return BoardUtils.EIGHT_COLUMN[currentPosition] && (candidateOffset == -15 || candidateOffset == -6 || 
				candidateOffset == 10 || candidateOffset == 17);
		}
}
