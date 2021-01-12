package com.offer;


import com.leetcode.leetcodeutils.ListNode;
import com.leetcode.leetcodeutils.NodeWrapper;
import org.junit.jupiter.api.Test;

/**
 * 合并两个排序的链表
 *
 * @author BaoZhou
 * @date 2020-5-28
 */

public class Q16 {


    @Test
    public void result() {
        ListNode head1 = NodeWrapper.stringToListNode("[]");
        ListNode head2 = NodeWrapper.stringToListNode("[]");
        NodeWrapper.prettyPrintLinkedList(Merge(head1, head2));
    }


    public ListNode Merge(ListNode list1, ListNode list2) {
        ListNode head = new ListNode(0);
        ListNode result = head;
        if (list1 == null) {
            return list2;
        }
        if (list2 == null) {
            return list1;
        }

        while (list1 != null && list2 != null) {

            if (list1.val < list2.val) {
                head.next = list1;
                list1 = list1.next;
            } else {
                head.next = list2;
                list2 = list2.next;
            }
            head = head.next;
        }
        if (list1 == null) {
            head.next = list2;
        }
        if (list2 == null) {
            head.next = list1;
        }
        return result.next;
    }
}
