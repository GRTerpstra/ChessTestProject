// User-defined package.
package com.chessTestProject.engine.player;

// Imported user-defined packages.
import com.chessTestProject.engine.board.Board;
import com.chessTestProject.engine.board.Move;

/**
 * Class that simulates a move to calculate the results.
 * @author Gerwin Terpstra
 * @version 1.0
 * @since 02-23-2021.
 */
public class MoveTransition {
	// Declare member variables.
	private final Board transitionBoard;
	private final Move move;
	private final MoveStatus moveStatus;

	// Constructor.
	public MoveTransition(final Board transitionBoard,
							final Move move,
							final MoveStatus moveStatus) {
		// Initialize member variables.
		this.transitionBoard = transitionBoard;
		this.move = move;
		this.moveStatus = moveStatus;
		}
	
	/**
	 * Method that returns the moveStatus a.k.a. results of a simulation.
	 * @return enum MoveStatus the results of the simulation.
	 */
	public MoveStatus getMoveStatus() {
		return this.moveStatus;
	}
}
