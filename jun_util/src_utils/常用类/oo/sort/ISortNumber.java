package book.oo.sort;
/**
 * 定义数字排序的接口
 */
public interface ISortNumber {
	/**
	 * 对整型数组按升序排序
	 * @param intArray	待排序的整型数组
	 * @return	按升序排序后的数组
	 */
	public int[] sortASC(int[] intArray);
}
