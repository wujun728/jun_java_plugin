package com.jun.plugin.javase.collectionn;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class Test1 {
	public static void main(String[] args) {
//		ArrayList
		/*Collection list=new ArrayList ();
		Collection list1=new ArrayList ();
		list.add("a");
		list.add("b");
		list.add("c");
		list.add("d");
		list1.add("c");
		list1.add("d");
		list1.add("e");
		list1.add("f");*/
//		list.removeAll(list1);//����
//		list.retainAll(list1);//����
//		list.clear();
//		System.out.println(list.hashCode());
		/*Iterator iter=list.iterator();
		while(iter.hasNext()){
			System.out.println(iter.next());
		}
		System.out.println("--------");
		Iterator it=list1.iterator();
		while(it.hasNext()){
			System.out.println(it.next());
		}*/
		
		
//		LinkedList
		/*List<String[]> linked=new LinkedList<String[]>();
		String[] s1={"q","w","e"};
		String[] s2={"p","o","i","h"};
		linked.add(s1);
		linked.add(s2);
		for(int j=0;j<linked.size();j++){
			for(int i=0;i<linked.get(0).length;i++){
				System.out.print(linked.get(j)[i]+",");
			}
			System.out.println();
		}
		System.out.println("---------");
		Iterator<String[]>it=linked.iterator();
		while(it.hasNext()){
			String[] str=(String[])it.next();
			for(int i=0;i<str.length;i++){
				System.out.print(str[i]+",");
			}
			System.out.println();
		}
		System.out.println();*/
		/*List linked1=new LinkedList ();
		String[] s11={"a","d","s","c"};
		int[] s22={1,2,3,4,5,6};
		int a=9;
		linked1.add(s11);
		linked1.add(s22);
		linked1.add(a);
		Iterator it1=linked1.iterator();
		while(it1.hasNext()){
			System.out.println(it1.next());
		}
		System.out.println("----------");*/
		
//		HashSet//���򡢲����ظ��Ҵ�����������Ϳ��Բ�ͬ
		/*Set s111=new HashSet();
		int[] arrNum={1,2,3,4,5};
		int[] arrNum2={9,8,7,6,5};
		s111.add("a");
		s111.add("a");
		s111.add(5);
		s111.add(arrNum);
		s111.add(arrNum);
		Iterator it11=s111.iterator();
		while(it11.hasNext()){
			System.out.println(it11.next());
		}
		System.out.println("----------");*/
		
		
//		TreeSet//���򡢲����ظ��Ҵ�����������ͱ�����ͬ���Դ���ĵ�һ��ֵ����Ϊ׼��������дcompareTo����
		/*Set s222=new TreeSet();
		int[] arrNum={1,2,3,4,5};
//		int[] arrNum2={9,8,7,6,5};
		s222.add(arrNum);
//		s222.add(arrNum2);
		Iterator it111=s222.iterator();
		while(it111.hasNext()){
			System.out.println(it111.next());
		}*/
		
	}
}


