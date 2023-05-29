package com.jun.plugin.util4j.queue.queueExecutor.queue;

import java.util.Collection;
import java.util.Queue;

/**
* 具有事件的队列包装器
* @author juebanlin
*/
public abstract class RunnableQueueEventWrapper extends RunnableQueueWrapper{

	public RunnableQueueEventWrapper(Queue<Runnable> queue) {
		super(queue);
	}
	
	@Override
	public final boolean add(Runnable e) {
		onAddBefore();
        boolean bool=super.add(e);
        onAddAfter(bool);
        return bool;
	}
	
	@Override
    public final boolean offer(Runnable e) {
		onAddBefore();
        boolean bool=super.offer(e);
        onAddAfter(bool);
        return bool;
    }
		
	@Override
	public final boolean addAll(Collection<? extends Runnable> c) {
		onAddBefore();
        boolean bool=super.addAll(c);
        onAddAfter(bool);
        return bool;
	}
		
	/**
	* 任务添加之前
	 */
	protected abstract void onAddBefore();
		
	/**
	 * 任务添加之后
	 * @param bool
	 */
	protected abstract void onAddAfter(boolean offeredSucceed);
}