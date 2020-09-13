package board;

public class Board {

    public Tile getTile( int coordinateX, int coordinateY){
        return null;
    }

    public static boolean coordinateIsValid(int coordinateX, int coordinateY) {

        return ((coordinateX >= 0 && coordinateX < 8) && (coordinateY >= 0 && coordinateY < 8));
    }
}
