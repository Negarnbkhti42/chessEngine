package board;

import pieces.Piece;

import static board.Board.*;

public abstract class Move {

    protected final Board board;
    protected final Piece movedPiece;
    protected final int destinationCoordinateRow, destinationCoordinateColumn;

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

    public Piece getMovedPiece() {
        return movedPiece;
    }

    public abstract Board execute();

    public static final class MajorMove extends Move {


        public MajorMove(final Board board, final Piece movedPiece, final int destinationRow, final int destinationColumn) {
            super(board, movedPiece, destinationRow, destinationColumn);
        }

        @Override
        public Board execute() {


            final Builder boardBuilder = new Builder();

            //TODO: hashcode and equals code for pieces
            for (final Piece piece : this.board.getCurrentPlayer().getActivePieces()) {
                if (!this.movedPiece.equals(piece)) {
                    boardBuilder.setPiece(piece);
                }
            }

            for (final Piece piece : this.board.getCurrentPlayer().getOpponent().getActivePieces()) {
                boardBuilder.setPiece(piece);
            }

            boardBuilder.setPiece(this.movedPiece.movePiece(this));
            boardBuilder.setMoveMaker(this.board.getCurrentPlayer().getOpponent().getAlliance());
            return boardBuilder.build();
        }
    }

    public static final class AttackMove extends Move {

        private final Piece attackedPiece;


        public AttackMove(final Board board, final Piece movedPiece, final int destinationRow, final int destinationColumn,
                          final Piece attackedPiece) {
            super(board, movedPiece, destinationRow, destinationColumn);
            this.attackedPiece = attackedPiece;
        }

        @Override
        public Board execute() {
            return null;
        }
    }
}
