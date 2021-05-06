package cn.iocoder.algorithm.leetcode.no0206;

/**
 * 递归实现
 *
 * 递归解法，可能有点难理解。胖友可以多画下执行过程，艿艿也卡了很久。
 */
public class Solution02 {

    public ListNode reverseList(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }

        // 递归
        ListNode p = reverseList(head.next); // 这样的递归方式，会返回尾节点

        // 设置下一个节点的指向（下一个节点）为自己（head）
        head.next.next = head;
        // 自己，指向空，否则会形成环路。这里可以画一画。因为，head 的下一个，本身就指向了 head.next
        head.next = null;

        return p;
    }

    public static void main(String[] args) {
        ListNode head = new ListNode(1);
        head.next = new ListNode(2);
        head.next.next = new ListNode(3);
        head.next.next.next = new ListNode(4);
        System.out.println(new Solution02().reverseList(head));
    }

}
