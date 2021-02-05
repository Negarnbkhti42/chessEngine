package com.chess.gui;

import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import com.chess.engine.board.Tile;
import com.chess.engine.pieces.Piece;
import com.chess.engine.player.MoveTransition;
import com.google.common.collect.Lists;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static javax.swing.SwingUtilities.isLeftMouseButton;
import static javax.swing.SwingUtilities.isRightMouseButton;

public class Table {

    private final JFrame gameFrame;
    private final BoardPanel boardPanel;

    private Board chessBoard;

    private Tile sourceTile;
    private Tile destinationTile;
    private Piece humanMovedPiece;
    private BoardDirection boardDirection;

    private boolean highlightLegalMoves;

    private final static Dimension OUTER_FRAME_DIMENSION = new Dimension(600, 600);
    private final static Dimension BOARD_PANEL_DIMENSION = new Dimension(400, 350);
    private final static Dimension TILE_PANEL_DIMENSION = new Dimension(10, 10);
    private static String defaultPieceImagePath = "resource/";

    private final Color lightTileColor = Color.decode("#FFFACD");
    private final Color darkTileColor = Color.decode("#593E1A");

    public Table() {
        this.gameFrame = new JFrame("JChess");
        this.gameFrame.setLayout(new BorderLayout());
        final JMenuBar tableMenuBar = createTableMenuBar();
        this.gameFrame.setJMenuBar(tableMenuBar);
        this.gameFrame.setSize(OUTER_FRAME_DIMENSION);
        this.chessBoard = Board.createStandardBoard();
        this.boardPanel = new BoardPanel();
        this.boardDirection = BoardDirection.NORMAL;
        this.highlightLegalMoves = false;
        this.gameFrame.add(this.boardPanel, BorderLayout.CENTER);

        this.gameFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
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

        final JMenuItem openPGN = new JMenuItem("load PGN File");
        openPGN.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("open that pgn file");
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
        final JMenu preferencesMenu = new JMenu("preferences");

        final JMenuItem flipBoard = new JMenuItem("flip board");
        flipBoard.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boardDirection = boardDirection.opposite();
                boardPanel.drawBoard(chessBoard);
            }
        });
        preferencesMenu.add(flipBoard);

        preferencesMenu.addSeparator();

        final JCheckBoxMenuItem legalMoveHighlighterCheckBox = new JCheckBoxMenuItem("highlight legal moves", false);
        legalMoveHighlighterCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                highlightLegalMoves = legalMoveHighlighterCheckBox.isSelected();
            }
        });
        preferencesMenu.add(legalMoveHighlighterCheckBox);
        return preferencesMenu;
    }

    private class BoardPanel extends JPanel {
        private final List<TilePanel> boardTiles;

        public BoardPanel() {
            super(new GridLayout(8, 8));
            this.boardTiles = new ArrayList<>();

            for (int i = 0; i < BoardUtils.NUM_TILES; i++) {
                final TilePanel tilePanel = new TilePanel(this, i);
                this.boardTiles.add(tilePanel);
                add(tilePanel);
            }
            setPreferredSize(BOARD_PANEL_DIMENSION);
            validate();
        }

        public void drawBoard(final Board board) {
            removeAll();

            for (final TilePanel tilePanel : boardDirection.traverse(boardTiles)) {
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
            return moves;
        }

        public void addMove (final Move move) {
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
        private final int idRow;
        private final int idColumn;


        TilePanel(final BoardPanel boardPanel, final int tileId) {
            super(new GridBagLayout());
            this.tileId = tileId;
            this.idRow = this.tileId / BoardUtils.TILES_PER_ROW;
            this.idColumn = this.tileId % BoardUtils.TILES_PER_ROW;
            setPreferredSize(TILE_PANEL_DIMENSION);
            assignTileColor();
            assignTilePieceIcon(chessBoard);

            addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(final MouseEvent e) {
                    if (isRightMouseButton(e)) {
                        sourceTile = null;
                        destinationTile = null;
                        humanMovedPiece = null;
                        boardPanel.drawBoard(chessBoard);
                    } else if (isLeftMouseButton(e)) {
                        if (sourceTile == null) {
                            //first click
                            sourceTile = chessBoard.getTile(idRow, idColumn);
                            humanMovedPiece = sourceTile.getPiece();
                            highlightLegalMoves(chessBoard);
                            if (humanMovedPiece == null ||
                                    (humanMovedPiece.getPieceAlliance() != chessBoard.getCurrentPlayer().getAlliance())) {
                                sourceTile = null;
                            }
                        } else {
                            //second click
                            destinationTile = chessBoard.getTile(idRow, idColumn);
                            final Move move = Move.MoveFactory.createMove(chessBoard, sourceTile.getTILE_COORDINATE_ROW(), sourceTile.getTILE_COORDINATE_COLUMN(),
                                    destinationTile.getTILE_COORDINATE_ROW(), destinationTile.getTILE_COORDINATE_COLUMN());
                            MoveTransition transition = chessBoard.getCurrentPlayer().makeMove(move);
                            if (transition.getMoveStatus().isDone()) {
                                chessBoard = transition.getTransitionBoard();
                                //TODO:add the move to the move log
                            }
                            sourceTile = null;
                            destinationTile = null;
                            humanMovedPiece = null;
                        }
                        SwingUtilities.invokeLater(new Runnable() {
                            @Override
                            public void run() {
                                boardPanel.drawBoard(chessBoard);
                            }
                        });
                    }
                }

                @Override
                public void mousePressed(final MouseEvent e) {

                }

                @Override
                public void mouseReleased(final MouseEvent e) {

                }

                @Override
                public void mouseEntered(final MouseEvent e) {

                }

                @Override
                public void mouseExited(final MouseEvent e) {

                }
            });

            validate();
        }

        public void drawTile(final Board board) {
            assignTileColor();
            assignTilePieceIcon(board);
            highlightLegalMoves(chessBoard);
            validate();
            repaint();
        }

        private void assignTilePieceIcon(final Board board) {
            this.removeAll();

            if (board.getTile(this.idRow, this.idColumn).isTileOccupied()) {

                try {
                    final BufferedImage image =
                            ImageIO.read(new File(defaultPieceImagePath + board.getTile(this.idRow, this.idColumn).getPiece().getPieceAlliance().toString().substring(0, 1) +
                                    board.getTile(this.idRow, this.idColumn).getPiece().toString() + ".png"));
                    add(new JLabel(new ImageIcon(image)));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        private void assignTileColor() {
            if (BoardUtils.EIGHTH_RANK.get(this.tileId) ||
                    BoardUtils.SIXTH_RANK.get(this.tileId) ||
                    BoardUtils.FOURTH_RANK.get(this.tileId) ||
                    BoardUtils.SECOND_RANK.get(this.tileId)) {
                setBackground(this.tileId % 2 == 0 ? lightTileColor : darkTileColor);
            } else if (BoardUtils.SEVENTH_RANK.get(this.tileId) ||
                    BoardUtils.FIFTH_RANK.get(this.tileId) ||
                    BoardUtils.THIRD_RANK.get(this.tileId) ||
                    BoardUtils.FIRST_RANK.get(this.tileId)) {
                setBackground(this.tileId % 2 != 0 ? lightTileColor : darkTileColor);
            }
        }

        private void highlightLegalMoves(final Board board) {
            if (highlightLegalMoves) {
                for (final Move move : pieceLegalMoves(board)) {
                    if (move.getDestinationRow() == this.idRow && move.getDestinationColumn() == this.idColumn) {
                        try {
                            add(new JLabel(new ImageIcon(ImageIO.read(new File("resource/green-dot-small.png")))));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }

        private Collection<Move> pieceLegalMoves(final Board board) {
            if (humanMovedPiece != null && humanMovedPiece.getPieceAlliance() == board.getCurrentPlayer().getAlliance()) {
                return humanMovedPiece.calculateMoves(board);
            }
            return Collections.emptyList();
        }
    }

    public enum BoardDirection {
            NORMAL {
                @Override
                List<TilePanel> traverse(List<TilePanel> boardTiles) {
                    return boardTiles;
                }

                @Override
                BoardDirection opposite() {
                    return FLIPPED ;
                }
            },
        FLIPPED {
            @Override
            List<TilePanel> traverse(List<TilePanel> boardTiles) {
                return Lists.reverse(boardTiles);
            }

            @Override
            BoardDirection opposite() {
                return NORMAL;
            }
        };

            abstract List<TilePanel> traverse(final List<TilePanel> boardTiles);
            abstract BoardDirection opposite();

    }

}
