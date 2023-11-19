package com.rules.engine.practice;

import java.util.Arrays;

public class InsertionSort {

    public static void main(String[] args) {
        insertionSort(new int[]{8,5,2,9,5,6,3});
        char a = 's';
        char b = 's';
        StringBuilder ss = new StringBuilder();
        ss.append(3 + b);
        String aa = "                                              ".trim();
        System.out.println("---"+3+a);
    }

    public static int[] insertionSort(int[] array) {
        for(int i = 0 ; i < array.length ; i++){
            int nextIndex = i + 1;
            if(nextIndex < array.length && array[nextIndex] < array[i]){
                //swap(array, nextIndex);
                for(int j = nextIndex ; j > 0 ; j--){
                    if(array[j] < array[j-1]){
                        int temp = array[j-1];
                        array[j-1] = array[j];
                        array[j] = temp;
                    }
                }
            }
        }
        System.out.println(Arrays.toString(array));
        return array;
    }
}
