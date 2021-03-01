package com.ky26.collectionn;

import java.util.LinkedList;

public class LinkedListTest {
	public static void main(String[] args) {
		LinkedList list=new LinkedList();
		list.add("a");
		list.add("b");
		list.add("c");
		/*pushS(list,"d");
		popS(list);*/
		queueAdd(list,"d");
		queueRemove(list);
	}
	
	static void pushS(LinkedList list,Object obj){
		list.add(0,obj);
		System.out.println(list);
	}//头部操作
	static void popS(LinkedList list){
		list.removeFirst();
		System.out.println(list);
	}
	static void queueAdd(LinkedList list,Object obj){
		list.add(list.size(),obj);
		System.out.println(list);
	}//尾部操作
	static void queueRemove(LinkedList list){
		list.removeLast();
		System.out.println(list);
	}
}
