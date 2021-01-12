package com.offer;

import com.leetcode.leetcodeutils.ListNode;
import org.junit.jupiter.api.Test;

/**
 * 两个链表的第一个公共结点
 *
 * @author BaoZhou
 * @date 2020-6-16
 */

public class Q36 {
    @Test
    public void result() {

    }

    public ListNode FindFirstCommonNode(ListNode pHead1, ListNode pHead2) {
        if (pHead1 == null || pHead2 == null) return null;
        ListNode p1 = pHead1;
        ListNode p2 = pHead2;
        while (p1 != p2) {
            p1 = p1 == null ? pHead2 : p1.next;
            p2 = p2 == null ? pHead1 : p2.next;
        }
        return p1;
    }
}



