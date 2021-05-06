package com.jun.web.biz.test;


public class ThreadDemo extends Thread {

	// ��дrun����
	public void run() {
		for (int i = 0; i < 5; i++)
			compute();
	}

	// main�����������������̺߳�ԭ���������ĳ�ʼ�̼߳���һ�����һ��ִ��
	public static void main(String[] args) {
		// ������һ���߳�
		ThreadDemo thread1 = new ThreadDemo();

		// �����ڶ����߳�
		Thread thread2 = new Thread(new Runnable() {
			public void run() {
				for (int i = 0; i < 5; i++)
					compute();
			}
		});
		// �����������̵߳�����
		if (args.length >= 1)
			thread1.setPriority(Integer.parseInt(args[0]));
		if (args.length >= 2)
			thread2.setPriority(Integer.parseInt(args[1]));
		// �����߳�
		thread1.start();
		thread2.start();
		// ���main()��������Java����������ĳ�ʼ�߳������е�
		for (int i = 0; i < 5; i++)
			compute();
	}

	// ThreadLocal������һ������ֵ����get()��set()�ģ�����ά����ͬ���̵߳Ĳ�ͬ����ֵ 
	// ���ڼ�¼ÿ���̵߳���compute�����Ĵ���
	static ThreadLocal numcalls = new ThreadLocal();

	// ���̵߳��õķ���
	static synchronized void compute() {
		// ���㱻��ǰ�̵߳��õĴ���
		Integer n = (Integer) numcalls.get();
		if (n == null)
			n = new Integer(1);
		else
			n = new Integer(n.intValue() + 1);
		numcalls.set(n);

		// ��ʾ�̵߳���ƺ���ĵ��ô���
		System.out.println(Thread.currentThread().getName() + ": " + n);

		// ģ����Ҫ����������߳�
		for (int i = 0, j = 0; i < 1000000; i++)
			j += i;
		try {
			// �߳�����һ��ʱ��
			Thread.sleep((int) (Math.random() * 100 + 1));
		} catch (InterruptedException e) {
		}

		// ��ǰ�߳���ͣ�����������߳�ִ�У���˲����м���״̬���߳�
		Thread.yield();
	}
}
