package com.chessTestProject.engine.pieces;

import java.util.Collection;

import com.chessTestProject.engine.Alliance;
import com.chessTestProject.engine.board.Board;
import com.chessTestProject.engine.board.Move;

public abstract class Piece {
	
	// Variables
	
	protected final int piecePosition;
	protected final Alliance pieceAlliance;
	protected final boolean isFirstMove;
	
	// Constructor
	
	Piece(final int piecePosition, final Alliance pieceAlliance) {
		this.pieceAlliance = pieceAlliance;
		this.piecePosition = piecePosition;
		//TODO more work here
		this.isFirstMove = false;
	}
	
	public Alliance getPieceAlliance() {
		return this.pieceAlliance;
	}
	
	public boolean isFirstMove() {
		return this.isFirstMove();
	}
	
	public abstract Collection<Move> calculateLegalMoves(final Board board);
	
	
}
