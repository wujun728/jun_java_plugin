package com.jun.plugin.demo;

class Lock {
	protected boolean locked; //�������н��֮���ͬ��

	public Lock() {
		locked = false;
	}

	// ͬ������,ÿ�����ȡ����֮ǰҪlocked=false����
	public synchronized void lock() throws InterruptedException {
		while (locked)
			wait(); //�ȴ�locked=false
		locked = true; //����,��ֹ��Ľ��ȡͬһ֧����
	}

	public synchronized void unlock() { //����
		locked = false;
		notify(); //���ѱ�Ľ��
	}
}

class Fork { //Fork��ʾ�Ͳ��õĿ���
	public char id; //ÿ֧���ӵı�ʶ��

	private Lock lock = new Lock(); //lock����ͬ��

	public void pickup() throws InterruptedException {
		lock.lock(); //ȡ���Ӻ����
	}

	public void putdown() throws InterruptedException {
		lock.unlock(); //�ſ��Ӻ����
	}

	public Fork(int j) { //���캯���ÿ֧���ӵı�ʶ��
		Integer i = new Integer(j);

		id = i.toString().charAt(0);
	}
}

//Philosopher�����ѧ��,��Thread������,���Ե�������һ���߳�
class Philosopher extends Thread {
	public char state = 't';
	//state�����ѧ�ҵ�״̬,��ʼ��Ϊ˼��״̬                              

	private Fork L, R; //L,R�ֱ��ʾ���ҿ���

	// ���캯��,����ǰ��ѧ���õ����ҿ��ӱ��
	public Philosopher(Fork left, Fork right) {
		super();
		L = left;
		R = right;
	}

	protected void think() throws InterruptedException {
		sleep((long) (Math.random() * 7.0));
	}
	protected void eat() throws InterruptedException {
		sleep((long) (Math.random() * 7.0));
	}

	// run����ʼһ���߳�,��?ǰ��ѧ�ҵĻ 
	public void run() {
		int i;
		try {
			for (i = 0; i < 100; i++) { //ÿ����ѧ��ѭ��һ�ٴκ����
				state = 't';
				think(); //˼��
				state = 'w';
				sleep(1);
				L.pickup(); //ȡ�����
				state = 'w';
				sleep(1);
				R.pickup(); //ȡ�ҿ���
				state = 'e';
				eat(); //�Ͳ�
				L.putdown(); //���������
				R.putdown(); //�����ҿ���
			}
			state = 'd'; //��ǰ�߳̽���,��ǰ��ѧ��ֹͣһ�л
		} catch (InterruptedException e) {
		}
	}
}

public class Dinner {
	static Fork[] fork = new Fork[5]; //��֧����
	static Philosopher[] philo = new Philosopher[5]; //�����ѧ��

	public static void main(String[] args) {
		int i, j = 0, k = 0;
		boolean goOn;

		for (i = 0; i < 5; i++) { //��ʼ��ÿ֧����
			fork[i] = new Fork(i);
		}
		for (i = 0; i < 4; i++) { //���Ի�ÿ����ѧ��
			philo[i] = new Philosopher(fork[i], fork[(i + 1) % 5]);
		}

		// ���һ����ѧ�����õ�һ֧�����һ֧����
		philo[4] = new Philosopher(fork[0], fork[4]);

		for (i = 0; i < 5; i++) { //��ʼÿ���߳�(��ѧ�ҿ�ʼ�).
			philo[i].start();
		}

		int newPrio = Thread.currentThread().getPriority() + 1;
		Thread.currentThread().setPriority(newPrio);
		goOn = true;
		while (goOn) {
			for (i = 0; i < 5; i++) {
				System.out.print(philo[i].state); //��ӡÿ���߳�(��ѧ��)��״̬
			}
			if (++j % 5 == 0)
				System.out.println();
			else
				System.out.print(' ');

			goOn = false;
			for (i = 0; i < 5; i++) { //��������̶߳�����,��ֹͣѭ��.
				goOn |= philo[i].state != 'd'; //diner[i].state = 'd'��ʾ�߳�i�ѽ���
			}
			try {
				Thread.sleep(51); //�õ�ǰ�߳�����
			} catch (InterruptedException e) {
				return;
			}
		}
	}
}