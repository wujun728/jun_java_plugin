package com.leetcode.primary.linklist;

import com.leetcode.leetcodeutils.ListNode;

/**
 * 合并两个有序链表
 *
 * @author: BaoZhou
 * @date : 2018/12/11 19:54
 */
class MergeTwoLists {
    public static void main(String[] args) {
        ListNode head1 = new ListNode(1);
        ListNode head2 = new ListNode(2);
        ListNode nextNode1 = head1;
        ListNode nextNode2 = head2;
        for (int i = 2; i <= 5; i++) {
            ListNode listNode = new ListNode(i);
            nextNode1.next = listNode;
            nextNode1 = listNode;
        }

        for (int i = 4; i <= 10; i++) {
            ListNode listNode = new ListNode(i);
            nextNode2.next = listNode;
            nextNode2 = listNode;
        }
        ListNode newHead = mergeTwoLists(head1, head2);
        while (newHead != null) {
            System.out.print(newHead.val + "->");
            newHead = newHead.next;
        }
        System.out.print("null");
    }

    public static ListNode mergeTwoLists(ListNode l1, ListNode l2) {

        if (l1 == null) {
            return l2;
        }

        if (l2 == null) {
            return l1;
        }

        ListNode head = new ListNode(0);
        ListNode p = head;
        while (l1 != null || l2 != null) {
            if (l1 == null) {
                p.next = l2;
                break;
            }
            if (l2 == null) {
                p.next = l1;
                break;
            }
            if (l1.val > l2.val) {
                p.next = new ListNode(l2.val);
                l2 = l2.next;
                p = p.next;
            } else {
                p.next = new ListNode(l1.val);
                l1 = l1.next;
                p = p.next;
            }
        }
        return head.next;
    }
}