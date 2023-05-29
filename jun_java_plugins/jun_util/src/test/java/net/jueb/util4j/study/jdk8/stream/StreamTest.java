package net.jueb.util4j.study.jdk8.stream;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.apache.commons.lang.math.RandomUtils;

public class StreamTest{

	public List<String> list=new ArrayList<>();
	
	
	public Spliterator<String> spliterator() {
        return Spliterators.spliteratorUnknownSize(list.iterator(), 0);
    }
	
	public Stream<String> stream() {
        return StreamSupport.stream(spliterator(), false);
    }

	public Stream<String> parallelStream() {
        return StreamSupport.stream(spliterator(), true);
    }
	
	/**
	 * 当使用顺序方式去遍历时，每个item读完后再读下一个item。
	 * 而使用并行去遍历时，数组会被分成多个段，其中每一个都在不同的线程中处理，然后将结果一起输出。
	 */
	public void testSpeed()
	{
		long t0 = System.nanoTime();
        //初始化一个范围100万整数流,求能被2整除的数字，toArray()是终点方法
        int a[]=IntStream.range(0, 1_000_000).filter(p -> p % 2==0).toArray();
        long t1 = System.nanoTime();
        //和上面功能一样，这里是用并行流来计算
        int b[]=IntStream.range(0, 1_000_000).parallel().filter(p -> p % 2==0).toArray();
        long t2 = System.nanoTime();
        //我本机的结果是serial: 0.06s, parallel 0.02s，证明并行流确实比顺序流快
        System.out.printf("serial: %.2fs, parallel %.2fs%n", (t1 - t0) * 1e-9, (t2 - t1) * 1e-9);
	}
	
	
	public int[] buildArray(int len)
	{
		int[] array=new int[len];
		for(int i=0;i<array.length;i++)
		{
			array[i]=RandomUtils.nextInt(10000);
		}
		return array;
	}
	
	public void blocking(long timeMils)
	{
		try {
			Thread.sleep(timeMils);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * ForkJoinPool线程池实现
	 */
	public void testSpeed2()
	{
		List<Integer> list1=new ArrayList<>();
		List<Integer> list2=new ArrayList<>();
		for(int i=0;i<10;i++)
		{//
			list1.add(i);
			list2.add(i);
		}
		long t0 = System.nanoTime();
		long count1=list1.stream().filter(a-> {blocking(100);return true;}).count();
        long t1 = System.nanoTime();
        long count2=list2.stream().parallel().filter(a-> {blocking(100);return true;}).count();
        long t2 = System.nanoTime();
        System.out.println("count1="+count1+",count2="+count2);
        System.out.printf("serial: %.2fs, parallel %.2fs%n", (t1 - t0) * 1e-9, (t2 - t1) * 1e-9);
        System.out.printf("serial: %d, parallel %d%n",(t1 - t0), (t2 - t1));
        System.out.printf("serial: %d, parallel %d%n",
        		TimeUnit.NANOSECONDS.toMillis((t1 - t0)), 
        		TimeUnit.NANOSECONDS.toMillis((t2 - t1)));
	}
	public static void main(String[] args) {
		Scanner sc=new Scanner(System.in);
		sc.nextLine();
		new StreamTest().testSpeed2();
		sc.nextLine();
	}
}
