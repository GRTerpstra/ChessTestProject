// User-defined package.
package com.chessTestProject.engine.pieces;

//Imported user-defined packages.
import com.chessTestProject.engine.Alliance;
import com.chessTestProject.engine.board.Board;
import com.chessTestProject.engine.board.Move;

// Imported built-in package.
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
			final Alliance pieceAlliance) {
		this.pieceType = pieceType;
		this.pieceAlliance = pieceAlliance;
		this.piecePosition = piecePosition;
		// TODO more work here
		this.isFirstMove = false;
		this.cachedHashCode = computeHashCode();
	}
	
	private int computeHashCode() {
		int result = pieceType.hashCode();
		result = 31 * result + pieceAlliance.hashCode();
		result = 31 * result + piecePosition;
		result = 31 * result + (isFirstMove ? 1 : 0);
		return result;	
	}

	// Overriding the equals() method here because we want object value equality, not object reference equality.
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
	
	@Override
	public int hashCode() {
		return this.cachedHashCode;
	}
	
	/**
	 * Method that returns the position of the piece on the board.
	 * @return int piecePosition the position of the piece.
	 */
	public int getPiecePosition() {
		return this.piecePosition;
	}
	
	// Declare abstract method.
	public abstract Collection<Move> calculateLegalMoves(final Board board);
	
	public abstract Piece movePiece(Move move);
	
	/**
	 * Abstract method that returns the piece's alliance.
	 * @return Alliance pieceAlliance the alliance of the piece.
	 */
	public Alliance getPieceAlliance() {
		return this.pieceAlliance;
	}		
	
	/**
	 * Abstract method that returns if it is the piece's first move of the game.
	 * @return boolean isFirstMove true if the piece has not moved yet, false if the piece has already moved at least once.
	 */
	public boolean isFirstMove() {
		return this.isFirstMove;
	}		
	
	public PieceType getPieceType() {
		return this.pieceType;
	}
	
	public enum PieceType {
		
		PAWN("p") {
			@Override
			public boolean isKing() {
				return false;
			}
		},
		KNIGHT("N") {
			@Override
			public boolean isKing() {
				return false;
			}
		},
		BISHOP("B") {
			@Override
			public boolean isKing() {
				return false;
			}
		},
		ROOK("R") {
			@Override
			public boolean isKing() {
				return false;
			}
		},
		QUEEN("Q") {
			@Override
			public boolean isKing() {
				return false;
			}
		},
		KING("K") {
			@Override
			public boolean isKing() {
				return true;
			}
		};
		
		final String pieceName;
		PieceType(final String pieceName) {
			this.pieceName = pieceName;
		}
		
		@Override
		public String toString() {
			return this.pieceName;
		}
		
		public abstract boolean isKing();
	}
}
