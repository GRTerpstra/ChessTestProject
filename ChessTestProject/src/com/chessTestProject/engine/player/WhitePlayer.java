package com.chessTestProject.engine.player;

import java.util.Collection;

import com.chessTestProject.engine.Alliance;
import com.chessTestProject.engine.board.Board;
import com.chessTestProject.engine.board.Move;
import com.chessTestProject.engine.pieces.Piece;

public class WhitePlayer extends Player {
	
	public WhitePlayer(Board board,
						Collection<Move> whiteStandardLegalMoves,
						Collection<Move> blackStandardLegalMoves) {
		super(board, blackStandardLegalMoves, blackStandardLegalMoves);
	}

	@Override
	public Collection<Piece> getActivePieces() {
		return this.board.getWhitePieces();
	}

	@Override
	public Alliance getAlliance() {
		return Alliance.WHITE;
	}

	@Override
	public Player getOpponent() {
		return this.board.whitePlayer();
	}
}
