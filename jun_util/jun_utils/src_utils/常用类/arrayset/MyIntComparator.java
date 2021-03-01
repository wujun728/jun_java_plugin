package book.arrayset;

import java.util.Comparator;

/**
 * 整数比较器，将整数按降序排列
 */
class MyIntComparator implements Comparator{

	/**
	 * o1比o2大，返回-1；o1比o2小，返回1。
	 */
	public int compare(Object o1, Object o2) {
		int i1 = ((Integer)o1).intValue();
		int i2 = ((Integer)o2).intValue();
		if (i1 < i2){
			return 1;
		}
		if (i1 > i2){
			return -1;
		}
		return 0;
	}
}
