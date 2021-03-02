// User-defined package.
package com.chessTestProject.engine.player;

// Imported user-defined classes.
import com.chessTestProject.engine.Alliance;
import com.chessTestProject.engine.board.Board;
import com.chessTestProject.engine.board.Move;
import com.chessTestProject.engine.board.Tile;
import com.chessTestProject.engine.pieces.Piece;
import com.chessTestProject.engine.pieces.Rook;

// Imported built-in classes.
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Subclass that is the framework of a player that is playing as white.
 * @author Gerwin Terpstra
 * @version 1.0
 * @since 02-23-2021.
 */
public class WhitePlayer extends Player {
	// Constructor.
	public WhitePlayer(final Board board,
						final Collection<Move> whiteStandardLegalMoves,
						final Collection<Move> blackStandardLegalMoves) {
		super(board, whiteStandardLegalMoves, blackStandardLegalMoves);
	}
	
	/**
	 * Overridden method that returns all the pieces of this player that are still in play.
	 * @return Collection<Piece> all white's pieces that are still in play.
	 */
	@Override
	public Collection<Piece> getActivePieces() {
		return this.board.getWhitePieces();
	}
	
	/**
	 * Overridden method that returns the WHITE constant from the Alliance enum.
	 * @return Alliance WHITE the constant of the Alliance enum that belong to the white alliance.
	 */
	@Override
	public Alliance getAlliance() {
		return Alliance.WHITE;
	}

	/**
	 * Overridden method that returns the opponent of the player playing as white.
	 * @return Player the player playing as black.
	 */
	@Override
	public Player getOpponent() {
		return this.board.blackPlayer();
	}
	
	@Override
	public String toString() {
		return Alliance.WHITE.toString();
	}

	
	/**
	 * Overridden method that checks if the player can castle, and returns a list of possible castle moves.
	 * @param Collection<Move> playerLegals TODO useless?
	 * @param Collection<Move> opponentsLegals a Collection of all the legal moves the opponent can make.
	 * @return List<Move> List of all the legal castle moves the player can make.
	 */
	@Override
	public Collection<Move> calculateKingCastles(final Collection<Move> playerLegals, 
													final Collection<Move> opponentsLegals) {
		// Declare and initialize local variables.
		final List<Move> kingCastles = new ArrayList<>();
		
		if(this.playerKing.isFirstMove() && !this.isInCheck()) {
			if(!this.board.getTile(61).isTileOccupied() && !this.board.getTile(62).isTileOccupied()) {
				final Tile rookTile = this.board.getTile(63);
				if(rookTile.isTileOccupied() && rookTile.getPiece().isFirstMove()) {
					if(Player.calculateAttacksOnTile(61, opponentsLegals).isEmpty() &&
						Player.calculateAttacksOnTile(62, opponentsLegals).isEmpty() &&
						rookTile.getPiece().getPieceType().isRook()) {
					kingCastles.add(new Move.KingSideCastleMove(this.board, 
																this.playerKing, 
																62, 
																(Rook)rookTile.getPiece(), 
																rookTile.getTileCoordinate(), 
																61));
					}
				}
			}
			if(!this.board.getTile(59).isTileOccupied() && 
				!this.board.getTile(58).isTileOccupied() &&
				!this.board.getTile(57).isTileOccupied()) {
				// Declare and initialize local variables
				final Tile rookTile = this.board.getTile(56);	
				
				if(rookTile.isTileOccupied() && rookTile.getPiece().isFirstMove() &&
				Player.calculateAttacksOnTile(58,  opponentsLegals).isEmpty() &&
				Player.calculateAttacksOnTile(59,  opponentsLegals).isEmpty() &&
				rookTile.getPiece().getPieceType().isRook()) {
					kingCastles.add(new Move.QueenSideCastleMove(this.board, 
																this.playerKing, 
																58, 
																(Rook)rookTile.getPiece(),
																rookTile.getTileCoordinate(), 
																59));
				}
			}
		}
		return kingCastles;
	}
}
