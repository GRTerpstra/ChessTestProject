// User-defined package.
package com.chessTestProject.engine.player;

/**
 * Enum that contains constants to define the results of a move simulation.
 * @author Gerwin Terpstra
 * @version 1.0
 * @since 02-23-2021.
 */
public enum MoveStatus {
	DONE {
		
		/**
		 * Overriden method that returns a boolean true by default.
		 * @return boolean true;
		 */
		@Override
		boolean isDone() {
			return true;
		}
	},
	ILLEGAL_MOVE {
		
		/**
		 * Overriden method that returns a boolean false by default.
		 * @return boolean false;
		 */
		@Override
		boolean isDone() {
			return false;
		}
	}, LEAVE_PLAYER_IN_CHECK {
		
		/**
		 * Overriden method that returns a boolean false by default.
		 * @return boolean false;
		 */
		@Override
		boolean isDone() {
			// TODO Auto-generated method stub
			return false;
		}
	};	
	// Declare abstract methods.
	abstract boolean isDone();
}
