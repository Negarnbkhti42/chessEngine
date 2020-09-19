package pieces;

import board.Board;
import board.Move;
import board.Tile;

import java.util.ArrayList;
import java.util.Collection;

import static board.Move.*;

public class Knight extends Piece {

    private static final int[][] CANDIDATE_MOVE_COORDINATES = {{2, 1}, {1, 2}, {2, -1}, {1, -2}, {-1, 2}, {-2, 1}, {-2, -1}, {-1, -2}};

    public Knight(final int xPosition, final int yPosition, final Alliance pieceAlliance) {
        super(xPosition, yPosition, pieceAlliance);
    }

    @Override
    public Collection<Move> calculateMoves(Board board) {

        ArrayList<Move> legalMoves = new ArrayList<>();

        int candidateDestinationRow, candidateDestinationColumn;

        for (int[] currentCandidate : CANDIDATE_MOVE_COORDINATES) {

            candidateDestinationRow = this.piecePositionRow + currentCandidate[0];
            candidateDestinationColumn = this.piecePositionColumn + currentCandidate[1];

            if (Board.coordinateIsValid(candidateDestinationRow, candidateDestinationColumn)) {

                Tile candidateDestinationTile = board.getTile(candidateDestinationRow, candidateDestinationColumn);

                if (!candidateDestinationTile.isTileOccupied()) {

                    legalMoves.add(new MajorMove(board, this, candidateDestinationRow, candidateDestinationColumn));

                } else if (candidateDestinationTile.getPiece().getPieceAlliance() != this.pieceAlliance) {

                    legalMoves.add(new AttackMove(board, this, candidateDestinationRow, candidateDestinationColumn, candidateDestinationTile.getPiece()));
                }
            }
        }
        return legalMoves;
    }

    @Override
    public String toString() {
        return PieceType.Knight.toString();
    }
}
