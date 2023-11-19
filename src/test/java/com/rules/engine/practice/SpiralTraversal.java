package com.rules.engine.practice;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SpiralTraversal {
    public static void main(String[] args) {
        int[][] array = {
                {1,2,3,4},
                {12,13,14,5},
                {11,16,15,6},
                {10,9,8,7}
        };
        spiralTraverse(array);

        int[][] intervals = {{2, 3},
                {4, 5},
                {6, 7},
                {8, 9},
                {1, 10}

  };
    }

    public static List<Integer> spiralTraverse(int[][] array) {
        List<Integer> result = new ArrayList<Integer>();
        int rows = array.length ;
        int columns = array[0].length ;
        //moveForward(0, rows - 1, 0, columns - 1, array, result, rows * columns);
        spiralFill(0, rows - 1, 0, columns - 1, array, result, rows * columns);
        return result;
    }

    private static void spiralFill(int row,
                                    int rowStop,
                                    int column,
                                    int columnStop,
                                    int[][] array,
                                    List<Integer> result,
                                    int maxTravel){
        if(stopSpiral(result, maxTravel))
            return;

        for(int i = column; i <= columnStop; i ++){
            result.add(array[row][i]);
        }
        //move down
        //moveDown(columnStop, column, row + 1, rowStop, array, result, maxTravel);
        for(int i = row + 1; i <= rowStop; i ++){
            result.add(array[i][columnStop]);
        }
        //move back
        //moveBack(rowStop, row, column - 1, columnStop, array, result, maxTravel);
        for(int i = columnStop - 1; i >= column; i-- ){
            result.add(array[rowStop][i]);
        }
        //move up
        //moveUp(row - 1, rowStop, columnStop, column, array, result, maxTravel);
        for(int i = rowStop - 1; i >= row + 1; i-- ){
            result.add(array[i][column]);
        }
        //move forward
        spiralFill(row + 1, rowStop - 1, column + 1, columnStop - 1, array, result, maxTravel);
    }

    private static void moveForward(int row,
                                    int rowStop,
                                    int column,
                                    int columnStop,
                                    int[][] array,
                                    List<Integer> result,
                                    int maxTravel){
        if(stopSpiral(result, maxTravel))
            return;

        for(int i = column; i <= columnStop; i ++){
            result.add(array[row][i]);
        }
        //move down
        moveDown(columnStop, column, row + 1, rowStop, array, result, maxTravel);
    }

    private static void moveDown(int column,
                                 int columnStop,
                                 int row,
                                 int rowStop,
                                 int[][] array,
                                 List<Integer> result,
                                 int maxTravel){
        if(stopSpiral(result, maxTravel))
            return;

        for(int i = row; i <= rowStop; i ++){
            result.add(array[i][column]);
        }
        //move back
        moveBack(rowStop, row, column - 1, columnStop, array, result, maxTravel);
    }

    private static void moveBack(int row,
                                 int rowStop,
                                 int column,
                                 int columnStop,
                                 int[][] array,
                                 List<Integer> result,
                                 int maxTravel){
        if(stopSpiral(result, maxTravel))
            return;

        for(int i = column; i >= columnStop; i-- ){
            result.add(array[row][i]);
        }
        //move up
        moveUp(row - 1, rowStop, columnStop, column, array, result, maxTravel);

    }

    private static void moveUp( int row,
                                int rowStop,
                                int column,
                                int columnStop,
                                int[][] array,
                                List<Integer> result,
                                int maxTravel){
        if(stopSpiral(result, maxTravel))
            return;

        for(int i = row; i >= rowStop; i-- ){
            result.add(array[i][column]);
        }
        int a = 0 , b= 0;
        int c = 0;
        c *= a;
        //move forward
        moveForward(rowStop, row, column + 1, columnStop, array, result, maxTravel);
    }




    private static boolean stopSpiral(List<Integer> result, int maxTravel){
        return result.size() >= maxTravel;
    }
}
