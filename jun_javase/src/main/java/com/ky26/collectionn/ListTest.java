package com.ky26.collectionn;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public class ListTest {
	public static void main(String[] args) {
		List list=new LinkedList();
		show(list);
		/*Object[] obj=new Object[10];
		int[] arr={1,2,3,4,5};
		String[] strArr={"a","b","c"};
		obj[0]=arr;
		obj[1]=arr;
		obj[2]=strArr;
		obj[3]=strArr;
		for(int i=0;i<obj.length;i++){
			System.out.println(obj[i]);
		}*/
	}
	static void show(List list){
		list.add("abc1");
		list.add("abc2");
		list.add("abc3");
		list.add("abc4");
		list.add("abc5");
		list.add("abc6");
		list.add(9);
		ListIterator it=list.listIterator();
		while(it.hasNext()){
			Object obj=it.next();
			if(obj.equals("abc3")){
				it.set("hehe");
			}
			System.out.println(obj);
		}
		
		System.out.println(list);
	}
}
