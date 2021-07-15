package com.jun.plugin.thread;

import java.util.ArrayList;
import java.util.List;

/**
 * 线程局部变量 ThreadLocal
 */
public class ThreadLocalVariable implements Runnable {

	public static List<String> stringList = new ArrayList<>();

	public static final ThreadLocal<List<String>> threadLocal = new ThreadLocal<List<String>>() {

		@Override
		protected List<String> initialValue() {
			// TODO Auto-generated method stub
			return new ArrayList<String>();
		}

	};

	public static List<String> getStringList1() {
		return threadLocal.get();
	}

	public static List<String> getStringList2() {
		return stringList;
	}

	public static void main(String[] args) {

		new Thread(new ThreadLocalVariable()).start();
		new Thread(new ThreadLocalVariable()).start();

	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		List<String> list = getStringList1();
		list.add(String.valueOf(this.hashCode()));
		for (String s : list) {
			System.out.print(s + ",");
		}
	}

}
