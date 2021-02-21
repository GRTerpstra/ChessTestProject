// User-defined package.
package com.chessTestProject.engine.pieces;

// Imported user-defined packages.
import com.chessTestProject.engine.Alliance;
import com.chessTestProject.engine.board.Board;
import com.chessTestProject.engine.board.BoardUtils;
import com.chessTestProject.engine.board.Move;

// Imported built-in packages.
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
	
	// Declare and initialize constant variable.
	private final static int[] CANDIDATE_MOVE_COORDINATE = {8};
	
	// Constructor.
	Pawn(final int piecePosition, final Alliance pieceAlliance) {
		super(piecePosition, pieceAlliance);
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
			final int candidateDestinationCoordinate = this.piecePosition + (this.getPieceAlliance().getDirection() + currentCandidateOffset);			
			if(!BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)) {
				continue;
			}		
			if(currentCandidateOffset == 8 && !board.getTile(candidateDestinationCoordinate).isTileOccupied()) {
				// !!! TODO more work to do here !!!
				legalMoves.add(new Move.MajorMove(board, this, candidateDestinationCoordinate));
			} else if(currentCandidateOffset == 16 && this.isFirstMove() && 
					(BoardUtils.SECOND_ROW[this.piecePosition] && this.getPieceAlliance().isBlack()) ||
					(BoardUtils.SEVENTH_ROW[this.piecePosition] && this.getPieceAlliance().isWhite()));
				final int behindCandidateDestinationCoordinate = this.piecePosition + (this.pieceAlliance.getDirection() * 8);
				if(!board.getTile(behindCandidateDestinationCoordinate).isTileOccupied() && 
						!board.getTile(candidateDestinationCoordinate).isTileOccupied()) {
					legalMoves.add(new Move.MajorMove(board, this, candidateDestinationCoordinate));
				}		
		}		
		return null;
	}
}
