package com.jun.web;


//���߳�ѭ��10�Σ��������߳�ѭ��100�������ֻص����߳�ѭ��10�Σ������ٻص����߳���ѭ��100�����ѭ��50��
public class TestThread2 {
	public static void main(String[] args) {
		final Business business = new Business();
//		new Thread(new Runnable() {
//			@Override
//			public void run() {
//				for (int k = 1; k <= 50; k++) {
//					System.out.println("sub");
//					business.sub(k);
//				}
//			}
//		}).start();
		new Thread() {
			@Override
			public void run() {
				for (int k = 1; k <= 50; k++) {
					System.out.println("sub");
					business.sub(k);
				}
			}
		}.start();
		for (int k = 1; k <= 50; k++) {
			System.out.println("main");
			business.main(k);
		}
	}

}

// class Business {
// public synchronized void sub(int k) {
// for (int i = 1; i <= 10; i++) {
// System.out.println("sub thread sequence " + i + " loop of " + k);
// }
// }
//
// public synchronized void main(int k) {
// for (int i = 1; i <= 100; i++) {
// System.out.println("main thread sequence " + i + " loop of " + k);
// }
// }
// }

class Business {
	// Ĭ�����߳���ִ��
	boolean isShouldSub = true;

	public synchronized void sub(int k) {
		System.out.println("��" + isShouldSub);
		// if (!isShouldSub) {//
		// �˴���while���,��Ϊ���ܳ��ּٻ���,//��while�Ļ����������ж�,������������Ͻ��ͽ�׳
		while (!isShouldSub) {
			try {
				System.out.println("sub�ȴ�");
				this.wait();// this��ʾͬ������������
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		for (int i = 1; i <= 10; i++) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("sub thread sequence " + i + " loop of " + k);
		}
		// ���߳�������,������Ϊfalse
		isShouldSub = false;
		// ���һ������߳�
		this.notify();
	}

	public synchronized void main(int k) {
		System.out.println("��" + isShouldSub);
		// if (isShouldSub) {//
		// �˴���while���,��Ϊ���ܳ��ּٻ���(API�ĵ����н���),//��while�Ļ����������ж�,������������Ͻ��ͽ�׳
		while (isShouldSub) {
			try {
				System.out.println("main�ȴ�");
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		for (int i = 1; i <= 100; i++) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("main thread sequence " + i + " loop of " + k);
		}
		// ���߳�������,������Ϊtrue
		isShouldSub = true;
		// ���һ������߳�
		this.notify();
	}
}
