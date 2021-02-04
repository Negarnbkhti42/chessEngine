package com.chess.engine.board;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BoardUtils {
    public static final int TILES_PER_ROW = 8;
    public static final int NUM_TILES = 64;

    public static final List<Boolean> FIRST_COLUMN = initColumn(0);
    public static final List<Boolean> SECOND_COLUMN = initColumn(1);
    public static final List<Boolean> THIRD_COLUMN = initColumn(2);
    public static final List<Boolean> FOURTH_COLUMN = initColumn(3);
    public static final List<Boolean> FIFTH_COLUMN = initColumn(4);
    public static final List<Boolean> SIXTH_COLUMN = initColumn(5);
    public static final List<Boolean> SEVENTH_COLUMN = initColumn(6);
    public static final List<Boolean> EIGHTH_COLUMN = initColumn(7);

    public static final List<Boolean> EIGHTH_RANK = initRow(0);
    public static final List<Boolean> SEVENTH_RANK = initRow(1);
    public static final List<Boolean> SIXTH_RANK = initRow(2);
    public static final List<Boolean> FIFTH_RANK = initRow(3);
    public static final List<Boolean> FOURTH_RANK = initRow(4);
    public static final List<Boolean> THIRD_RANK = initRow(5);
    public static final List<Boolean> SECOND_RANK = initRow(6);
    public static final List<Boolean> FIRST_RANK = initRow(7);

    public static final String[][] ALGEBRAIC_NOTATION = initializeAlgebraicNotation();
    public static final Map<String, Integer[]> POSITION_TO_COORDINATE = initializePositionToCoordinateMap();

    private static List<Boolean> initColumn(int columnNumber) {

        final Boolean[] column = new Boolean[NUM_TILES];

        for (int i = 0; i < column.length; i++) {
            column[i] = false;
        }

        do {
            column[columnNumber] = true;
            columnNumber += TILES_PER_ROW;
        } while (columnNumber < NUM_TILES);

        return Arrays.asList(column);
    }

    private static List<Boolean> initRow( int rowNumber) {

        final Boolean[] row = new Boolean[NUM_TILES];

        for(int i = 0; i < row.length; i++) {
            row[i] = false;
        }
        rowNumber *= TILES_PER_ROW;
        do {
            row[rowNumber] = true;
            rowNumber++;
        } while(rowNumber % TILES_PER_ROW != 0);

        return Arrays.asList(row);
    }

    private static String[][] initializeAlgebraicNotation() {
        String[][] algebraicNotations = new String[8][8];

        for (int i = 0; i < 8; i++) {
            for (int j =0; j < 8; j++) {
                char columnChar = getColumnChar(j);
                algebraicNotations[i][j] = "" + columnChar + (8-i);
            }
        }

        return algebraicNotations;
    }

    private static char getColumnChar(int column) {
        switch (column) {
            case 0 :
                return 'a';
            case 1 :
                return 'b';
            case 2 :
                return 'c';
            case 3 :
                return 'd';
            case 4 :
                return 'e';
            case 5 :
                return 'f';
            case 6 :
                return 'g';
            default:
                return 'h';
        }
    }

    private static Map<String, Integer[]> initializePositionToCoordinateMap() {
        Map<String, Integer[]> coordinateMap = new HashMap<>();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                coordinateMap.put(ALGEBRAIC_NOTATION[i][j], new Integer[] {i, j});
            }
        }
        return coordinateMap;
    }

    public static boolean coordinateIsValid(int coordinateX, int coordinateY) {

        return ((coordinateX >= 0 && coordinateX < TILES_PER_ROW) && (coordinateY >= 0 && coordinateY < TILES_PER_ROW));
    }

    public static Integer[] getCoordinateAtPosition (String position) {
        return POSITION_TO_COORDINATE.get(position);
    }

    public static String getPositionAtCoordinate (int coordinateRow, int coordinateColumn) {
        return ALGEBRAIC_NOTATION[coordinateRow] [coordinateColumn];
    }
}
