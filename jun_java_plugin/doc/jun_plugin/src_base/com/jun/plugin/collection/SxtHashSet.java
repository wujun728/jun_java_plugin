package com.jun.plugin.collection;

import java.util.HashMap;

/**
 * �Զ����Լ���HashSet
 * @author Administrator
 *
 */
public class SxtHashSet {

	HashMap map;
	private static final Object PRESENT = new Object();

	public SxtHashSet(){
		map = new HashMap();
	}
	
	public int size(){
		return map.size();
	}
	
	public void add(Object o){
		map.put(o, PRESENT);   //set�Ĳ����ظ�����������map��������Ĳ����ظ���
	}
	
	public static void main(String[] args) {
		SxtHashSet s = new SxtHashSet();
		s.add("aaa");
		s.add(new String("aaa"));
		System.out.println(s.size());
	}

}
