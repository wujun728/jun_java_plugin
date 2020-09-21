package com.baijob.commonTools.thread;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 同步队列
 * 
 * @author Luxiaolei
 * 
 * @param <E>
 */
public class SyncQueue<E> {
	private static Logger logger = LoggerFactory.getLogger(SyncQueue.class);

	/*--------------------------私有属性 start-------------------------------*/
	/** 队列条目 */
	private final E[] items;
	/** 获取对象的游标 */
	private int takeIndex;
	/** 加入对象的游标 */
	private int putIndex;
	/** 条目数目 */
	private int count;
	/** 队列结束标记 */
	private boolean isClosed;

	/** 主锁 */
	private final ReentrantLock lock;
	/** 入队列的条件锁 */
	private final Condition putCondition;
	/** 出队列的条件锁 */
	private final Condition takeCondition;

	/*--------------------------私有属性 end-------------------------------*/

	/*--------------------------构造器 start-------------------------------*/
	@SuppressWarnings("unchecked")
	public SyncQueue(int capacity) {
		this.items = (E[]) new Object[capacity];
		lock = new ReentrantLock(false);
		takeCondition = lock.newCondition();
		putCondition = lock.newCondition();
	}

	/*--------------------------构造器 end-------------------------------*/

	/*--------------------------公有方法 start-------------------------------*/
	/**
	 * 入队列。若队列已满，则阻塞线程
	 * 
	 * @param item 项
	 * @return 是否成功插入
	 * @throws InterruptedException
	 * @throws NullPointerException item为空时，抛出此异常
	 */
	public boolean put(E item) throws InterruptedException {
		if (item == null)
			throw new NullPointerException();
		if (isClosed) {
			logger.warn("队列已经关闭，入队列失败！");
			return false;
		}
		lock.lockInterruptibly();
		try {
			try {
				while (count == items.length) {
					putCondition.await();
				}
			} catch (InterruptedException ie) {
				putCondition.signal();
				throw ie;
			}
			insert(item);
		} finally {
			lock.unlock();
		}
		return true;
	}

	/**
	 * 出队列。若队列空，阻塞队列<br/>
	 * 如果队列已经关闭，只有队列为空时才返回null
	 * @return 项
	 * @throws InterruptedException
	 */
	public E take() throws InterruptedException {
		lock.lockInterruptibly();
		try {
			try {
				while (count == 0) {
					if (isClosed)
						return null;
					takeCondition.await();
				}
			} catch (InterruptedException ie) {
				takeCondition.signal();
				throw ie;
			}
			return extract();
		} finally {
			lock.unlock();
		}
	}

	/**
	 * 队列中的项数
	 * 
	 * @return 项数
	 */
	public int size() {
		lock.lock();
		try {
			return count;
		} finally {
			lock.unlock();
		}
	}

	/**
	 * 清空队列<br/>
	 * 清空队列后，会重新唤醒所有阻塞的put线程
	 */
	public void clear() {
		lock.lock();
		try {
			int i = takeIndex;
			int k = count;
			while (k-- > 0) {
				items[i] = null;
				i = inc(i);
			}
			count = 0;
			putIndex = 0;
			takeIndex = 0;
			isClosed = false;
			putCondition.signalAll();
		} finally {
			lock.unlock();
		}
	}

	/**
	 * 唤醒所有出队列线程<br/>
	 * 此方法多用于当队列关闭且队列中数据已空时，唤醒被阻塞的出队列线程，使其退出或执行其他动作
	 */
	public void signalAllTake() {
		lock.lock();
		try {
			takeCondition.signalAll();
		} finally {
			lock.unlock();
		}
	}
	
	/**
	 * 唤醒所有入队列线程<br/>
	 * 此方法多用于队列关闭且队列已满，而再没有任何线程调用take方法时，唤醒阻塞的入队列线程。
	 */
	public void signalAllput() {
		lock.lock();
		try {
			takeCondition.signalAll();
		} finally {
			lock.unlock();
		}
	}

	/**
	 * 关闭队列<br/>
	 * 关闭队列后，仍然可以出队列直到队列为空，但是无法将数据入队列。当队列数据为空后，出队列数据为null<br/>
	 * 当关闭队列后，关闭标志将一直存在，除非执行clear()方法重置队列。
	 */
	public void close() {
		this.isClosed = true;
	}

	/**
	 * 队列是否已经关闭
	 * @return 是否已经关闭
	 */
	public boolean isClosed() {
		return this.isClosed;
	}

	/*--------------------------公有方法 end-------------------------------*/

	/*--------------------------私有方法 start-------------------------------*/
	/**
	 * 循环队列增，若增加值超出数组范围，则返回到第一个项
	 * 
	 * @param index 起始的位置
	 */
	private final int inc(int index) {
		return (++index == items.length) ? 0 : index;
	}

	/**
	 * 插入条目（项）
	 * @param item 项
	 */
	private void insert(E item) {
		items[putIndex] = item;
		putIndex = inc(putIndex);
		++count;
		// 插入后唤醒等待take的线程
		takeCondition.signal();
	}

	/**
	 * 提取条目（项）
	 * @return 项
	 */
	private E extract() {
		if (count == 0)
			return null;

		E x = items[takeIndex];
		items[takeIndex] = null;
		takeIndex = inc(takeIndex);
		--count;
		// 提取后唤醒等待put的线程
		putCondition.signal();
		return x;
	}
	/*--------------------------私有方法 end-------------------------------*/
}
