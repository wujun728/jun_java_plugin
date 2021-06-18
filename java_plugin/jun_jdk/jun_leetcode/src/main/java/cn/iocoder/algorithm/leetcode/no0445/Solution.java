package cn.iocoder.algorithm.leetcode.no0445;

/**
 * https://leetcode-cn.com/problems/add-two-numbers-ii/
 *
 * 主要实现有两点
 * 1. 因为 l1 和 l2 可能长度不同，所以通过在短的链表的前面，补 0 。
 * 2. 因为最高位是在最前面，所以需要有栈来模拟整个计算。考虑到这道题是在链表里，所以通过递归模拟栈
 *
 * 当然，最方便的解决方案，直接使用栈。这样，上述的【步骤 1】，就可以不用做了。
 */
class Solution {

    /**
     * 结果
     */
    private ListNode result = null;
    /**
     * 额外进位
     */
    private int extra = 0;

    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        // 将 l1 和 l2 长度统一。短的链表，在开头不断 0 的节点。
        int counts1 = count(l1);
        int counts2 = count(l2);
        if (counts1 > counts2) {
            l2 = normalized(l2, counts1 - counts2);
        } else if (counts1 < counts2) {
            l1 = normalized(l1, counts2 - counts1);
        }

        // 递归，模拟栈的求解过程
        sum(l1, l2);
        if (extra > 0) {
            addResultNode(extra);
        }

        return result;
    }

    private int count(ListNode list) {
        int counts = 0;
        while (list != null) {
            counts++;
            list = list.next;
        }
        return counts;
    }

    private ListNode normalized(ListNode list, int counts) {
        for (int i = 0; i < counts; i++) {
            ListNode node = new ListNode(0);
            node.next = list;
            list = node;
        }
        return list;
    }

    private void sum(ListNode l1, ListNode l2) {
        // 结束
        if (l1 == null && l2 == null) {
            return;
        }

        // 递归计算
        sum(l1.next, l2.next);

        // 计算当前节点
        int sum = l1.val + l2.val + extra;
        extra = 0;
        if (sum >= 10) {
            sum = sum - 10;
            extra = 1;
        }
        addResultNode(sum);
    }

    private void addResultNode(int sum) {
        ListNode node = new ListNode(sum);
        if (result == null) {
            result = node;
        } else {
            node.next = result;
            result = node;
        }
    }

}
