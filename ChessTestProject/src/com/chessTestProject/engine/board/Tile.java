// User-defined package.
package com.chessTestProject.engine.board;

// Imported user-defined classes.
import com.chessTestProject.engine.pieces.Piece;

// Imported built-in classes.
import java.util.HashMap;
import java.util.Map;

/**
 * Abstract class that defines and creates a tile of a board.
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
	public abstract Piece getPiece();
	
	/**
	 * Helper method that returns a boolean false by default.
	 * @return boolean false.
	 */
	public boolean isTileOccupied() {
		return false;
	}	
	
	/**
	 * Method that returns the coordinate of the tile.
	 * @return int the coordinate of this Tile object.
	 */
	public int getTileCoordinate() {
		return this.tileCoordinate;
	}
	
	/**
	 * Method that creates a map of empty tiles that resembles a playing board.
	 * @return Map of 64 EmptyTile objects.
	 */
	private static Map<Integer, EmptyTile> createAllPossibleEmptyTiles() {
		final Map<Integer, EmptyTile> emptyTileMap = new HashMap<>();		
		for(int i = 0; i < BoardUtils.NUM_TILES; i++) {
			emptyTileMap.put(i, new EmptyTile(i));
		}
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
		
		/**
		 * Overridden method that returns a hyphen of the String type to represent an empty tile.
		 * return String a hyphen that represents an empty tile.
		 */
		@Override
		public String toString() {
			return "-";
		}
		
		/**
		 * Helper method that returns a boolean false by default.
		 * @return boolean false.
		 */
		@Override
		public boolean isTileOccupied() {
			return false;
		}
		
		/**
		 * Helper method that returns a null by default.
		 * @return null.
		 */
		@Override
		public Piece getPiece() {
			return null;
		}
	}
	
	/**
	 * Nested subclass that defines and creates an occupied tile. 
	 * @author Gerwin Terpstra.
	 * @version 1.0.
	 * @since 02-18-2021.
	 */
	public static final class OccupiedTile extends Tile {
		// Declare member variables.
		private final Piece pieceOnTile;
		
		// Constructor.		
		private OccupiedTile(int tileCoordinate, final Piece pieceOnTile) {
			super(tileCoordinate);
			// Initialize member variables.
			this.pieceOnTile = pieceOnTile;
		}
		
		/**
		 * Overridden method that returns a String representing a tile occupied with a piece.
		 * If the piece belongs to the black alliance it will be in lowercase, otherwise it will be in uppercase.
		 * The value of the String depends on the piece that occupies the tile (which is defined in the PieceType enum).
		 * @return String representing a tile occupied with a pice.
		 */
		@Override
		public String toString() {
			return getPiece().getPieceAlliance().isBlack() ? getPiece().toString().toLowerCase() :
				getPiece().toString();
		}
		
		/**
		 * Helper method that returns a boolean true by default.
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
