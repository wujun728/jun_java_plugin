package com.jun.plugin.demo.chap07.sec02;

import java.util.ArrayList;

public class TestArrayList {

	private static void printArrayList(ArrayList<String> arrayList){
		System.out.println("��ǰ�ļ���Ԫ�أ�");
		for(int i=0;i<arrayList.size();i++){
			System.out.print(arrayList.get(i)+" ");
		}
		System.out.println();
	}
	
	public static void main(String[] args) {
		ArrayList<String> arrayList=new ArrayList<String>();
		// ���Ԫ��
		arrayList.add("����");
		arrayList.add("����");
		printArrayList(arrayList);
		// ��ָ����Ԫ�ز�����б��е�ָ��λ�á�
		arrayList.add(1, "С����");
		printArrayList(arrayList);
		// ��ָ����Ԫ��������б���ָ��λ���ϵ�Ԫ�ء�
		arrayList.set(2, "С����");
		printArrayList(arrayList);
		// �Ƴ����б���ָ��λ���ϵ�Ԫ�ء�
		arrayList.remove(0);
		printArrayList(arrayList);
	}
}
