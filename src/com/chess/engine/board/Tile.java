package com.chess.engine.board;

import com.chess.engine.pieces.Piece;

import java.util.HashMap;

public abstract class Tile {

    protected final int TILE_COORDINATE_ROW, TILE_COORDINATE_COLUMN;

    private static final HashMap<Integer, HashMap<Integer, EmptyTile>> EMPTY_TILE_CACHE = createAllEmptyTiles();

    private static HashMap<Integer, HashMap<Integer, EmptyTile>> createAllEmptyTiles() {
        HashMap<Integer, HashMap<Integer, EmptyTile>> columns = new HashMap<>();

        for (int i = 0; i < Board.TILES_PER_ROW; i++) {
            columns.put(i, new HashMap<>());
            for (int j = 0; j < Board.TILES_PER_ROW; j++) {
                columns.get(i).put(j, new EmptyTile(i, j));
            }
        }

        return columns;
    }

    private Tile(int xCoordinate, int yCoordinate) {
        this.TILE_COORDINATE_ROW = xCoordinate;
        this.TILE_COORDINATE_COLUMN = yCoordinate;
    }

    public abstract boolean isTileOccupied();

    public abstract Piece getPiece();

    public static Tile createTile(final int xCoordinate, final int yCoordinate, final Piece piece) {
        if (piece == null) {
            return EMPTY_TILE_CACHE.get(xCoordinate).get(yCoordinate);
        }

        return new OccupiedTile(xCoordinate, yCoordinate, piece);
    }

    public int getTILE_COORDINATE_COLUMN() {
        return TILE_COORDINATE_COLUMN;
    }

    public int getTILE_COORDINATE_ROW() {
        return TILE_COORDINATE_ROW;
    }

    public static final class EmptyTile extends Tile {

        private EmptyTile(final int xCoordinate, final int yCoordinate) {
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

        @Override
        public String toString() {
            return "-";
        }
    }

    public static final class OccupiedTile extends Tile {

        private final Piece PIECE_ON_TILE;

        private OccupiedTile(final int xCoordinate, final int yCoordinate, Piece piece) {
            super(xCoordinate, yCoordinate);
            this.PIECE_ON_TILE = piece;
        }

        @Override
        public boolean isTileOccupied() {
            return true;
        }

        @Override
        public Piece getPiece() {
            return this.PIECE_ON_TILE;
        }

        @Override
        public String toString() {
            return getPiece().getPieceAlliance().isBlack() ? getPiece().toString().toLowerCase() : getPiece().toString();
        }
    }
}
