package cn.iocoder.algorithm.leetcode.no0707;

import cn.iocoder.algorithm.leetcode.common.ListNode;

/**
 * https://leetcode-cn.com/problems/design-linked-list/
 *
 * 头节点 head 实现版。
 *
 * 完美实现，应该是 head + tail 的方式，这样如果添加尾节点的操作多，性能会提升一些。
 */
public class MyLinkedList {

    /**
     * 开头
     */
    private ListNode head;

    /**
     * 节点数量
     */
    private int counts;

    /** Initialize your data structure here. */
    public MyLinkedList() {

    }

    /** Get the value of the index-th node in the linked list. If the index is invalid, return -1. */
    public int get(int index) {
        if (index < 0 && index >= counts) {
            return -1;
        }

        ListNode node = head;
        for (int i = 0; i < index; i++) {
            node = node.next;
        }
        return node.val;
    }

    /** Add a node of value val before the first element of the linked list. After the insertion, the new node will be the first node of the linked list. */
    public void addAtHead(int val) {
        ListNode node = new ListNode(val);
        if (head == null) {
            head = node;
        } else {
            node.next = head;
            head = node;
        }
        counts++;
    }

    /** Append a node of value val to the last element of the linked list. */
    public void addAtTail(int val) {
        // 无原因，添加到头
        if (counts == 0) {
            addAtHead(val);
            return;
        }

        ListNode tail = head;
        while (tail.next != null) {
            tail = tail.next;
        }
        tail.next = new ListNode(val);
        counts++;
    }

    /** Add a node of value val before the index-th node in the linked list. If index equals to the length of linked list, the node will be appended to the end of linked list. If index is greater than the length, the node will not be inserted. */
    public void addAtIndex(int index, int val) {
        if (index < 0) { // 有点奇怪，题目里没说。
            addAtHead(val);
            return;
        }
        if (index > counts) {
            return;
        }
        if (index == 0) {
            addAtHead(val);
            return;
        }

        // 寻找前一结点
        ListNode prev = head;
        for (int i = 0; i < index - 1; i++) {
            prev = prev.next;
        }
        // 插入
        ListNode node = new ListNode(val);
        ListNode next = prev.next;
        prev.next = node;
        node.next = next;
        // 增加数量
        counts++;
    }

    /** Delete the index-th node in the linked list, if the index is valid. */
    public void deleteAtIndex(int index) {
        if (index < 0 || index >= counts) {
            return;
        }
        if (index == 0) {
            head = head.next;
        } else {
            // 寻找前一结点
            ListNode prev = head;
            for (int i = 0; i < index - 1; i++) {
                prev = prev.next;
            }
            // 删除
            prev.next = prev.next.next;
        }
        // 减少数量
        counts--;
    }

    public static void main(String[] args) {
        if (false) {
            MyLinkedList linkedList = new MyLinkedList();
            linkedList.addAtHead(1);
            linkedList.addAtTail(3);
            linkedList.addAtIndex(1, 2);   //链表变为1-> 2-> 3
            linkedList.get(1);            //返回2
            linkedList.deleteAtIndex(1);  //现在链表是1-> 3
            linkedList.get(1);            //返回3
        }
        if (false) {
            MyLinkedList linkedList = new MyLinkedList();
            linkedList.addAtHead(1);
            linkedList.addAtHead(2);
            linkedList.addAtHead(3);
            linkedList.addAtHead(4);
            linkedList.deleteAtIndex(2);
            linkedList.deleteAtIndex(3);
            linkedList.deleteAtIndex(0);
            linkedList.deleteAtIndex(1);
//            linkedList.deleteAtIndex(0);
//            linkedList.deleteAtIndex(0);
        }
        if (false) {
            MyLinkedList linkedList = new MyLinkedList();
            linkedList.addAtIndex(-1, 0);
            linkedList.get(0);
            linkedList.deleteAtIndex(-1);
        }
        if (false) {
            MyLinkedList linkedList = new MyLinkedList();
            linkedList.addAtHead(1);
            linkedList.addAtTail(3);
            linkedList.addAtIndex(1, 2);
            linkedList.get(-1);
            linkedList.deleteAtIndex(1);
            linkedList.get(-3);
        }
        if (true) {
            MyLinkedList linkedList = new MyLinkedList();
            linkedList.addAtIndex(-1, 0);
            linkedList.get(0);
            linkedList.deleteAtIndex(-1);
        }
    }

}
