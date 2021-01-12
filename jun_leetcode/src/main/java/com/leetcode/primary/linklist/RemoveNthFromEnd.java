package com.leetcode.primary.linklist;

import com.leetcode.leetcodeutils.ListNode;

/**
 * 删除链表的倒数第N个节点
 * @author: BaoZhou
 * @date : 2018/12/11 19:54
 */
class RemoveNthFromEnd {
    public static void main(String[] args) {

    }

    public ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode p1 = head;
        ListNode p2 = head;
        for (int i = 0; i < n; i++) {
            p1 = p1.next;
        }

        if(p1 == null){
            return p2.next;
        }

        while (p1.next != null) {
            p1 = p1.next;
            p2 = p2.next;
        }

        p2.next = p2.next.next;
        return head;
    }
}