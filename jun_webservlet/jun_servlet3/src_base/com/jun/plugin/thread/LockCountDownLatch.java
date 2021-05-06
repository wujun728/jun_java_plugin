package com.jun.plugin.thread;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CountDownLatch;

/**
 * 闭锁用于多条件满足的任务等待
 */
public class LockCountDownLatch {

	public static void main(String[] args) throws InterruptedException {
		LockCountDownLatch lock = new LockCountDownLatch();
		lock.goToLunch();
	}

	int count = 5;

	public void goToLunch() throws InterruptedException {

		final CountDownLatch notify = new CountDownLatch(1);
		final CountDownLatch tickets = new CountDownLatch(count);

		Set<Student> students = new HashSet<>();
		for (int i = 0; i < count; i++) {
			students.add(new Student(i + 1));
		}

		for (final Student stu : students) {
			Thread thread = new Thread() {

				@Override
				public void run() {

					try {
						notify.await();
						System.out.println(stu.hashCode() + "接到了通知、出发了");
						long a = System.currentTimeMillis();
						Thread.sleep(stu.minute);
						System.out.println(System.currentTimeMillis() - a);
						System.out.println(stu.hashCode() + "到地方了");
					} catch (InterruptedException ignored) {
					} finally {
						tickets.countDown();
					}

				}

			};
			thread.start();
		}

		notify.countDown();

		System.out.println("像所有同学发送了通知");

		System.out.println(0);

		tickets.await();

		System.out.println("人都到齐了、我们去吃饭吧");

	}
}

class Student {

	public final int minute;

	public Student(int minute) {
		this.minute = minute * 1200;
		System.out.println("我是学生" + this.hashCode() + ",我要" + minute + "分后到食堂");
	}

}
