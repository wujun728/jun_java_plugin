package cn.iocoder.algorithm.leetcode.no0083;

import cn.iocoder.algorithm.leetcode.common.ListNode;

/**
 * https://leetcode-cn.com/problems/remove-duplicates-from-sorted-list/
 */
public class Solution {

    public ListNode deleteDuplicates(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }

        ListNode dummy = new ListNode(0);
        dummy.next = head;

        ListNode prev = head;
        while (prev.next != null) {
            // 如果相等，则指向下下一个节点
            if (prev.val == prev.next.val) {
                prev.next = prev.next.next;
            } else {
                // 下跳
                prev = prev.next;
            }
        }

        return dummy.next;
    }

    public static void main(String[] args) {
        if (true) {
            ListNode head = new ListNode(1);
            head.next = new ListNode(1);
            head.next.next = new ListNode(2);
            head.next.next.next = new ListNode(3);
            head.next.next.next.next = new ListNode(3);
            new Solution().deleteDuplicates(head);
        }
        if (false) {
            ListNode head = new ListNode(1);
            head.next = new ListNode(1);
            head.next.next = new ListNode(1);
            new Solution().deleteDuplicates(head);
        }
    }

}
