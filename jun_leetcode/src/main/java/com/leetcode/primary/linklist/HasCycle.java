package com.leetcode.primary.linklist;

import com.leetcode.leetcodeutils.ListNode;

/**
 * 环形链表
 *
 * @author: BaoZhou
 * @date : 2018/12/11 19:54
 */
class HasCycle {
    public static void main(String[] args) {
        int[] data = {1};
        //int[] data = {1, 0,0};
        ListNode head = new ListNode(0);
        ListNode p = head;
        for (int i = 0; i < data.length; i++) {
            p.next = new ListNode(data[i]);
            p = p.next;
        }
        ListNode p2 = head;
        while (head != null) {
            System.out.print(head.val + "->");
            head = head.next;
        }


        System.out.println(hasCycle(p2.next));

    }

    public static boolean hasCycle(ListNode head) {
        if (head == null) {
            return false;
        }
        if (head.next == null) {
            return false;
        }

        ListNode slow = head;
        ListNode fast = head;

        while (fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (fast == null) {
                return false;
            }
            if (slow.equals(fast)) {
                return true;
            }
        }
        return false;
    }
}