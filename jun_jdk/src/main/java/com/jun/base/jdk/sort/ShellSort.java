package com.jun.base.jdk.sort;

/** 
 * 希尔排序 
希尔排序(Shell Sort)是插入排序的一种。是针对直接插入排序算法的改进。该方法又称缩
小增量排序，因 DL．Shell 于 1959 年提出而得名。 
希尔排序属于插入类排序,是将整个无序列分割成若干小的子序列分别进行插入排序  
 
算法描述：  
先取一个正整数 d1<n，把所有序号相隔 d1 的数组元素放一组，组内进行直接插入排
序；然后取 d2<d1，重复上述分组和排序操作；直至 di=1，即所有记录放进一个组中
排序为止 。 
 
过程举例： 
假设待排序文件有 10 个记录，其关键字分别是：  
49，38，65，97，76，13，27，49，55，04。  
增量序列 d 的取值依次为：  
5，3，1  
 
d=5  
    49 38 65 97 76 13 27 49* 55 04  
    49 13  
    |-------------------|  
    38 27  
    |-------------------|  
    65 49*  
    |-------------------|  
    97 55  
    |-------------------|  
    76 04  
    |-------------------|  
    一趟结果  
13 27 49* 55 04 49 38 65 97 76  
-------------------------------------------------------------------- 
     d=3  
    13 27 49* 55 04 49 38 65 97 76  
    13 55 38 76  
    |------------|------------|------------|  
    27 04 65  
    |------------|------------|  
    49* 49 97  
    |------------|------------|  
    二趟结果  
13 04 49* 38 27 49 55 65 97 76  
--------------------------------------------------------------------- 
 
    d=1  
    13 04 49* 38 27 49 55 65 97 76  
    |----|----|----|----|----|----|----|----|----|  
    三趟结果  
    04 13 27 38 49* 49 55 65 76 97  
--------------------------------------------------------------------- 
 * @author 	<a href="mailto:ketayao@gmail.com">ketayao</a>
 * @since   2014年1月23日 下午4:56:56 
 */
public class ShellSort extends Sort {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new ShellSort().sort(array);
	}

	/* (non-Javadoc)
	 * @see sort.Sort#execute(int[])
	 */
	@Override
	public void execute(int[] array) {
//	  int i,j,k;   
//	  int t;   
//		 
//		k = array.length / 2;
//		System.out.println(k + "--");
//		while (k > 0) {
//			for (i = k; i < array.length; i++) {
//				t = array[i];
//				for (j = i - k; j >= 0 && array[j] > t; j -= k)
//					array[j + k] = array[j];
//				array[j + k] = t;
//			}
//			k /= 2;
//			System.out.println(k + "--");
//			print(array);
//		}
		
		
		int d = array.length / 2;
		while (d > 0) {
			System.out.println(d + "--");
			for (int i = d; i < array.length; i++) {
				int t = array[i];
				int j;
				for (j = i - d; j >= 0 && array[j] > t; j -= d) {
					array[j + d] = array[j];
				}
				
				array[j + d] = t; 
			}
			print(array);
			d /= 2;
		}
	}
}
