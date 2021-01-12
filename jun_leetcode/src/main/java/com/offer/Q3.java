package com.offer;


import com.leetcode.leetcodeutils.ListNode;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Stack;

/**
 * 从尾到头打印链表
 * @author: BaoZhou
 * @date : 2020/5/28 9:57 上午
 */
public class Q3 {

    @Test
    public void result() {
        printListFromTailToHead(new ListNode(1));
    }


    public ArrayList<Integer> printListFromTailToHead(ListNode listNode) {
        Stack<Integer> stack = new Stack<>();
        ArrayList<Integer> result = new ArrayList<>();
        if (listNode == null) {
            return result;
        }
        while (listNode.next != null) {
            stack.push(listNode.val);
            listNode = listNode.next;
        }
        while (!stack.empty()) {
            result.add(stack.pop());
        }
        return result;
    }
}
