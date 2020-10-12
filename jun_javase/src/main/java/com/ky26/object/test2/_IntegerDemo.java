package com.ky26.object.test2;

public class _IntegerDemo {
	public static void main(String[] args) {
		int a=12;
		Integer b;
		b=a;
		a=b;//上面一个等式和本等式交换位置会报错
		System.out.println(b==a);
		System.out.println(Integer.toString(a));
		
		String str=Integer.toString(456);
		String str1=Integer.toBinaryString(456);
		String str2=Integer.toHexString(456);
		String str3=Integer.toOctalString(456);
		System.out.println("十进制"+str);
		System.out.println("二进制"+str1);
		System.out.println("十六进制"+str2);
		System.out.println("八进制"+str3);
		
		String strArr[]={"12","22","32","42"};
		int sum=0;
		for(int i=0;i<strArr.length;i++){
			int myInt=Integer.parseInt(strArr[i]);
			sum+=myInt;
		}
		System.out.println(sum);
		
		String str11="21";
		System.out.println(Integer.valueOf(str11));
		
	}
}
