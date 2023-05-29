package net.jueb.util4j.test;

import java.util.Scanner;

import org.jctools.queues.MpscChunkedArrayQueue;
import org.jctools.queues.MpscCompoundQueue;
import org.jctools.queues.MpscGrowableArrayQueue;
import org.jctools.queues.MpscLinkedQueue;
import org.jctools.queues.MpscUnboundedArrayQueue;
import org.jctools.queues.atomic.MpscLinkedAtomicQueue;

public class TestJctools {

	{
		MpscCompoundQueue<Runnable> queue1;
		/**
		 * MPSC数组队列开始在容量和增长相关的初始MaxCapacity块大小。
		 * 仅当当前缓冲区已满且元素不在大小上复制时，队列才会增长，
		 * 取而代之的是将新缓冲区的链接存储在旧缓冲区中，以供消费者遵循.。
		 */
		MpscGrowableArrayQueue<Runnable> queue2=new MpscGrowableArrayQueue<>(10);
		MpscUnboundedArrayQueue<Runnable> queue3=new MpscUnboundedArrayQueue<>(8);
		MpscLinkedAtomicQueue<Runnable> queue4=new MpscLinkedAtomicQueue<>();
//		MpscLinkedQueue<Runnable> queue5=MpscLinkedQueue.newMpscLinkedQueue();
	}
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		sc.nextLine();
		TestJctools t=new TestJctools();
		t.teste11();
		sc.nextLine();
	}
	
	/**
	 * 固定复合数组队列
	 */
	public void teste1()
	{
		MpscCompoundQueue[] qs=new MpscCompoundQueue[65535];
		for(int i=0;i<qs.length;i++)
		{
			qs[i]=new MpscCompoundQueue<>(1024);
		}
	}
	
	public void teste11()
	{
		MpscChunkedArrayQueue[] qs=new MpscChunkedArrayQueue[65535];
		for(int i=0;i<qs.length;i++)
		{
			qs[i]=new MpscChunkedArrayQueue<>(1048576);
		}
	}
	
	/**
	 * 自增长数组队列
	 */
	public void teste2()
	{
		MpscGrowableArrayQueue[] qs=new MpscGrowableArrayQueue[65535];
		for(int i=0;i<qs.length;i++)
		{
			qs[i]=new MpscGrowableArrayQueue<>(8);
		}
	}
	
	/**
	 * 无边界数组队列
	 */
	public void teste3()
	{
		MpscUnboundedArrayQueue[] qs=new MpscUnboundedArrayQueue[65535];
		for(int i=0;i<qs.length;i++)
		{
			qs[i]=new MpscUnboundedArrayQueue<>(8);
		}
	}
	
	/**
	 * 链表队列
	 */
//	public void teste5()
//	{
//		MpscLinkedQueue[] qs=new MpscLinkedQueue[65535];
//		for(int i=0;i<qs.length;i++)
//		{
//			qs[i]=MpscLinkedQueue.newMpscLinkedQueue();
//		}
//	}
	
	/**
	 * 链表原子队列占用内存比MpscLinkedQueue低
	 */
	public void teste4()
	{
		MpscLinkedAtomicQueue[] qs=new MpscLinkedAtomicQueue[65535];
		for(int i=0;i<qs.length;i++)
		{
			qs[i]=new MpscLinkedAtomicQueue<>();
		}
	}
}
