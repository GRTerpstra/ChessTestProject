package com.chessTestProject.engine.player.ai;

import com.chessTestProject.engine.board.Board;
import com.chessTestProject.engine.board.Move;

public interface MoveStrategy {
	
	Move execute(Board board);

}
