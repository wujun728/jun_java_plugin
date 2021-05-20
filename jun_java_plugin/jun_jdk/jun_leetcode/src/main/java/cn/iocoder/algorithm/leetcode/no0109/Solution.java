package cn.iocoder.algorithm.leetcode.no0109;

import cn.iocoder.algorithm.leetcode.common.ListNode;
import cn.iocoder.algorithm.leetcode.common.TreeNode;

/**
 * https://leetcode-cn.com/problems/convert-sorted-list-to-binary-search-tree/
 */
public class Solution {

    public TreeNode sortedListToBST(ListNode head) {
        if (head == null) {
            return null;
        }

        ListNode middlePrev = this.findMiddlePrev(head);
        ListNode middle;
        if (middlePrev == null) {
            // 有两个节点的情况
            if (head.next == null) {
                return new TreeNode(head.val);
            // 只有一个节点的情况
            } else {
                middle = head;
                head = null;
            }
        } else { // 有中间节点
            middle = middlePrev.next;
            // 断开链表
            middlePrev.next = null;
        }
        TreeNode node = new TreeNode(middle.val);

        // 左右子树
        node.left = this.sortedListToBST(head);
        node.right = this.sortedListToBST(middle.next);

        return node;
    }

    /**
     * 寻找中间点的前一个节点，通过快慢指针
     *
     * @param head 头节点
     * @return 中间点的前一个节点
     */
    private ListNode findMiddlePrev(ListNode head) {
        ListNode slowPre = null;
        ListNode slow = head;
        ListNode fast = head.next;
        while (fast != null && fast.next != null) {
            // 记录上一个 slow 的前一个节点
            slowPre = slow;
            // 快慢走
            slow = slow.next;
            fast = fast.next.next;
        }

        return slowPre;
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
//        ListNode node = ListNode.create(-10, -3, 0, 5, 9);
//        System.out.println(solution.sortedListToBST(node));
//        ListNode node = ListNode.create(10);
//        System.out.println(solution.sortedListToBST(node));
        ListNode node = ListNode.create(10, 12);
        System.out.println(solution.sortedListToBST(node));
    }

}
