package pieces;

import board.Board;
import board.Move;

import java.util.ArrayList;
import java.util.Collection;

import static board.Move.AttackMove;
import static board.Move.MajorMove;

public class Pawn extends Piece {

    private final static int[][] CANDIDATE_MOVE_COORDINATE = {{1, 0}, {2, 0}, {1, 1}, {1, -1}};

    Pawn(int rowPosition, int columnPosition, Alliance pieceAlliance) {
        super(rowPosition, columnPosition, pieceAlliance);
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
            } else if (board.getTile(candidateDestinationRow, candidateDestinationColumn).isTileOccupied()) {

                legalMoves.add(new AttackMove(board, this, candidateDestinationRow, candidateDestinationColumn,
                        board.getTile(candidateDestinationRow, candidateDestinationColumn).getPiece()));
            }

        }
        return legalMoves;
    }

    public boolean isFirstMove() {
        return (pieceAlliance.equals(Alliance.BLACK) && piecePositionRow == 1) ||
                (pieceAlliance.equals(Alliance.BLACK) && piecePositionRow == 6);
    }
}
