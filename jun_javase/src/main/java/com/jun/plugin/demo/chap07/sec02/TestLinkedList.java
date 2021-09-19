package com.jun.plugin.demo.chap07.sec02;

import java.util.LinkedList;

public class TestLinkedList {

	private static void printLinkedList(LinkedList<String> linkedList){
		System.out.println("��ǰԪ�ؼ���");
		for(int i=0;i<linkedList.size();i++){
			System.out.print(linkedList.get(i)+" ");
		}
		System.out.println();
	}
	
	public static void main(String[] args) {
		LinkedList<String> linkedList=new LinkedList<String>();
		linkedList.add("����");
		linkedList.add("����");
		linkedList.add("����");
		linkedList.add("����");
		linkedList.add("����");
		
		printLinkedList(linkedList);
		
		// ���ش��б����״γ��ֵ�ָ��Ԫ�ص�������������б��в�������Ԫ�أ��򷵻� -1��
		System.out.println(linkedList.indexOf("����"));
		printLinkedList(linkedList);
		// ��ȡ�����Ƴ����б�ĵ�һ��Ԫ�أ�������б�Ϊ�գ��򷵻� null��
		System.out.println(linkedList.peekFirst());
		printLinkedList(linkedList);
		// ��ȡ�����Ƴ����б�����һ��Ԫ�أ�������б�Ϊ�գ��򷵻� null��
		System.out.println(linkedList.peekLast());
		printLinkedList(linkedList);
		// ��ȡ���Ƴ����б�ĵ�һ��Ԫ�أ�������б�Ϊ�գ��򷵻� null��
		System.out.println(linkedList.pollFirst());
		printLinkedList(linkedList);
		// ��ȡ���Ƴ����б�����һ��Ԫ�أ�������б�Ϊ�գ��򷵻� null��
		System.out.println(linkedList.pollLast());
		printLinkedList(linkedList);
		
	}
}
