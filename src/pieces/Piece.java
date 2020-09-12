package pieces;

import board.Board;
import board.Move;

import java.util.Collection;

public abstract class Piece {

    protected final int[] piecePosition;
    protected Alliance pieceAlliance;

    Piece(final int xPosition, final int yPosition, final Alliance pieceAlliance){
        this.piecePosition = new int[] {xPosition, yPosition};
        this.pieceAlliance = pieceAlliance;
    }

    public abstract Collection<Move> calculateMoves(final Board board);
}
