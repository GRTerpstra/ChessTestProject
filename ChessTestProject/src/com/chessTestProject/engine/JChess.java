// User-defined package.
package com.chessTestProject.engine;

// Imported user-defined packages.
import com.chessTestProject.engine.board.Board;

/**
 * 
 * @author Gebruiker
 *
 */
public class JChess {

	public static void main(String[] args) {
		
		Board board = Board.createStandardBoard();
		
		System.out.println(board);
		
	}
	
}
