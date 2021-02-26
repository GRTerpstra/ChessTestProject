// User-defined package.
package com.chessTestProject.engine.board;

// Imported user-defined classes.
import com.chessTestProject.engine.Alliance;
import com.chessTestProject.engine.pieces.*;
import com.chessTestProject.engine.player.BlackPlayer;
import com.chessTestProject.engine.player.Player;
import com.chessTestProject.engine.player.WhitePlayer;

// Imported built-in classes.
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class that builds the gameboard and initializes everything on it.
 * @author Gerwin Terpstra.
 * @version 1.0.
 * @since 02-17-2021.
 */
public class Board {		
	
	// Declare member variables.
	private final List<Tile> gameBoard;
	private final Collection<Piece> whitePieces;
	private final Collection<Piece> blackPieces;	
	private final WhitePlayer whitePlayer;
	private final BlackPlayer blackPlayer;
	private final Player currentPlayer;
	
	// Constructor.
	private Board(final Builder builder) {
		// Initialize member variables.
		this.gameBoard = createGameBoard(builder);
		this.whitePieces = calculateActivePieces(this.gameBoard, Alliance.WHITE);
		this.blackPieces = calculateActivePieces(this.gameBoard, Alliance.BLACK);	
		
		// Declare and initialize local variables.
		final Collection<Move> whiteStandardLegalMoves = calculateLegalMoves(this.whitePieces);
		final Collection<Move> blackStandardLegalMoves = calculateLegalMoves(this.blackPieces);
		
		// Initialize member variables with the now initialized local variables.
		this.whitePlayer = new WhitePlayer(this, whiteStandardLegalMoves, blackStandardLegalMoves);
		this.blackPlayer = new BlackPlayer(this, whiteStandardLegalMoves, whiteStandardLegalMoves);
		this.currentPlayer = builder.nextMoveMaker.choosePlayer(this.whitePlayer, this.blackPlayer);
	}
	
	/**
	 * Method that builds the board, together with all its pieces, with the String type. 
	 * The method overrides the built-in method of String.toString().
	 * @return String builder the board in a String type.
	 */
	@Override
	public String toString() {
		// Declare and initialize local variables.
		final StringBuilder builder = new StringBuilder();
		
		for(int i = 0; i < BoardUtils.NUM_TILES; i++) {
			final String tileText = this.gameBoard.get(i).toString();
			builder.append(String.format("%3s", tileText));
			if((i + 1) % BoardUtils.NUM_TILES_PER_ROW == 0) {
				builder.append("\n");
			}
		}
		return builder.toString();
	}
		
	/**
	 * Method that returns the player that is playing as white.
	 * @return WhitePlayer whitePlayer the player that is playing a the white alliance.
	 */
	public Player whitePlayer() {
		return this.whitePlayer;
	}
	
	/**
	 * Method that returns the player that is playing as white.
	 * @return WhitePlayer whitePlayer the player that is playing a the white alliance.
	 */
	public Player blackPlayer()  {
		return this.blackPlayer;
	}
	
	/**
	 * Method that returns the player who's turn it is.
	 * @return Player currentPlayer the player who's turn it is.
	 */
	public Player currentPlayer() {
		return this.currentPlayer;
	}
	
	/**
	 * Method that returns a list of all of black's pieces on the board.
	 * @return List<Piece> blackPieces all of black's pieces that are currently on the board.
	 */	
	public Collection<Piece> getBlackPieces() {
		return this.blackPieces;
	}
	
	/**
	 * Method that returns a list of all of white's pieces on the board.
	 * @return List<Piece> whitePieces all of white's pieces that are currently on the board.
	 */	
	public Collection<Piece> getWhitePieces() {
		return this.whitePieces;
	}

	/**
	 * Method that returns a list of all the legal moves that any of the pieces on the board can make.
	 * @param pieces List<Piece> pieces a list of all the pieces on the board.
	 * @return List<Move> legalMoves all the legals moves that any of the pieces, that are currently on the board, can make.
	 */
	private Collection<Move> calculateLegalMoves(final Collection<Piece> pieces) {	
		// Declare and initialize local variable.
		final List<Move> legalMoves = new ArrayList<>();
		
		for(final Piece piece : pieces) {
			legalMoves.addAll(piece.calculateLegalMoves(this));
		}		
		return legalMoves;
	}

	/**
	 * Method that returns a list of all the pieces of a specific alliance/color that are still on the board.
	 * @param List<Tile> gameBoard a list of all the tiles from the board.
	 * @param Alliance alliance the alliance/color of the pieces you want to get.
	 * @return List<Piece> a list of all the active pieces of a specific alliance/color.
	 */
	private Collection<Piece> calculateActivePieces(final List<Tile> gameBoard, 
													final Alliance alliance) {
		// Declare and initialize local variable.
		final List<Piece> activePieces = new ArrayList<>();
		
		for(final Tile tile: gameBoard) {
			if(tile.isTileOccupied()) {
				final Piece piece = tile.getPiece();
				if(piece.getPieceAlliance() == alliance) {
					activePieces.add(piece);
				}
			}
		}
		return activePieces;
	}
	
	/**
	 * Method that returns a specific tile from the board.
	 * @param int titleCoordinate the coordinate of the tile you want to get.
	 * @return Tile the tile that is on the given coordinate.
	 */
	public Tile getTile(final int titleCoordinate) {
		return gameBoard.get(titleCoordinate);			
	}
	
	/**
	 * Method that creates a state of a game board and returns a list of all the tiles of the board 
	 * with pieces on them that the builder class passed through.
	 * @param Builder builder an object of the Builder class that will pass all the pieces through.
	 * @return List<Tile> tiles a list of all the tiles of the board of which some with pieces on them.
	 */
	private static List<Tile> createGameBoard(final Builder builder) {
		// Declare and initialize local variables.
		final Tile[] tiles = new Tile[BoardUtils.NUM_TILES];
		
		for(int i = 0; i < BoardUtils.NUM_TILES; i++) {
			tiles[i] = Tile.createTile(i, builder.boardConfig.get(i));
		}
		return Arrays.asList(tiles);
	}

	/**
	 * Method that creates a fresh board with all the pieces in their starting position.
	 * @return Board the board initialized with all the pieces at their starting position.
	 */
	public static Board createStandardBoard() {
		//Declare and initialize local variables.
		final Builder builder = new Builder();
		
		// Black pieces.
		builder.setPiece(new Rook(0, Alliance.BLACK));
		builder.setPiece(new Knight(1, Alliance.BLACK));
		builder.setPiece(new Bishop(2, Alliance.BLACK));
		builder.setPiece(new Queen(3, Alliance.BLACK));
		builder.setPiece(new King(4, Alliance.BLACK));
		builder.setPiece(new Bishop(5, Alliance.BLACK));
		builder.setPiece(new Knight(6, Alliance.BLACK));
		builder.setPiece(new Rook(7, Alliance.BLACK));
		builder.setPiece(new Pawn(8, Alliance.BLACK));
		builder.setPiece(new Pawn(9, Alliance.BLACK));
		builder.setPiece(new Pawn(10, Alliance.BLACK));
		builder.setPiece(new Pawn(11, Alliance.BLACK));
		builder.setPiece(new Pawn(12, Alliance.BLACK));
		builder.setPiece(new Pawn(13, Alliance.BLACK));
		builder.setPiece(new Pawn(14, Alliance.BLACK));
		builder.setPiece(new Pawn(15, Alliance.BLACK));
		
		// White pieces.
		builder.setPiece(new Pawn(48, Alliance.WHITE));
		builder.setPiece(new Pawn(49, Alliance.WHITE));
		builder.setPiece(new Pawn(50, Alliance.WHITE));
		builder.setPiece(new Pawn(51, Alliance.WHITE));
		builder.setPiece(new Pawn(52, Alliance.WHITE));
		builder.setPiece(new Pawn(53, Alliance.WHITE));
		builder.setPiece(new Pawn(54, Alliance.WHITE));
		builder.setPiece(new Pawn(55, Alliance.WHITE));
		builder.setPiece(new Rook(56, Alliance.WHITE));
		builder.setPiece(new Knight(57, Alliance.WHITE));
		builder.setPiece(new Bishop(58, Alliance.WHITE));
		builder.setPiece(new Queen(59, Alliance.WHITE));
		builder.setPiece(new King(60, Alliance.WHITE));
		builder.setPiece(new Bishop(61, Alliance.WHITE));
		builder.setPiece(new Knight(62, Alliance.WHITE));
		builder.setPiece(new Rook(63, Alliance.WHITE));
		builder.setMoveMaker(Alliance.WHITE);
		return builder.build();
	}
	
	/**
	 * Class that helps creating and initializing a game board.
	 * @author Gerwin Terpstra.
	 * @version 1.0.
	 * @since 02-21-2021.
	 */
	public static class Builder {
		// Declare member variables.
		Map<Integer, Piece> boardConfig;
		Alliance nextMoveMaker;
		Pawn enPassantPawn;
		
		// Constructor
		public Builder() {
			// initialize member variables.
			this.boardConfig = new HashMap<>();
		}
			
		/**
		 * Method that links a piece to its coordinate and put them in a HashMap.
		 * @param Piece piece an object of the Piece class.
		 * @return Builder updated version of the builder object.
		 */
		public Builder setPiece(final Piece piece) {
			this.boardConfig.put(piece.getPiecePosition(), piece);
			return this;
		}
		
		/**
		 * Method that defines and changes which of the alliances/colors its turn it is.
		 * @param Alliance nextMoveMaker the alliance/color that has to make the next move.
		 * @return Builder updated version of the builder object.
		 */
		public Builder setMoveMaker(final Alliance nextMoveMaker) {
			this.nextMoveMaker = nextMoveMaker;
			return this;
		}
		
		/**
		 * Method that returns a new object of the Board class with this object of the Build class with its current values.
		 * @return Board a new board initialized with the values given from this object of the Build class.
		 */
		public Board build() {
			return new Board(this);
		}

		/**
		 * Method that defines and changes a pawn that just made a 'pawn jump', so it can be taken with an 'en passant' move.
		 * @param Pawn enPassantPawn a pawn that just made a 'pawn jump'
		 */
		public void setEnPassantPawn(Pawn enPassantPawn) {
			this.enPassantPawn = enPassantPawn;
		}
	}

	/**
	 * Method that returns an ArrayList with all the legal moves that every piece on the board can make.
	 * @return ArrayList<Move> allLegalMoves an ArrayList of all the legal moves that can be made in the current state of the board. 
	 */
	public Iterable<Move> getAllLegalMoves() {
		List<Move> allLegalMoves = new ArrayList<>();
		allLegalMoves.addAll(this.whitePlayer.getLegalMoves());
		allLegalMoves.addAll(this.blackPlayer.getLegalMoves());
		return allLegalMoves;
	}	
}
