package com.chessTestProject.engine.player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.chessTestProject.engine.Alliance;
import com.chessTestProject.engine.board.Board;
import com.chessTestProject.engine.board.Move;
import com.chessTestProject.engine.pieces.King;
import com.chessTestProject.engine.pieces.Piece;

public abstract class Player {
	protected final Board board;
	protected final King playerKing;
	protected final Collection<Move> legalMoves;
	private final boolean isIncheck;
	
	Player(final Board board,
			final Collection<Move> legalMoves,
			final Collection<Move> opponentMoves) {
		
		this.board = board;
		this.playerKing = establishKing();
		this.legalMoves = legalMoves;
		// XXX Doing this concatenation different than the guide!
		this.legalMoves.addAll(calculateKingCastles(legalMoves,opponentMoves));
		this.isIncheck = !Player.calculateAttacksOnTile(this.playerKing.getPiecePosition(), opponentMoves).isEmpty();
	}

	public King getPlayerKing() {
		return this.playerKing;
	}
	
	public Collection<Move> getLegalMoves() {
		return this.legalMoves;
	}
	
	protected static Collection<Move> calculateAttacksOnTile(int piecePosition, Collection<Move> moves) {
		final List<Move> attackMoves = new ArrayList<>();
		for(final Move move : moves) {
			if(piecePosition == move.getDestinationCoordinate()) {
				attackMoves.add(move);
			}
		}
		return attackMoves;
	}

	private King establishKing() {
		for(final Piece piece : getActivePieces()) {
			if(piece.getPieceType().isKing() ) {
				return (King) piece;
			}
		}
		throw new RuntimeException("Should not reach here! Not a valid board!");
	}

	public boolean isMoveLegal(final Move move) {
		return this.legalMoves.contains(move);
	}
	
	// TODO Implement these methods.
	public boolean isInCheck() {
		return this.isIncheck;
	}
	
	public boolean isInCheckMate() {
		return this.isIncheck && !hasEscapeMoves();
	}
	
	public boolean isInStaleMate() {
		return !this.isIncheck && !hasEscapeMoves();
	}
	
	public boolean isCastled() {
		return false;
	}
	
	protected boolean hasEscapeMoves() {
		for(final Move move : this.legalMoves) {
			final MoveTransition transition = makeMove(move);
			if(transition.getMoveStatus().isDone()) {
				return true;
			}
		}
		return false;
	}
	
	public MoveTransition makeMove(final Move move) {
		
		if(!isMoveLegal(move)) {
			return new MoveTransition(this.board, move, MoveStatus.ILLEGAL_MOVE);
		}
		
		final Board transitionBoard = move.execute();
		
		final Collection<Move> kingAttacks = Player.calculateAttacksOnTile(transitionBoard.currentPlayer().getOpponent().getPlayerKing().getPiecePosition(),
											transitionBoard.currentPlayer().getLegalMoves());
		
		if(!kingAttacks.isEmpty()) {
			return new MoveTransition(this.board, move, MoveStatus.LEAVE_PLAYER_IN_CHECK);
		}
		
		return new MoveTransition(transitionBoard, move, MoveStatus.DONE);		
	}
	
	public abstract Collection<Piece> getActivePieces();
	public abstract Alliance getAlliance();
	public abstract Player getOpponent();
	protected abstract Collection<Move> calculateKingCastles(Collection<Move> playerLegals, Collection<Move> opponentsLegals);
	
}
