package com.leetcode.middle.linklist;

import com.leetcode.leetcodeutils.ListNode;
import com.leetcode.leetcodeutils.NodeWrapper;

/**
 * 奇偶链表
 *
 * @author BaoZhou
 * @date 2018/12/24
 */
public class OddEvenList {
    public static void main(String[] args) {
        int[] data = {2};
        ListNode head1 = new ListNode(1);
        ListNode p1 = head1;
        for (int i = 0; i < data.length; i++) {
            p1.next = new ListNode(data[i]);
            p1 = p1.next;
        }
        ListNode result = oddEvenList(head1);
        NodeWrapper.prettyPrintLinkedList(result);
    }

    public static ListNode oddEvenList(ListNode head) {

        if (head == null || head.next == null) {
            return head;
        }

        ListNode p = head;
        ListNode evenHead = p.next;
        ListNode p2 = evenHead;


        while (p.next != null && p.next.next != null) {
            p.next = p.next.next;
            p2.next = p.next.next;
            p = p.next;
            p2 = p2.next;
        }
        p.next = evenHead;
        return head;
    }
}
