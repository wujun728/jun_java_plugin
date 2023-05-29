package book.oo.sort.impl;

import book.oo.sort.ISortNumber;
/**
 * 采用快速排序法实现数组的排序
 */
public class QuickSort implements ISortNumber {

	public QuickSort(){
	}
	/**
	 * 快速排序法
	 */
	public int[] sortASC(int[] intArray) {
		if (intArray == null){
			return null;
		}
		int[] srcDatas = (int[]) intArray.clone();
		return this.quickSort(srcDatas, 0, srcDatas.length - 1);
	}
	/**
	 * 采用分治递归的方法实现快速排序法
	 * @param srcDatas	待排序的数组
	 * @param first		起始下标
	 * @param last		终止下标
	 * @return
	 */
	private int[] quickSort(int[] srcDatas, int first, int last) {
		if (first < last) {
			int pos = partition(srcDatas, first, last);
			quickSort(srcDatas, first, pos - 1);
			quickSort(srcDatas, pos + 1, last);
		}
		return srcDatas;
	}
	/**
	 * 寻找数组的分治点，
	 * 根据数组的第一个数分治，把比数组第一个数大的往后排，把比数组第一个数小的往前排
	 * @param srcDatas
	 * @param first
	 * @param last
	 * @return	传入数组的第一个数的最终下标
	 */
	private int partition(int[] srcDatas, int first, int last) {
		int temp = srcDatas[first];
		int pos = first;
		for (int i = first + 1; i <= last; i++) {
			if (srcDatas[i] < temp) {
				pos++;
				swap(srcDatas, pos, i);
			}
		}
		swap(srcDatas, first, pos);
		return pos;
	}
	/**	交换数组中下标为src和dest的值*/
	private void swap(int[] data, int src, int dest) {
		int temp = data[src];
		data[src] = data[dest];
		data[dest] = temp;
	}
}
