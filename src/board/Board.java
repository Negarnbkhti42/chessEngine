package board;

public class Board {

    public static final int NUM_TILES = 64;
    public static final int TILES_PER_ROW = 8;

    public Tile getTile( int coordinateX, int coordinateY){
        return null;
    }

    public static boolean coordinateIsValid(int coordinateX, int coordinateY) {

        return ((coordinateX >= 0 && coordinateX < TILES_PER_ROW) && (coordinateY >= 0 && coordinateY < TILES_PER_ROW));
    }
}
