package net.jueb.util4j.queue.taskQueue.impl;

import net.jueb.util4j.queue.taskQueue.Task;

class RunnableTaskAdapter implements Task{

	private final Runnable runnable;
	
	public RunnableTaskAdapter(Runnable runnable) {
		if(runnable==null)
		{
			throw new RuntimeException("runnable is null");
		}
		this.runnable=runnable;
	}
	
	@Override
	public void run() {
		runnable.run();
	}

	public Runnable getRunnable() {
		return runnable;
	}

	@Override
	public String name() {
		return runnable.getClass().toString();
	}

	@Override
	public String toString() {
		return "RunnableTaskAdapter [runnable=" + runnable + "]";
	}
}
