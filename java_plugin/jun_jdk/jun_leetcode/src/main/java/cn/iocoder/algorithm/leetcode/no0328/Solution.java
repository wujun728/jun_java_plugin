package cn.iocoder.algorithm.leetcode.no0328;

import cn.iocoder.algorithm.leetcode.common.ListNode;

/**
 * https://leetcode-cn.com/problems/odd-even-linked-list/
 *
 * 在博客 https://leetcode-cn.com/problems/odd-even-linked-list/solution/qi-ou-lian-biao-by-leetcode/ 中，还有一种更吊的实现。当然，处理起来要比较小心。
 */
public class Solution {

        public ListNode oddEvenList(ListNode head) {
            if (head == null || head.next == null) {
                return head;
            }

            ListNode odd = new ListNode(0); // 奇数
            ListNode even = new ListNode(0); // 偶数

            ListNode oddCurrent = odd;
            ListNode evenCurrent = even;
            int index = 1;
            while (head != null) {
                if ((index & 1) == 1) {
                    oddCurrent.next = head;
                    oddCurrent = oddCurrent.next;
                } else {
                    evenCurrent.next = head;
                    evenCurrent = evenCurrent.next;
                }
                head = head.next;
                index++;
            }

            // 拼接
            evenCurrent.next = null; // 断掉，避免循环链表
            oddCurrent.next = even.next; // 奇数指向偶数
            return odd.next;
        }

    public static void main(String[] args) {
        Solution solution = new Solution();
//        System.out.println(solution.oddEvenList(ListNode.create(1, 2, 3, 4, 5)));
        System.out.println(solution.oddEvenList(ListNode.create(2, 1, 3, 5, 6, 4, 7)));
    }

}
