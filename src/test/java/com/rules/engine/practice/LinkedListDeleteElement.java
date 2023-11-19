package com.rules.engine.practice;

import java.util.*;
import java.util.stream.Collectors;

public class LinkedListDeleteElement {
    public static void main(String[] args) {
        ListNode listNode1 = new ListNode(7, null);
        ListNode listNode2 = new ListNode(7, listNode1);
        ListNode listNode3 = new ListNode(7, listNode2);
        ListNode listNode4 = new ListNode(7, listNode3);
        removeElements(listNode4, 7);
        List<Integer> l = new ArrayList();
        l.add(1);
        l.add(10);
        l.add(13);
        l.add(6);
        l.sort(Comparator.reverseOrder());
        Arrays.sort(args, Collections.reverseOrder());
        int [] sort = {1,10,13,6};
        int[] arrDesc = Arrays.stream(sort).boxed()
                .sorted(Collections.reverseOrder())
                .mapToInt(Integer::intValue)
                .toArray();
        String s = Arrays.toString(arrDesc);
        System.out.println(s);
        if(l instanceof ArrayList){}
        int i = Integer.valueOf(1);
    }

    public static int calcSum(List<Object> array, int depth){
        int sum = 0;
        for(Object product : array){
            if(product instanceof ArrayList){
                sum += depth * calcSum((List<Object>) product, depth + 1);
            }else if(product instanceof Integer){
                sum += (Integer) product;
            }
        }
        return sum;
    }
    public static ListNode removeElements(ListNode head, int val) {

        ListNode temp = head;
        ListNode prev = head;
        while (temp != null) {
            if (prev.val == val) {
                prev = prev.next;
                head = head.next;
            } else if (temp.val == val) {
                prev.next = temp.next;
            } else {
                prev = temp;
            }
            temp = temp.next;
        }
        return head;
    }

    public static class ListNode {
        int val;
        ListNode next;

        ListNode() {
        }

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }
}




