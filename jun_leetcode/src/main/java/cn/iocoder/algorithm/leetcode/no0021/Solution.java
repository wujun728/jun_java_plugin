package cn.iocoder.algorithm.leetcode.no0021;

import cn.iocoder.algorithm.leetcode.common.ListNode;

/**
 * https://leetcode-cn.com/problems/merge-two-sorted-lists/
 */
public class Solution {

    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        ListNode dummy = new ListNode(0);
        // 比较
        ListNode current = dummy;
        while (l1 != null && l2 != null) {
            if (l1.val >= l2.val) {
                current.next = l2; // 指向 l2 当前头节点
                l2 = l2.next;
            } else {
                current.next = l1; // 指向 l1 当前头节点
                l1 = l1.next;
            }
            // 跳到下一个节点
            current = current.next;
        }

        // 补全
        current.next = l1 != null ? l1 : l2;

        return dummy.next;
    }

    public static void main(String[] args) {
        ListNode l1 = new ListNode(1);
        l1.next = new ListNode(2);
//        l1.next = new ListNode(2);
//        l1.next.next = new ListNode(4);
        ListNode l2 = new ListNode(1);
        l2.next = new ListNode(3);
        l2.next.next = new ListNode(4);
        new Solution().mergeTwoLists(l1, l2);
    }

}
