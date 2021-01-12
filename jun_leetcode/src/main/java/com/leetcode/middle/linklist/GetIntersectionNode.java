package com.leetcode.middle.linklist;

import com.leetcode.leetcodeutils.ListNode;

/**
 * 相交链表
 *
 * @author BaoZhou
 * @date 2018/12/24
 */
public class GetIntersectionNode {
    public static void main(String[] args) {

    }

    public static ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        ListNode p1 = headA, p2 = headB;
        int pos1 = 0 , pos2=0;
        while (p1 != null) {
            p1 = p1.next;
            pos1++;
        }
        while (p2 != null) {
            p2 = p2.next;
            pos2++;
        }
        if (p1 != p2){
            return null;
        }else {
            if(pos1>pos2){
                for (int i = 0; i < pos1-pos2; i++) {
                    headA = headA.next;
                }
            }
            else {
                for (int i = 0; i <pos2- pos1 ; i++) {
                    headB = headB.next;
                }
            }

            while (headA !=headB){
                headA = headA.next;
                headB = headB.next;
            }
            return headA;
        }
    }
}
