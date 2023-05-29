package book.arrayset;

/**
 * 动态调整数组的长度
 */
public class AdjustArrayLength {
	private static int DEFAULT_LENGTH = 10; 
	
	public static Integer[] increase(Integer[] src){
		return increase(src, DEFAULT_LENGTH);
	}
	public static Integer[] increase(Integer[] src, int length){
		if (src == null){
			return null;
		}
		//新建一个数组，长度为旧数组的长度加上length
		Integer[] result = new Integer[src.length + length];
		//新数组的前面部分的值与旧数组的值一样
		//这是一种常用的拷贝数组的方法
		System.arraycopy(src, 0, result, 0, src.length);
		return result;
	}
	
	public static void main(String[] args) {
		Integer[] array = new Integer[10];
		for (int i=0; i<10; i++){
			array[i] = new Integer(i);
		}
		//增加数组的长度
		Integer[] newArray = AdjustArrayLength.increase(array);
		//添加数据
		newArray[10] = new Integer(11);
	}
}
