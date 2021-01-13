package book.oo.sort;

import book.oo.sort.impl.BinaryInsertSort;
import book.oo.sort.impl.BubbleSort;
import book.oo.sort.impl.LinearInsertSort;
import book.oo.sort.impl.QuickSort;
import book.oo.sort.impl.SelectionSort;

public class SortTest {
	
	/**
	 * 打印数组
	 * @param intArray  待打印的数组
	 */
	public static void printIntArray(int[] intArray){
		if (intArray == null){
			return;
		}
		for (int i=0; i<intArray.length; i++){
			System.out.print(intArray[i] + " ");
		}
		System.out.println();
	}
	public static void main(String[] args){
		int[] intArray = new int[]{6,3,4,2,7,2,-3,3};
		
		System.out.print("排序前的数组：");
		printIntArray(intArray);
		ISortNumber test = new SelectionSort();
		System.out.print("选择排序法排序结果：");
		printIntArray(test.sortASC(intArray));
		
		System.out.print("排序前的数组：");
		printIntArray(intArray);
		test = new BubbleSort();
		System.out.print("冒泡排序法排序结果：");
		printIntArray(test.sortASC(intArray));
		
		System.out.print("排序前的数组：");
		printIntArray(intArray);
		test = new LinearInsertSort();
		System.out.print("线性插入排序法排序结果：");
		printIntArray(test.sortASC(intArray));
		
		System.out.print("排序前的数组：");
		printIntArray(intArray);
		test = new BinaryInsertSort();
		System.out.print("二分插入排序法排序结果：");
		printIntArray(test.sortASC(intArray));
		
		System.out.print("排序前的数组：");
		printIntArray(intArray);
		test = new QuickSort();
		System.out.print("快速排序法排序结果：");
		printIntArray(test.sortASC(intArray));
	}
//	排序前的数组：6 3 4 2 7 2 -3 3 
//	选择排序法排序结果：-3 2 2 3 3 4 6 7 
//	排序前的数组：6 3 4 2 7 2 -3 3 
//	冒泡排序法排序结果：2 2 -3 3 3 4 6 7 
//	排序前的数组：6 3 4 2 7 2 -3 3 
//	线性插入排序法排序结果：-3 2 2 3 3 4 6 7 
//	排序前的数组：6 3 4 2 7 2 -3 3 
//	二分插入排序法排序结果：-3 2 2 3 3 4 6 7 
//	排序前的数组：6 3 4 2 7 2 -3 3 
//	快速排序法排序结果：-3 2 2 3 3 4 6 7 
}
