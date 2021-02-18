package com.jun.plugin.demo;
public class IntegerDemo{
	public IntegerDemo(){
	}
	
	public static void main(String[] args){
		Integer[] array = {new Integer(20),new Integer(40), new Integer("110")};
		for(int i=0; i<array.length; i++){
			System.out.print(Integer.toBinaryString(array[i].intValue())+"\t");
			System.out.print(Integer.toHexString(array[i].intValue())+"\t");
			System.out.print(Integer.toOctalString(array[i].intValue())+"\n");
		}
	}
}