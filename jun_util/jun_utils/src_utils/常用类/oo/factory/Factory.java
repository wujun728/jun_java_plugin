package book.oo.factory;

import book.oo.sort.ISortNumber;
import book.oo.sort.impl.BubbleSort;
import book.oo.sort.impl.LinearInsertSort;
import book.oo.sort.impl.QuickSort;
import book.oo.sort.impl.SelectionSort;

/*
 * 模式名称：工厂模式
 * 模式特征：通过一个通用的接口创建不同的类对象
 * 模式用途：面向接口编程，动态联编，多态性
 **/
public class Factory {
	public static final String SELECTION_SORT = "selection";
	public static final String BUBBLE_SORT = "bubble";
	public static final String LINEARINSERT_SORT = "liner";
	public static final String QUICK_SORT = "quick";

	public static ISortNumber getOrderNumber(String id) {
		if (SELECTION_SORT.equalsIgnoreCase(id)) {
			return new SelectionSort();
		} else if (BUBBLE_SORT.equalsIgnoreCase(id)) {
			return new BubbleSort();
		} else if (LINEARINSERT_SORT.equalsIgnoreCase("id")) {
			return new LinearInsertSort();
		} else if (QUICK_SORT.equalsIgnoreCase("id")) {
			return new QuickSort();
		} else {
			return null;
		}
	}
	/**
	 * 输出整型数组
	 * @param array	待输出的数组
	 */
	public static void printIntArray(int[] array) {
		if (array != null) {
			for (int i = 0; i < array.length; i++) {
				System.out.print(array[i] + " ");
			}
			System.out.println();
		}
	}
	public static void main(String[] args) {
		int[] intarray = new int[] { 6, 1, 3, 5, 4 };
		System.out.println("排序前的数组是：");
		printIntArray(intarray);
		System.out.println("用选择排序法类对数组进行降序排序后的结果是：");
		int[] orderedArray = Factory.getOrderNumber(Factory.SELECTION_SORT)
				.sortASC(intarray);
		printIntArray(orderedArray);
		System.out.println("用冒泡排序法对数组进行升序排序后的结果是：");
		orderedArray = Factory.getOrderNumber(Factory.BUBBLE_SORT).sortASC(
				intarray);
		printIntArray(orderedArray);
		System.out.println("用线性插入排序法对数组进行升序排序后的结果是：");
		orderedArray = Factory.getOrderNumber(Factory.LINEARINSERT_SORT)
				.sortASC(intarray);
		printIntArray(orderedArray);
		System.out.println("用快速排序法对数组进行升序排序后的结果是：");
		orderedArray = Factory.getOrderNumber(Factory.QUICK_SORT).sortASC(
				intarray);
		printIntArray(orderedArray);
	}
}
