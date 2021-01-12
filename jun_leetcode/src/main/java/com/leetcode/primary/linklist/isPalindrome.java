package com.leetcode.primary.linklist;

import com.leetcode.leetcodeutils.ListNode;

/**
 * 回文链表
 * 1.先通过快慢指针寻找中点
 * 2.翻转反转后半部分链表
 * 3.对比
 * @author: BaoZhou
 * @date : 2018/12/11 19:54
 */
class isPalindrome {
    public static void main(String[] args) {
        int[] data = {1, 2, 3, 2, 1};
        //int[] data = {1, 0,0};
        ListNode head = new ListNode(0);
        ListNode p = head;
        for (int i = 0; i < data.length; i++) {
            p.next = new ListNode(data[i]);
            p = p.next;
        }
        ListNode p2 = head.next;
        while (p2 != null) {
            System.out.print(p2.val + "->");
            p2 = p2.next;
        }

        System.out.println(isPalindrome(head.next));

    }

    public static boolean isPalindrome(ListNode head) {
        if (head == null || head.next == null) {
            return true;
        }

        ListNode slow = head;
        ListNode fast = head;

        while (fast != null && fast.next!=null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        ListNode p = slow;
        ListNode pre = null;
        while (p != null) {
            ListNode next = p.next;
            p.next = pre;
            pre = p;
            p = next;
        }

        while (pre != null) {
            if (head.val != pre.val) {
                return false;
            } else {
                head = head.next;
                pre = pre.next;
            }
        }
        return true;
    }
}