// User-defined package
package com.chessTestProject.engine.board;

// Imported built-in packages
import java.util.HashMap;
import java.util.Map;

import com.chessTestProject.engine.pieces.Piece;

/**
 * Abstract class that creates the tiles of a board.
 * @author Gerwin Terpstra
 * @version 1.0
 * @since 02-18-2021
 */
public abstract class Tile {

	// Declare variables	
	protected final int tileCoordinate;	
	
	// Declare and initializes variables.
	private static final Map<Integer, EmptyTile> EMPTY_FILE_CACHE = createAllPossibleEmptyTiles();
	
	// Constructor		
	private Tile(final int tileCoordinate) {
		this.tileCoordinate = tileCoordinate;
	}
	
	// Declare abstract methods
	public abstract boolean isTileOccupied();	
	public abstract Piece getPiece();
	
	/**
	 * Method that creates a map of tiles that resembles a playing board.
	 * @return Map of 64 objects of EmptyTile.
	 */
	private static Map<Integer, EmptyTile> createAllPossibleEmptyTiles() {
		final Map<Integer, EmptyTile> emptyTileMap = new HashMap<>();		
		for(int i = 0; i < BoardUtils.NUM_TILES; i++) {
			emptyTileMap.put(i, new EmptyTile(i));
		}
		// !!! Maybe use the class ImmutableMap for this return statement (See video 2) !!!
		return emptyTileMap;
	}
	
	/**
	 * Method 
	 * @param tileCoordinate
	 * @param piece
	 * @return
	 */
	public static Tile createTile(final int tileCoordinate, final Piece piece) {
		return piece != null ? new OccupiedTile(tileCoordinate, piece) : EMPTY_FILE_CACHE.get(tileCoordinate);
	}
	
	
	/**
	 * Nested subclass that creates an empty tile.
	 * @author Gerwin Terpstra
	 * @version 1.0
	 * @since 02-18-2021
	 */
	public static final class EmptyTile extends Tile {
		
		// Constructor		
		private EmptyTile(final int coordinate) {
			super(coordinate);
		}
		
		/**
		 * Method that returns a boolean false because an empty tile is not occupied.		
		 * @return boolean false.
		 */
		
		@Override
		public boolean isTileOccupied() {
			return false;
		}
		
		/**
		 * Method that returns null because an empty tile does not contain a piece.
		 * @return null.
		 */
		
		@Override
		public Piece getPiece() {
			return null;
		}
	}
	
	/**
	 * Nested subclass that creates an occupied tile. 
	 * @author Gerwin Terpstra
	 * @version 1.0
	 * @since 02-18-2021
	 */
	public static final class OccupiedTile extends Tile {
		
		// Declare variable.
		private final Piece pieceOnTile;
		
		// Constructor		
		private OccupiedTile(int tileCoordinate, final Piece pieceOnTile) {
			super(tileCoordinate);
			this.pieceOnTile = pieceOnTile;
		}
		
		/**
		 * Method that returns a boolean true because an occupied tile is occupied.
		 * @return boolean true.
		 */
		@Override
		public boolean isTileOccupied() {
			return true;
		}
		
		/**
		 * Method that returns the piece that is occupying this tile.
		 * @return Piece the piece that is occupying this tile.
		 */
		@Override
		public Piece getPiece() {
			return this.pieceOnTile;
		}
	}	
}
