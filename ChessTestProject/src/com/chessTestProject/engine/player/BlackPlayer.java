package com.chessTestProject.engine.player;

import java.util.Collection;

import com.chessTestProject.engine.Alliance;
import com.chessTestProject.engine.board.Board;
import com.chessTestProject.engine.board.Move;
import com.chessTestProject.engine.pieces.Piece;

public class BlackPlayer extends Player {
	
	public BlackPlayer(final Board board,
						final Collection<Move> whiteStandardLegalMoves,
						final Collection<Move> blackStandardLegalMoves) {
		super(board, blackStandardLegalMoves, whiteStandardLegalMoves);
	}

	@Override
	public Collection<Piece> getActivePieces() {
		return this.board.getBlackPieces();
	}

	@Override
	public Alliance getAlliance() {
		return Alliance.BLACK;
	}

	@Override
	public Player getOpponent() {
		return this.board.blackPlayer();
	}
}
