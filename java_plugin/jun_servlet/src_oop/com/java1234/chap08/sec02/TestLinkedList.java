package com.java1234.chap08.sec02;

import java.util.LinkedList;

public class TestLinkedList {

	private static void printLinkedList(LinkedList<String> linkedList){
		System.out.print("当前元素的集合:");
		for(int i=0;i<linkedList.size();i++){
			System.out.print(linkedList.get(i)+" ");
		}
		System.out.println();
	}
	
	public static void main(String[] args) {
		LinkedList<String> linkedList=new LinkedList<String>();
		linkedList.add("张三");
		linkedList.add("李四");
		linkedList.add("王五");
		linkedList.add("李四");
		linkedList.add("赵六");
		printLinkedList(linkedList);
		// 返回此列表中首次出现的指定元素的索引，如果此列表中不包含该元素，则返回 -1。
		System.out.println(linkedList.indexOf("李四"));
		printLinkedList(linkedList);
		// 获取但不移除此列表的第一个元素；如果此列表为空，则返回 null。
		System.out.println(linkedList.peekFirst());
		printLinkedList(linkedList);
		// 获取但不移除此列表的最后一个元素；如果此列表为空，则返回 null。
		System.out.println(linkedList.peekLast());
		printLinkedList(linkedList);
		// 获取并移除此列表的第一个元素；如果此列表为空，则返回 null。
		System.out.println(linkedList.pollFirst());
		printLinkedList(linkedList);
		// 获取并移除此列表的最后一个元素；如果此列表为空，则返回 null。
		System.out.println(linkedList.pollLast());
		printLinkedList(linkedList);
	}
}
