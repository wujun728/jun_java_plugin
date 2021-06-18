package com.ky26.collectionn;

import java.util.HashSet;
import java.util.Iterator;
import java.util.TreeSet;

public class SetTest {
	public static void main(String[] args) {
		HashSet hs=new HashSet();
		hs.add(new Person1("zs",21));
		hs.add(new Person1("ls",22));
		hs.add(new Person1("ww",23));
		Iterator it=hs.iterator();
		while(it.hasNext()){
			System.out.println(it.next());
		}
		
		System.out.println("-----------");
		
		TreeSet ts=new TreeSet();
		ts.add(new Person1("zs",21));
		ts.add(new Person1("ls",19));
		ts.add(new Person1("ww",3));
		Iterator iter=ts.iterator();
		while(iter.hasNext()){
			System.out.println(iter.next());
		}
		
	}
}
