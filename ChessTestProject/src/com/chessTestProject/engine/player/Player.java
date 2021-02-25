// User-defined package.
package com.chessTestProject.engine.player;

// Imported user-defined packages.
import com.chessTestProject.engine.Alliance;
import com.chessTestProject.engine.board.Board;
import com.chessTestProject.engine.board.Move;
import com.chessTestProject.engine.pieces.King;
import com.chessTestProject.engine.pieces.Piece;

// Imported built-in packages.
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Abstract class that is the framework for a player.
 * @author Gerwin Terpstra.
 * @version 1.0
 * @since 02-23-2021.
 */
public abstract class Player {
	// Declare member variables.
	protected final Board board;
	protected final King playerKing;
	protected final Collection<Move> legalMoves;
	private final boolean isIncheck;
	
	// Constructor.
	Player(final Board board,
			final Collection<Move> legalMoves,
			final Collection<Move> opponentMoves) {
		
		// Initialize member variables.
		this.board = board;
		this.playerKing = establishKing();
		this.legalMoves = legalMoves;
		// XXX Doing this concatenation different than the guide!
		this.legalMoves.addAll(calculateKingCastles(legalMoves,opponentMoves));
		this.isIncheck = !Player.calculateAttacksOnTile(this.playerKing.getPiecePosition(), opponentMoves).isEmpty();
	}

	/**
	 * Method that returns the king piece of this player.
	 * @return King an instance of King that belongs to this instance of Player.
	 */
	public King getPlayerKing() {
		return this.playerKing;
	}
	
	/**
	 * Method that returns a collection of of all the legal moves this player can make
	 * in the current state of the board.
	 * @return Collection<Move> a collection of all the moves this player can make.
	 */
	public Collection<Move> getLegalMoves() {
		return this.legalMoves;
	}
	
	/**
	 * Method that checks if any of the moves from the given collection have a possible destination coordinate
	 * of the given position coordinate, and then return those moves in a list.
	 * @param int piecePosition the position coordinate that is getting checked for being a possible destination of other moves.
	 * @param Collection<Move> moves a collection of moves.
	 * @return List<Move> a list of Moves that have the given position coordinate as possible destination coordinate.
	 */
	protected static Collection<Move> calculateAttacksOnTile(int piecePosition, Collection<Move> moves) {
		final List<Move> attackMoves = new ArrayList<>();
		for(final Move move : moves) {
			if(piecePosition == move.getDestinationCoordinate()) {
				attackMoves.add(move);
			}
		}
		return attackMoves;
	}

	/**
	 * Method that returns the king of a player.
	 * @return King piece an instance of King.
	 */
	private King establishKing() {
		for(final Piece piece : getActivePieces()) {
			if(piece.getPieceType().isKing() ) {
				return (King) piece;
			}
		}
		throw new RuntimeException("Should not reach here! Not a valid board!");
	}

	/**
	 * Method that checks if the given move is a legal move.
	 * @param Move move the move that is going to get checked.
	 * @return boolean true if the move is a legal move, false otherwise.
	 */
	public boolean isMoveLegal(final Move move) {
		return this.legalMoves.contains(move);
	}
	
	/**
	 * Method that checks if the king of the player is in check.
	 * @return boolean true if the player's king is in check, false otherwise.
	 */
	public boolean isInCheck() {
		return this.isIncheck;
	}
	
	/**
	 * Method that checks if the king of the player is in checkmate.
	 * @return boolean true if the player's king is in checkmate, false otherwise.
	 */
	public boolean isInCheckMate() {
		return this.isIncheck && !hasEscapeMoves();
	}
	
	/**
	 * Method that checks if a there is a stalemate; the player's king is not in check, 
	 * but the player has no legal moves left.
	 * @return boolean true if the player's king is not in check and the player has no legal moves left, false otherwise.
	 */
	public boolean isInStaleMate() {
		return !this.isIncheck && !hasEscapeMoves();
	}
	
	/**
	 * Helper method that returns a boolean false by default.
	 * @return boolean false.
	 */
	public boolean isCastled() {
		return false;
	}
	
	/**
	 * Method that checks if a player has any legal moves left.
	 * @return boolean true if the player has legal moves to do, false otherwise.
	 */
	protected boolean hasEscapeMoves() {
		for(final Move move : this.legalMoves) {
			// Declare and initialize local variables.
			final MoveTransition transition = makeMove(move);
			if(transition.getMoveStatus().isDone()) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Method that simulates a move and the condition of the board before and after the move.
	 * @param Move move a move that is going to be simulated.
	 * @return MoveTransition a new instance of MoveTransition containing the result of the simulation.
	 */
	public MoveTransition makeMove(final Move move) {
		if(!isMoveLegal(move)) {
			return new MoveTransition(this.board, move, MoveStatus.ILLEGAL_MOVE);
		}
		// Declare and initialize local variables.
		final Board transitionBoard = move.execute();
		final Collection<Move> kingAttacks = Player.calculateAttacksOnTile(transitionBoard.currentPlayer().getOpponent().getPlayerKing().getPiecePosition(),
											transitionBoard.currentPlayer().getLegalMoves());
		
		if(!kingAttacks.isEmpty()) {
			return new MoveTransition(this.board, move, MoveStatus.LEAVE_PLAYER_IN_CHECK);
		}		
		return new MoveTransition(transitionBoard, move, MoveStatus.DONE);		
	}
	
	// Declare abstract methods.
	public abstract Collection<Piece> getActivePieces();
	public abstract Alliance getAlliance();
	public abstract Player getOpponent();
	protected abstract Collection<Move> calculateKingCastles(Collection<Move> playerLegals, Collection<Move> opponentsLegals);
}
