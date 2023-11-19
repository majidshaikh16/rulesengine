package com.rules.engine.practice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SumOf3 {
    public static void main(String[] args) {
        int[] input = {-1,0,1,2,-1,4};
        List<List<Integer>> lists = threeSum(input);
        System.out.println(lists);
    }
    public static List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> list = new ArrayList<>();
        Map<Integer, Integer> existMap = new HashMap<>();
        Map<Integer, Integer> map = new HashMap<>();
        for(int i = 0 ; i < nums.length ; i ++){
            int num = nums[i];
            if(num == 0){
                continue;
            }
            int find = 0 - num;

            for(int j = 0; j < nums.length; j++){
                int eval = find - nums[j];
                if(map.containsKey(eval)){
                    int sum = num + eval + nums[j];
                    List<Integer> collect = List.of(num, eval, nums[j]).stream().sorted().collect(Collectors.toList());
//                    if(!existMap.containsKey(sum)){
//                        existMap.put(sum, 0);
                        List<Integer> found = new ArrayList<>();
                        found.add(num);
                        found.add(eval);
                        found.add(nums[j]);
                        list.add(found);
                        break;
//                    }
                }else{
                    map.put(nums[j], 0);
                }
            }
        }
        return list;
    }
}
