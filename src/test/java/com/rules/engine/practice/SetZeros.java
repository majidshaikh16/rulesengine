package com.rules.engine.practice;

import java.util.*;

public class SetZeros {
    public void setZeroesOptimised(int[][] matrix) {
        int R = matrix.length;
        int C = matrix[0].length;
        Set<Integer> rows = new HashSet<Integer>();
        Set<Integer> cols = new HashSet<Integer>();

        // Essentially, we mark the rows and columns that are to be made zero
        for (int i = 0; i < R; i++) {
            for (int j = 0; j < C; j++) {
                if (matrix[i][j] == 0) {
                    rows.add(i);
                    cols.add(j);
                }
            }
        }

        // Iterate over the array once again and using the rows and cols sets, update the elements.
        for (int i = 0; i < R; i++) {
            for (int j = 0; j < C; j++) {
                if (rows.contains(i) || cols.contains(j)) {
                    matrix[i][j] = 0;
                }
            }
        }
    }
    public void setZeroes(int[][] matrix) {
        Map<Integer, Integer> colIndexMap = new HashMap();
        int rowSize = matrix.length;
        int colSize = matrix[0].length;
        for (int row = 0 ; row < rowSize ; row++){
            boolean foundZero = false;
            int foundZeroAtIndex = -1;
            int foundAtRowIndex = -1;
            for(int col = 0 ; col < colSize ; col ++){
                int num = matrix[row][col];
                if(num == 0){
                    colIndexMap.put(col, num);
                    foundZero = true;
                    foundZeroAtIndex = col;
                    foundAtRowIndex = row;
                }
                if(foundZero){
                    matrix[row][col] = 0;
                }
                if(colIndexMap.containsKey(col)){
                    matrix[row][col] = 0;
                }

            }
            if(foundZeroAtIndex > -1){
                for(int i = 0 ; i < foundZeroAtIndex ; i++){
                    matrix[row][i] = 0;
                }
            }
            if(foundAtRowIndex > -1){
                for(int i = 0 ; i < foundAtRowIndex ; i++){
                    for(int j = 0 ; j < colSize ; j ++){
                        if(colIndexMap.containsKey(j)){
                            matrix[i][j] = 0;
                        }
                    }
                }
            }
        }
    }
}
