package com.rules.engine.practice;

public class ValidBST {
    public static void main(String[] args) {
        BST tree = new BST(10);
        tree.left = new BST(5);
        tree.left.right = new BST(10);
        tree.right = new BST(15);
        System.out.println(validateBst(tree));
        int j = 16 /2;
        int k = 3/2;
        System.out.println(j);
        System.out.println(k);

    }
    public static boolean validateBst(BST tree) {
        if(tree.left != null){
            boolean isvalidLeft = validateLeft(tree, tree.left);
            if(!isvalidLeft)
                return false;
        }

        if(tree.right != null){
            boolean isvalidRight = validateRight(tree, tree.right);
            if(!isvalidRight)
                return false;
        }

        return true;
    }

    public static boolean validateLeft(BST parent, BST child){
        if(parent.value > child.value){
            if(child.left != null)
                return validateLeft(child, child.left);
            if(child.right != null)
                return validateRight(child, child.right);
        }else
            return false;

        return true;
    }

    public static boolean validateRight(BST parent, BST child){
        if(parent.value <= child.value){
            if(child.right != null)
                return validateRight(child, child.right);
            if(child.left != null)
                return validateLeft(child, child.left);
        }else
            return false;

        return true;
    }

    static class BST {
        public int value;
        public BST left;
        public BST right;

        public BST(int value) {
            this.value = value;
        }
    }
}
