// User-defined package.
package com.chessTestProject.engine.board;

// Imported user-defined packages.
import com.chessTestProject.engine.Alliance;
import com.chessTestProject.engine.pieces.*;

import java.util.Arrays;
import java.util.List;
// Imported built-in packages.
import java.util.Map;

/**
 * TODO
 * @author Gerwin Terpstra.
 * @version 1.0.
 * @since 02-17-2021.
 */
public class Board {		
	
	// Declare variables.
	private final List<Tile> gameBoard;
	
	// Constructor.
	private Board(Builder builder) {
		this.gameBoard = createGameBoard(builder);
		
	}
	
	public Tile getTile(final int titleCoordinate) {
		return null;			
	}
	
	private static List<Tile> createGameBoard(final Builder builder) {
		final Tile[] tiles = new Tile[BoardUtils.NUM_TILES];
		for(int i = 0; i < BoardUtils.NUM_TILES; i++) {
			tiles[i] = Tile.createTile(i, builder.boardConfig.get(i));
		}
		return Arrays.asList(tiles);
	}

	public static Board createStandardBoard() {
		return null;
	}
	
	public static class Builder {
		
		Map<Integer, Piece> boardConfig;
		Alliance nextMoveMaker;
		
		public Builder() {
		}
			
		public Builder setPiece(final Piece piece) {
			this.boardConfig.put(piece.getPiecePosition(), piece);
			return this;
		}
		
		public Builder setMoveMaker(final Alliance nextMoveMaker) {
			this.nextMoveMaker = nextMoveMaker;
			return this;
		}
		
		public Board build() {
			return new Board(this);
		}
	}	
}
