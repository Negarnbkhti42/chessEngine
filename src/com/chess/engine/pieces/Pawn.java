package com.chess.engine.pieces;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;

import java.util.ArrayList;
import java.util.Collection;

import static com.chess.engine.board.Move.*;

public class Pawn extends Piece {

    private final static int[][] CANDIDATE_MOVE_COORDINATE = {{1, 0}, {2, 0}, {1, 1}, {1, -1}};

    public Pawn(int rowPosition, int columnPosition, Alliance pieceAlliance) {
        super(PieceType.Pawn, rowPosition, columnPosition, pieceAlliance, true);
    }

    public Pawn(int rowPosition, int columnPosition, Alliance pieceAlliance, boolean firstMove) {
        super(PieceType.Pawn, rowPosition, columnPosition, pieceAlliance, firstMove);
    }

    @Override
    public Collection<Move> calculateMoves(Board board) {

        ArrayList<Move> legalMoves = new ArrayList<>();

        for (int[] currentCandidateOffset : CANDIDATE_MOVE_COORDINATE) {

            int candidateDestinationRow = piecePositionRow + (this.getPieceAlliance().getDirection() * currentCandidateOffset[0]);
            int candidateDestinationColumn = piecePositionColumn + currentCandidateOffset[1];

            if (!BoardUtils.coordinateIsValid(candidateDestinationRow, candidateDestinationColumn)) {
                continue;
            }

            if (currentCandidateOffset[0] == 1 && currentCandidateOffset[1] == 0 &&
                    !board.getTile(candidateDestinationRow, candidateDestinationColumn).isTileOccupied()) {

                legalMoves.add(new MajorMove(board, this, candidateDestinationRow, candidateDestinationColumn));
            } else if (currentCandidateOffset[0] == 2 && isFirstMove() &&
                    !board.getTile(candidateDestinationRow, candidateDestinationColumn).isTileOccupied() &&
                    !board.getTile(candidateDestinationRow - this.getPieceAlliance().getDirection(), candidateDestinationColumn).isTileOccupied()) {
                legalMoves.add(new PawnJump(board, this, candidateDestinationRow, candidateDestinationColumn));
            } else if (board.getTile(candidateDestinationRow, candidateDestinationColumn).isTileOccupied() &&
                        board.getTile(candidateDestinationRow, candidateDestinationColumn).getPiece().getPieceAlliance() != pieceAlliance) {

                legalMoves.add(new PawnAttackMove(board, this, candidateDestinationRow, candidateDestinationColumn,
                        board.getTile(candidateDestinationRow, candidateDestinationColumn).getPiece()));
            }

        }
        return legalMoves;
    }

    @Override
    public Piece movePiece(Move move) {
        return new Pawn(move.getDestinationRow(),move.getDestinationColumn(),move.getMovedPiece().getPieceAlliance(), false);
    }

    @Override
    public String toString() {
        return PieceType.Pawn.toString();
    }
}
