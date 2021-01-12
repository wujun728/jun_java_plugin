package com.offer;


import com.leetcode.leetcodeutils.ListNode;
import com.leetcode.leetcodeutils.NodeWrapper;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;


/**
 * 链表中环的入口结点
 *
 * @author BaoZhou
 * @date 2020-6-28
 */
public class Q55 {

    @Test
    public void result() {
    }

    public ListNode EntryNodeOfLoop(ListNode pHead) {
        ListNode slow = pHead;
        ListNode fast = pHead;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast) {
                break;
            }
        }
        if (fast == null || fast.next == null) return null;
        slow = pHead;
        while (slow != fast) {
            slow = slow.next;
            fast = fast.next;
        }
        return slow;
    }
}
