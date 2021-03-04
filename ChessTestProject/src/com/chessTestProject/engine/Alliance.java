// User-defined package.
package com.chessTestProject.engine;

//Imported user-defined classes.
import com.chessTestProject.engine.board.BoardUtils;
import com.chessTestProject.engine.player.Player;
import com.chessTestProject.engine.player.BlackPlayer;
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
		 * Overridden method that returns a direction to indicate which side of the board a pawn can move towards.
		 * @return int returns -1 by default.
		 */
		@Override
		public int getDirection() {
			return -1;
		}
		
		/**
		 * Overriden method that returns the opposite direction of this alliance's pawns.
		 * @return int returns 1 by default
		 */
		@Override
		public int getOppositeDirection() {
			return 1;
		}

		/**
		 * Overridden method that defines that this alliance is white.
		 * @return boolean true by default.
		 */
		@Override
		public boolean isWhite() {
			return true;
		}
		
		/**
		 * Overridden method that defines that this alliance is not black.
		 * @return boolean false by default.
		 */
		@Override
		public boolean isBlack() {
			return false;
		}
		
		/**
		 * Overriden method that returns a String of the name of this alliance.
		 * @return String returns "White" by default.
		 */
		@Override
		public String toString() {
			return "White";
		}

		/**
		 * Overridden method that returns the player that is playing with white.
		 * @param WhitePlayer whitePlayer the player that is playing with white.
		 * @param BlackPlayer blackPlayer the player that is playing with black.
		 * @return WhitePlayer whitePlayer the player that is playing with white.
		 */
		@Override
		public Player choosePlayer(final WhitePlayer whitePlayer, 
										final BlackPlayer blackPlayer) {
			return whitePlayer;
		}
		
		/**
		 * Method that checks if the given position of a white pawn is a position from which the pawn can promote.
		 * @param int position the coordinate of the tile the pawn is on.
		 * @return boolean true if the EIGHT_RANK constant boolean array contains the given position's coordinate, false otherwise.
		 */
		@Override
		public boolean isPawnPromotionSquare(int position) {
			return BoardUtils.EIGHT_RANK[position];
		}
	},	
	BLACK {
		/**
		 * Overridden method that returns a direction to indicate which side of the board a pawn can move towards.
		 * @return int returns 1 by default.
		 */
		@Override
		public int getDirection() {
			return 1;
		}
		
		/**
		 * Overriden method that returns the opposite direction of this alliance's pawns.
		 * @return int returns -1 by default
		 */
		@Override
		public int getOppositeDirection() {
			return -1;
		}
		
		/**
		 * Overridden method that defines that this alliance is black.
		 * @return boolean true by default.
		 */
		@Override
		public boolean isBlack() {
			return true;
		}

		/**
		 * Overridden method that defines that this alliance is not white.
		 * @return boolean false by default.
		 */
		@Override
		public boolean isWhite() {
			return false;
		}

		/**
		 * Overriden method that returns a String of the name of this alliance.
		 * @return String returns "Black" by default.
		 */		
		@Override
		public String toString() {
			return "Black";
		}
		
		/**
		 * Overridden method that returns the player that is playing with black.
		 * @param WhitePlayer whitePlayer the player that is playing with white.
		 * @param BlackPlayer blackPlayer the player that is playing with black.
		 * @return WhitePlayer whitePlayer the player that is playing with black.
		 */
		@Override
		public Player choosePlayer(final WhitePlayer whitePlayer, 
										final BlackPlayer blackPlayer) {
			return blackPlayer;
		}
		
		/**
		 * Method that checks if the given position of a black pawn is a position from which the pawn can promote.
		 * @param int position the coordinate of the tile the pawn is on.
		 * @return boolean true if the FIRST_RANK constant boolean array contains the given position's coordinate, false otherwise.
		 */
		@Override
		public boolean isPawnPromotionSquare(int position) {
			return BoardUtils.FIRST_RANK[position];
		}
	};
	
	// Declare abstract methods
	public abstract int getDirection();
	public abstract int getOppositeDirection();
	public abstract boolean isWhite();
	public abstract boolean isBlack();
	public abstract Player choosePlayer(final WhitePlayer whitePlayer, final BlackPlayer blackPlayer);
	public abstract boolean isPawnPromotionSquare(int position);
}
