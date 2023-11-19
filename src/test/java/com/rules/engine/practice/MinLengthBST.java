package com.rules.engine.practice;

import lombok.ToString;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MinLengthBST {
    public static void main(String[] args) {
        System.out.println(minHeightBst(List.of(1,2,5,7,10,13,14,15,22)));
    }
    public static BST minHeightBst(List<Integer> array) {
        return build(array, 0, array.size() - 1);
    }

    public static BST build(List<Integer> array, int low, int high){
        if(high < low) return null;
        int mid = (low + high) / 2;
        BST bst = new BST(array.get(mid));
        bst.left = build(array, low, mid - 1);
        bst.right = build(array, mid + 1, high);
        return bst;
    }

@ToString
    static class BST {
        public int value;
        public BST left;
        public BST right;

        public BST(int value) {
            this.value = value;
            left = null;
            right = null;
        }

        public void insert(int value) {
            if (value < this.value) {
                if (left == null) {
                    left = new BST(value);
                } else {
                    left.insert(value);
                }
            } else {
                if (right == null) {
                    right = new BST(value);
                } else {
                    right.insert(value);
                }
            }
        }
    }
}
