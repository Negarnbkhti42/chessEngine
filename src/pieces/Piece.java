package pieces;

import board.Board;
import board.Move;

import java.util.Collection;

public abstract class Piece {

    protected final PieceType pieceType;
    protected final int piecePositionRow, piecePositionColumn;
    protected final Alliance pieceAlliance;

    Piece(final PieceType pieceType, final int rowPosition, final int columnPosition, final Alliance pieceAlliance){
        this.pieceType = pieceType;
        this.piecePositionRow = rowPosition;
        this.piecePositionColumn = columnPosition;
        this.pieceAlliance = pieceAlliance;
    }

    public abstract Collection<Move> calculateMoves(final Board board);

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

    public enum PieceType {

        Pawn("p") {
            @Override
            public boolean isKing() {
                return false;
            }
        },
        Knight("N") {
            @Override
            public boolean isKing() {
                return false;
            }
        },
        Bishop("B") {
            @Override
            public boolean isKing() {
                return false;
            }
        },
        Rook("R") {
            @Override
            public boolean isKing() {
                return false;
            }
        },
        Queen("Q") {
            @Override
            public boolean isKing() {
                return false;
            }
        },
        King("K") {
            @Override
            public boolean isKing() {
                return true;
            }
        };

        private String pieceName;

        PieceType(String pieceName) {
            this.pieceName = pieceName;
        }

        public abstract boolean isKing();

        @Override
        public String toString() {
            return this.pieceName;
        }
    }
}
