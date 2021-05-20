package cn.iocoder.algorithm.leetcode.no0206;

/**
 * https://leetcode-cn.com/problems/reverse-linked-list/
 *
 * 参考博客：https://leetcode-cn.com/problems/reverse-linked-list/solution/fan-zhuan-lian-biao-by-leetcode/
 */
class Solution {

    public ListNode reverseList(ListNode head) {
        // last ，指向最后遍历到的头节点
        ListNode last = null;
        // 开始从头节点 head 遍历
        while (head != null) {
            // 记录当前的 head
            ListNode tmp = head;
            // head 跳转到下一个节点
            head = head.next;
            // 原有 head 指向
            tmp.next = last;
            // last 指向原有 head 节点
            last = tmp;
        }

        return last;
    }

}
