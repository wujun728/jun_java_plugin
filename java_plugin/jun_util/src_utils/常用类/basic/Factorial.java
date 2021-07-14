package book.basic;

public class Factorial {
	/**
	 * 计算n！的值
	 */
	public long getFactorial(int n) {
		// 因为当n大于17时，n!的值超过了long类型的范围，会出现错误。因此这里限定了n必须小于等于17。
		// 数学上没有负数的阶乘的概念，因此n必须大于等于0。
		if ((n < 0) || (n > 17)) {
			System.err.println("n的值范围必须在区间[0, 17]内！");
			return -1;
		} else if (n == 0) {
			// 0!的值为1
			return 1;
		} else {
			long result = 1;
			for (; n > 0; n--) {
				result *= n;
			}
			return result;
		}
	}
	public static void main(String[] args) {
		Factorial test = new Factorial();
		System.out.println(test.getFactorial(15));//1307674368000
	}
}
