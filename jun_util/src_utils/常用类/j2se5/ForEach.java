package book.j2se5;

import java.util.ArrayList;
import java.util.List;

/**
 * 新的for循环。格式为for(type x: type y)；
 * 表示依次遍历数组或集合y的元素，把元素值赋给x，
 * 注意，
 * (1)只能遍历的取数组元素，不能修改数组的元素，即修改x的值对数组没有影响；
 * (2)遍历是依次，不能跳着遍历。
 */
public class ForEach {
	/**
	 * 对整数数组求和
	 */
	public static long getSum(int[] nums) throws Exception{
		if (nums == null){
			throw new Exception("错误的参数输入，不能为null！");
		}
		long sum = 0;
		// 依次取得nums元素的值并累加
		for (int x : nums){
			sum += x;
		}
		return sum;
	}
	/**
	 * 对整数列表求和
	 * @param nums
	 * @return
	 * @throws Exception
	 */
	public static long getSum(List<Integer> nums) throws Exception{
		if (nums == null){
			throw new Exception("错误的参数输入，不能为null！");
		}
		long sum = 0;
		// 可以跟遍历数组一样的方式遍历列表
		for (int x : nums){
			sum += x;
		}
		return sum;
	}
	/**
	 * 求多维数组的平均值
	 * @param nums
	 * @return
	 * @throws Exception
	 */
	public static int getAvg(int[][] nums) throws Exception{
		if (nums == null){
			throw new Exception("错误的参数输入，不能为null！");
		}
		long sum = 0;
		long size = 0;
		// 对于二维数组，每个数组元素都是一维数组
		for (int[] x : nums){
			// 一维数组中的元素才是数字
			for (int y : x){
				sum += y;
				size ++;
			}
		}
		return (int)(sum/size);
	}

	public static void main(String[] args) throws Exception {
		int[] nums = {456, 23, -739, 163, 390};
		List<Integer> list_I = new ArrayList<Integer>();
		for (int i=0; i<5; i++){
			list_I.add(nums[i]);
		}
		System.out.println(getSum(nums));
		System.out.println(getSum(list_I));
		int[][] numss = {{1,2,3}, {4,5,6}, {7,8,9,10}};
		System.out.println(getAvg(numss));
	}
}
