// User-defined package.
package com.chessTestProject.engine.pieces;

// Imported user-defined classes.
import com.chessTestProject.engine.Alliance;
import com.chessTestProject.engine.board.Board;
import com.chessTestProject.engine.board.BoardUtils;
import com.chessTestProject.engine.board.Move;
import com.chessTestProject.engine.pieces.Piece.PieceType;

// Imported built-in classes.
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Class that creates a pawn and defines all the legal moves a pawn can make.
 * @author Gerwin Terpstra.
 * @version 1.0.
 * @since 02-19-2021.
 */
public class Pawn extends Piece {
	
	// Declare and initialize constant member variable.
	private final static int[] CANDIDATE_MOVE_COORDINATE = {8, 16, 7, 9};
	
	// Constructor.
	public Pawn(final Alliance pieceAlliance,
				final int piecePosition) {
		super(PieceType.PAWN, pieceAlliance, piecePosition, true);
	}	
	public Pawn(final Alliance pieceAlliance,
				final int piecePosition,
				final boolean isFirstMove) {
		super(PieceType.PAWN, pieceAlliance, piecePosition, isFirstMove);
	}
	
	/**
	 * Method that defines and returns a list of all the legal moves a pawn can make distinguished by a normal move and an attacking move.
	 * @param final Board board the board where the piece is going to play on.
	 * @return List<Move> collection of all the legal moves a pawn can make.
	 */
	@Override
	public Collection<Move> calculateLegalMoves(final Board board) {			
		final List<Move> legalMoves = new ArrayList<>();				
		for(final int currentCandidateOffset : CANDIDATE_MOVE_COORDINATE) {			
			final int candidateDestinationCoordinate = this.piecePosition + (this.pieceAlliance.getDirection() * currentCandidateOffset);			
			
			if(!BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)) {
				continue;
			}		
			if(currentCandidateOffset == 8 && !board.getTile(candidateDestinationCoordinate).isTileOccupied()) {
				legalMoves.add(new Move.MajorMove(board, this, candidateDestinationCoordinate));
			} else if(currentCandidateOffset == 16 && this.isFirstMove() && 
					((BoardUtils.SEVENTH_RANK[this.piecePosition] && this.getPieceAlliance().isBlack()) ||
					(BoardUtils.SECOND_RANK[this.piecePosition] && this.getPieceAlliance().isWhite()))) {
				final int behindCandidateDestinationCoordinate = this.piecePosition + (this.pieceAlliance.getDirection() * 8);
				if(!board.getTile(behindCandidateDestinationCoordinate).isTileOccupied() && 
						!board.getTile(candidateDestinationCoordinate).isTileOccupied()) {
					legalMoves.add(new Move.PawnJump(board, this, candidateDestinationCoordinate));
				}		
			} else if(currentCandidateOffset == 7 &&
					!((BoardUtils.EIGHT_COLUMN[this.piecePosition] && this.pieceAlliance.isWhite() ||
					(BoardUtils.FIRST_COLUMN[this.piecePosition] && this.pieceAlliance.isBlack())))) {
				if(board.getTile(candidateDestinationCoordinate).isTileOccupied()) {
					final Piece pieceOnCandidate = board.getTile(candidateDestinationCoordinate).getPiece();
					
					if(this.pieceAlliance != pieceOnCandidate.getPieceAlliance()) {
						legalMoves.add(new Move.PawnAttackMove(board, this, candidateDestinationCoordinate, pieceOnCandidate));
					}
				}				
			} else if(currentCandidateOffset == 9 &&
				!((BoardUtils.FIRST_COLUMN[this.piecePosition] && this.pieceAlliance.isWhite() ||
				(BoardUtils.EIGHT_COLUMN[this.piecePosition] && this.pieceAlliance.isBlack())))) {
				if(board.getTile(candidateDestinationCoordinate).isTileOccupied()) {
					final Piece pieceOnCandidate = board.getTile(candidateDestinationCoordinate).getPiece();
					if(this.pieceAlliance != pieceOnCandidate.getPieceAlliance()) {
						legalMoves.add(new Move.PawnAttackMove(board, this, candidateDestinationCoordinate, pieceOnCandidate));
					}
				}	
								
			}
		}
		return legalMoves;
	}
	
	/**
	 * Overridden method that creates a new pawn from the given alliance and on the given coordinate.
	 * @param Move move an instance of Move.
	 * @return Pawn a new instance of Pawn.
	 */
	@Override
	public Pawn movePiece(Move move) {
		return new Pawn(move.getMovedPiece().getPieceAlliance(), move.getDestinationCoordinate());
	}
	
	/**
	 * Overridden method that returns a String representing a pawn piece.
	 * The value of the String is defined in the PieceType enum.
	 * @return String representing a pawn piece.
	 */
	@Override
	public String toString() {
		return PieceType.PAWN.toString();
	}
}
