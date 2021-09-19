
package com.jun.plugin.reflect;

public class ClassDemo{
	
	public ClassDemo(){
	}
	
	public static void main(String[] args){
		ClassDemo test = new ClassDemo();
		System.out.println("The class of test is " + test.getClass().getName());
		try{
			Class c = Class.forName("java.lang.Object");
			System.out.println(c.getPackage()+"\t");
			System.out.println(c);
			java.lang.reflect.Method[] m = c.getMethods();
			System.out.println("The element in String[] is "
					+ String[].class.getComponentType());
			System.out.println((new int[3][4][5][6]).getClass().getName());
			System.out.println("The method defined in Object class:");
			for(int i=0; i<m.length; i++)
				System.out.println(m[i]);
		}
		catch(ClassNotFoundException e){
			e.printStackTrace();
		}
	}
}