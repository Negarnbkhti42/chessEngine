package pieces;

import board.Board;
import board.Move;
import board.Tile;

import java.util.ArrayList;
import java.util.Collection;

public class Queen extends Piece {

    private static final int[][] CANDIDATE_VECTORS_COORDINATE = {{1, 1}, {-1, 1}, {-1, -1}, {-1, 1}, {1, 0}, {0, 1}, {-1, 0}, {0, -1}};

    public Queen(int rowPosition, int columnPosition, Alliance pieceAlliance) {
        super(rowPosition, columnPosition, pieceAlliance);
    }

    @Override
    public Collection<Move> calculateMoves(Board board) {

        ArrayList<Move> legalMoves = new ArrayList<>();

        for (int[] candidateCoordinateOffset : CANDIDATE_VECTORS_COORDINATE) {

            int candidateDestinationRow = this.piecePositionRow;
            int candidateDestinationColumn = this.piecePositionColumn;

            while (Board.coordinateIsValid(candidateDestinationRow, candidateDestinationColumn)) {

                candidateDestinationRow += candidateCoordinateOffset[0];
                candidateDestinationColumn += candidateCoordinateOffset[1];


                if (Board.coordinateIsValid(candidateDestinationRow, candidateDestinationColumn)) {

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
        }


        return legalMoves;
    }

    @Override
    public String toString() {
        return PieceType.Queen.toString();
    }
}
