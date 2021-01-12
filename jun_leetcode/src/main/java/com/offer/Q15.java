package com.offer;


import com.leetcode.leetcodeutils.ListNode;
import com.leetcode.leetcodeutils.NodeWrapper;
import org.junit.jupiter.api.Test;

/**
 * 反转链表
 * @author BaoZhou
 * @date 2020-5-28
 */

public class Q15 {


    @Test
    public void result() {
        ListNode head = NodeWrapper.stringToListNode("[1,2]");
        NodeWrapper.prettyPrintLinkedList(ReverseList(head));
    }


    public ListNode ReverseList(ListNode head) {
        if (head == null){
            return head;
        }
        ListNode pre = null;
        ListNode after;
        while (true) {
            after = head.next;
            head.next = pre;
            pre = head;
            if (after == null) {
                return head;
            } else {
                head = after;
            }
        }
    }
}
