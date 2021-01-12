package com.leetcode.primary.linklist;

import com.leetcode.leetcodeutils.ListNode;

class DeleteNode {
    public static void main(String[] args) {

    }
    public void deleteNode(ListNode node) {
        node.val = node.next.val;
        node.next = node.next.next;
    }
}