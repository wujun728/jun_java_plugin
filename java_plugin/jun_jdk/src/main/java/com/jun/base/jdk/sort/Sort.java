package com.jun.base.jdk.sort;

/** 
 * 常见排序算法 
 
排序是计算机内经常进行的一种操作，其目的是将一组“无序”的记录序列调整为“有序”的记
录序列。 
 
排序分内排序和外排序。 
内排序：指在排序期间数据对象全部存放在内存的排序。 
外排序：指在排序期间全部对象个数太多，不能同时存放在内存，必须根据排序过程的要求，
不断在内、外存之间移动的排序。 
 
内排序的方法有许多种，按所用策略不同，可归纳为五类：插入排序、选择排序、交
换排序、归并排序和分配排序。 
插入排序主要包括直接插入排序和希尔排序两种； 
选择排序主要包括直接选择排序和堆排序； 
交换排序主要包括冒泡排序和快速排序； 
归并排序主要包括二路归并（常用的归并排序）和自然归并。 
分配排序主要包括箱排序和基数排序。 
 
稳定排序：假设在待排序的文件中，存在两个或两个以上的记录具有相同的关键字，
在用某种排序法排序后，若这些相同关键字的元素的相对次序仍然不变，则这种排序
方法是稳定的。 
其中冒泡，插入，基数，归并属于稳定排序； 
选择，快速，希尔，堆属于不稳定排序。 
 
时间复杂度是衡量算法好坏的最重要的标志。 
排序的时间复杂度与算法执行中的数据比较次数与数据移动次数密切相关。 
 
以下给出介绍简单的排序方法：插入排序，选择排序，冒泡排序。 
三种算法的时间复杂度都是 n 2 级的。 
 * @author Wujun
 * @since   2014年1月23日 下午3:43:34 
 */
public abstract class Sort {
	protected static int[] array = {34, 6, 32, 1, 90, 11};
	
	public void print(int[] array) {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < array.length; i++) {
			builder.append(array[i] + ",");
		}
		
		if (builder.length() > 0) {
			System.out.println(builder.substring(0, builder.length() - 1));
		} else {
			System.out.println(builder.toString());
		}
	}
	
	public void sort(int[] array) {
		execute(array);
		print(array);
	}
	
	public abstract void execute(int[] array);
}
