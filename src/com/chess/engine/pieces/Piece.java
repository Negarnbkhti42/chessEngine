package com.chess.engine.pieces;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.Move;

import java.util.Collection;

public abstract class Piece {

    protected final PieceType pieceType;
    protected final int piecePositionRow, piecePositionColumn;
    protected final Alliance pieceAlliance;
    //TODO: more work here
    protected boolean firstMove;
    private final int cashedHashCode;

    Piece(final PieceType pieceType, final int rowPosition, final int columnPosition,
          final Alliance pieceAlliance, final boolean firstMove){
        this.pieceType = pieceType;
        this.piecePositionRow = rowPosition;
        this.piecePositionColumn = columnPosition;
        this.pieceAlliance = pieceAlliance;
        this.firstMove = firstMove;
        this.cashedHashCode = computeHashCode();
    }

    private int computeHashCode() {
        int result = pieceType.hashCode();
        result = 31 * result + pieceAlliance.hashCode();
        result = 31 * result + piecePositionRow;
        result = 31 * result + piecePositionColumn;
        result = 31 * result + (firstMove? 1 : 0);
        return result;
    }

    public abstract Collection<Move> calculateMoves(final Board board);

    public boolean isFirstMove() {
       return firstMove;
    }

    public PieceType getPieceType() {
        return pieceType;
    }

    public Alliance getPieceAlliance() {
        return pieceAlliance;
    }

    public int getPiecePositionRow() {
        return piecePositionRow;
    }

    public int getPiecePositionColumn() {
        return piecePositionColumn;
    }

    public abstract Piece movePiece(Move move);

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Piece)) {
            return false;
        }
        final Piece otherPiece = (Piece) other;
        return pieceAlliance == otherPiece.getPieceAlliance() && pieceType == otherPiece.pieceType &&
                piecePositionRow == otherPiece.piecePositionRow && piecePositionColumn == otherPiece.piecePositionColumn
                && isFirstMove() == otherPiece.isFirstMove();
    }

    @Override
    public int hashCode() {
        return cashedHashCode;
    }

    public enum PieceType {

        Pawn("p") {
            @Override
            public boolean isKing() {
                return false;
            }

            @Override
            public boolean isRook() {
                return false;
            }
        },
        Knight("N") {
            @Override
            public boolean isKing() {
                return false;
            }

            @Override
            public boolean isRook() {
                return false;
            }
        },
        Bishop("B") {
            @Override
            public boolean isKing() {
                return false;
            }

            @Override
            public boolean isRook() {
                return false;
            }
        },
        Rook("R") {
            @Override
            public boolean isKing() {
                return false;
            }

            @Override
            public boolean isRook() {
                return true;
            }
        },
        Queen("Q") {
            @Override
            public boolean isKing() {
                return false;
            }

            @Override
            public boolean isRook() {
                return false;
            }
        },
        King("K") {
            @Override
            public boolean isKing() {
                return true;
            }

            @Override
            public boolean isRook() {
                return false;
            }
        };

        private String pieceName;

        PieceType(String pieceName) {
            this.pieceName = pieceName;
        }

        public abstract boolean isKing();

        public abstract boolean isRook();

        @Override
        public String toString() {
            return this.pieceName;
        }
    }
}
