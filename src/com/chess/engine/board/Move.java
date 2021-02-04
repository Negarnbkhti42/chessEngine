package com.chess.engine.board;

import com.chess.engine.pieces.Pawn;
import com.chess.engine.pieces.Piece;
import com.chess.engine.pieces.Rook;

import static com.chess.engine.board.Board.Builder;

public abstract class Move {

    protected final Board board;
    protected final Piece movedPiece;
    protected final int destinationRow, destinationColumn;
    protected final boolean isFirstMove;

    public static final Move NULL_MOVE = new NullMove();

    public Move(final Board board, final Piece movedPiece, final int destinationRow, final int destinationColumn) {
        this.board = board;
        this.movedPiece = movedPiece;
        this.destinationRow = destinationRow;
        this.destinationColumn = destinationColumn;
        this.isFirstMove = movedPiece.isFirstMove();
    }

    private Move(final Board board, final int destinationRow, final int destinationColumn) {
        this.board = board;
        this.destinationRow = destinationRow;
        this.destinationColumn = destinationColumn;
        this.movedPiece = null;
        this.isFirstMove = false;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;

        result = prime * result + this.destinationColumn;
        result = prime * result + this.destinationRow;
        result = prime * result + this.movedPiece.hashCode();
        result = prime * result + this.movedPiece.getPiecePositionColumn();
        result = prime * result + this.movedPiece.getPiecePositionRow();
        return result;
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Move)) {
            return false;
        }
        final Move otherMove = (Move) other;

        return getCurrentRow() == otherMove.getCurrentRow()
                && getCurrentColumn() == otherMove.getCurrentColumn()
                && getDestinationColumn() == otherMove.getDestinationColumn()
                && getDestinationRow() == otherMove.getDestinationRow()
                && getMovedPiece().equals(otherMove.getMovedPiece());
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

    public boolean isAttack() {
        return false;
    }

    public boolean isCastlingMove() {
        return false;
    }

    public Piece getAttackedPiece() {
        return null;
    }

    public Board execute() {


        final Builder boardBuilder = new Builder();

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

        @Override
        public boolean equals(Object other) {
            return this == other || other instanceof MajorMove && super.equals(other);
        }

        @Override
        public String toString() {
            return movedPiece.getPieceType().toString() + BoardUtils.getPositionAtCoordinate(getDestinationRow(), getDestinationColumn());
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
        public int hashCode() {
            return this.attackedPiece.hashCode() + super.hashCode();
        }

        @Override
        public boolean equals(final Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof AttackMove)) {
                return false;
            }
            final AttackMove otherAttackMove = (AttackMove) other;

            return super.equals(otherAttackMove) && getAttackedPiece().equals(otherAttackMove.getAttackedPiece());
        }

        @Override
        public Board execute() {
            return null;
        }

        @Override
        public boolean isAttack() {
            return true;
        }

        @Override
        public Piece getAttackedPiece() {
            return attackedPiece;
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

    public static final class PawnEnPassantAttackMove extends PawnAttackMove {

        public PawnEnPassantAttackMove(Board board, Piece movedPiece, int destinationRow, int destinationColumn, Piece attackedPiece) {
            super(board, movedPiece, destinationRow, destinationColumn, attackedPiece);
        }
    }

    public static final class PawnJump extends Move {

        public PawnJump(Board board, Piece movedPiece, int destinationRow, int destinationColumn) {
            super(board, movedPiece, destinationRow, destinationColumn);
        }

        @Override
        public Board execute() {
            final Builder builder = new Builder();

            for (final Piece piece : this.board.getCurrentPlayer().getActivePieces()) {
                if (!this.movedPiece.equals(piece)) {
                    builder.setPiece(piece);
                }
            }

            for (final Piece piece : this.board.getCurrentPlayer().getOpponent().getActivePieces()) {
                builder.setPiece(piece);
            }

            final Pawn movedPawn = (Pawn) this.movedPiece.movePiece(this);
            builder.setPiece(movedPawn);
            builder.setEnPassantPawn(movedPawn);
            builder.setMoveMaker(this.board.getCurrentPlayer().getOpponent().getAlliance());
            return builder.build();
        }
    }

    static abstract class CastleMove extends Move {

        protected final Rook castleRook;
        protected final int[] castleRookStart;
        protected final int[] castleRookDestination;

        public CastleMove(final Board board, final Piece movedPiece, final int destinationRow, final int destinationColumn,
                          final Rook castleRook, final int rookStartRow, final int rookStartColumn,
                          final int rookDestinationRow, final int rookDestinationColumn) {

            super(board, movedPiece, destinationRow, destinationColumn);

            this.castleRook = castleRook;
            this.castleRookStart = new int[]{rookStartRow, rookStartColumn};
            this.castleRookDestination = new int[]{rookDestinationRow, rookDestinationColumn};
        }

        public Rook getCastleRook() {
            return castleRook;
        }

        @Override
        public boolean isCastlingMove() {
            return true;
        }

        @Override
        public Board execute() {
            Builder builder = new Builder();

            for (final Piece piece : board.getCurrentPlayer().getActivePieces()) {
                if (!this.movedPiece.equals(piece) && !this.castleRook.equals(piece)) {
                    builder.setPiece(piece);
                }
            }

            for (final Piece piece : board.getCurrentPlayer().getOpponent().getActivePieces()) {
                builder.setPiece(piece);
            }

            builder.setPiece(this.movedPiece.movePiece(this));
            //TODO: look into the first move for com.chess.engine.pieces
            builder.setPiece(new Rook(this.castleRook.getPiecePositionRow(), this.castleRook.getPiecePositionColumn(),
                    this.castleRook.getPieceAlliance()));
            builder.setMoveMaker(this.board.getCurrentPlayer().getOpponent().getAlliance());
            return builder.build();
        }
    }

    public static final class KingSideCastleMove extends CastleMove {

        public KingSideCastleMove(Board board, Piece movedPiece, int destinationRow, int destinationColumn,
                                  final Rook castleRook, final int rookStartRow, final int rookStartColumn,
                                  final int rookDestinationRow, final int rookDestinationColumn) {
            super(board, movedPiece, destinationRow, destinationColumn,
                    castleRook, rookStartRow, rookStartColumn,
                    rookDestinationRow, rookDestinationColumn);
        }

        @Override
        public String toString() {
            return "O-O";
        }
    }

    public static final class QueenSideCastleMove extends CastleMove {

        public QueenSideCastleMove(Board board, Piece movedPiece, int destinationRow, int destinationColumn,
                                   final Rook castleRook, final int rookStartRow, final int rookStartColumn,
                                   final int rookDestinationRow, final int rookDestinationColumn) {
            super(board, movedPiece, destinationRow, destinationColumn,
                    castleRook, rookStartRow, rookStartColumn,
                    rookDestinationRow, rookDestinationColumn);
        }

        @Override
        public String toString() {
            return "O-O-O";
        }
    }

    public static final class NullMove extends Move {

        public NullMove() {
            super(null, null, -1, -1);
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
            for (final Move move : board.getAllLegalMoves()) {
                if ((move.getCurrentRow() == currentRow && move.getCurrentColumn() == currentColumn) &&
                        (move.getDestinationRow() == destinationRow && move.getDestinationColumn() == destinationColumn)) {
                    return move;
                }
            }
            return null;
        }
    }
}
