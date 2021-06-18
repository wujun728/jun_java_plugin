package com.ky26.object.test2;

import java.util.ArrayList;
import java.util.List;

public class ListGather {
	public static void main(String[] args) {
		List<String> list=new ArrayList<String>();
		list.add("a");
		list.add("b");
		list.add("c");
		int i=(int)(Math.random()*list.size());
		System.out.println("随机获得数组中的元素是"+list.get(i));
		list.remove(2);
		System.out.println("去掉索引为2的元素后");
		for(int j=0;j<list.size();j++){
			System.out.println(list.get(j));
		}
		
	}
}
