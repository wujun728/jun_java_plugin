package com.java1234.chap07.sec04;

import java.util.HashSet;
import java.util.Iterator;

public class TestHashSet {

	public static void main(String[] args) {
		/**
		 * 1，HashSet是无序
		 * 2，不循序有重复的值
		 */
		HashSet<String> hs=new HashSet<String>();
		hs.add("1");
		hs.add("2");
		hs.add("3");
		hs.add("4");
		hs.add("3");
		
		/**
		 * 用Iterator遍历集合
		 */
		Iterator<String> it=hs.iterator();
		while(it.hasNext()){
			String s=it.next();
			System.out.println(s+" ");
		}
	}
}
