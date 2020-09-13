package pieces;

import board.Board;
import board.Move;
import board.Tile;

import java.util.ArrayList;
import java.util.Collection;

public class Knight extends Piece {

    private static final int[][] CANDIDATE_MOVE_COORDINATES = {{2, 1}, {1, 2}, {2, -1}, {1, -2}, {-1, 2}, {-2, 1}, {-2, -1}, {-1, -2}};

    Knight(final int xPosition, final int yPosition, final Alliance pieceAlliance) {
        super(xPosition, yPosition, pieceAlliance);
    }

    @Override
    public Collection<Move> calculateMoves(Board board) {

        ArrayList<Move> legalMoves = new ArrayList<>();

        int candidateDestinationX, candidateDestinationY;

        for (int[] currentCandidate : CANDIDATE_MOVE_COORDINATES) {

            candidateDestinationX = this.piecePosition[0] + currentCandidate[0];
            candidateDestinationY = this.piecePosition[1] + currentCandidate[1];

            if (Board.coordinateIsValid(candidateDestinationX, candidateDestinationY)) {

                Tile candidateDestinationTile = board.getTile(candidateDestinationX, candidateDestinationY);

                if (!candidateDestinationTile.isTileOccupied() ||
                        candidateDestinationTile.getPiece().getPieceAlliance() != this.pieceAlliance) {

                    legalMoves.add(new Move());
                }
            }
        }
        return legalMoves;
    }
}
