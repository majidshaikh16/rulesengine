package com.rules.engine.practice;

import java.util.*;

public class GroupAnagrams {
    public static void main(String[] args) {
        String s [] = {"eat","tea","tan","ate","nat","bat"};
        groupAnagrams(s);
    }

    public static List<List<String>> groupAnagrams(String[] strs) {
        Map<String, List<String>> map = new HashMap<>();
        List<List<String>> list = new ArrayList<>();
        for(int i = 0 ; i < strs.length ; i ++){
            char[] c = strs[i].toCharArray();
            Arrays.sort(c);
            String sorted = new String(c);
            List<String> anagrams  = map.get(sorted);
            if(anagrams != null){
                anagrams.add(strs[i]);
                map.put(sorted, anagrams);
            }else{
                anagrams = new ArrayList<>();
                anagrams.add(strs[i]);
                map.put(sorted, anagrams);
            }
        }
        return new ArrayList<>(map.values());
    }
}
