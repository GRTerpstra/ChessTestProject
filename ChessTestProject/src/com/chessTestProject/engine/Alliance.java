// User-defined package
package com.chessTestProject.engine;

/**
 * Enum that contains the 'white' and 'black' constants to define the alliance of a piece.
 * 
 * @author Gerwin Terpstra
 * @version 1.0
 * @since 02-17-2021
 */
public enum Alliance {	
	WHITE {
		/**
		 * Method that returns the direction to indicate which side of the board a pawn can move towards.
		 * @return int number to calculate the coordinates a pawn can move to.
		 */
		@Override
		public int getDirection() {
			return -1;
		}

		/**
		 * Method that defines and returns that the alliance of the piece is white.
		 * @return boolean true for a white piece.
		 */
		@Override
		public boolean isWhite() {
			return true;
		}
		
		/**
		 * Method that defines and returns that the alliance of a piece is not black.
		 * @return boolean false for a white piece.
		 */
		@Override
		public boolean isBlack() {
			return false;
		}
	},	
	BLACK {
		/**
		 * Method that returns the direction to indicate which side of the board a pawn can move towards.
		 * @return int number to calculate the coordinates a pawn can move to.
		 */
		@Override
		public int getDirection() {
			return 1;
		}

		/**
		 * Method that defines and returns that the alliance of the piece is not white.
		 * @return boolean false for a black piece.
		 */
		@Override
		public boolean isWhite() {
			return false;
		}

		/**
		 * Method that defines and returns that the alliance of the piece is black.
		 * @return boolean true for a black piece.
		 */
		@Override
		public boolean isBlack() {
			return true;
		}
	};
	
	// Declaration of abstract methods
	public abstract int getDirection();
	public abstract boolean isWhite();
	public abstract boolean isBlack();
}
