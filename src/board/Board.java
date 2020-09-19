package board;

import pieces.*;

import java.util.*;

public class Board {

    public static final int NUM_TILES = 64;
    public static final int TILES_PER_ROW = 8;

    private final ArrayList<Tile> GAME_BOARD;
    private final Collection<Piece> whitePieces;
    private final Collection<Piece> blackPieces;

    public Board(Builder builder) {
        this.GAME_BOARD = createGameBoard(builder);
        this.whitePieces = calculateActivePiece (this.GAME_BOARD, Alliance.WHITE);
        this.blackPieces = calculateActivePiece (this.GAME_BOARD, Alliance.BLACK);
    }

    private static ArrayList<Tile> createGameBoard(Builder builder) {
        Tile[][] tiles = new Tile[TILES_PER_ROW][TILES_PER_ROW];

        for (int i = 0; i < TILES_PER_ROW; i++) {
            for (int j = 0; j < TILES_PER_ROW; j++) {
                tiles[i][j] = Tile.createTile(i, j, builder.boardConfig.get(i).get(j));
            }
        }
        ArrayList<Tile> listOfTiles = new ArrayList<>();
        for (Tile[] innerTiles: tiles) {
            listOfTiles.addAll(Arrays.asList(innerTiles));
        }

        return listOfTiles;
    }

    public static Board createStandardBoard() {
        Builder builder = new Builder();

        builder.setPiece(new Rook(0, 0 ,Alliance.BLACK));
        builder.setPiece(new Knight(0, 1, Alliance.BLACK));
        builder.setPiece(new Bishop(0, 2, Alliance.BLACK));
        builder.setPiece(new Queen(0, 3, Alliance.BLACK));
        builder.setPiece(new King(0, 4, Alliance.BLACK));
        builder.setPiece(new Bishop(0, 5, Alliance.BLACK));
        builder.setPiece(new Knight(0, 6, Alliance.BLACK));
        builder.setPiece(new Rook(0, 7, Alliance.BLACK));
        builder.setPiece(new Pawn(1, 0, Alliance.BLACK));
        builder.setPiece(new Pawn(1, 1, Alliance.BLACK));
        builder.setPiece(new Pawn(1, 2, Alliance.BLACK));
        builder.setPiece(new Pawn(1, 3, Alliance.BLACK));
        builder.setPiece(new Pawn(1, 4, Alliance.BLACK));
        builder.setPiece(new Pawn(1, 5, Alliance.BLACK));
        builder.setPiece(new Pawn(1, 6, Alliance.BLACK));
        builder.setPiece(new Pawn(1, 7, Alliance.BLACK));

        builder.setPiece(new Rook(7, 0 ,Alliance.WHITE));
        builder.setPiece(new Knight(7, 1, Alliance.WHITE));
        builder.setPiece(new Bishop(7, 2, Alliance.WHITE));
        builder.setPiece(new Queen(7, 3, Alliance.WHITE));
        builder.setPiece(new King(7, 4, Alliance.WHITE));
        builder.setPiece(new Bishop(7, 5, Alliance.WHITE));
        builder.setPiece(new Knight(7, 6, Alliance.WHITE));
        builder.setPiece(new Rook(7, 7, Alliance.WHITE));
        builder.setPiece(new Pawn(6, 0, Alliance.WHITE));
        builder.setPiece(new Pawn(6, 1, Alliance.WHITE));
        builder.setPiece(new Pawn(6, 2, Alliance.WHITE));
        builder.setPiece(new Pawn(6, 3, Alliance.WHITE));
        builder.setPiece(new Pawn(6, 4, Alliance.WHITE));
        builder.setPiece(new Pawn(6, 5, Alliance.WHITE));
        builder.setPiece(new Pawn(6, 6, Alliance.WHITE));
        builder.setPiece(new Pawn(6, 7, Alliance.WHITE));

        return builder.build();
    }

    private Collection<Piece> calculateActivePiece(ArrayList<Tile> gameBoard, Alliance alliance) {
        ArrayList<Piece> activePieces = new ArrayList<>();

        for (Tile tile : gameBoard) {
            if (tile.isTileOccupied()) {
                if (tile.getPiece().getPieceAlliance() == alliance) {
                    activePieces.add(tile.getPiece());
                }
            }
        }

        return activePieces;
    }

    public Tile getTile( int coordinateX, int coordinateY){
        return null;
    }

    public static boolean coordinateIsValid(int coordinateX, int coordinateY) {

        return ((coordinateX >= 0 && coordinateX < TILES_PER_ROW) && (coordinateY >= 0 && coordinateY < TILES_PER_ROW));
    }

    public static class Builder {

        private HashMap<Integer, HashMap<Integer, Piece>> boardConfig;
        private Alliance nextMoveMaker;

        public Builder () {
            boardConfig = new HashMap<>();
        }

        public Builder setPiece(Piece piece) {

            boardConfig.computeIfAbsent(piece.getPiecePositionRow(), k -> new HashMap<>());

                boardConfig.get(piece.getPiecePositionRow()).put(piece.getPiecePositionColumn(), piece);


            return this;
        }

        public Builder setMoveMaker (Alliance nextMoveMaker) {

            this.nextMoveMaker = nextMoveMaker;
            return this;
        }

         public Board build() {
             return new Board(this);
         }
    }
}
