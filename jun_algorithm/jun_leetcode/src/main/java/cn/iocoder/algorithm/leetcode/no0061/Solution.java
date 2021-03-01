package cn.iocoder.algorithm.leetcode.no0061;

import cn.iocoder.algorithm.leetcode.common.ListNode;

/**
 * https://leetcode-cn.com/problems/rotate-list/
 *
 * 链表
 *
 * 解题思路，向右移动 k 个节点，实际就是把倒数第 k 个节点，作为头节点。
 */
public class Solution {

    public ListNode rotateRight(ListNode head, int k) {
        // 无需移动 || 节点不存在 || 只有一个节点
        if (k == 0 || head == null || head.next == null) {
            return head;
        }

        // 计算节点数量
        int count = this.calcNodeCount(head);
        k = k % count;
        if (k == 0) { // 实际上，
            return head;
        }

        // 快慢指针，寻找倒数第 k 个节点
        ListNode fast = head;
        ListNode slow = head;
        // 快指针，先走 k 步
        for (int i = 0; i < k; i++) {
            fast = fast.next;
        }
        while (fast.next != null) { // 注意，其实我们寻找的是，倒数第 k+1 个节点。
            fast = fast.next;
            slow = slow.next;
        }

        // 修改指向
        ListNode newHead = slow.next;
        slow.next = null;
        fast.next = head;
        return newHead;
    }

    private int calcNodeCount(ListNode head) {
        int count = 0;
        while (head != null) {
            count++;
            head = head.next;
        }
        return count;
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
//        ListNode head = ListNode.create(1, 2, 3, 4, 5);
        ListNode head = ListNode.create(0, 1, 2);
        System.out.println(solution.rotateRight(head, 4));
    }

}
