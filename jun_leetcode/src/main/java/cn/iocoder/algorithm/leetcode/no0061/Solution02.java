package cn.iocoder.algorithm.leetcode.no0061;

import cn.iocoder.algorithm.leetcode.common.ListNode;

/**
 * 和 {@link Solution} 差不多的思路。
 *
 * 参考 https://leetcode-cn.com/problems/rotate-list/solution/xuan-zhuan-lian-biao-by-leetcode/ 博客。
 *
 * 只是不依赖于快慢指针实现。差别不大。
 */
public class Solution02 {

    public ListNode rotateRight(ListNode head, int k) {
        // 无需移动 || 节点不存在 || 只有一个节点
        if (k == 0 || head == null || head.next == null) {
            return head;
        }

        // 计算链表长度
        int n = 1;
        ListNode oldTail = head; // 寻找当前结尾
        while (oldTail.next != null) {
            n++;
            oldTail = oldTail.next;
        }

        // 计算需要右移动的数
        k = k % n;
        if (k == 0) {
            return head;
        }

        // 把尾巴指向头
        oldTail.next = head;

        // 开始遍历，寻找倒数第 k + 1 个
        ListNode newTail = head;
        for (int i = 0; i < n - k - 1; i++) {
            newTail = newTail.next;
        }

        // 设置指向
        ListNode newHead = newTail.next;
        newTail.next = null; // 干掉环
        return newHead;
    }

    public static void main(String[] args) {
        Solution02 solution = new Solution02();
        ListNode head = ListNode.create(1, 2, 3, 4, 5);
//        ListNode head = ListNode.create(0, 1, 2);
        System.out.println(solution.rotateRight(head, 2));
    }

}
