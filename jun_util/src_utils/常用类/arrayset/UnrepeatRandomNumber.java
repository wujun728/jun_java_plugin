package book.arrayset;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

/**
 * 在指定的范围内，生成不重复的随机数序列
 */
public class UnrepeatRandomNumber {
	private int min;
	private int max;
	public UnrepeatRandomNumber(){
		this.min = 0;
		this.max = 10;
	}
	public UnrepeatRandomNumber(int min, int max){
		this();
		if (max >= min){
			this.min = min;
			this.max = max;
		} else {
			System.out.println("max比min小，按缺省值生成UnrepeatRandomNumber对象！");
		}
	}
	/**
	 * 第一种方法：排除法。随机生成数字，如果是新生成的数字，则放到结果列表种
	 * 否则是已经生成过的，则不加入结果列表，继续随机生成。
	 * @param length	结果列表的长度
	 * @return
	 */
	public Integer[] getRandomMethodA(int length) {
		if (length <= 0) {
			return new Integer[0];
		} else if (length > (this.max - this.min)) {
			System.out.println("结果列表长度不能达到:" + length + ", 结果长度只能是："
					+ (this.max - this.min));
			length = this.max - this.min;
		}
		Random rd = new Random();//用于生成随机结果
		List resultList = new ArrayList();
		while (resultList.size() < length) {
			//将[min, max]区间等价于min + [0, max - min + 1) 
			Integer randnum = new Integer(this.min + rd.nextInt(this.max - this.min + 1));
			if (!resultList.contains(randnum)){
				resultList.add(randnum);
			}
		}
		//使用toArray方法将List转换成对象数组返回
		return (Integer[])resultList.toArray(new Integer[0]);
	}
	
	/**
	 * 第二种方法：筛选法。将所有可能被生成的数字放到一个候选列表中。
	 * 然后生成随机数，作为下标，将候选列表中相应下标的数字放到放到结果列表中，
	 * 同时，把它在候选列表中删除。
	 * @param length	结果列表的长度
	 * @return
	 */
	public Integer[] getRandomMethodB(int length) {
		if (length <= 0) {
			return new Integer[0];
		} else if (length > (this.max - this.min)) {
			System.out.println("结果列表长度不能达到:" + length + ", 结果长度只能是："
					+ (this.max - this.min));
			length = this.max - this.min;
		}
		//初始化候选列表，列表长度为 max -min + 1
		int candidateLength = this.max - this.min + 1;
		List candidateList = new ArrayList();
		for (int i=this.min; i<= this.max; i++){
			candidateList.add(new Integer(i));
		}
		
		Random rd = new Random();//用于生成随机下标
		List resultList = new ArrayList();
		while (resultList.size() < length) {
			//生成下标，在[0,candidateLength)范围内
			int index = rd.nextInt(candidateLength);
			//将候选队列中下标为index的数字对象放入结果队列中
			resultList.add(candidateList.get(index));
			//将下标为index的数字对象从候选队列中删除
			candidateList.remove(index);
			//候选队列长度减去1
			candidateLength --;
		}
		//使用toArray方法将List转换成对象数组返回
		return (Integer[])resultList.toArray(new Integer[0]);
	}
	
	public static void outputArray(Integer[] array){
		if (array != null){
			for (int i=0; i<array.length; i++){
				System.out.print(array[i] + "  ");
			}
		}
		System.out.println();
	}

	public static void main(String[] args) {
		
		UnrepeatRandomNumber test = new UnrepeatRandomNumber(5, 15);
		outputArray(test.getRandomMethodA(8));
		outputArray(test.getRandomMethodB(8));
		//相比之下，第一种方法利用Random对象生成的随机数的次数比较多，可能生成了20次数字，才能找到10个不一样的数字。
		//第二种方法利用Random对象生成的随机数的次数比较少，需要多少个，就生成多少个，保证了每次生成的数字都不重复。
		//也就是说第一种方法在时间花费上更多。但是第二种方法需要初始化一个候选队列，需要更多的空间花费。
	}
}
