package cn.iocoder.algorithm.leetcode.no0019;

import cn.iocoder.algorithm.leetcode.common.ListNode;

/**
 * https://leetcode-cn.com/problems/remove-nth-node-from-end-of-list/
 *
 * 双指针实现。
 *
 * 感兴趣的同学，可以通过递归，模拟入栈，然后出栈去移除。
 */
public class Solution {

    public ListNode removeNthFromEnd(ListNode head, int n) {
        // 虚拟头节点，方便处理，移除头节点的情况。
        ListNode dummy = new ListNode(0);
        dummy.next = head;

        // 先使用 fast 走 n + 1 步，fast 和 slow 就刚好差 n 个节点。
        // 这样，后续 fast 和 slow 每人走一步，最终 fast 走到结尾时，slow 就在倒数 n 位置。
        ListNode fast = dummy;
        ListNode slow = dummy;
        for (int i = 0; i < n; i++) {
            fast = fast.next;
        }

        // 因为，要删除倒数第 n 个节点，所以 slow 走到倒数第 n + 1 个节点，就要停止了。因此这里判断 fast.next = null 为结尾
        while (fast.next != null) {
            fast = fast.next;
            slow = slow.next;
        }
        slow.next = slow.next.next; // 删除第 n 个节点

        return dummy.next;
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        solution.removeNthFromEnd(ListNode.create(1, 2, 3, 4, 5), 5);
    }

}
