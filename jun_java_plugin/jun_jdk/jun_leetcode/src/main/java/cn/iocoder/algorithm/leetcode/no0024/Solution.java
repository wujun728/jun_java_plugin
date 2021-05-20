package cn.iocoder.algorithm.leetcode.no0024;

/**
 * https://leetcode-cn.com/problems/swap-nodes-in-pairs/
 *
 * 因为写的，感觉略微有点乱，所以后来参考了 https://leetcode-cn.com/problems/swap-nodes-in-pairs/solution/hua-jie-suan-fa-24-liang-liang-jiao-huan-lian-biao/ 博客
 * 然后写了 {@link Solution02} 和 {@link Solution03}
 */
class Solution {

    public ListNode swapPairs(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        ListNode result = head.next;

        // 开始交换
        ListNode lastHeadNext = null;
        while (head != null && head.next != null) {
            // 交换
            ListNode headNext = head.next;
            head.next = headNext.next;
            headNext.next = head;
            // 之前的尾巴
            if (lastHeadNext != null) {
                lastHeadNext.next = headNext;
            }
            // head 跳到下 2 个
            lastHeadNext = headNext.next;
            head = headNext.next.next;
        }

        return result;
    }

    public static void main(String[] args) {
        ListNode head = new ListNode(1);
        head.next = new ListNode(2);
        head.next.next = new ListNode(3);
        head.next.next.next = new ListNode(4);
        System.out.println(new Solution().swapPairs(head));
    }

}
