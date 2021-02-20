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
	WHITE
 {
		@Override
		public int getDirection() {
			return -1;
		}

		@Override
		public boolean isWhite() {
			return true;
		}

		@Override
		public boolean isBlack() {
			return false;
		}
	},	
	BLACK {
		@Override
		public int getDirection() {
			return 1;
		}

		@Override
		public boolean isWhite() {
			return false;
		}

		@Override
		public boolean isBlack() {
			return true;
		}
	};
	
		public abstract int getDirection();
		public abstract boolean isWhite();
		public abstract boolean isBlack();
}
