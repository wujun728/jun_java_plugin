package cn.iocoder.algorithm.leetcode.no0141;

import java.util.HashSet;
import java.util.Set;

/**
 * 哈希表
 *
 * 可以认为是 {@link Solution} 的优化方法
 */
public class Solution02 {

    public boolean hasCycle(ListNode head) {
        return detectCycle(head) != null;
    }

    public ListNode detectCycle(ListNode head) {
        if (head == null) {
            return null;
        }
        Set<ListNode> visited = new HashSet<>();
        while (head != null && head.next != null) {
            visited.add(head);
            // 如果下一个，已经被访问过，说明存在回环
            if (visited.contains(head.next)) {
                return head.next;
            }
            head = head.next;
        }
        return null;
    }

}
