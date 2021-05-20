package sort;

/** 
 * 直接选择排序 
算法描述： 
首先找出最大的元素，将其与 a[n-1]位置交换； 
然后在余下的 n-1 个元素中寻找最大的元素，将其与 a[n-2]位置交换， 
如此进行下去直至 n 个元素排序完毕。 
 
过程举例： 
初始元素序列：      8          3          2        5        9        3*        6 
第一趟排序：          8            3          2        5      6        3*  【9  】 
第二趟排序：          3*        3          2        5        6  【  8        9  】 
第三趟排序：          3*        3          2        5    【6        8        9  】 
第四趟排序：          3*        3          2    【5        6        8        9  】 
第五趟排序：          2          3      【3*      5        6        8        9  】 
第六趟排序：          2    【  3          3*      5        6        8        9  】 
 * @author Wujun
 * @since   2014年1月23日 下午3:49:15 
 */
public class SelectionSort extends Sort {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new SelectionSort().sort(array);
	}

	/* (non-Javadoc)
	 * @see sort.Sort#execute(int[])
	 */
	@Override
	public void execute(int[] array) {
		for (int i = 0; i < array.length - 1; i++) {
			int k = i;
			for (int j = i + 1; j < array.length; j++) {
				if (array[k] > array[j]) {
					k = j;
				}
			}
			
			int small = array[k];
			array[k] = array[i];
			array[i] = small;
		}
		
	}

}
