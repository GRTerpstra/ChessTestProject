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
 * Class that creates a rook and defines all the legal moves a rook can make.
 * @author Gerwin Terpstra.
 * @version 1.0.
 * @since 02-19-2021.
 */
public class Rook extends Piece {
	
	// Declare and initialize constant member variable.
	private final static int[] CANDIDATE_MOVE_VECTOR_COORDINATES = {-8, -1, 1, 8};	
	
	// Constructor.
	public Rook(final Alliance pieceAlliance,
				final int piecePosition) {
		super(PieceType.ROOK, pieceAlliance, piecePosition, true);
	}	
	public Rook(final Alliance pieceAlliance,
				final int piecePosition,
				final boolean isFirstMove) {
		super(PieceType.ROOK, pieceAlliance, piecePosition, isFirstMove);
	}
	
	/**
	 * Method that defines and returns a list of all the legal moves a rook can make distinguished by a normal move and an attacking move.
	 * @param final Board board the board where the piece is going to play on.
	 * @return List<Move> collection of all the legal moves a rook can make.
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
		// XXX Maybe immutable instead see video 7
		return legalMoves;
	}
	
	/**
	 * Overridden method that creates a new rook from the given alliance and on the given coordinate.
	 * @param Move move an instance of Move.
	 * @return Rook a new instance of Rook.
	 */
	@Override
	public Rook movePiece(Move move) {
		return new Rook(move.getMovedPiece().getPieceAlliance(), move.getDestinationCoordinate());
	}

	/**
	 * Overridden method that returns a String representing a rook piece.
	 * The value of the String is defined in the PieceType enum.
	 * @return String representing a rook piece.
	 */
	@Override
	public String toString() {
		return PieceType.ROOK.toString();
	}
	
	/**
	 * Method that checks if the rook is on the first column or not, and if the move that it wants to do is invalid from the first column.
	 * @param int currentPosition the coordinate of the current position of the rook.
	 * @param int candidateOffset the number with which you indicate/calculate where the rook has to move to.
	 * @return boolean true if the rook wants to do an invalid move while being on the first column of the board, false otherwise.
	 */
	private static boolean isFirstColumnExclusion(final int currentPosition, final int candidateOffset) {
		return BoardUtils.FIRST_COLUMN[currentPosition] && (candidateOffset == -1);
	}
	
	/**
	 * Method that checks if the rook is on the eight column or not, and if the move that it wants to do is invalid from the eight column.
	 * @param int currentPosition the coordinate of the current position of the rook.
	 * @param int candidateOffset the number with which you indicate/calculate where the rook has to move to.
	 * @return boolean true if the rook wants to do an invalid move while being on the eight column of the board, false otherwise.
	 */	
	private static boolean isEightColumnExclusion(final int currentPosition, final int candidateOffset) {
		return BoardUtils.EIGHT_COLUMN[currentPosition] && (candidateOffset == 1);
	}
}
