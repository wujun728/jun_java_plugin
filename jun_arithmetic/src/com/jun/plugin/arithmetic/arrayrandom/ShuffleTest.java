package com.jun.plugin.arithmetic.arrayrandom;

public class ShuffleTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Integer[] arr={1,2,3,4,5};
		MyPrinter2.print(shufile_overall(arr));
	}
	
	/**
	 * 数组乱序，随机生成新的数组
	 * @param arr
	 * @return
	 */
	public static Object[] shufile_overall(Object[] arr){
		int length=arr.length;
		for(int i=0;i<length;i++){
			int index=(int)(Math.random()*length);		//生成一个数组长度的随机数	
			Object obj=arr[i];//按迭代顺序，交换数据第N跟生成的随机的坐标的值
			arr[i]=arr[index];
			arr[index]=obj;
		}
		return arr;
	}
	
}
