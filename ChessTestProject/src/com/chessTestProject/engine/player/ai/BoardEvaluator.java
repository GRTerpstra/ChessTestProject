package com.chessTestProject.engine.player.ai;

import com.chessTestProject.engine.board.Board;

public interface BoardEvaluator {

	int evaluate(Board board, int depth);
}
