package com.leetcode.primary.linklist;

import com.leetcode.leetcodeutils.ListNode;

/**
 * 反转链表(递归)
 *
 * @author: BaoZhou
 * @date : 2018/12/11 19:54
 */
class ReverseList {
    public static void main(String[] args) {
        ListNode head = new ListNode(1);
        ListNode nextNode = head;
        for (int i = 2; i <= 5; i++) {
            ListNode listNode = new ListNode(i);
            nextNode.next = listNode;
            nextNode = listNode;
        }
        ListNode newHead = reverseList(head);
        while (newHead != null) {
            System.out.print(newHead.val + "->");
            newHead = newHead.next;
        }
        System.out.print("null");
    }

    /**
     * 迭代
     * @param head
     * @return
     */
    public static ListNode reverseList(ListNode head) {
        if (head == null) {
            return null;
        }
        if (head.next == null) {
            return head;
        }

        ListNode p = head;
        ListNode pre = null;
        while (p != null) {
            ListNode next = p.next;
            p.next = pre;
            pre = p;
            p = next;
        }
        return pre;
    }

    /**
     * 递归
     * https://www.cnblogs.com/kubixuesheng/p/4394509.html
     * @param head
     * @return
     */
    public static ListNode reverseList2(ListNode head) {
        if (head == null) {
            return null;
        }
        if (head.next == null) {
            return head;
        }

        ListNode newHead = reverseList2(head.next);
        head.next.next = head;
        head.next =null;
        return newHead;
    }
}