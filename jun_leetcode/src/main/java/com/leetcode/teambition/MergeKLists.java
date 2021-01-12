package com.leetcode.teambition;

import com.leetcode.leetcodeutils.ListNode;
import com.leetcode.leetcodeutils.NodeWrapper;
import org.junit.jupiter.api.Test;

/**
 * 合并K个排序链表
 *
 * @author BaoZhou
 * @date 2019/7/26 0:57
 */
public class MergeKLists {

    @Test
    public void test() {

        ListNode listNode1 = NodeWrapper.stringToListNode("[]");
        ListNode listNode2 = NodeWrapper.stringToListNode("[1,3,4]");
        ListNode listNode3 = NodeWrapper.stringToListNode("[2,6]");
        ListNode[] listNodes = {listNode1, listNode2, listNode3};
        NodeWrapper.prettyPrintLinkedList(mergeKLists(listNodes));
    }

    public ListNode mergeKLists(ListNode[] lists) {
        ListNode node = null;
        ListNode head = null;
        int n = 0;
        while (n != lists.length) {
            n = 0;
            int min = Integer.MAX_VALUE;
            int min_pos = -1;
            for (int i = 0; i < lists.length; i++) {
                if (lists[i] == null) {
                    n++;
                    if (n == lists.length) {
                        break;
                    }
                }
                if (lists[i] != null && lists[i].val < min) {
                    min = lists[i].val;
                    min_pos = i;
                }

                if (i == lists.length - 1) {
                    if (node == null) {
                        if (lists[min_pos] == null) {
                            node = null;
                        } else {
                            node = new ListNode(lists[min_pos].val);
                        }
                        head = node;
                    } else {
                        node.next = new ListNode(lists[min_pos].val);
                        node = node.next;
                    }
                    lists[min_pos] = lists[min_pos].next;
                }
            }


        }
        return head;
    }
}
