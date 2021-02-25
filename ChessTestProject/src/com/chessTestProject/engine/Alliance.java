// User-defined package.
package com.chessTestProject.engine;

// Imported user-defined packages.
import com.chessTestProject.engine.player.BlackPlayer;
import com.chessTestProject.engine.player.Player;
import com.chessTestProject.engine.player.WhitePlayer;

/**
 * Enum that contains the 'white' and 'black' constants to define the alliance of a piece,
 * together with methods to return information about the specific alliance.
 * @author Gerwin Terpstra.
 * @version 1.0.
 * @since 02-17-2021.
 */
public enum Alliance {	
	WHITE {
		/**
		 * Overridden method that returns the direction to indicate which side of the board a pawn can move towards.
		 * @return int number to calculate the coordinates a pawn can move to.
		 */
		@Override
		public int getDirection() {
			return -1;
		}

		/**
		 * Overridden method that defines and returns that the alliance of the piece is white.
		 * @return boolean true for a white piece.
		 */
		@Override
		public boolean isWhite() {
			return true;
		}
		
		/**
		 * Overridden method that defines and returns that the alliance of a piece is not black.
		 * @return boolean false for a white piece.
		 */
		@Override
		public boolean isBlack() {
			return false;
		}

		/**
		 * Overridden method that returns the white player if it's the white players turn.
		 * @param WhitePlayer whitePlayer the player with the white alliance
		 * @param BlackPlayer blackPlayer the player with the black alliance.
		 * @return WhitePlayer whitePlayer the player with the white alliance.
		 */
		@Override
		public Player choosePlayer(final WhitePlayer whitePlayer, 
										final BlackPlayer blackPlayer) {
			return whitePlayer;
		}
	},	
	BLACK {
		/**
		 * Overridden method that returns the direction to indicate which side of the board a pawn can move towards.
		 * @return int number to calculate the coordinates a pawn can move to.
		 */
		@Override
		public int getDirection() {
			return 1;
		}

		/**
		 * Overridden method that defines and returns that the alliance of the piece is not white.
		 * @return boolean false for a black piece.
		 */
		@Override
		public boolean isWhite() {
			return false;
		}

		/**
		 * Overridden method that defines and returns that the alliance of the piece is black.
		 * @return boolean true for a black piece.
		 */
		@Override
		public boolean isBlack() {
			return true;
		}
		
		/**
		 * Overridden method that returns the black player if it's the black players turn.
		 * @param WhitePlayer whitePlayer the player with the white alliance
		 * @param BlackPlayer blackPlayer the player with the black alliance.
		 * @return BlackPlayer blackPlayer the player with the black alliance.
		 */
		@Override
		public Player choosePlayer(final WhitePlayer whitePlayer, 
										final BlackPlayer blackPlayer) {
			return blackPlayer;
		}
	};
	
	// Declare abstract methods
	public abstract int getDirection();
	public abstract boolean isWhite();
	public abstract boolean isBlack();
	public abstract Player choosePlayer(WhitePlayer whitePlayer, BlackPlayer blackPlayer);
}
