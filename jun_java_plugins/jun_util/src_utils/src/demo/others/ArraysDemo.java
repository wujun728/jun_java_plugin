package demo.others;

import java.util.Arrays;

public class ArraysDemo {
	/*
	 * 1.数组类提供了排序功能，对基本数据类型length<7采用直接插入排序，否则采用快速排序 如果数组元素时对象，采用合并排序
	 * 2.提供二分查找法实现，注意二分查找时先对数组进行排序，否则返回一个不确定值
	 */
	public static void main(String[] args) {
		int[][] a = { { 1, 2 } };
		int[][] b = { { 1, 2 } };
		System.out.println(Arrays.toString(a));
		// 3. 多维数组的toString
		System.out.println(Arrays.deepToString(a));
		System.out.println(Arrays.hashCode(a));
		System.out.println(Arrays.deepHashCode(a));
		System.out.println(Arrays.equals(a, b));
		// 4. 多维数组的比较
		System.out.println(Arrays.deepEquals(a, b));

		// 5. 并没有实现多维数组的复制
		int[][] c = Arrays.copyOf(a, 1);
		// 6.填充数组
		Arrays.fill(a[0], 5);// 在此改变a的值影响到了数组c的值
		System.out.println(Arrays.deepToString(c));
		System.out.println(Arrays.equals(a, c));
		System.out.println(Arrays.deepEquals(a, c));

	}
}
