package book.oo.factory;

/**
 * 接口类，完成数字的排序功能
 */
public interface IOrderNumber {
	/**
	 * 对整数按升序排序
	 * @param intArray	待排序的整数数组
	 * @return	排序后的整数数组
	 */
	public int[] orderASC(int[] intArray);
	
	/**	对整数按降序排序	*/
	public int[] orderDESC(int[] intArray);
}
