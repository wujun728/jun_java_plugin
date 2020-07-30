package cn.iocoder.algorithm.leetcode.no0092;

/**
 * 在 {@link Solution} 的基础上，去掉虚拟头节点
 */
public class Solution02 {

    public ListNode reverseBetween(ListNode head, int m, int n) {
        ListNode oriHead = head;

        // 【前段】无需反转
        ListNode leftTail = null;
        for (int i = 1; i < m; i++) {
            leftTail = head;
            head = head.next;
        }

        // 【中段】反转 last ，指向最后一个遍历到的 head 节点
        ListNode middleLast = head;
        ListNode last = null; // 同时，last 就是中段的 head
        for (int i = m; i <= n; i++) {
            // 记录当前节点
            ListNode tmp = head;
            // head 指向下一个节点
            head = head.next;
            // tmp 的下一个指向 last
            tmp.next = last;
            // 设置新的 last
            last = tmp;
        }

        // 【后段】无需反转

        // 拼接三段
        if (leftTail == null) { // 这种情况，就是 m = 1 的时候。此时，因为不需要反转，所以直接 vHead 的下一个，指向 last
            oriHead = last;
        } else {
            leftTail.next = last;
        }
        if (middleLast != null) {
            middleLast.next = head;
        }

        return oriHead;
    }

    public static void main(String[] args) {
        Solution02 solution = new Solution02();
        if (true) {
            ListNode head = new ListNode(1);
            head.next = new ListNode(2);
            head.next.next = new ListNode(3);
            head.next.next.next = new ListNode(4);
            head.next.next.next.next = new ListNode(5);
            System.out.println(solution.reverseBetween(head, 2, 4));
        }
        if (true) {
            ListNode head = new ListNode(5);
            System.out.println(solution.reverseBetween(head, 1, 1));
        }
        if (true) {
            ListNode head = new ListNode(3);
            head.next = new ListNode(5);
            head.next.next = new ListNode(7);
            System.out.println(solution.reverseBetween(head, 1, 2));
        }
    }

}
