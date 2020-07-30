package cn.iocoder.algorithm.leetcode.no0160;

import cn.iocoder.algorithm.leetcode.common.ListNode;

/**
 * https://leetcode-cn.com/problems/intersection-of-two-linked-lists/
 *
 * 双指针法，比较巧妙，参考博客：https://www.cnblogs.com/grandyang/p/4128461.html
 * 两个指针，分别走自己对应的 A、B 节点，然后，走到尾部后，走对方的节点。要么走到相交点相交，要么走到末尾 null 相交。
 *
 * 另外，还有两种相对一般的方法
 * 1. 暴力法
 * 2. 哈希表
 */
public class Solution {

    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        if (headA == null || headB == null) {
            return null;
        }

        ListNode pa = headA, pb = headB;
        while (pa != pb) {
            pa = (pa != null) ? pa.next : headB;
            pb = (pb != null) ? pb.next : headA;
        }

        return pa;
    }

}
