package com.rules.engine.binarysearch;
import java.util.*;
import java.util.stream.Collectors;

public class AggressiveCows {
    public static void main(String[] args) {
        aggressiveCows(new int[]{1,2,3},2);
    }
    public static int aggressiveCows(int []stalls, int k) {
        Arrays.sort(stalls);
        int n = stalls.length;
        int low = stalls[0], high = stalls[n - 1];
        int ans = Integer.MAX_VALUE;
        while(high >= low){
            int mid = (low + high) / 2;
            if(isPossible(stalls, mid, k)){
                ans = Math.min(ans, mid);
                System.out.println("Ans -> " + ans);
                high = mid - 1;
            }else{
                low = mid + 1;
            }
        }
        return ans;
    }

    private static boolean isPossible(int[] stalls, int pos, int k){
        int cowPlaces = 1;
        int prevPos = stalls[0];
        for(int i = 1; i < stalls.length; i++){
            int curr = stalls[i];
            int diffInPos = curr - prevPos;
            if(diffInPos >= pos){
                prevPos = curr;
                cowPlaces++;
            }
        }
        return cowPlaces == k;
    }
}