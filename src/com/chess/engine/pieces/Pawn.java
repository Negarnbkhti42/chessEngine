package com.chess.engine.pieces;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.Move;

import java.util.ArrayList;
import java.util.Collection;

import static com.chess.engine.board.Move.AttackMove;
import static com.chess.engine.board.Move.MajorMove;

public class Pawn extends Piece {

    private final static int[][] CANDIDATE_MOVE_COORDINATE = {{1, 0}, {2, 0}, {1, 1}, {1, -1}};
    private boolean firstMove;

    public Pawn(int rowPosition, int columnPosition, Alliance pieceAlliance) {
        super(PieceType.Pawn, rowPosition, columnPosition, pieceAlliance);
    }

    @Override
    public Collection<Move> calculateMoves(Board board) {

        ArrayList<Move> legalMoves = new ArrayList<>();

        for (int[] currentCandidateOffset : CANDIDATE_MOVE_COORDINATE) {

            int candidateDestinationRow = piecePositionRow + (this.getPieceAlliance().getDirection() + currentCandidateOffset[0]);
            int candidateDestinationColumn = piecePositionColumn + currentCandidateOffset[1];

            if (!Board.coordinateIsValid(candidateDestinationRow, candidateDestinationColumn)) {
                continue;
            }

            if (currentCandidateOffset[0] == 1 && currentCandidateOffset[1] == 0 &&
                    !board.getTile(candidateDestinationRow, candidateDestinationColumn).isTileOccupied()) {

                legalMoves.add(new MajorMove(board, this, candidateDestinationRow, candidateDestinationColumn));
            } else if (currentCandidateOffset[0] == 2 && isFirstMove() &&
                    !board.getTile(candidateDestinationRow, candidateDestinationColumn).isTileOccupied() &&
                    board.getTile(candidateDestinationRow - 1, candidateDestinationColumn).isTileOccupied()) {

                legalMoves.add(new MajorMove(board, this, candidateDestinationRow, candidateDestinationColumn));
            } else if (board.getTile(candidateDestinationRow, candidateDestinationColumn).isTileOccupied() &&
                        board.getTile(candidateDestinationRow, candidateDestinationColumn).getPiece().getPieceAlliance() != pieceAlliance) {

                legalMoves.add(new AttackMove(board, this, candidateDestinationRow, candidateDestinationColumn,
                        board.getTile(candidateDestinationRow, candidateDestinationColumn).getPiece()));
            }

        }
        return legalMoves;
    }

    @Override
    public Piece movePiece(Move move) {
        return new Pawn(move.getDestinationRow(),move.getDestinationRow(),move.getMovedPiece().getPieceAlliance());
    }

    @Override
    public String toString() {
        return PieceType.Pawn.toString();
    }
}
