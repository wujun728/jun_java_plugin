package net.jueb.util4j.test;

import java.util.Scanner;

import com.jun.plugin.util4j.queue.queueExecutor.groupExecutor.QueueGroupExecutor;
import com.jun.plugin.util4j.queue.queueExecutor.groupExecutor.impl.DefaultQueueGroupExecutor;

public class TestQueue {

	public static void main(String[] args) {
		QueueGroupExecutor qe=new DefaultQueueGroupExecutor(2,8);//线程池范围
		qe.execute((short) 1,new Runnable() {
			
			@Override
			public void run() {
				//桌子1的逻辑
				System.out.println("1");
				try {
					Thread.sleep(100000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println("2");
			}
		});
		qe.execute((short) 2,new Runnable() {
					
					@Override
					public void run() {
						//桌子2的逻辑
						System.out.println("3");
						try {
							Thread.sleep(100000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						System.out.println("4");
					}
				});
		new Scanner(System.in).nextLine();
	}
}
