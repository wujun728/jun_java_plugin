package book.arrayset;

import java.util.Arrays;

/**
 * 使用java.util.Arrays类操作数组
 */
public class UsingArrays {
	/**
	 * 整型数组输出
	 * @param array
	 */
	public static void output(int[] array) {
		if (array != null) {
			for (int i = 0; i < array.length; i++) {
				System.out.print(array[i] + " ");
			}
		}
		System.out.println();
	}
	public static void main(String[] args) {

		// 填充数组
		int[] array0 = new int[5];
		// 将array0中所有元素的值都赋为5
		Arrays.fill(array0, 5);
		System.out.println("调用Arrays.fill(array0, 5)后: ");
		UsingArrays.output(array0);
		// 将array0中的第2个到第3个元素的值赋为8
		Arrays.fill(array0, 2, 4, 8);
		System.out.println("调用Arrays.fill(array0, 2, 4, 8)后: ");
		UsingArrays.output(array0);

		// 对数组排序
		int[] array1 = new int[] { 7, 8, 3, 12, 6, 3, 5, 4 };
		// 对数组的第2个到第6个元素进行排序
		Arrays.sort(array1, 2, 7);
		System.out.println("调用Arrays.sort(array1, 2, 7)后: ");
		UsingArrays.output(array1);
		// 对整个数组进行排序
		Arrays.sort(array1);
		System.out.println("调用Arrays.sort(array1)后: ");
		UsingArrays.output(array1);

		// 比较数组元素是否相等
		System.out.println(Arrays.equals(array0, array1));
		int[] array2 = (int[]) array1.clone();
		System.out.println("array1 和 array2是否相等？ "
				+ Arrays.equals(array1, array2));

		// 使用二分法在数组中查找指定元素所在的下标（返回的下标从1开始）
		// 数组必须是排好序的，否则结果会不正确
		Arrays.sort(array1);
		System.out.println("元素8在array1中的位置: " + Arrays.binarySearch(array1, 8));
		// 如果不存在，就返回负数
		System.out.println("元素9在array1中的位置: " + Arrays.binarySearch(array1, 9));
	}
}
