package cn.iocoder.algorithm.leetcode.no0023;

import cn.iocoder.algorithm.leetcode.common.ListNode;
import cn.iocoder.algorithm.leetcode.no0021.Solution;

/**
 * 在 {@link Solution} 的基础上，实现两两合并。
 *
 * 时间复杂度 O(N * logk) 。
 *
 * 参考博客：https://leetcode-cn.com/problems/merge-k-sorted-lists/solution/he-bing-kge-pai-xu-lian-biao-by-leetcode/
 *
 * 对于多个数组元素的合并，两两合并，能带来很好的效果。在反向思考，求 N 次方。
 */
public class Solution02 {

    public ListNode mergeKLists(ListNode[] lists) {
        if (lists == null || lists.length == 0) {
            return null;
        }

        int length = lists.length;
        while (length > 1) {
            // 计算各种变量
            int start = 0; // 开始位置
            if ((length & 1) == 1) { // 不被整除，说明有一个不能被两两合并，就跳过第一个。
                start = 1;
            }

            // 开始合并
            for (int i = start; i < length; i += 2) {
                lists[start] = mergeTwoLists(lists[i], lists[i + 1]);
                start++;
            }

            // 设置新的长度
            length = start;
        }

        return lists[0];
    }

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

}
