package book.oo.sort.impl;

import book.oo.sort.ISortNumber;
/**
 * 采用冒泡法对整型数组排序
 */
public class BubbleSort implements ISortNumber {

	public BubbleSort() {
	}

	/**
	 * 冒泡排序法
	 */
	public int[] sortASC(int[] intArray) {
		if (intArray == null){
			return null;
		}
		//因为Java的参数传递是采用引用传值方式，因为在排序的过程中，需要对改变数组，
		//所以，为了保证输入参数的值不变，这里采用了数组的clone方法，直接克隆一个数组。
		int[] srcDatas = (int[]) intArray.clone();
		boolean changedPosition = true;
		int comparedTimes = 0;
		int maxComparedTimes = srcDatas.length - 1;

		while ((comparedTimes < maxComparedTimes) && (changedPosition)) {
			for (int i = 0; i < (maxComparedTimes - comparedTimes); i++) {
				changedPosition = false;
				if (srcDatas[i] > srcDatas[i + 1]) {
					swap(srcDatas, i, i + 1);
					changedPosition = true;
				}
			}
			comparedTimes++;
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
