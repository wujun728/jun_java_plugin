package com.ky26.object;

import java.util.Random;

public class FinalKey {
	public static void main(String[] args) {
		/*FatherDemo data=new FatherDemo();
		data.test=new Test1();
		
		data.value2++;
		
		data.test2=new Test1();
		
		for(int i=0;i<data.a.length;i++){
			a[i]=9;
		}
		System.out.println(data);
		System.out.println("data2");
		System.out.println(new FatherDemo());
		System.out.println(data);*/
		FatherDemo f1=new FatherDemo();
		f1.test();
	}
}
class Test1{
	int i=0;
}

class FatherDemo{
	static Random rand=new Random();
	private final int VALUE_1=9;
	
	private static final int VALUE_2=10;
	
	private final Test1 test=new Test1();
	
	private Test1 test2=new Test1();
	
	private final int[] a={1,2,3,4,5};
	
	private final int i4=rand.nextInt(20);
	
	private static final int i5=rand.nextInt(20);
	
	public String toString(){
		return i4+" "+i5+" ";
	}
	
	public void test(){
		System.out.println(VALUE_1);
	}
	
	
}

