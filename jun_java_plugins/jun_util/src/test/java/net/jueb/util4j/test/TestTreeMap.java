package net.jueb.util4j.test;

import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.google.common.util.concurrent.RateLimiter;
import com.jun.plugin.util4j.cache.map.btree.BTreeMap;
import com.jun.plugin.util4j.thread.NamedThreadFactory;

public class TestTreeMap {

	final Logger log=LoggerFactory.getLogger(getClass());
	Map<String,Integer> map=new BTreeMap<String,Integer>();
	
	int testRange=1000000;
	AtomicLong writeCount=new AtomicLong();//写入次数
	volatile long maxWriteTime=0;
	volatile long totalWriteTime=0;
	
	AtomicLong readCount=new AtomicLong();//读取次数
	volatile long maxReadTime=0;
	volatile long totalReadTime=0;
	
	RateLimiter writeLimiter = RateLimiter.create(10000);
	RateLimiter readLimiter = RateLimiter.create(100000);
	
	/**
	 * 写入测试
	 */
	public void writeTest()
	{
		Random ran=new Random();
		long time=System.currentTimeMillis();
		for(;;)
		{
			writeLimiter.acquire();
			int value=ran.nextInt(testRange);
			String key="key_"+value;
			if(get(key)==null)
			{
				time=System.currentTimeMillis();
				map.put(key, value);
				time=System.currentTimeMillis()-time;
				if(time>maxWriteTime)
				{
					maxWriteTime=time;
				}
				totalWriteTime+=time;
				writeCount.incrementAndGet();
			}
		}
	}
	
	public Integer get(String key)
	{
		long time=System.currentTimeMillis();
		Integer value=map.get(key);
		time=System.currentTimeMillis()-time;
		if(time>maxReadTime)
		{
			maxReadTime=time;
		}
		totalReadTime+=time;
		readCount.incrementAndGet();
		return value;
	}
	
	/**
	 * 读取测试
	 */
	public void readTest()
	{
		Random ran=new Random();
		for(;;)
		{
			readLimiter.acquire();
			int value=ran.nextInt(testRange);
			String key="key_"+value;
			get(key);
		}
	}
	
	public void printInfo()
	{
		try {
			String headInfo="map大小:"+map.size();
			String writeInfo="写入次数:"+writeCount.get()+",最大耗时:"+maxWriteTime+",累计耗时:"+totalWriteTime;
			String readInfo="读取次数:"+readCount.get()+",最大耗时:"+maxReadTime+",累计耗时:"+totalReadTime;
			log.debug("\n"+headInfo+"\n"+writeInfo+"\n"+readInfo+"\n");
		} catch (Throwable e) {
			log.error(e.getMessage(),e);
		}
	}
	
	public void runTest()
	{
		ScheduledExecutorService s=new ScheduledThreadPoolExecutor(2, new NamedThreadFactory("Scheduled"));
		ExecutorService es=Executors.newCachedThreadPool();
		es.execute(this::writeTest);
		es.execute(this::writeTest);
		es.execute(this::writeTest);
		es.execute(this::writeTest);
		es.execute(this::readTest);
		es.execute(this::readTest);
		es.execute(this::readTest);
		es.execute(this::readTest);
		es.execute(this::readTest);
		es.execute(this::readTest);
		s.scheduleAtFixedRate(this::printInfo,5, 5, TimeUnit.SECONDS);
	}
	public static void main(String[] args) {
		TestTreeMap t=new TestTreeMap();
		t.runTest();
		Scanner sc=new Scanner(System.in);
		sc.nextLine();
		sc.close();
	}
}
