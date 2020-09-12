package board;

import pieces.Piece;

public abstract class Tile {

    int[] tileCoordinate;

    private Tile(int xCoordinate, int yCoordinate){
        this.tileCoordinate =new int[] {xCoordinate, yCoordinate};
    }

    public abstract boolean isTileOccupied();

    public abstract Piece getPiece();

    public static final class EmptyTile extends Tile{

        private EmptyTile(int xCoordinate, int yCoordinate) {
            super(xCoordinate, yCoordinate);
        }

        @Override
        public boolean isTileOccupied() {
            return false;
        }

        @Override
        public Piece getPiece() {
            return null;
        }
    }

    public static final class OccupiedTile extends Tile{

        private Piece piece;

        private OccupiedTile(int xCoordinate, int yCoordinate, Piece piece) {
            super(xCoordinate, yCoordinate);
            this.piece = piece;
        }

        @Override
        public boolean isTileOccupied() {
            return true;
        }

        @Override
        public Piece getPiece() {
            return this.piece;
        }
    }
}
