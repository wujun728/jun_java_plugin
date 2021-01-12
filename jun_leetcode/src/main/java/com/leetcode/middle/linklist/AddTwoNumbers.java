package com.leetcode.middle.linklist;

import com.leetcode.leetcodeutils.ListNode;
import com.leetcode.leetcodeutils.NodeWrapper;

/**
 * 两数相加
 *
 * @author BaoZhou
 * @date 2018/12/24
 */
public class AddTwoNumbers {
    public static void main(String[] args) {
        int[] data = {0, 8};
        //int[] data = {1, 0,0};
        ListNode head1 = new ListNode(7);
        ListNode p1 = head1;
        for (int i = 0; i < data.length; i++) {
            p1.next = new ListNode(data[i]);
            p1 = p1.next;
        }

        int[] data2 = {0};
        ListNode head2 = new ListNode(8);
        ListNode p2 = head2;
        for (int i = 0; i < data2.length; i++) {
            p2.next = new ListNode(data2[i]);
            p2 = p2.next;
        }
        ListNode result = addTwoNumbers(head1, head2);
        NodeWrapper.prettyPrintLinkedList(result);

    }

    public static ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode result = new ListNode(0);
        ListNode head = result;
        Boolean carry = false;
        while (l1 != null || l2 != null || carry) {
            int val;
            if (carry) {
                val = 1;
            } else {
                val = 0;
            }

            if (l1 != null) {
                val += l1.val;
                l1 = l1.next;
            }
            if (l2 != null) {
                val += l2.val;
                l2 = l2.next;
            }
            carry = val >= 10;
            ListNode nextNode = new ListNode(val % 10);
            result.next = nextNode;
            result = result.next;
        }
        return head.next;
    }
}
