package com.ky26.string;

public class Test4 {
	public static void main(String[] args) {
		String[] c1={"abc","bcd","cde","张三"};
		StringBuffer s1=new StringBuffer("abc");
		for(int i=0;i<c1.length;i++){
			s1.append(c1[i]);
		}
		System.out.println(s1.toString());
		
		s1.replace(0, 3, "李四");
		System.out.println(s1.toString());
		
		s1.substring(3,7);
		System.out.println(s1.toString());
		System.out.println(s1.substring(3,7));
	}
}
