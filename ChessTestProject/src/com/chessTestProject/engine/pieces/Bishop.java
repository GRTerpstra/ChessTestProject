// User-defined package.
package com.chessTestProject.engine.pieces;

// Imported user-defined classes.
import com.chessTestProject.engine.Alliance;
import com.chessTestProject.engine.board.Board;
import com.chessTestProject.engine.board.BoardUtils;
import com.chessTestProject.engine.board.Move;
import com.chessTestProject.engine.board.Tile;
import com.chessTestProject.engine.board.Move.MajorAttackMove;
import com.chessTestProject.engine.board.Move.AttackMove;
import com.chessTestProject.engine.board.Move.MajorMove;
import com.chessTestProject.engine.pieces.Piece.PieceType;

// Imported built-in classes.
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Class that creates a bishop and defines all the legal moves a bishop can make.
 * @author Gerwin Terpstra.
 * @version 1.0.
 * @since 02-19-2021.
 */
public class Bishop extends Piece {

	// declare and initialize constant member variables.
	private final static int[] CANDIDATE_MOVE_VECTOR_COORDINATES = {-9, -7, 7, 9};
	
	// Constructor.
	public Bishop(final Alliance pieceAlliance,
				final int piecePosition) {
		super(PieceType.BISHOP, pieceAlliance, piecePosition, true);
	}	
	public Bishop(final Alliance pieceAlliance,
				final int piecePosition,
				final boolean isFirstMove) {
		super(PieceType.BISHOP, pieceAlliance, piecePosition, isFirstMove);
}

	/**
	 * Method that defines and returns a list of valid moves a bishop can make distinguished by normal moves and attacking moves.
	 * @param final Board board the board where the piece is going to play on.
	 * @return List<Move> collection of all the legal moves the bishop can make from it's position.
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
							legalMoves.add(new MajorAttackMove(board, this, candidateDestinationCoordinate, pieceAtDestination));
						}
						break;
					}
				}
			}
		}
		return legalMoves;
	}
	
	/**
	 * Overridden method that returns a String representing a bishop piece.
	 * The value of the String is defined in the PieceType enum.
	 * @return String representing a bishop piece.
	 */
	@Override
	public String toString() {
		return PieceType.BISHOP.toString();
	}
	
	/**
	 * Method that checks if the bishop is on the first column or not, and if the move that it wants to do is invalid from the first column.
	 * @param int currentPosition the coordinate of the current position of the bishop.
	 * @param int candidateOffset the number with which you indicate/calculate where the bishop has to move to.
	 * @return boolean true if the bishop wants to do an invalid move while being on the first column of the board, false otherwise.
	 */
	private static boolean isFirstColumnExclusion(final int currentPosition, final int candidateOffset) {
		return BoardUtils.FIRST_COLUMN[currentPosition] && (candidateOffset == -9 || candidateOffset == 7);
	}
	
	/**
	 * Method that checks if the bishop is on the eight column or not, and if the move that it wants to do is invalid from the eight column.
	 * @param int currentPosition the coordinate of the current position of the bishop.
	 * @param int candidateOffset the number with which you indicate/calculate where the bishop has to move to.
	 * @return boolean true if the bishop wants to do an invalid move while being on the eight column of the board, false otherwise.
	 */
	private static boolean isEightColumnExclusion(final int currentPosition, final int candidateOffset) {
		return BoardUtils.EIGHT_COLUMN[currentPosition] && (candidateOffset == -7 || candidateOffset == 9);
	}

	/**
	 * Overridden method that creates a new bishop from the given alliance and on the given coordinate.
	 * @param Move move an instance of Move.
	 * @return Bishop a new instance of Bishop.
	 */
	@Override
	public Bishop movePiece(Move move) {
		return new Bishop(move.getMovedPiece().getPieceAlliance(), move.getDestinationCoordinate());
	}
}
