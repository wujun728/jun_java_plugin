package cn.iocoder.algorithm.leetcode.no0141;

/**
 * https://leetcode-cn.com/problems/linked-list-cycle/
 *
 * 性能较差
 *
 * 每次走到下一个节点，都从头节点重新遍历，看看有没回环。
 */
public class Solution {

    public boolean hasCycle(ListNode head) {
        return detectCycle(head) != null;
    }

    public ListNode detectCycle(ListNode head) {
        if (head == null) {
            return null;
        }
        // 寻找结尾
        ListNode current = head;
        while (current.next != null) {
            ListNode oldCurrent = current;
            ListNode tmpHead = head;
            // 跳到下一个
            current = current.next;
            // 处理，自己指向自己的情况
            if (current == oldCurrent) {
                return current;
            }
            // 从头结点开始走，判断是否有回环
            while (tmpHead != null) {
                // 先指到了 oldCurrent ，说明无回环
                if (tmpHead == oldCurrent) {
                    break;
                }
                // 先指到了 oldCurrent 的下一个 current ，说明有回环
                if (tmpHead == current) {
                    return current;
                }
                // 跳到下个节点
                tmpHead = tmpHead.next;
            }
        }
        return null;
    }

}
