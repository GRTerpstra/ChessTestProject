// User-defined package.
package com.chessTestProject.engine.board;

// Imported built-in packages.
import java.util.HashMap;
import java.util.Map;

import com.chessTestProject.engine.pieces.Piece;

/**
 * Abstract class that creates the tiles of a board.
 * @author Gerwin Terpstra.
 * @version 1.0.
 * @since 02-18-2021.
 */
public abstract class Tile {

	// Declare member variables.
	protected final int tileCoordinate;	
	
	// Declare and initializes member variables.
	private static final Map<Integer, EmptyTile> EMPTY_FILE_CACHE = createAllPossibleEmptyTiles();
	
	// Constructor.
	private Tile(final int tileCoordinate) {
		this.tileCoordinate = tileCoordinate;
	}
	
	// Declare abstract methods.
	public boolean isTileOccupied() {
		// TODO Auto-generated method stub
		return false;
	}	
	public abstract Piece getPiece();
	
	/**
	 * Method that creates a map of empty tiles that resembles a playing board.
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
	 * Method that creates a tile that is either empty or occupied with a piece.
	 * @param int tileCoordinate coordinate that the tile should have.
	 * @param Piece piece an optional piece that could occupy the tile.
	 * @return OccupiedTile a tile with a piece on it OR EmptyTile a tile with no piece on it.
	 */
	public static Tile createTile(final int tileCoordinate, final Piece piece) {
		return piece != null ? new OccupiedTile(tileCoordinate, piece) : EMPTY_FILE_CACHE.get(tileCoordinate);
	}
	
	/**
	 * Nested subclass that creates an empty tile.
	 * @author Gerwin Terpstra.
	 * @version 1.0.
	 * @since 02-18-2021.
	 */
	public static final class EmptyTile extends Tile {
		
		// Constructor.	
		private EmptyTile(final int coordinate) {
			super(coordinate);
		}
		
		@Override
		public String toString() {
			return "-";
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
	 * @author Gerwin Terpstra.
	 * @version 1.0.
	 * @since 02-18-2021.
	 */
	public static final class OccupiedTile extends Tile {
		
		// Declare variable.
		private final Piece pieceOnTile;
		
		// Constructor.		
		private OccupiedTile(int tileCoordinate, final Piece pieceOnTile) {
			super(tileCoordinate);
			this.pieceOnTile = pieceOnTile;
		}
		
		@Override
		public String toString() {
			return getPiece().getPieceAlliance().isBlack() ? getPiece().toString().toLowerCase() :
				getPiece().toString();
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
