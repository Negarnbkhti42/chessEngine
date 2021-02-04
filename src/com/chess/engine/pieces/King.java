package com.chess.engine.pieces;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import com.chess.engine.board.Tile;

import java.util.ArrayList;
import java.util.Collection;

public class King extends Piece {

    private final static int[][] CANDIDATE_MOVE_COORDINATE = {{1, 1}, {-1, 1}, {-1, -1}, {-1, 1}, {1, 0}, {0, 1}, {-1, 0}, {0, -1}};

    public King(int rowPosition, int columnPosition, Alliance pieceAlliance) {
        super(PieceType.King, rowPosition, columnPosition, pieceAlliance, true);
    }

    public King(int rowPosition, int columnPosition, Alliance pieceAlliance, boolean firstMove) {
        super(PieceType.King, rowPosition, columnPosition, pieceAlliance, firstMove);
    }


    @Override
    public Collection<Move> calculateMoves(Board board) {

        ArrayList<Move> legalMoves = new ArrayList<>();

        for (int[] candidateCoordinateOffset : CANDIDATE_MOVE_COORDINATE) {

            int candidateDestinationRow = this.piecePositionRow;
            int candidateDestinationColumn = this.piecePositionColumn;

            candidateDestinationRow += candidateCoordinateOffset[0];
            candidateDestinationColumn += candidateCoordinateOffset[1];


            if (BoardUtils.coordinateIsValid(candidateDestinationRow, candidateDestinationColumn)) {

                Tile candidateDestinationTile = board.getTile(candidateDestinationRow, candidateDestinationColumn);

                if (!candidateDestinationTile.isTileOccupied()) {

                    legalMoves.add(new Move.MajorMove(board, this, candidateDestinationRow, candidateDestinationColumn));

                } else {

                    if (candidateDestinationTile.getPiece().getPieceAlliance() != this.pieceAlliance) {
                        legalMoves.add(new Move.AttackMove(board, this, candidateDestinationRow, candidateDestinationColumn, candidateDestinationTile.getPiece()));
                    }

                    break;
                }
            }
        }


        return legalMoves;
    }

    @Override
    public Piece movePiece(Move move) {
        return new King(move.getDestinationRow(),move.getDestinationColumn(),move.getMovedPiece().getPieceAlliance(), false);
    }

    @Override
    public String toString() {
        return PieceType.King.toString();
    }
}
