package cn.iocoder.algorithm.leetcode.no0002;

/**
 * https://leetcode-cn.com/problems/add-two-numbers/
 */
public class Solution {

    /**
     * 头节点
     */
    private ListNode head = null;
    /**
     * 尾节点
     */
    private ListNode tail = null;

    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        int extra = 0; // 进位
        while (true) {
            int sum = extra;
            extra = 0;
            // 判断是否结束
            if (l1 == null && l2 == null) {
                if (sum > 0) {
                    addNode(sum);
                }
                break;
            }
            // 计算
            if (l1 != null) {
                sum += l1.val;
            }
            if (l2 != null) {
                sum += l2.val;
            }
            if (sum > 9) {
                sum = sum - 10;
                extra = 1;
            }
            // 设置到节点上
            addNode(sum);
            // 跳到下个节点
            if (l1 != null) {
                l1 = l1.next;
            }
            if (l2 != null) {
                l2 = l2.next;
            }
        }

        return head;
    }

    private void addNode(int val) {
        // 不存在头节点
        if (head == null) {
            head = tail = new ListNode(val);
            return;
        }
        // 存在头节点
        tail.next = new ListNode(val);
        tail = tail.next;
    }

}
