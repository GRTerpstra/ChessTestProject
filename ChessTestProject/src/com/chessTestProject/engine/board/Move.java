// User-defined package
package com.chessTestProject.engine.board;

// imported user-defined package
import com.chessTestProject.engine.pieces.Piece;

/**
 * 
 * @author Gerwin Terpstra
 * @version 1.0
 * @since 02-17-2021
 */
public abstract class Move {

	final Board board;
	final Piece movedPiece;
	final int destinationCoordinate;
	
	private Move(final Board board, final Piece movedPiece, final int destinationCoordinate) {
		this.board = board;
		this.movedPiece = movedPiece;
		this.destinationCoordinate = destinationCoordinate;
	}
	
	public static final class MajorMove extends Move {
		public MajorMove(final Board board, final Piece movedPiece, final int destinationCoordinate) {
			super(board, movedPiece, destinationCoordinate);
		}
	}
		
	public static final class AttackMove extends Move {
		final Piece attackedPiece;
		public AttackMove(final Board board, final Piece movedPiece, final int destinationCoordinate, final Piece attackedPiece) {
			super(board, movedPiece, destinationCoordinate);
			this.attackedPiece = attackedPiece;
		}
	}
}
