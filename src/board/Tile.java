package board;

import pieces.Piece;

import java.util.HashMap;

public abstract class Tile {

    protected final int[] TILE_COORDINATE;

    private static final HashMap<Integer, HashMap<Integer, EmptyTile>> EMPTY_TILE_CACHE = createAllEmptyTiles();

    private static HashMap<Integer, HashMap<Integer, EmptyTile>> createAllEmptyTiles(){
        HashMap<Integer, HashMap<Integer, EmptyTile>> columns = new HashMap<>();

        for (int i=0; i<8; i++){
            columns.put(i, new HashMap<>());
            for (int j=0; j<8; j++){
                columns.get(i).put(j, new EmptyTile(i,j));
            }
        }

        return columns;
    }

    private Tile(int xCoordinate, int yCoordinate){
        this.TILE_COORDINATE =new int[] {xCoordinate, yCoordinate};
    }

    public abstract boolean isTileOccupied();

    public abstract Piece getPiece();

    public static Tile createTile(final int xCoordinate,final int yCoordinate,final Piece piece){
        if (piece == null){
            return EMPTY_TILE_CACHE.get(xCoordinate).get(yCoordinate);
        }

        return new OccupiedTile(xCoordinate, yCoordinate, piece);
    }

    public static final class EmptyTile extends Tile{

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
    }

    public static final class OccupiedTile extends Tile{

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
    }
}
