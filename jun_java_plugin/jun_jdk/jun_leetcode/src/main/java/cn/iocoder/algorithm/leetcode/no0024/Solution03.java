package cn.iocoder.algorithm.leetcode.no0024;

/**
 * 和 {@link Solution} 一个思路，就是代码更漂亮
 */
@SuppressWarnings("ConstantConditions")
public class Solution03 {

    public ListNode swapPairs(ListNode head) {
        // 创建一个前置的特殊节点，指向 head 。这样，就能统一，站在第 -1 个节点，看 0，1 交换。站在 1 个节点，看 2，3 交换。。相比我之前写的代码，会更加统一。
        ListNode pre = new ListNode(0);
        pre.next = head;

        // 设置 temp ，开始往下循环。
        ListNode temp = pre;
        while(temp.next != null && temp.next.next != null) {
            // 下一个节点，和下两个节点
            ListNode start = temp.next;
            ListNode end = temp.next.next;
            // tmp 指向下两个节点
            temp.next = end;
            // 下一个节点，指向下三个节点
            start.next = end.next;
            // 下两个节点，指向下一个节点
            end.next = start;
            // tmp 指向 start
            temp = start;
            // 小结，确实会比我写的清晰一些。很多时候，特殊的头节点，能带来这样的好处。
        }

        return pre.next;
    }


}
