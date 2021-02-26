// User-defined package.
package com.chessTestProject.engine.pieces;

// Imported user-defined classes.
import com.chessTestProject.engine.Alliance;
import com.chessTestProject.engine.board.Board;
import com.chessTestProject.engine.board.Move;

// Imported built-in classes.
import java.util.Collection;

/**
 * Abstract class that is the framework for a piece.
 * @author Gerwin Terpstra.
 * @version 1.0.
 * @since 02-19-2021.
 */
public abstract class Piece {
	
	// Declare member variables.
	protected final PieceType pieceType;
	protected final int piecePosition;
	protected final Alliance pieceAlliance;
	protected final boolean isFirstMove;
	private final int cachedHashCode;
	
	// Constructor.
	Piece(final PieceType pieceType, 
			final int piecePosition, 
			final Alliance pieceAlliance,
			final boolean isFirstMove) {
		
		// Initialize member variables.
		this.pieceType = pieceType;
		this.pieceAlliance = pieceAlliance;
		this.piecePosition = piecePosition;
		this.isFirstMove = isFirstMove;
		this.cachedHashCode = computeHashCode();
	}
	
	/**
	 * Method that generates an ID of a piece based on its values.
	 * @return int result the ID of the Piece object.
	 */
	private int computeHashCode() {
		int result = pieceType.hashCode();
		result = 31 * result + pieceAlliance.hashCode();
		result = 31 * result + piecePosition;
		result = 31 * result + (isFirstMove ? 1 : 0);
		return result;	
	}

	/**
	 * Overridden method that checks if a given object has the same object values of this piece or not.
	 * The Object.equals() method gets overridden here because it's needed to check for object value equality here,
	 * not object reference equality.
	 * @param Object other the object we want to compare to this Piece.
	 * @return boolean true if the objects have the same value, false otherwise.
	 */
	@Override
	public boolean equals(final Object other) {
		if(this == other) {
			return true;
		}
		if(!(other instanceof Piece)) {
			return false;
		}
		final Piece otherPiece = (Piece) other;
		return piecePosition == otherPiece.getPiecePosition() && pieceType == otherPiece.getPieceType() &&
				pieceAlliance == otherPiece.getPieceAlliance() && isFirstMove == otherPiece.isFirstMove();
	}
	
	/**
	 * Overridden method that returns the cachedHashCode a.k.a. the ID of a piece.
	 * @return int the cachedHashCode of this instance of Piece.
	 */
	@Override
	public int hashCode() {
		return this.cachedHashCode;
	}
	
	/**
	 * Method that returns the position coordinate of the piece on the board.
	 * @return int piecePosition the position coordinate of the piece.
	 */
	public int getPiecePosition() {
		return this.piecePosition;
	}
	
	// Declare abstract methods.
	public abstract Collection<Move> calculateLegalMoves(final Board board);	
	public abstract Piece movePiece(Move move);
	
	/**
	 * Method that returns the piece's alliance.
	 * @return Alliance pieceAlliance the alliance of the piece.
	 */
	public Alliance getPieceAlliance() {
		return this.pieceAlliance;
	}		
	
	/**
	 * Method that returns if it is the piece's first move of the game.
	 * @return boolean isFirstMove true if the piece has not moved yet, false if the piece has already moved at least once.
	 */
	public boolean isFirstMove() {
		return this.isFirstMove;
	}		
	
	/**
	 * Method that returns the PieceType of the piece.
	 * @return PieceType the type of the pieve.
	 */
	public PieceType getPieceType() {
		return this.pieceType;
	}
	
	/**
	 * Enum that defines the type of a piece.
	 * @author Gerwin Terpstra
	 * @version 1.0
	 * @since 02-22-2021.
	 */
	public enum PieceType {
		/**
		 * Overridden method that returns a boolean false by default.
		 * @return boolean false.
		 */
		PAWN("p") {
			@Override
			public boolean isKing() {
				return false;
			}

			/**
			 * Overridden method that returns a boolean false by default.
			 * @return boolean false.
			 */
			@Override
			public boolean isRook() {
				// TODO Auto-generated method stub
				return false;
			}
		},
		KNIGHT("N") {
			/**
			 * Overridden method that returns a boolean false by default.
			 * @return boolean false.
			 */
			@Override
			public boolean isKing() {
				return false;
			}

			/**
			 * Overridden method that returns a boolean false by default.
			 * @return boolean false.
			 */
			@Override
			public boolean isRook() {
				return false;
			}
		},
		BISHOP("B") {
			/**
			 * Overridden method that returns a boolean false by default.
			 * @return boolean false.
			 */
			@Override
			public boolean isKing() {
				return false;
			}

			/**
			 * Overridden method that returns a boolean false by default.
			 * @return boolean false.
			 */
			@Override
			public boolean isRook() {
				return false;
			}
		},
		
		/**
		 * Overridden method that returns a boolean false by default.
		 * @return boolean false.
		 */
		ROOK("R") {
			@Override
			public boolean isKing() {
				return false;
			}

			/**
			 * Overridden method that returns a boolean true by default.
			 * @return boolean true.
			 */
			@Override
			public boolean isRook() {
				return true;
			}
		},
		QUEEN("Q") {
			/**
			 * Overridden method that returns a boolean by default.
			 * @return boolean false.
			 */
			@Override
			public boolean isKing() {
				return false;
			}

			/**
			 * Overridden method that returns a boolean false by default.
			 * @return boolean false.
			 */
			@Override
			public boolean isRook() {
				return false;
			}
		},
		KING("K") {
			/**
			 * Overridden method that returns a boolean true by default.
			 * @return boolean true.
			 */
			@Override
			public boolean isKing() {
				return true;
			}
			
			/**
			 * Overridden method that returns a boolean false by default.
			 * @return boolean false.
			 */
			@Override
			public boolean isRook() {
				return false;
			}
		};
		
		// Declare member variables.
		final String pieceName;
		
		// Constructor.
		PieceType(final String pieceName) {
			this.pieceName = pieceName;
		}
		
		/**
		 * Overridden method that returns a String representing a specific piece type.
		 * @return String representing a specific piece type.
		 */
		@Override
		public String toString() {
			return this.pieceName;
		}
		
		// Declare abstract methods.
		public abstract boolean isKing();
		public abstract boolean isRook();
	}
}
