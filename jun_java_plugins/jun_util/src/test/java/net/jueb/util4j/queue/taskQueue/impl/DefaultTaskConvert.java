package net.jueb.util4j.queue.taskQueue.impl;

import net.jueb.util4j.queue.taskQueue.Task;
import net.jueb.util4j.queue.taskQueue.TaskConvert;

final public class DefaultTaskConvert implements TaskConvert{

	public Task convert(Runnable task)
	{
		if(task instanceof Task)
		{
			return (Task)task;
		}else
		{
			return new RunnableTaskAdapter(task);
		}
	}
}
