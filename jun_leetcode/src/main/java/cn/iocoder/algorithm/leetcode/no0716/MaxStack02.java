package cn.iocoder.algorithm.leetcode.no0716;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

/**
 * 双向链表(LinkedList) + 平衡树(TreeMap)
 */
public class MaxStack02 {

    private class LinkedList {

        /**
         * 虚拟头节点
         */
        private Node head;
        /**
         * 虚拟尾节点
         */
        private Node tail;

        public LinkedList() {
            head = new Node(0);
            tail = new Node(0);
            head.next = tail;
            tail.prev = head;
        }

        public void add(Node node) {
            Node prev = tail.prev;
            // tail 的前置节点的下一个，指向 node
            prev.next = node;
            // tail 的前置节点，指向 node
            tail.prev = node;
            // node 的指向
            node.prev = prev;
            node.next = tail;
        }

        public Node pop() {
            // 获得要被 pop 出来的节点
            Node node = tail.prev;
            // 去除 node 节点
            tail.prev = node.prev;
            node.prev.next = tail;
            return node;
        }

        public Node top() {
            return tail.prev;
        }

        public void remove(Node node) {
            // 获得前后节点
            Node prev = node.prev;
            Node next = node.next;
            // 前后互相指向
            prev.next = next;
            next.prev = prev;
        }

    }

    private class Node {

        /**
         * 值
         */
        private int val;
        /**
         * 前节点
         */
        private Node prev;
        /**
         * 后节点
         */
        private Node next;

        public Node(int val) {
            this.val = val;
        }
    }

    /**
     * 使用双向链表，模拟栈
     */
    private LinkedList list;

    /**
     * 映射
     *
     * key：值
     */
    private TreeMap<Integer, List<Node>> map;

    /** initialize your data structure here. */
    public MaxStack02() {
        list = new LinkedList();
        map = new TreeMap<>();
    }

    public void push(int x) {
        // 添加到 list 中
        Node node = new Node(x);
        list.add(node);

        // 添加到 map 中
        map.computeIfAbsent(x, integer -> new ArrayList<>(1)).add(node);
    }

    public int pop() {
        // 从 list 移除
        Node node = list.pop();

        // 从 map 移除
        List<Node> values = map.get(node.val);
        if (values.size() == 1) {
            map.remove(node.val);
        } else {
            values.remove(values.size() - 1); // 因为先进后出
        }

        return node.val;
    }

    public int top() {
        return list.top().val;
    }

    public int peekMax() {
        return map.lastKey();
    }

    public int popMax() {
        // 从 map 移除
        List<Node> values = map.lastEntry().getValue();
        Node node = values.get(values.size() - 1);
        if (values.size() == 1) {
            map.remove(node.val);
        } else {
            values.remove(values.size() - 1); // 因为先进后出
        }

        // 从 list 移除
        list.remove(node);

        return node.val;
    }

    public static void main(String[] args) {
        MaxStack02 stack = new MaxStack02();
        stack.push(5);
        stack.push(1);
        stack.push(5);
        System.out.println(stack.top()); // -> 5
        System.out.println(stack.popMax()); // -> 5
        System.out.println(stack.top()); // -> 1
        System.out.println(stack.peekMax()); // -> 5
        System.out.println(stack.pop()); // -> 1
        System.out.println(stack.top()); // -> 5
    }

}
