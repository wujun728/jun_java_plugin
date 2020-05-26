package com.jun.base.jdk.sort;

/** 
 * 冒泡排序： 
标准的冒泡排序过程如下： 
首先比较 a[1]与 a[2]的值，若 a[1]大于 a[2]则交换两者的值，否则不变。 
再比较 a[2]与 a[3]的值，若 a[2]大于 a[3]则交换两者的值，否则不变。 
再比较 a[3]与 a[4]，以此类推，最后比较 a[n-1]与 a[n]的值。 
这样处理一轮后，a[n]的值一定是这组数据中最大的。 
再对 a[1]~a[n-1]以相同方法处理一轮。 
共处理 n-1 轮后 a[1]、a[2]、……a[n]就以升序排列了。  
过程举例： 
初始元素序列：  8          3          2          5        9        3*      6 
第一趟排序：      3          2          5          8        3*      6  【  9  】 
第二趟排序：      2          3          5          3*      6    【8        9  】 
第三趟排序：      2          3          3*        5  【  6        8        9  】 
第四趟排序：      2          3          3*    【5        6        8        9  】 
第五趟排序：      2          3      【3*        5        6        8        9  】 
第六趟排序：      2        【3        3*        5        6        8        9  】 
 * @author 	<a href="mailto:ketayao@gmail.com">ketayao</a>
 * @since   2014年1月23日 下午3:38:49 
 */
public class BubbleSort extends Sort{

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new BubbleSort().sort(array);
	}

	public void execute(int[] array) {
		
		for (int i = 0; i < array.length - 1; i++) {
			for (int j = i + 1; j < array.length; j++) {
				if (array[i] > array[j]) {
					int array_j = array[j]; 
					array[j] = array[i];
					array[i] = array_j;
				}
			}
		}
		
	}
}
