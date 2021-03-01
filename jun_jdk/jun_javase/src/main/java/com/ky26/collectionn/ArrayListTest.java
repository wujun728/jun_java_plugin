package com.ky26.collectionn;

import java.util.ArrayList;
import java.util.Iterator;

public class ArrayListTest {
	public static void main(String[] args) {
		ArrayList a1=new ArrayList();
		a1.add(new Person1("list",21));
		a1.add(new Person1("list1",22));
		a1.add(new Person1("list2",23));
		a1.add(new Person1("list3",24));
		a1.add(new Person1("list4",25));
		a1.add(new Person1("list4",25));
		
		Person1 p1=new Person1("list4",25);
		Person1 p2=new Person1("list4",25);
		System.out.println(p1.getName().equals(p2.getName()));
		
		/*a1=getSingleArrayList(a1);
		System.out.println(a1);*/
		
	}
	static ArrayList getSingleArrayList(ArrayList a1){
		ArrayList temp=new ArrayList();
		boolean flag=false;
		Iterator iter=a1.iterator();
		while(iter.hasNext()){
			Object obj=iter.next();
			if(!temp.contains(obj)){
				temp.add(obj);
				flag=true;
			}
		}
		System.out.println(flag);
		return temp;
	}
}
