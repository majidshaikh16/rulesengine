package com.rules.engine.practice;

import java.util.Arrays;
import java.util.List;

public class SmallestDiff {
    public static void main(String[] args) {
        StringBuilder sb = new StringBuilder();;
        sb.setLength(0);
        char a = 'a';
        sb.append(a);
        System.out.println(sb.toString());
        List l =  null;
        l.set(1,"");
        smallestDifference(new int[]{10, 1000, 9124, 2142, 59, 24, 596, 591, 124, -123},new int[]{-1441, -124, -25, 1014, 1500, 660, 410, 245, 530});
    }
    public static int[] smallestDifference(int[] arrayOne, int[] arrayTwo) {
        Arrays.sort(arrayOne);
        Arrays.sort(arrayTwo);
        int oneLength = arrayOne.length;
        int arrayOneLargestNum = arrayOne[oneLength - 1];
        int[] pair = new int[2];
        pair[0] = arrayOneLargestNum;
        pair[1] = arrayTwo[0];
        int prevDiff = Integer.MAX_VALUE;
        for(int i = oneLength-1 ; i >= 0 ; i--){
            int diff = getDiff(i, prevDiff, arrayOne, arrayTwo, pair);
            if(prevDiff < diff || 0==diff)
                break;
            prevDiff = diff;
        }


        return pair;
    }

    public static int getDiff(int i, int prevDiff, int[] arrayOne, int[] arrayTwo, int[] pair){
        for(int j = 0 ; j < arrayTwo.length ; j++){
            int diff = arrayOne[i] - arrayTwo[j];
            if(diff == 0){
                pair[0] = arrayOne[i];
                pair[1] = arrayTwo[j];
                return 0;
            }
            else if(diff > 0 && diff < prevDiff){
                pair[0] = arrayOne[i];
                pair[1] = arrayTwo[j];
            }
            else if(diff < 0 && Math.abs(diff) < prevDiff){
                pair[0] = arrayOne[i];
                pair[1] = arrayTwo[j];
            }
            else if(arrayTwo[j] < 0)
                continue;
            else{
                break;
            }
            prevDiff = diff;
        }
        return prevDiff;
    }
}
