package com.jun.plugin.javase.object.test2;

import java.util.ArrayList;
import java.util.List;

public class ListGather {
	public static void main(String[] args) {
		List<String> list=new ArrayList<String>();
		list.add("a");
		list.add("b");
		list.add("c");
		int i=(int)(Math.random()*list.size());
		System.out.println("�����������е�Ԫ����"+list.get(i));
		list.remove(2);
		System.out.println("ȥ������Ϊ2��Ԫ�غ�");
		for(int j=0;j<list.size();j++){
			System.out.println(list.get(j));
		}
		
	}
}
