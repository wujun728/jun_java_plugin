package book.oo.sort.impl;

import book.oo.sort.ISortNumber;

/**
 * 使用选择排序法对整型数组进行排序
 */
public class SelectionSort implements ISortNumber {
	public SelectionSort(){
	}
	/**
	 * 选择排序法
	 */
	public int[] sortASC(int[] intArray) {
		if (intArray == null){
			return null;
		}
		//因为Java的参数传递是采用引用传值方式，因为在排序的过程中，需要对改变数组，
		//所以，为了保证输入参数的值不变，这里采用了数组的clone方法，直接克隆一个数组。
		int[] srcDatas = (int[]) intArray.clone();
		int size = srcDatas.length;
		for (int i = 0; i < size; i++) {
			for (int j = i; j < size; j++) {
				if (srcDatas[i] > srcDatas[j]) {
					swap(srcDatas, i, j);
				}
			}
		}
		return srcDatas;
	}
	/**
	 * 交换数组中下标为src和dest的值
	 * @param data	数组
	 * @param src	源下标
	 * @param dest  目标下标
	 */
	private void swap(int[] data, int src, int dest) {
		int temp = data[src];
		data[src] = data[dest];
		data[dest] = temp;
	}

}
