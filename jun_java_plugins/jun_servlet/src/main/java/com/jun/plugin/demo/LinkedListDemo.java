package com.jun.plugin.demo;
import java.util.LinkedList;

//������ʹ��LinkedListʵ��һ����ջ
class LinkedListStack {
	// ��ջ�ڲ�ʹ��һ��LinkedList�������Ԫ��
	private LinkedList data = new LinkedList();
	public boolean isEmpty() {
		return data.isEmpty();
	}
	public Object top() {
		// ȡջ��Ԫ���൱��ȡLinkedList������ͷλ�õ�Ԫ��
		return data.getFirst();
	}
	public void push(Object element) {
		// ѹ��Ԫ���൱����LinkedList������ͷλ�ò���Ԫ��
		data.addFirst(element);
	}
	public void pop() {
		// ����Ԫ���൱��ɾ��LinkedList������ͷλ�õ�Ԫ��
		data.removeFirst();
	}
}
public class LinkedListDemo {
	public static void main(String[] args) {
		LinkedListStack stack = new LinkedListStack();
		// ��������ʹ����LinkedListʵ�ֵĶ�ջ���÷�
		stack.push("C++");
		stack.push("C#");
		stack.push("Java");
		while (! stack.isEmpty()) {
			System.out.println(stack.top());
			stack.pop();
		}
	}
}
