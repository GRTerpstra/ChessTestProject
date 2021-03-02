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
 * Subclass that is the framework of a player that is playing as black.
 * @author Gerwin Terpstra
 * @version 1.0
 * @since 02-23-2021.
 */
public class BlackPlayer extends Player {
	// Constructor.
	public BlackPlayer(final Board board,
						final Collection<Move> whiteStandardLegalMoves,
						final Collection<Move> blackStandardLegalMoves) {
		super(board, blackStandardLegalMoves, whiteStandardLegalMoves);
	}

	/**
	 * Overridden method that returns all the pieces of this player that are still in play.
	 * @return Collection<Piece> all black's pieces that are still in play.
	 */
	@Override
	public Collection<Piece> getActivePieces() {
		return this.board.getBlackPieces();
	}

	/**
	 * Overridden method that returns the BLACK constant from the Alliance enum.
	 * @return Alliance BLACK the constant of the Alliance enum that belong to the black alliance.
	 */
	@Override
	public Alliance getAlliance() {
		return Alliance.BLACK;
	}

	/**
	 * Overridden method that returns the opponent of the player playing as black.
	 * @return Player the player playing as white.
	 */
	@Override
	public Player getOpponent() {
		return this.board.whitePlayer();
	}
	
	@Override
	public String toString() {
		return Alliance.BLACK.toString();
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
			if(!this.board.getTile(5).isTileOccupied() && !this.board.getTile(6).isTileOccupied()) {
				final Tile rookTile = this.board.getTile(7);
				if(rookTile.isTileOccupied() && rookTile.getPiece().isFirstMove()) {
					if(Player.calculateAttacksOnTile(5, opponentsLegals).isEmpty() &&
						Player.calculateAttacksOnTile(6, opponentsLegals).isEmpty() &&
						rookTile.getPiece().getPieceType().isRook()) {
					kingCastles.add(new Move.KingSideCastleMove(this.board,
																this.playerKing,
																6,
																(Rook) rookTile.getPiece(),
																rookTile.getTileCoordinate(), 
																5));
					}
				}
			}
			if(!this.board.getTile(1).isTileOccupied() && 
				!this.board.getTile(2).isTileOccupied() &&
				!this.board.getTile(3).isTileOccupied()) {
				// Declare and initialize local variables.
				final Tile rookTile = this.board.getTile(0);
				
				if(rookTile.isTileOccupied() && rookTile.getPiece().isFirstMove() &&
					Player.calculateAttacksOnTile(2,  opponentsLegals).isEmpty() &&
					Player.calculateAttacksOnTile(3,  opponentsLegals).isEmpty() &&
					rookTile.getPiece().getPieceType().isRook()) {
					kingCastles.add(new Move.QueenSideCastleMove(this.board,
																	this.playerKing,
																	2,
																	(Rook) rookTile.getPiece(),
																	rookTile.getTileCoordinate(), 
																	3));
				}
			}
		}
		return kingCastles;
	}
}
