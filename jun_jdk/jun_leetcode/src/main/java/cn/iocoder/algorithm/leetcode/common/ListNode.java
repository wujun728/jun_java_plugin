package cn.iocoder.algorithm.leetcode.common;

public class ListNode {

    public int val;

    public ListNode next;

    public ListNode(int x) {
        val = x;
        next = null;
    }

    @Override
    public String toString() {
        return "ListNode{" +
                "val=" + val +
                '}';
    }

    public static ListNode create(int... args) {
        if (args.length == 0) {
            return null;
        }
        ListNode dummy = new ListNode(0);
        ListNode current = dummy;
        for (int arg : args) {
            current.next = new ListNode(arg);
            current = current.next;
        }
        return dummy.next;
    }

}
