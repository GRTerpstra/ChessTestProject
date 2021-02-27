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
 * Class that creates a queen and defines all the legal moves a queen can make.
 * @author Gerwin Terpstra.
 * @version 1.0.
 * @since 02-19-2021.
 */
public class Queen extends Piece{	
	
	// Declare and initialize constant member variable.
	private final static int[] CANDIDATE_MOVE_VECTOR_COORDINATES = {-9, -8, -7, -1, 1, 7, 8, 9};
	
	// Constructor.
	public Queen(final Alliance pieceAlliance,
					final int piecePosition) {
		super(PieceType.QUEEN, pieceAlliance, piecePosition, true);
	}	
	public Queen(final Alliance pieceAlliance,
					final int piecePosition,
					final boolean isFirstMove) {
		super(PieceType.QUEEN, pieceAlliance, piecePosition, isFirstMove);
	}

	/**
	 * Method that defines and returns a list of all the legal moves a queen can make distinguished by a normal move and an attacking move.
	 * @param final Board board the board where the piece is going to play on.
	 * @return List<Move> collection of all the legal moves a queen can make.
	 */
	@Override
	public Collection<Move> calculateLegalMoves(final Board board) {		
		final List<Move> legalMoves = new ArrayList<>();		
		for(final int candidateCoordinateOffset: CANDIDATE_MOVE_VECTOR_COORDINATES) {
			int candidateDestinationCoordinate = this.piecePosition;			
			while(BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)) {				
				if(isFirstColumnExclusion(candidateDestinationCoordinate, candidateCoordinateOffset) ||
						isEightColumnExclusion(candidateDestinationCoordinate, candidateCoordinateOffset)) {
					break;
				}				
				candidateDestinationCoordinate += candidateCoordinateOffset;				
				if(BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)) {
					final Tile candidateDestinationTile = board.getTile(candidateDestinationCoordinate);					
					if(!candidateDestinationTile.isTileOccupied()) {
						legalMoves.add(new MajorMove(board, this, candidateDestinationCoordinate));						
					} else {						
						final Piece pieceAtDestination = candidateDestinationTile.getPiece();
						final Alliance pieceAlliance = pieceAtDestination.getPieceAlliance();						
						if(this.pieceAlliance != pieceAlliance) {
							legalMoves.add(new AttackMove(board, this, candidateDestinationCoordinate, pieceAtDestination));
						}
						break;
					}
				}
			}			
		}		
		// !!! Maybe immutable instead see video 7 !!!
		return legalMoves;
	}
	
	/**
	 * Overridden method that creates a new queen from the given alliance and on the given coordinate.
	 * @param Move move an instance of Move.
	 * @return Queen a new instance of Queen.
	 */
	@Override
	public Queen movePiece(Move move) {
		return new Queen(move.getMovedPiece().getPieceAlliance(), move.getDestinationCoordinate());
	}
	
	/**
	 * Overridden method that returns a String representing a queen piece.
	 * The value of the String is defined in the PieceType enum.
	 * @return String representing a queen piece.
	 */
	@Override
	public String toString() {
		return PieceType.QUEEN.toString();
	}

	/**
	 * Method that checks if the queen is on the first column or not, and if the move that it wants to do is invalid from the first column.
	 * @param int currentPosition the coordinate of the current position of the queen.
	 * @param int candidateOffset the number with which you indicate/calculate where the queen has to move to.
	 * @return boolean true if the queen wants to do an invalid move while being on the first column of the board, false otherwise.
	 */
	private static boolean isFirstColumnExclusion(final int currentPosition, final int candidateOffset) {
		return BoardUtils.FIRST_COLUMN[currentPosition] && (candidateOffset == -1 || candidateOffset == -9 || candidateOffset == 7);
	}
	
	/**
	 * Method that checks if the queen is on the eight column or not, and if the move that it wants to do is invalid from the eight column.
	 * @param int currentPosition the coordinate of the current position of the queen.
	 * @param int candidateOffset the number with which you indicate/calculate where the queen has to move to.
	 * @return boolean true if the queen wants to do an invalid move while being on the eight column of the board, false otherwise.
	 */
	private static boolean isEightColumnExclusion(final int currentPosition, final int candidateOffset) {
		return BoardUtils.EIGHT_COLUMN[currentPosition] && (candidateOffset == -7 || candidateOffset == 1 || candidateOffset == 9);
	}
}
