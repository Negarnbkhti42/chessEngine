package pieces;

import board.Board;
import board.Move;

import java.util.Collection;

public abstract class Piece {

    protected final int piecePositionRow, piecePositionColumn;
    protected Alliance pieceAlliance;

    Piece(final int rowPosition, final int columnPosition, final Alliance pieceAlliance){
        this.piecePositionRow = rowPosition;
        this.piecePositionColumn = columnPosition;
        this.pieceAlliance = pieceAlliance;
    }

    public abstract Collection<Move> calculateMoves(final Board board);

    public Alliance getPieceAlliance() {
        return pieceAlliance;
    }

    public int getPiecePositionRow() {
        return piecePositionRow;
    }

    public int getPiecePositionColumn() {
        return piecePositionColumn;
    }
}
