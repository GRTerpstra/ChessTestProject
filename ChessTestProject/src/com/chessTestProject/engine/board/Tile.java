// User-defined package

package com.chessTestProject.engine.board;

// Imported built-in packages

import java.util.HashMap;
import java.util.Map;

import com.chessTestProject.engine.pieces.Piece;

public abstract class Tile {

	// Variables
	
	protected final int tileCoordinate;	
	private static final Map<Integer, EmptyTile> EMPTY_FILE_CACHE = createAllPossibleEmptyTiles();

	// Method: create all empty tiles and put them in a hashMap
	
	private static Map<Integer, EmptyTile> createAllPossibleEmptyTiles() {
		
		final Map<Integer, EmptyTile> emptyTileMap = new HashMap<>();
		
		for(int i = 0; i < BoardUtils.NUM_TILES; i++) {
			emptyTileMap.put(i, new EmptyTile(i));
		}
		
		// !!! Maybe use the class ImmutableMap for this return statement (See video 2) !!!
		
		return emptyTileMap;
	}
	
	public static Tile createTile(final int tileCoordinate, final Piece piece) {
		return piece != null ? new OccupiedTile(tileCoordinate, piece) : EMPTY_FILE_CACHE.get(tileCoordinate);
	}
	
	// Constructor	
	
	private Tile(final int tileCoordinate) {
		this.tileCoordinate = tileCoordinate;
	}
	
	// Methods	
	
	public abstract boolean isTileOccupied();
	
	public abstract Piece getPiece();
	
	// Subclass for an empty tile
	
	public static final class EmptyTile extends Tile {
		
		// Constructor
		
		private EmptyTile(final int coordinate) {
			super(coordinate);
		}
		
		// Methods
		
		@Override
		public boolean isTileOccupied() {
			return false;
		}
		
		@Override
		public Piece getPiece() {
			return null;
		}
	}
	
	// Subclass for an occupied tile.
	
	public static final class OccupiedTile extends Tile {
		
		// Variables
		
		private final Piece pieceOnTile;
		
		// Constructor
		
		private OccupiedTile(int tileCoordinate, final Piece pieceOnTile) {
			super(tileCoordinate);
			this.pieceOnTile = pieceOnTile;
		}
		
		// Methods
		
		@Override
		public boolean isTileOccupied() {
			return true;
		}
		
		@Override
		public Piece getPiece() {
			return this.pieceOnTile;
		}
		
	}
	
}
