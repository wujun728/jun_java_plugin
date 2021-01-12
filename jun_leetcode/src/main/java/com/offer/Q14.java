package com.offer;


import com.leetcode.leetcodeutils.ListNode;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

/**
 * 链表中倒数第k个结点
 *
 * @author: BaoZhou
 * @date : 2020/5/28 10:17 上午
 */
public class Q14 {


    @Test
    public void result() {

    }


    public ListNode FindKthToTail(ListNode head, int k) {
        ListNode slow = head;
        ListNode fast = head;
        for (int i = 0; i < k; i++) {
            if (fast != null) {
                fast = fast.next;
            } else {
                return fast;
            }
        }
        while (fast != null) {
            slow = slow.next;
            fast = fast.next;
        }
        return slow;
    }
}

