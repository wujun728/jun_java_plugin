package cn.iocoder.algorithm.leetcode.no0141;

/**
 * 弗洛伊德的乌龟和兔子
 *
 * 参考博客：https://leetcode-cn.com/problems/linked-list-cycle-ii/solution/huan-xing-lian-biao-ii-by-leetcode/
 */
public class Solution03 {

    public boolean hasCycle(ListNode head) {
        // 实际情况下，调用 getIntersect 方法即可。
        // 此处这么写，主要为了方便和 leetcode 142 统一
        return detectCycle(head) != null;
    }

    public ListNode detectCycle(ListNode head) {
        if (head == null) {
            return null;
        }

        // 先使用快慢指针，找到想交的节点
        ListNode intersect = getIntersect(head);
        if (intersect == null) { // 如果为空，说明不存在环
            return null;
        }

        // 找到环的入口
        ListNode ptr1 = head;
        ListNode ptr2 = intersect;
        while (ptr1 != ptr2) {
            ptr1 = ptr1.next;
            ptr2 = ptr2.next;
        }

        return ptr1;
    }

    private ListNode getIntersect(ListNode head) {
        ListNode slow = head;
        ListNode fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast) {
                return slow;
            }
        }
        return null;
    }

}
