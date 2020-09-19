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

        Collection<Move> whiteStandardLegalMoves = calculateLegalMoves(this.whitePieces);
        Collection<Move> blackStandardLegalMoves = calculateLegalMoves(this.blackPieces);
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

    private static Collection<Piece> calculateActivePiece(ArrayList<Tile> gameBoard, Alliance alliance) {
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

    private Collection<Move> calculateLegalMoves(Collection<Piece> pieces) {
        ArrayList<Move> legalMoves = new ArrayList<>();

        for (Piece piece : pieces) {
            legalMoves.addAll(piece.calculateMoves(this));
        }

        return legalMoves;
    }

    public Tile getTile( int coordinateX, int coordinateY){
        return this.GAME_BOARD.get((coordinateX * TILES_PER_ROW) + coordinateY);
    }

    public static boolean coordinateIsValid(int coordinateX, int coordinateY) {

        return ((coordinateX >= 0 && coordinateX < TILES_PER_ROW) && (coordinateY >= 0 && coordinateY < TILES_PER_ROW));
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < NUM_TILES; i++) {
            String tileText = this.GAME_BOARD.get(i).toString();
            stringBuilder.append(String.format("%3s", tileText));

            if ((i + 1) % TILES_PER_ROW == 0) {
                stringBuilder.append('\n');
            }
        }

        return stringBuilder.toString();
    }

    public static class Builder {

        private HashMap<Integer, HashMap<Integer, Piece>> boardConfig;
        private Alliance nextMoveMaker;

        public Builder () {
            this.boardConfig = new HashMap<>();
            for (int i = 0; i < Board.TILES_PER_ROW; i ++) {
                boardConfig.put(i, new HashMap<>());
            }
        }

        public Builder setPiece(Piece piece) {


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
