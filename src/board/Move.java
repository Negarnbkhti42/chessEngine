package board;

import pieces.Piece;

public abstract class Move {

    private final Board board;
    private final Piece movedPiece;
    private final int destinationCoordinateRow, destinationCoordinateColumn;

    public Move(final Board board, final Piece movedPiece, final int destinationRow, final int destinationColumn) {
        this.board = board;
        this.movedPiece = movedPiece;
        this.destinationCoordinateRow = destinationRow;
        this.destinationCoordinateColumn = destinationColumn;
    }

    public int getDestinationCoordinateRow() {
        return destinationCoordinateRow;
    }

    public int getDestinationCoordinateColumn() {
        return destinationCoordinateColumn;
    }

    public abstract Board execute();

    public static final class MajorMove extends Move {


        public MajorMove(final Board board, final Piece movedPiece, final int destinationRow, final int destinationColumn) {
            super(board, movedPiece, destinationRow, destinationColumn);
        }

        @Override
        public Board execute() {
            return null;
        }
    }

    public static final class AttackMove extends Move {

        private final Piece attackedPiece;


        public AttackMove(final Board board, final Piece movedPiece, final int destinationRow, final int destinationColumn, final Piece attackedPiece) {
            super(board, movedPiece, destinationRow, destinationColumn);
            this.attackedPiece = attackedPiece;
        }

        @Override
        public Board execute() {
            return null;
        }
    }
}
