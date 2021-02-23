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

	// Declare and initializes member variables. 
	final Board board;
	final Piece movedPiece;
	final int destinationCoordinate;
	
	public static final Move NULL_MOVE = new NullMove();
	
	// Constructor.
	private Move(final Board board, 
			final Piece movedPiece, 
			final int destinationCoordinate) {
		this.board = board;
		this.movedPiece = movedPiece;
		this.destinationCoordinate = destinationCoordinate;
	}
	
	public int getCurrentCoordinate() {
		return this.movedPiece.getPiecePosition();
	}
	
	public int getDestinationCOordinate() {
		return this.destinationCoordinate;
	}
	
	public Piece getMovedPiece() {
		return this.movedPiece;
	}
	
	public Board execute() {
		final Board.Builder builder = new Board.Builder();			
		for(final Piece piece : this.board.currentPlayer().getActivePieces()) {
			// TODO Hashcode and equals for pieces 
			if(!this.movedPiece.equals(piece)) {
				builder.setPiece(piece);
			}
		}
		
		for(final Piece piece : this.board.currentPlayer().getOpponent().getActivePieces()) {
			builder.setPiece(piece);
		}
		//move the moved piece!
		builder.setPiece(this.movedPiece.movePiece(this));
		builder.setMoveMaker(this.board.currentPlayer().getOpponent().getAlliance());
		return builder.build();				
	}

	/**
	 * Nested class that moves a piece around the board.
	 * @author Gerwin Terpstra
	 * @version 1.0
	 * @Since 02-18-2021
	 *
	 */
	// Video 22
	public static final class MajorMove extends Move {
		public MajorMove(final Board board, 
				final Piece movedPiece, 
				final int destinationCoordinate) {
			super(board, movedPiece, destinationCoordinate);
		}
	}
	
	/**
	 * Nested class that attacks on a piece.
	 * @author Gerwin Terpstra
	 * @version 1.0
	 * @since 02-18-2021
	 */
	public static class AttackMove extends Move {
		final Piece attackedPiece;
		public AttackMove(final Board board, 
				final Piece movedPiece, 
				final int destinationCoordinate, 
				final Piece attackedPiece) {
			super(board, movedPiece, destinationCoordinate);
			this.attackedPiece = attackedPiece;
		}
		
		@Override
		public Board execute() {
			return null;
		}
	}
	
	public static final class PawnMove extends Move {
		public PawnMove(final Board board, 
						final Piece movedPiece, 
						final int destinationCoordinate) {
			super(board, movedPiece, destinationCoordinate);
		}
	}
	
	public static final class PawnAttackMove extends AttackMove {
		public PawnAttackMove(final Board board, 
								final Piece movedPiece, 
								final int destinationCoordinate,
								final Piece attackedPiece) {
			super(board, movedPiece, destinationCoordinate, attackedPiece);
		}
	}
	
	public static final class PawnEnPassantAttackMove extends AttackMove {
		public PawnEnPassantAttackMove(final Board board, 
								final Piece movedPiece, 
								final int destinationCoordinate,
								final Piece attackedPiece) {
			super(board, movedPiece, destinationCoordinate, attackedPiece);
		}
	}
	
	public static final class PawnJump extends Move {
		public PawnJump(final Board board, 
							final Piece movedPiece, 
							final int destinationCoordinate) {
			super(board, movedPiece, destinationCoordinate);
		}
	}
	
	static abstract class CastleMove extends Move {
		public CastleMove(final Board board, 
							final Piece movedPiece, 
							final int destinationCoordinate) {
			super(board, movedPiece, destinationCoordinate);
		}
	}
	
	public static final class KingSideCastleMove extends CastleMove {
		public KingSideCastleMove(final Board board, 
									final Piece movedPiece, 
									final int destinationCoordinate) {
			super(board, movedPiece, destinationCoordinate);
		}
	}
	
	public static final class QueenSideCastleMove extends CastleMove {
		public QueenSideCastleMove(final Board board, 
									final Piece movedPiece, 
									final int destinationCoordinate) {
			super(board, movedPiece, destinationCoordinate);
		}
	}

	public static final class NullMove extends Move {
		public NullMove() {
			super(null, null, -1);
		}
		
		@Override
		public Board execute() {
			throw new RuntimeException("cannot execute the null move!");
		}
	}
	
	public static class MoveFactory {
		private MoveFactory() {
			throw new RuntimeException("Not instantiable!");
		}
		
		public static Move createMove(final Board board,
										final int currentCoordinate,
										final int destinationCoordinate) {
			for(final Move move : board.getAllLegalMoves()) {
				if(move.getCurrentCoordinate() == currentCoordinate &&
						move.getDestinationCOordinate() == destinationCoordinate) {
					return move;
				}
			}
			return NULL_MOVE;
		}
	}
}
