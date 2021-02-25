// User-defined package.
package com.chessTestProject.engine.board;

/**
 * Class that creates the logic of the board. * 
 * @author Gerwin Terpstra.
 * @version 1.0.
 * @since 02-18-2021.
 */
public class BoardUtils {
	// Declare and initialize member constant variables.
	public static final int NUM_TILES = 64;
	public static final int NUM_TILES_PER_ROW = 8;
	public static final boolean[] FIRST_COLUMN = initColumn(0);	
	public static final boolean[] SECOND_COLUMN = initColumn(1);
	public static final boolean[] SEVENTH_COLUMN = initColumn(6);
	public static final boolean[] EIGHT_COLUMN = initColumn(7);	
	public static final boolean[] SECOND_ROW = initRow(8);
	public static final boolean[] SEVENTH_ROW = initRow(48);
	
	// Constructor.
	private BoardUtils() {
		throw new RuntimeException("You cannot instantiate me!");
	}
		
	/**
	 * Method that creates and returns an array of booleans containing eight 'trues' which represent
	 * the eight tiles of a column.
	 * @param int columnNumber the coordinate where the column starts (the first tile of the row).
	 * @return boolean[] column array of eight boolean 'trues'.
	 */
	private static boolean[] initColumn(int columnNumber) {
		final boolean[] column = new boolean[NUM_TILES];	
		do {			
			column[columnNumber] = true;
			columnNumber += NUM_TILES_PER_ROW;
		} while(columnNumber <64);
		return column;
	}
	
	/**
	 * Method that creates and returns an array of booleans containing eight 'trues' which represent
	 * the eight tiles of a row.
	 * @param int rowNumber the coordinate where the row starts (the first tile of the row).
	 * @return boolean[] row array of eight boolean 'trues'.
	 */
	private static boolean[] initRow(int rowNumber) {
		final boolean[] row = new boolean[NUM_TILES];
		do {
			row[rowNumber] = true;
			rowNumber++;
		} while(rowNumber % NUM_TILES_PER_ROW != 0);
		return row;
	}
	
	/**
	 * The isValidTileCoordinate method checks if a given coordinate is between the extreme coordinates
	 * of the board.
	 * @param int coordinate of a tile.
	 * @return boolean true if the coordinate is valid, false otherwise.
	 */
	public static boolean isValidTileCoordinate(final int coordinate) {
		return coordinate >= 0 && coordinate < NUM_TILES;
		}
}
