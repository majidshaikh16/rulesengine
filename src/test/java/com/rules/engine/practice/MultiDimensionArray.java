package com.rules.engine.practice;

public class MultiDimensionArray {
    public static void main(String[] args) {
        int[][] arr = new int [3][5];
        for (int i = 0 ; i < arr.length ; i++){
            arr[i][i] = 1;
        }
        int i = 0;
    }
}
