package com.java1234.chap08.sec04;

import java.util.HashSet;
import java.util.Iterator;

public class TestHashSet {

	public static void main(String[] args) {
		/**
		 * 1,HashSet是无序的
		 * 2,不允许存在重复的值
		 */
		HashSet<String> hs=new HashSet<String>();
		hs.add("232");
		hs.add("21");
		hs.add("222");
		hs.add("333");
		hs.add("21");
		
		Iterator<String> it=hs.iterator();
		while(it.hasNext()){
			String s=it.next();
			System.out.println(s);
		}
	}
}
