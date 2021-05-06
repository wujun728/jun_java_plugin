package cn.iocoder.algorithm.leetcode.no0148;

/**
 * https://leetcode-cn.com/problems/sort-list/
 *
 * 参考博客：https://leetcode-cn.com/problems/sort-list/solution/gui-bing-pai-xu-he-kuai-su-pai-xu-by-a380922457/
 *
 * 归并排序。
 *
 * 另外，上述博客，也提供了快速排序的实现方式。
 */
public class Solution {

    public ListNode sortList(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }

        // 寻找中间节点
        ListNode slow = head,  // slow 节点，基本就是中间点
                fast = slow.next; // fast 节点
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        // 右半边，归类
        ListNode right = sortList(slow.next);
        // 左半边，归类
        slow.next = null; // 断开，这样 head 的结尾，就是 null 了。这也是为什么先 right ，后 left 的原因
        ListNode left = sortList(head);

        // 合并
        return mergeSort(left, right);
    }

    private ListNode mergeSort(ListNode left, ListNode right) {
        ListNode dummy = new ListNode(0);
        ListNode current = dummy;
        // 比较
        while (left != null && right != null) {
            if (left.val < right.val) {
                current.next = left;
                left = left.next;
            } else {
                current.next = right;
                right = right.next;
            }
            // 跳转下一个
            current = current.next;
        }
        // 拼接剩余的
        current.next = left != null ? left : right;

        // 返回
        return dummy.next;
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        if (true) {
            ListNode head = new ListNode(4);
            head.next = new ListNode(2);
            head.next.next = new ListNode(1);
            head.next.next.next = new ListNode(3);
            ListNode result = solution.sortList(head);
            System.out.println(result);
        }
    }

}
