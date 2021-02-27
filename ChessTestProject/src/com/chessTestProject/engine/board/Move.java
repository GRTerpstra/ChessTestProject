// User-defined package
package com.chessTestProject.engine.board;

// Imported user-defined classes.
import com.chessTestProject.engine.board.Board.Builder;
import com.chessTestProject.engine.pieces.Pawn;
import com.chessTestProject.engine.pieces.Piece;
import com.chessTestProject.engine.pieces.Rook;

/**
 * Abstract class that defines how pieces can move around the board.
 * @author Gerwin Terpstra.
 * @version 1.0.
 * @since 02-17-2021.
 */
public abstract class Move {
	// Declare member variables. 
	protected final Board board;
	protected final Piece movedPiece;
	protected final int destinationCoordinate;
	protected final boolean isFirstMove;
	
	// Declare and initializes member constant variables. 
	public static final Move NULL_MOVE = new NullMove();
	
	// Constructor.
	private Move(final Board board, 
					final Piece movedPiece, 
					final int destinationCoordinate) {
		
		// Initialize member variables.
		this.board = board;
		this.movedPiece = movedPiece;
		this.destinationCoordinate = destinationCoordinate;
		this.isFirstMove = movedPiece.isFirstMove();
	}
	
	private Move(final Board board,
					final int destinationCoordinate) {
		this.board = board;
		this.destinationCoordinate = destinationCoordinate;
		this.movedPiece = null;
		this.isFirstMove = false;
	}
	
	/**
	 * Overridden method that generates an ID of a move based on its values.
	 * @return int result the ID of the Move object.
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + this.destinationCoordinate;
		result = prime * result + this.movedPiece.hashCode();
		result = prime * result + this.movedPiece.getPiecePosition();
		return result;
	}
	
	/**
	 * Overridden method that checks if a given object has the same object values of this move or not.
	 * The Object.equals() method gets overridden here because it's needed to check for object value equality here,
	 * not object reference equality.
	 * @param Object other the object we want to compare to this Move.
	 * @return boolean true if the objects have the same value, false otherwise.
	 */
	@Override
	public boolean equals(final Object other) {
		if(this == other) {
			return true;
		}
		if(!(other instanceof Move)) {
			return false;
		}
		final Move otherMove = (Move) other;
		return getCurrentCoordinate() == otherMove.getCurrentCoordinate() &&
				getDestinationCoordinate() == otherMove.getDestinationCoordinate() &&
				getMovedPiece().equals(otherMove.getMovedPiece());
		
	}
	
	public Board getBoard() {
		return this.board;
	}
	
	/**
	 * Method that returns the current coordinate of the piece that is going to be moved.
	 * @return int the current coordinate of the piece that is being moved.
	 */
	public int getCurrentCoordinate() {
		return this.getMovedPiece().getPiecePosition();
	}
	
	/**
	 * Method that returns the destination coordinate of the piece that is going to be moved.
	 * @return int the destination coordinate of the piece that is being moved.
	 */
	public int getDestinationCoordinate() {
		return this.destinationCoordinate;
	}
	
	/**
	 * Method that returns the piece that is going to be moved.
	 * @return Piece the piece that is being moved.
	 */
	public Piece getMovedPiece() {
		return this.movedPiece;
	}
	
	/**
	 * Helper method that returns a boolean false by default.
	 * @return boolean false.
	 */
	public boolean isAttack() {
		return false;
	}
	
	/**
	 * Helper method that returns a boolean false by default.
	 * @return boolean false.
	 */
	public boolean isCastlingMove() {
		return false;
	}
	
	/**
	 * Helper method that returns a null by default.
	 * @return Piece null.
	 */
	public Piece getAttackedPiece() {
		return null;
	}
	
	/**
	 * Method that creates a new version of the board and returns the board now updated with the moved piece.
	 * @return Board the new version of the board updated with the changes after a move.
	 */
	public Board execute() {
		final Builder builder = new Builder();			
		for(final Piece piece : this.board.currentPlayer().getActivePieces()) {
			if(!this.movedPiece.equals(piece)) {
				builder.setPiece(piece);
			}
		}
		for(final Piece piece : this.board.currentPlayer().getOpponent().getActivePieces()) {
			builder.setPiece(piece);
		}
		builder.setPiece(this.movedPiece.movePiece(this));
		builder.setMoveMaker(this.board.currentPlayer().getOpponent().getAlliance());
		return builder.build();				
	}

	/**
	 * Nested class that moves a piece around the board.
	 * @author Gerwin Terpstra.
	 * @version 1.0.
	 * @Since 02-18-2021.
	 *
	 */
	
	public static class MajorAttackMove extends AttackMove {
		
		public MajorAttackMove(final Board board,
								final Piece pieceMoved,
								final int destinationCoordinate,
								final Piece pieceAttacked) {
			super(board, pieceMoved, destinationCoordinate, pieceAttacked);
		}
		
		@Override
		public boolean equals(final Object other) {
			return this == other || other instanceof MajorAttackMove && super.equals(other);
		}
		
		@Override
		public String toString() {
			return movedPiece.getPieceType() + BoardUtils.getPositionAtCoordinate(this.destinationCoordinate);
		}
		
	}
	
	public static final class MajorMove extends Move {
		// Constructor.
		public MajorMove(final Board board, 
							final Piece movedPiece, 
							final int destinationCoordinate) {
			super(board, movedPiece, destinationCoordinate);
		}
		
		@Override
		public boolean equals(final Object other) {
			return this == other || other instanceof MajorMove && super.equals(other);
		}
		
		@Override
		public String toString() {
			return movedPiece.getPieceType().toString() + BoardUtils.getPositionAtCoordinate(this.destinationCoordinate);
		}
	}
	
	/**
	 * Nested class that attacks on a piece.
	 * @author Gerwin Terpstra
	 * @version 1.0
	 * @since 02-18-2021
	 */
	public static class AttackMove extends Move {
		// Declare member variables.
		final Piece attackedPiece;
		
		// Constructor.
		public AttackMove(final Board board, 
							final Piece movedPiece, 
							final int destinationCoordinate, 
							final Piece attackedPiece) {
			super(board, movedPiece, destinationCoordinate);
			this.attackedPiece = attackedPiece;
		}
		
		/** 
		 * Overridden method that generates an ID of an attacking move based on its values.
		 * @return int result the ID of the AttackMove object.
		 */
		@Override
		public int hashCode() {
			return this.attackedPiece.hashCode() + super.hashCode();
		}
		
		/**
		 * Overridden method that checks if a given object has the same object values of this attacking move or not.
		 * @param Object other the object we want to compare to this AttackMove.
		 * @return boolean true if the objects have the same value, false otherwise.
		 */
		@Override
		public boolean equals(final Object other) {
			if(this == other ) {
				return true;
			}
			if(!(other instanceof AttackMove)) {
				return false;
			}
			final AttackMove otherAttackMove = (AttackMove) other;
			return super.equals(otherAttackMove) && getAttackedPiece().equals(otherAttackMove.getAttackedPiece());
		}
				
		/**
		 * Overridden helper method that returns a boolean true by default.
		 * @return boolean true.
		 */
		@Override
		public boolean isAttack() {
			return true;
		}
		
		/**
		 * Overridden method that returns a piece that is getting attacked.
		 * @return Piece piece the piece that is getting attacked.
		 */
		@Override
		public Piece getAttackedPiece() {
			return this.attackedPiece;
		}
	}
	
	/**
	 * Class that defines and creates a move of a pawn.
	 * @author Gerwin Terpstra. 
	 * @version 1.0.
	 * @since 02-23-2021.
	 */
	public static final class PawnMove extends Move {
			// Constructor.
		public PawnMove(final Board board, 
						final Piece movedPiece, 
						final int destinationCoordinate) {
			super(board, movedPiece, destinationCoordinate);
		}
		
		@Override
		public boolean equals(final Object other) {
			return this == other || other instanceof PawnMove && super.equals(other);
		}
		
		@Override
		public String toString() {
			return BoardUtils.getPositionAtCoordinate(this.destinationCoordinate);
		}
		
	}
	
	/**
	 * Class that defines and creates a move of an attacking pawn.
	 * @author Gerwin Terpstra. 
	 * @version 1.0.
	 * @since 02-23-2021.
	 */
	public static final class PawnAttackMove extends AttackMove {
		// Constructor.
		public PawnAttackMove(final Board board, 
								final Piece movedPiece, 
								final int destinationCoordinate,
								final Piece attackedPiece) {
			super(board, movedPiece, destinationCoordinate, attackedPiece);
		}
		
		@Override
		public boolean equals (final Object other) {
			return this == other || other instanceof PawnAttackMove && super.equals(other);
		}
		@Override
		public String toString() {
			return BoardUtils.getPositionAtCoordinate(this.movedPiece.getPiecePosition()).substring(0, 1) + "x" +
					BoardUtils.getPositionAtCoordinate(this.destinationCoordinate);
		}
	}
	
	/**
	 * Class that defines and creates a move of pawn performing an 'en passant' attack.
	 * @author Gerwin Terpstra. 
	 * @version 1.0.
	 * @since 02-23-2021.
	 */
	public static final class PawnEnPassantAttackMove extends AttackMove {
		// Constructor
		public PawnEnPassantAttackMove(final Board board, 
								final Piece movedPiece, 
								final int destinationCoordinate,
								final Piece attackedPiece) {
			super(board, movedPiece, destinationCoordinate, attackedPiece);
		}
		
		@Override
		public boolean equals(final Object other) {
			return this == other || other instanceof PawnEnPassantAttackMove && super.equals(other);
		} 
		
		@Override
		public Board execute() {
			final Builder builder = new Builder();
			for(final Piece piece : this.board.currentPlayer().getActivePieces()) {
				if(!this.movedPiece.equals(piece)) {
					builder.setPiece(piece);
				}
			}
			for(final Piece piece : this.board.currentPlayer().getOpponent().getActivePieces()) {
				if(!piece.equals(this.getAttackedPiece())) {
					builder.setPiece(piece);
				}
			}
			builder.setPiece(this.movedPiece.movePiece(this));
			builder.setMoveMaker(this.board.currentPlayer().getOpponent().getAlliance());
			return builder.build();
		}
	}
	
	public static class PawnPromotion extends Move {
		
		final Move decoratedMove;
		final Pawn promotedPawn;
		
		public PawnPromotion(final Move decoratedMove) {
			super(decoratedMove.getBoard(), decoratedMove.getMovedPiece(), decoratedMove.getDestinationCoordinate());
			this.decoratedMove = decoratedMove;
			this.promotedPawn = (Pawn) decoratedMove.getMovedPiece();
		}
		
		@Override
		public int hashCode() {
			return decoratedMove.hashCode() + (31 * promotedPawn.hashCode());
		}
		
		@Override
		public boolean equals(final Object other) {
			return this == other || other instanceof PawnPromotion && (super.equals(other));
		}
		
		@Override
		public Board execute() {
			
			final Board pawnMovedBoard = this.decoratedMove.execute();
			final Board.Builder builder = new Builder();
			for(final Piece piece : pawnMovedBoard.currentPlayer().getActivePieces()) {
				if(!this.promotedPawn.equals(piece)) {
					builder.setPiece(piece);
				}
			}
			for(final Piece piece : pawnMovedBoard.currentPlayer().getOpponent().getActivePieces()) {
				builder.setPiece(piece);
			}
			builder.setPiece(this.promotedPawn.getPromotionPiece().movePiece(this));
			builder.setMoveMaker(pawnMovedBoard.currentPlayer().getAlliance());
			return builder.build();	
		}
		
		@Override
		public boolean isAttack() {
			return this.decoratedMove.isAttack();
		}
		
		@Override
		public Piece getAttackedPiece() {
			return this.decoratedMove.getAttackedPiece();
		}
		
		@Override
		public String toString() {
			return "";
		}
	}
	
	/**
	 * Class that defines and creates a move of a pawn performing a 'jump'.
	 * @author Gerwin Terpstra. 
	 * @version 1.0.
	 * @since 02-23-2021.
	 */
	public static final class PawnJump extends Move {
		// Constructor.
		public PawnJump(final Board board, 
							final Piece movedPiece, 
							final int destinationCoordinate) {
			super(board, movedPiece, destinationCoordinate);
		}
		
		/**
		 * Method that creates a new version of the board and returns the board
		 * now updated with the moved pawn after it performed a 'pawn jump'.
		 * @return Board the new version of the board updated with the changes after a 'pawn jump' move.
		 */
		@Override
		public Board execute() {
			// Declare and initialize local variables.
			final Builder builder = new Builder();
			
			for(final Piece piece : this.board.currentPlayer().getActivePieces()) {
				if(!this.movedPiece.equals(piece)) {
					builder.setPiece(piece);
				}
			}
			for(final Piece piece : this.board.currentPlayer().getOpponent().getActivePieces()) {
				builder.setPiece(piece);
			}
			// Declare and initialize more local variables.
			final Pawn movedPawn = (Pawn)this.movedPiece.movePiece(this);
			
			builder.setPiece(movedPawn);
			builder.setEnPassantPawn(movedPawn);
			builder.setMoveMaker(this.board.currentPlayer().getOpponent().getAlliance());
			return builder.build();
		}
		
		@Override
		public String toString() {
			return BoardUtils.getPositionAtCoordinate(this.destinationCoordinate);
		}
	}
	
	/**
	 * Abstract class that defines and creates a 'castling' move.
	 * @author Gerwin Terpstra.
	 * @version 1.0.
	 * @since 02-23-2021.
	 */
	static abstract class CastleMove extends Move {
		// Declare member variables.
		protected final Rook castleRook;
		protected final int castleRookStart;
		protected final int castleRookDestination;
		
		// Constructor.
		public CastleMove(final Board board, 
							final Piece movedPiece, 
							final int destinationCoordinate,
							final Rook castleRook,
							final int castleRookStart,
							final int castleRookDestination) {
			super(board, movedPiece, destinationCoordinate);
			
			// Initialize member variables.
			this.castleRook = castleRook;
			this.castleRookStart = castleRookStart;
			this.castleRookDestination = castleRookDestination;
		}
		
		/**
		 * Method that returns the rook piece that is getting moved.
		 * @return Rook a rook piece.
		 */
		public Rook getCastleRook() {
			return this.castleRook;
		}
		
		/**
		 * Overridden method that returns a boolean true by default.
		 * @return boolean true.
		 */
		@Override
		public boolean isCastlingMove() {
			return true;
		}
		
		/**
		 * Method that creates a new version of the board and returns the board now updated with the moved pieces.
		 * @return Board the new version of the board updated with the changes after a move.
		 */
		@Override
		public Board execute() {
			// Declare and initialize local variables.
			final Builder builder = new Builder();
			
			for(final Piece piece : this.board.currentPlayer().getActivePieces()) {
				if(!this.movedPiece.equals(piece)) {
					builder.setPiece(piece);
				}
			}
			for(final Piece piece : this.board.currentPlayer().getOpponent().getActivePieces()) {
				builder.setPiece(piece);
			}
			builder.setPiece(this.movedPiece.movePiece(this));
			// TODO Look into the first move on normal pieces.
			builder.setPiece(new Rook(this.castleRook.getPieceAlliance(), this.castleRookDestination));
			builder.setMoveMaker(this.board.currentPlayer().getOpponent().getAlliance());
			return builder.build();
		}
		
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = super.hashCode();
			result = prime * result + this.castleRook.hashCode();
			result = prime * result + this.castleRookDestination;
			return result;
		}
		
		@Override
		public boolean equals(final Object other) {
			if(this == other) {
				return true;
			}
			if(!(other instanceof CastleMove)) {
				return false;
			}
			final CastleMove otherCastleMove = (CastleMove) other;
			return super.equals(otherCastleMove) && this.castleRook.equals(otherCastleMove.getCastleRook());
 		}
	}
	
	/**
	 * class that defines and creates a 'castling' move on the king's side.
	 * @author Gerwin Terpstra.
	 * @version 1.0.
	 * @since 02-23-2021.
	 */
	public static final class KingSideCastleMove extends CastleMove {
		// Constructor.
		public KingSideCastleMove(final Board board, 
									final Piece movedPiece, 
									final int destinationCoordinate,
									final Rook castleRook,
									final int castleRookStart,
									final int castleRookDestination) {
			super(board, movedPiece, destinationCoordinate, castleRook, castleRookStart, castleRookDestination);
		}
		@Override
		public boolean equals(final Object other) {
			return this == other || other instanceof KingSideCastleMove && super.equals(other);
		}
	
		/**
		 * Overridden method that returns the chess notation of the 'kingside castle' move.
		 * @return String a string representing the chess notation of 'kingside castling'.
		 */
		@Override
		public String toString() {
			return "0-0";
		}
	}
		
	/**
	 * class that defines and creates a 'castling' move on the queens' side.
	 * @author Gerwin Terpstra.
	 * @version 1.0.
	 * @since 02-23-2021.
	 */
	public static final class QueenSideCastleMove extends CastleMove {
		// Constructor.
		public QueenSideCastleMove(final Board board, 
									final Piece movedPiece, 
									final int destinationCoordinate,
									final Rook castleRook,
									final int castleRookStart,
									final int castleRookDestination) {
			super(board, movedPiece, destinationCoordinate, castleRook, castleRookStart, castleRookDestination);
		}
		
		@Override
		public boolean equals(final Object other) {
			return this == other || other instanceof QueenSideCastleMove && super.equals(other);
		}
		
		/**
		 * Overridden method that returns the chess notation of the 'queenside castle' move.
		 * @return String a string representing the chess notation of 'queenside castling'.
		 */
		@Override
		public String toString() {
			return "0-0-0";
		}
	}
	
	/**
	 * Helper class that defines and deals with a move that is incorrect and null.
	 * @author Gerwin Terpstra.
	 * @version 1.0.
	 * @since 02-23-2021.
	 */
	public static final class NullMove extends Move {
		// Constructor.
		public NullMove() {
			super(null, 65);
		}
		
		@Override
		public int getCurrentCoordinate() {
			return -1;
		}
		
		@Override
		public int getDestinationCoordinate() {
			return -1;
		}
		
		@Override
		public String toString() {
			return "Null Move";
		}
		
		/**
		 * Overridden method that throws a RuntimeException by default.
		 * @throws RuntimeException with the message that the null move cannot be executed.
		 */
		@Override
		public Board execute() {
			throw new RuntimeException("cannot execute the null move!");
		}
	}
		
	/**
	 * Factory class that helps with creating a move.
	 * @author Gerwin Terpstra.
	 * @version 1.0.
	 * @since 02-23-2021.
	 */
	public static class MoveFactory {
		
		private static final Move NULL_MOVE = new NullMove();
		
		// Constructor.
		private MoveFactory() {
			throw new RuntimeException("Not instantiable!");
		}
		
		public static Move getNullMove() {
			return NULL_MOVE;
		}
			
		/**
		 * Method that creates a Move object.
		 * @param Board board the instance of a Board that the move is going to take place on.
		 * @param int currentCoordinate the current coordinate of the piece that is going to move.
		 * @param int destinationCoordinate the destination coordinate of the piece that is going to move.
		 * @return Move move the initialized instance of a Move class if the move is legal, NullMove NULL_MOVE otherwise.
		 */
		public static Move createMove(final Board board,
										final int currentCoordinate,
										final int destinationCoordinate) {
			for(final Move move : board.getAllLegalMoves()) {
				if(move.getCurrentCoordinate() == currentCoordinate &&
						move.getDestinationCoordinate() == destinationCoordinate) {
					return move;
				}
			}
			return NULL_MOVE;
		}
	}
}
