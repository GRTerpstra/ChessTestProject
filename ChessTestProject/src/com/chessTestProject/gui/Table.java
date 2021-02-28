// User-defined package.
package com.chessTestProject.gui;

// Imported user-defined classes.
import com.chessTestProject.engine.board.BoardUtils;
import com.chessTestProject.engine.board.Move;
import com.chessTestProject.engine.board.Tile;
import com.chessTestProject.engine.pieces.Piece;
import com.chessTestProject.engine.player.MoveTransition;
import com.chessTestProject.engine.board.Board;

// Imported built-in classes.
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import static javax.swing.SwingUtilities.isLeftMouseButton;
import static javax.swing.SwingUtilities.isRightMouseButton;

public class Table {
	
	private final JFrame gameFrame;
	private final GameHistoryPanel gameHistoryPanel;
	private final TakenPiecesPanel takenPiecesPanel;
	private final BoardPanel boardPanel;
	private final MoveLog moveLog;
	private Board chessBoard;
	private Tile sourceTile;
	private Tile destinationTile;
	private Piece humanMovedPiece;
	private BoardDirection boardDirection;
	private boolean highlightLegalMoves;
	private final static Dimension OUTER_FRAME_DIMENSION = new Dimension(800,800);
	private final static Dimension BOARD_PANEL_DIMENSION = new Dimension(400, 350);
	private final static Dimension TILE_PANEL_DIMENSION = new Dimension(10, 10);
	private static String defaultPieceImagesPath = "art/pieces/";
	private final Color lightTileColor = Color.decode("#FFFACD");
    private final Color darkTileColor = Color.decode("#593E1A");
	
	public Table() {
		this.gameFrame = new JFrame("JChess");
		this.gameFrame.setLayout(new BorderLayout());
		final JMenuBar tableMenuBar = createTableMenuBar();
		this.gameFrame.setJMenuBar(tableMenuBar);
		this.gameFrame.setSize(OUTER_FRAME_DIMENSION);
		this.gameHistoryPanel = new GameHistoryPanel();
		this.takenPiecesPanel = new TakenPiecesPanel();
		this.chessBoard = Board.createStandardBoard();
		this.boardPanel = new BoardPanel();
		this.moveLog = new MoveLog();
		this.boardDirection = BoardDirection.NORMAL;
		this.highlightLegalMoves = true;
		this.gameFrame.add(this.takenPiecesPanel, BorderLayout.WEST);
		this.gameFrame.add(this.boardPanel, BorderLayout.CENTER);
		this.gameFrame.add(this.gameHistoryPanel, BorderLayout.EAST);
		this.gameFrame.setVisible(true);	
	}

	private JMenuBar createTableMenuBar() {
		final JMenuBar tableMenuBar = new JMenuBar();
		tableMenuBar.add(createFileMenu());
		tableMenuBar.add(createPreferencesMenu());
		return tableMenuBar;
	}

	private JMenu createFileMenu() {
		final JMenu fileMenu = new JMenu("File");
		
		final JMenuItem openPGN = new JMenuItem("Load PGN File");
		openPGN.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("open up that PGN file!");
			}
		});
		fileMenu.add(openPGN);
		
		final JMenuItem exitMenuItem = new JMenuItem("Exit");
		exitMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		fileMenu.add(exitMenuItem);
		return fileMenu;
	}	
	
	private JMenu createPreferencesMenu() {
		
		final JMenu preferencesMenu = new JMenu("Preferences");
		
		final JMenuItem flipBoardMenuItem = new JMenuItem("Flip Board");
		
		flipBoardMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				boardDirection = boardDirection.opposite();
				boardPanel.drawBoard(chessBoard);
			}
		});
		
		preferencesMenu.add(flipBoardMenuItem);
		preferencesMenu.addSeparator();		
		
		final JCheckBoxMenuItem legalMoveHighlighterCheckbox = new JCheckBoxMenuItem("Highlight legal Moves", true);
		legalMoveHighlighterCheckbox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				highlightLegalMoves = legalMoveHighlighterCheckbox.isSelected();
			}
		});
		
		preferencesMenu.add(legalMoveHighlighterCheckbox);
		return preferencesMenu;
		
	}
	  
	
	public enum BoardDirection {
		
		NORMAL {
			@Override
			List<TilePanel> traverse(final List<TilePanel> boardTiles) {
				return boardTiles;
			}
			
			@Override
			BoardDirection opposite() {
				return FLIPPED;
			}
		},
		FLIPPED {
			@Override
			List<TilePanel> traverse(final List<TilePanel> boardTiles) {
				List<TilePanel> list = reverseList(boardTiles);
				return list;
			}

			@Override
			BoardDirection opposite() {
				return NORMAL;
			}
		};
	
		abstract List<TilePanel> traverse(final List<TilePanel> boardTiles);
		abstract BoardDirection opposite();
	}
	
	// Interesting piece of code that helped fix a bug with reversing flipping the board,
	// if the traverse method can't use Collection.reverse(boardTiles), something with immutability.
	public static<T> List<T> reverseList(List<T> list) {
        return list.stream()
                .collect(Collectors.collectingAndThen(
                        Collectors.toCollection(ArrayList::new), lst -> {
                            Collections.reverse(lst);
                            return lst.stream();
                        }
                )).collect(Collectors.toCollection(ArrayList::new));
    }

	private class BoardPanel extends JPanel {
		final List<TilePanel> boardTiles;
		
		BoardPanel() {
			super(new GridLayout(8,8));
			this.boardTiles = new ArrayList<>();
			for(int i = 0; i < BoardUtils.NUM_TILES; i++) {
				final TilePanel tilePanel = new TilePanel(this, i);
				this.boardTiles.add(tilePanel);
				add(tilePanel);
			}
			setPreferredSize(BOARD_PANEL_DIMENSION);
			validate();
		}

		public void drawBoard(final Board board) {
			removeAll();
			for(final TilePanel tilePanel : boardDirection.traverse(boardTiles)) {
				tilePanel.drawTile(board);
				add(tilePanel);
			}
			validate();
			repaint();
		}
	}
	
	public static class MoveLog {
		private final List<Move> moves;
		
		MoveLog() {
			this.moves = new ArrayList<>();
		}
		
		public List<Move> getMoves() {
			return this.moves;
		}
		
		public void addMove(final Move move) {
			this.moves.add(move);
		}
		
		public int size() {
			return this.moves.size();
		}
		
		public void clear() {
			this.moves.clear();
		}
		
		public Move removeMove(int index) {
			return this.moves.remove(index);
		}
		
		public boolean removeMove(final Move move) {
			return this.moves.remove(move);
		}
	}
	
	private class TilePanel extends JPanel {
		
		private final int tileId;
		
		TilePanel(final BoardPanel boardPanel, 
					final int tileId) {
			super(new GridBagLayout());
			this.tileId = tileId;
			setPreferredSize(TILE_PANEL_DIMENSION);
			assignTileColor();
			assignTilePieceIcon(chessBoard);
			
			addMouseListener(new MouseListener() {
				@Override
				public void mouseClicked(final MouseEvent e) {
					if(SwingUtilities.isRightMouseButton(e)) {
						sourceTile = null;
						destinationTile = null;
						humanMovedPiece = null;	

					} else if(SwingUtilities.isLeftMouseButton(e)) {
						if(sourceTile == null) {
							sourceTile = chessBoard.getTile(tileId);
							humanMovedPiece = sourceTile.getPiece();
							if(humanMovedPiece == null) {
								sourceTile = null;
							}
						} else {
							destinationTile = chessBoard.getTile(tileId);
							final Move move = Move.MoveFactory.createMove(chessBoard, 
																			sourceTile.getTileCoordinate(), 
																			destinationTile.getTileCoordinate());
							final MoveTransition transition = chessBoard.currentPlayer().makeMove(move);
							if(transition.getMoveStatus().isDone()) {
								chessBoard = transition.getTransitionBoard();
								moveLog.addMove(move);
							}
							sourceTile = null;
							destinationTile = null;
							humanMovedPiece = null;
						}
						SwingUtilities.invokeLater(new Runnable() {
							@Override
							public void run() {
								gameHistoryPanel.redo(chessBoard, moveLog);
								takenPiecesPanel.redo(moveLog);
								boardPanel.drawBoard(chessBoard);
							}
						});
					}
				}

				@Override
				public void mousePressed(MouseEvent e) {
					
				}

				@Override
				public void mouseReleased(MouseEvent e) {
					
				}

				@Override
				public void mouseEntered(MouseEvent e) {
					
				}

				@Override
				public void mouseExited(MouseEvent e) {
					
				}
				
			});
			
			validate();
		}
		
		public void drawTile(final Board board) {
			
			assignTileColor();
			assignTilePieceIcon(board);
			highlightLegals(board);
			validate();
			repaint();
			
		}

		private void assignTilePieceIcon(final Board board) {
			this.removeAll();
			if(board.getTile(this.tileId).isTileOccupied()) {
				try {					
					final BufferedImage image = ImageIO.read(new File(defaultPieceImagesPath + board.getTile(this.tileId).getPiece().getPieceAlliance().toString().substring(0, 1) + 
							board.getTile(this.tileId).getPiece().toString() + ".gif"));
				add(new JLabel(new ImageIcon(image)));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		private void highlightLegals(final Board board) {
			if(highlightLegalMoves) {
				for(final Move move : pieceLegalMoves(board)) {
					if(move.getDestinationCoordinate() == this.tileId) {
						try {
							add(new JLabel(new ImageIcon(ImageIO.read(new File("art/misc/green_dot.png")))));
						} catch(Exception e) {
							e.printStackTrace();
						}
					}
				}
			}	
		}
		
		private Collection<Move> pieceLegalMoves(final Board board) {
			if(humanMovedPiece != null && humanMovedPiece.getPieceAlliance() == board.currentPlayer().getAlliance()) {
				// Piece of code that does not really belong here, but is quick fix to the problem of
				// castling moves not being highlighted as legal moves.
				if(humanMovedPiece.getPieceType().isKing() && humanMovedPiece.isFirstMove()){
				     final List<Move> includesCastleMoves = new ArrayList<>();
				     includesCastleMoves.addAll(board.currentPlayer().calculateKingCastles(board.currentPlayer().getLegalMoves(), 
				    		 					board.currentPlayer().getOpponent().getLegalMoves()));
				     List<Move> newList = new ArrayList<Move>();
				     newList.addAll(humanMovedPiece.calculateLegalMoves(board));
				     newList.addAll(includesCastleMoves);	
				     return newList;
				  }
				return humanMovedPiece.calculateLegalMoves(board);
			}
			return Collections.emptyList();
		}

		private void assignTileColor() {
			if (BoardUtils.EIGHT_RANK[this.tileId] ||
					BoardUtils.SIXTH_RANK[this.tileId] ||
					BoardUtils.FOURTH_RANK[this.tileId] ||
					BoardUtils.SECOND_RANK[this.tileId]) {
				setBackground(this.tileId % 2 == 0 ? lightTileColor : darkTileColor);
			} else if(BoardUtils.SEVENTH_RANK[this.tileId] ||
						BoardUtils.FIFTH_RANK[this.tileId] ||
						BoardUtils.THIRD_RANK[this.tileId] ||
						BoardUtils.FIRST_RANK[this.tileId]) {
				setBackground(this.tileId % 2 != 0 ? lightTileColor : darkTileColor);
			}
		}
	}
}
