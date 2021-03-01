package cn.iocoder.algorithm.leetcode.no0024;

/**
 * 递归实现
 *
 * 相对比较简洁
 */
public class Solution02 {

    public ListNode swapPairs(ListNode head) {
        // 如果无，就跳过
        if(head == null || head.next == null){
            return head;
        }

        // 交换
        ListNode next = head.next; // 选择下一个
        head.next = swapPairs(next.next); // 递归，以 head 的下两个节点为开始的子链表，进行交换。同时 head.next 指向子链表的头
        next.next = head; // 将 next 和 head 交换

        // 返回结果
        return next;
    }

}
