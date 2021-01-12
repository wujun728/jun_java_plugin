package com.offer;


import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * 复杂链表的复制
 *
 * @author BaoZhou
 * @date 2020-6-9
 */

public class Q25 {
    public class RandomListNode {
        int label;
        RandomListNode next = null;
        RandomListNode random = null;

        RandomListNode(int label) {
            this.label = label;
        }
    }

    @Test
    public void result() {
        RandomListNode node1 = new RandomListNode(1);
        RandomListNode node2 = new RandomListNode(2);
        RandomListNode node3 = new RandomListNode(3);
        node1.next = node2;
        node1.random = node3;
        node2.next = node3;
        RandomListNode result = Clone(node1);
    }

    public RandomListNode Clone(RandomListNode pHead) {
        if (pHead == null) return null;
        RandomListNode head = pHead;
        Map<RandomListNode, RandomListNode> map = new HashMap<>();
        while (pHead != null) {
            map.put(pHead, new RandomListNode(pHead.label));
            pHead = pHead.next;
        }

        pHead = head;
        while (pHead != null) {
            if (pHead.random == null) {
                map.get(pHead).next = map.get(pHead.next);
                pHead = pHead.next;
                continue;
            }
            if (map.containsKey(pHead.random)) {
                map.get(pHead).random = map.get(pHead.random);
            } else {
                map.get(pHead).random = new RandomListNode(pHead.random.label);
            }
            map.get(pHead).next = map.get(pHead.next);
            pHead = pHead.next;
        }
        return map.get(head);
    }


}
