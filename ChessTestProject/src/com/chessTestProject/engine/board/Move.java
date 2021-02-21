// User-defined package
package com.chessTestProject.engine.board;

// imported user-defined packages.
import com.chessTestProject.engine.pieces.Piece;

/**
 * Abstract class that defines how pieces can move around the board.
 * 
 * @author Gerwin Terpstra.
 * @version 1.0.
 * @since 02-17-2021.
 */
public abstract class Move {

	// Declare and initializes variables. 
	final Board board;
	final Piece movedPiece;
	final int destinationCoordinate;
	
	// Constructor.
	private Move(final Board board, final Piece movedPiece, final int destinationCoordinate) {
		this.board = board;
		this.movedPiece = movedPiece;
		this.destinationCoordinate = destinationCoordinate;
	}
	
	/**
	 * Nested class that moves a piece around the board.
	 * @author Gerwin Terpstra
	 * @version 1.0
	 * @Since 02-18-2021
	 *
	 */
	public static final class MajorMove extends Move {
		public MajorMove(final Board board, final Piece movedPiece, final int destinationCoordinate) {
			super(board, movedPiece, destinationCoordinate);
		}
	}
	
	/**
	 * Nested class that attacks on a piece.
	 * @author Gerwin Terpstra
	 * @version 1.0
	 * @since 02-18-2021
	 */
	public static final class AttackMove extends Move {
		final Piece attackedPiece;
		public AttackMove(final Board board, final Piece movedPiece, final int destinationCoordinate, final Piece attackedPiece) {
			super(board, movedPiece, destinationCoordinate);
			this.attackedPiece = attackedPiece;
		}
	}
}
