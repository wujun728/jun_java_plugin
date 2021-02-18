package com.java1234.chap08.sec02;

import java.util.ArrayList;

public class TestArrayList {

	private static void pringArrayList(ArrayList<String> arrayList){
		System.out.print("当前的集合元素:");
		for(int i=0;i<arrayList.size();i++){
			System.out.print(arrayList.get(i)+" ");
		}
		System.out.println();
	}
	
	public static void main(String[] args) {
		ArrayList<String> arrayList=new ArrayList<String>();
		arrayList.add("张三");
		arrayList.add("李四");
		pringArrayList(arrayList);
		// 将指定的元素插入此列表中的指定位置。
		arrayList.add(1,"小张三");
		pringArrayList(arrayList);
		// 用指定的元素替代此列表中指定位置上的元素。
		arrayList.set(2, "小李四");
		pringArrayList(arrayList);
		// 移除此列表中指定位置上的元素。
		arrayList.remove(0);
		pringArrayList(arrayList);
	}
}
