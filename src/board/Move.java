package board;

import pieces.Piece;

import static board.Board.*;

public abstract class Move {

    protected final Board board;
    protected final Piece movedPiece;
    protected final int destinationRow, destinationColumn;

    public static final Move NULL_MOVE = new NullMove();

    public Move(final Board board, final Piece movedPiece, final int destinationRow, final int destinationColumn) {
        this.board = board;
        this.movedPiece = movedPiece;
        this.destinationRow = destinationRow;
        this.destinationColumn = destinationColumn;
    }

    public int getDestinationRow() {
        return destinationRow;
    }

    public int getDestinationColumn() {
        return destinationColumn;
    }

    public int getCurrentRow() {
        return this.movedPiece.getPiecePositionRow();
    }

    public int getCurrentColumn() {
        return this.movedPiece.getPiecePositionColumn();
    }

    public Piece getMovedPiece() {
        return movedPiece;
    }

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

    public static final class MajorMove extends Move {


        public MajorMove(final Board board, final Piece movedPiece, final int destinationRow, final int destinationColumn) {
            super(board, movedPiece, destinationRow, destinationColumn);
        }

    }

    public static class AttackMove extends Move {

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

    public static final class PawnMove extends Move {

        public PawnMove(Board board, Piece movedPiece, int destinationRow, int destinationColumn) {
            super(board, movedPiece, destinationRow, destinationColumn);
        }
    }

    public static class PawnAttackMove extends AttackMove {

        public PawnAttackMove(Board board, Piece movedPiece, int destinationRow, int destinationColumn, Piece attackedPiece) {
            super(board, movedPiece, destinationRow, destinationColumn, attackedPiece);
        }
    }

    public static final class PawnEnPassantAttackMove extends PawnAttackMove{

        public PawnEnPassantAttackMove(Board board, Piece movedPiece, int destinationRow, int destinationColumn, Piece attackedPiece) {
            super(board, movedPiece, destinationRow, destinationColumn, attackedPiece);
        }
    }

    public static final class PawnJump extends Move {

        public PawnJump(Board board, Piece movedPiece, int destinationRow, int destinationColumn) {
            super(board, movedPiece, destinationRow, destinationColumn);
        }
    }

    static abstract class CastleMove extends Move {


        public CastleMove(final Board board, final Piece movedPiece, final int destinationRow, final int destinationColumn) {
            super(board, movedPiece, destinationRow, destinationColumn);
        }

    }

    public static final class KingSideCastleMove extends CastleMove {

        public KingSideCastleMove(Board board, Piece movedPiece, int destinationRow, int destinationColumn) {
            super(board, movedPiece, destinationRow, destinationColumn);
        }
    }

    public static final class QueenSideCastleMove extends CastleMove {

        public QueenSideCastleMove(Board board, Piece movedPiece, int destinationRow, int destinationColumn) {
            super(board, movedPiece, destinationRow, destinationColumn);
        }
    }

    public static final class NullMove extends Move {

        public NullMove() {
            super(null,null, -1, -1);
        }

        @Override
        public Board execute() {
            throw new RuntimeException("cannot execute the null move!");
        }
    }

    public static class MoveFactory {
        private MoveFactory() {
            throw new RuntimeException("not instantiable");
        }

        public static Move createMove(final Board board,
                                      final int currentRow, final int currentColumn,
                                      final int destinationRow, final int destinationColumn) {
            for (final Move move: board.getAllLegalMoves()) {
                if ((move.getCurrentRow() == currentRow && move.getCurrentColumn() == currentColumn) &&
                        (move.getDestinationRow() == destinationRow && move.getDestinationColumn() == destinationColumn)) {
                    return move;
                }
            }
            return null;
        }
    }
}
