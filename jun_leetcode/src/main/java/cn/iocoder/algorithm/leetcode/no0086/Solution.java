package cn.iocoder.algorithm.leetcode.no0086;

import cn.iocoder.algorithm.leetcode.common.ListNode;

/**
 * https://leetcode-cn.com/problems/partition-list/
 *
 * 链表
 *
 * 双虚拟头指针，第一个添加小于 x 的节点，第二个添加大于等于 x 的节点。
 * 最终，两个虚拟头指针拼接。
 */
public class Solution {

    public ListNode partition(ListNode head, int x) {
        // 初始化虚拟节点
        ListNode dummy01 = new ListNode(0); // 比 x 小的
        ListNode dummy01Current = dummy01;
        ListNode dummy02 = new ListNode(0); // 比 x 大于等于的
        ListNode dummy02Current = dummy02;

        // 遍历
        while (head != null) {
            // 虚节点，指向下一个
            if (head.val < x) {
                dummy01Current.next = head;
                dummy01Current = dummy01Current.next;
            } else {
                dummy02Current.next = head;
                dummy02Current = dummy02Current.next;
            }
            // head 指向下一个
            head = head.next;
        }

        // 拼接
        dummy01Current.next = dummy02.next;
        dummy02Current.next = null;
        return dummy01.next;
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
//        ListNode head = ListNode.create(1, 4, 3, 2, 5, 2);
        ListNode head = ListNode.create(1, 2, 2, 2);
        System.out.println(solution.partition(head, 3));
    }

}
