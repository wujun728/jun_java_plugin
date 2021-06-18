package book.arrayset;

import java.util.Arrays;
/**
 * 求指定范围内的质数
 */
public class PrimeNumber {
	/**
	 * 显示range范围内的质数
	 * @param range
	 */
	public void showPrimeNumber(int range){
		boolean[] primes = this.sieve(range);
		int number = 0;
		if (primes != null){
			int size = primes.length;
			System.out.println("范围在" + range + "内的质数个数有:");
			for (int i=1; i<size; i++){
				if (primes[i]){
					System.out.print(i + "  ");
					//每输出10个质数换行
					//number先加1，再跟10做模运算，如果余数为0，则换行
					if (++number % 10 == 0){
						System.out.println();
					}
				}
			}
			System.out.println();
		} 
		System.out.println("一共有" + number + "个");
	}
	/**
	 * 筛选法求质数
	 * @param range
	 * @return
	 */
	private boolean[] sieve(int range){
		if (range <= 0){
			System.out.println("求质数的范围range必须大于0！");
			return null;
		} 
		//用一个布尔数组标示是否为质数，如果下标值为质数，那么该下标值对应的数组元素的值为true。
		//如2是质数，isPrime[2] = true
		//因为数组是下标是从0开始的，所以这里新建的数组大小为range+1
		boolean[] isPrime = new boolean[range + 1];
		//1不是质数
		isPrime[1] = false;
		//用Arrays的fill方法将数组下标从2到range+1之间的元素的值都赋为true
		Arrays.fill(isPrime, 2, range+1, true);
		//上面一句代码等价于下面被注释的代码
//		for (int i=2; i< range+1; i++){
//			isPrime[i] = true;
//		}
		//Math的sqrt方法用于求开方
		int n = (int)Math.sqrt(range);
		for (int i=1; i<=n; i++){
			if (isPrime[i]){
				//如果i是质数，那么i的倍数不是质数
				for (int j=2 * i; j<=range; j+=i){
					isPrime[j] = false;
				}
			}
		}
		return isPrime;
	}   
	public static void main(String[] args) {
		int range = 200;
		PrimeNumber test = new PrimeNumber();
		test.showPrimeNumber(range);
	}
}
