// User-defined package.
package com.chessTestProject;

// Imported user-defined classes.
import com.chessTestProject.engine.board.Board;
import com.chessTestProject.gui.Table;

/**
 * Main class of the application. 
 * @author Gerwin Terpstra
 * @Version 1.0
 * @Since 02-22-2021
 */
public class JChess {
	public static void main(String[] args) {	
		// Declare and initialize local variables.
		Board board = Board.createStandardBoard();
		
		
		System.out.println(board);
		
		Table table = new Table();
	}
}
