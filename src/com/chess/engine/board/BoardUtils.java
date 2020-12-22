package com.chess.engine.board;

import com.sun.org.apache.xpath.internal.operations.Bool;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

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

}
