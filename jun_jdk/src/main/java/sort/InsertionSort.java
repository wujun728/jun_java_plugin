package sort;

/** 
 * 直接插入排序 
算法描述： 
每步将一个待排序元素，插入到前面已经排好序的一组元素的适当位置上，直到全部
元素插入为止。 
 
过程举例： 
初始元素序列：    【8  】  3          2        5        9        3*        6 
第一趟排序：        【3        8】      2        5        9        3*        6 
第二趟排序：        【2        3          8】    5        9        3*        6 
第三趟排序：        【2        3          5        8】    9        3*        6 
第四趟排序：        【2        3          5        8        9】    3*        6 
第五趟排序：        【2        3          3*      5        8        9】      6 
第六趟排序：        【2        3          3*      5        6        8          9】 
 * @author 	<a href="mailto:ketayao@gmail.com">ketayao</a>
 * @since   2014年1月23日 下午4:45:59 
 */
public class InsertionSort extends Sort {
	public static void main(String[] args) {
		new InsertionSort().sort(array);
	}

	/* (non-Javadoc)
	 * @see sort.Sort#execute(int[])
	 */
	@Override
	public void execute(int[] array) {
		for (int i = 1; i < array.length; i++) {
			for (int j = 0; j < i; j++) {
				if (array[j] > array[i]) {
					int small = array[i];
					array[i] = array[j];
					array[j] = small;
				}
			}
		}
	}

}
