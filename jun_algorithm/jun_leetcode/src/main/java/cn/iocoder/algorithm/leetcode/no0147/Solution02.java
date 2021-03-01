package cn.iocoder.algorithm.leetcode.no0147;

import cn.iocoder.algorithm.leetcode.common.ListNode;

/**
 * 插入排序
 */
public class Solution02 {

    public ListNode insertionSortList(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }

        // 虚拟头节点
        ListNode dummy = new ListNode(Integer.MIN_VALUE);

        // 开始比较
        while (head != null) {
            ListNode next = head.next;

            // 寻找插入的点
            ListNode dummyInsertion = dummy;
            while (dummyInsertion.next != null
                && dummyInsertion.next.val <= head.val) {
                dummyInsertion = dummyInsertion.next;
            }

            // 插入到 dummyInsertion 后
            if (dummyInsertion.next == null) {
                dummyInsertion.next = head;
                head.next = null;
            } else {
                head.next = dummyInsertion.next;
                dummyInsertion.next = head;
            }

            // head
            head = next;
        }

        return dummy.next;
    }


// 数组的快速排序的实现
//    public void insertionSort(int[] array) {
//        for (int i = 1; i < array.length; i++) {
//            int key = array[i];
//            int j = i - 1;
//            while (j >= 0 && array[j] > key) {
//                array[j + 1] = array[j];
//                j--;
//            }
//            array[j + 1] = key;
//        }
//    }

}
