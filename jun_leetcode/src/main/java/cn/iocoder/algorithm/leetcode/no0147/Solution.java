package cn.iocoder.algorithm.leetcode.no0147;

import cn.iocoder.algorithm.leetcode.common.ListNode;

/**
 * https://leetcode-cn.com/problems/insertion-sort-list/
 *
 * TODO 芋艿 参考 https://www.cnblogs.com/zqiguoshang/p/5897991.html 博客实现，看的有点懵逼，不是非常理解。
 *
 * 后来，编写了 {@link Solution02} 来实现解决。不过性能上，Solution02 比 Solution 差蛮多。
 */
public class Solution {

    public ListNode insertionSortList(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }

        // 虚拟头节点，方便编程
        ListNode dummy = new ListNode(Integer.MIN_VALUE);
        dummy.next = head;

        // 从头节点开始，插入排序
        ListNode prev = head;
        while (prev.next != null) {
            // 如果当前节点（prev）比下一个节点大，直接跳到下个节点。TODO 芋艿
            if (prev.val <= prev.next.val) {
                prev = prev.next;
                continue;
            }

            // 删除 prev.next 节点
            ListNode tmp = prev.next;
            prev.next = prev.next.next;

            // 寻找最后一个比 prev.next 小的节点
            ListNode query = dummy;
            while (tmp.val >= query.next.val) { // 注意，这里的 query.next
                query = query.next;
            }

            // 将 tmp 插入到 query 后面
            tmp.next = query.next;
            query.next = tmp;
        }

        return dummy.next;
    }

//    public static void bubbleSort(int []arr) {
//        int[] arr = {12,23,34,56,56,56,78};
//        for(int i =0;i<arr.length-1;i++) {
//            for(int j=0;j<arr.length-i-1;j++) {//-1为了防止溢出
//                if(arr[j]>arr[j+1]) {
//                    int temp = arr[j];
//
//                    arr[j]=arr[j+1];
//
//                    arr[j+1]=temp;
//                }
//            }
//        }
//    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        if (false) {
            System.out.println(solution.insertionSortList(ListNode.create(5, 1, 2, 3, 4)));
        }
        if (true) {
            System.out.println(solution.insertionSortList(ListNode.create(5, 2, 3, 4, 1)));
        }
        if (false) {
            ListNode head = new ListNode(4);
            head.next = new ListNode(2);
            head.next.next = new ListNode(1);
            head.next.next.next = new ListNode(3);
            System.out.println(solution.insertionSortList(head));
        }
        if (false) {
            ListNode head = new ListNode(-1);
            head.next = new ListNode(5);
            head.next.next = new ListNode(3);
            head.next.next.next = new ListNode(4);
            head.next.next.next.next = new ListNode(0);
            System.out.println(solution.insertionSortList(head));
        }
        if (false) {
            ListNode head = new ListNode(1);
            head.next = new ListNode(1);
            System.out.println(solution.insertionSortList(head));
        }
        if (false) {
            ListNode head = new ListNode(3);
            head.next = new ListNode(2);
            head.next.next = new ListNode(4);
            System.out.println(solution.insertionSortList(head));
        }
    }

}
