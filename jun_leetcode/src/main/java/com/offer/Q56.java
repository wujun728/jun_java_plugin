package com.offer;


import com.designpatterns.composite.Node;
import com.leetcode.leetcodeutils.ListNode;
import com.leetcode.leetcodeutils.NodeWrapper;
import org.junit.jupiter.api.Test;


/**
 * 删除链表中重复的结点
 *
 * @author BaoZhou
 * @date 2020-7-1
 */
public class Q56 {

    @Test
    public void result() {
        ListNode listNode = NodeWrapper.stringToListNode("[1,2,3,3,4,4,5]");
        ListNode listNode2 = NodeWrapper.stringToListNode("[2,2,3,3,4,4,5]");
        ListNode listNode3 = NodeWrapper.stringToListNode("[1,1,2,3,3,4,5,5]");

        ListNode result = deleteDuplication(listNode);
        ListNode result2 = deleteDuplication(listNode2);
        ListNode result3 = deleteDuplication(listNode3);
        NodeWrapper.prettyPrintLinkedList(result);
        NodeWrapper.prettyPrintLinkedList(result2);
        NodeWrapper.prettyPrintLinkedList(result3);
    }

    public ListNode deleteDuplication(ListNode pHead) {
        if (pHead == null || pHead.next == null) {
            return pHead;
        }
        boolean flag = false;
        ListNode head = pHead;
        ListNode result = null;
        ListNode nextNode = head;
        while (true) {
            //处理重复的节点的尾部
            if (nextNode == null) {
                head.next = null;
                break;
            }
            //
            while (nextNode.next != null && nextNode.val == nextNode.next.val) {
                nextNode = nextNode.next;
                flag = true;
            }

            if (flag) {
                nextNode = nextNode.next;
                flag = false;
            } else {
                if (result == null) {
                    //设置了头部
                    result = nextNode;
                    head = nextNode;
                } else {
                    //链接节点
                    head.next = nextNode;
                    head = head.next;
                }
                nextNode = nextNode.next;
            }
        }
        return result;

    }
}
