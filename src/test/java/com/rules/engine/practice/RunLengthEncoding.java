package com.rules.engine.practice;

import java.util.*;

public class RunLengthEncoding {
    public static void main(String[] args) {
        char c = 's';
        System.out.println(runLengthEncoding("AAAAAAAAAAAAABBCCCCDD") + c);
        String s1 = "aheaollabbhb";
        String s2 = "hello";
        char[] chars1 = s1.toCharArray();
        Arrays.sort(chars1);
        s1 = new String(chars1);
        char[] chars2 = s2.toCharArray();
        Arrays.sort(chars2);
        s2 = new String(chars2);
        System.out.println("char 1 : "+ s1);
        System.out.println("char 2 : "+ s2);
        System.out.println("char 3 : "+ s1.contains(s2));
        String str = String.valueOf(c);
        Set<Character> existChars = new HashSet<>();
    }

    public static String runLengthEncoding(String string) {
        string = string.trim();
        if(string.length() == 0)
            return string;

        int count = 1;
        int maxCharInRow = 10;
        char currChar = '0';
        StringBuilder encoded = new StringBuilder();
        for(int i = 0 ; i < string.length() ; i ++){
            if(i+1 < string.length()){
                currChar = string.charAt(i);
                char nextChar = string.charAt(i+1);
                if(currChar == nextChar){
                    count++;
                }else{
                    if(count > maxCharInRow){
                        int appendCount = count / 9;
                        int remainder  = count % 9;
                        for(int j = 0 ; j < appendCount ; j++){
                            encoded.append(9 + currChar);
                        }
                        if(remainder > 0){
                            encoded.append(remainder + currChar);
                        }
                    }else{
                        encoded.append(count + currChar);
                    }
                    count = 1;
                }
            }
        }
        return encoded.toString();
    }
}
