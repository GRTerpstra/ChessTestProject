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
	
	// Declare variables.
	protected final int piecePosition;
	protected final Alliance pieceAlliance;
	protected final boolean isFirstMove;
	
	// Constructor.
	Piece(final int piecePosition, final Alliance pieceAlliance) {
		this.pieceAlliance = pieceAlliance;
		this.piecePosition = piecePosition;
		// !!!! TODO more work here !!! 
		this.isFirstMove = false;
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
		return this.isFirstMove();
	}		
}
