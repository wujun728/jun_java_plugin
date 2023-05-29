package net.jueb.util4j.queue.taskQueue;

/**
 * 任务转换器,如果以后task有扩展特性,需要设置默认实现
 * @author juebanlin
 */
public interface TaskConvert {
	
	public Task convert(Runnable runnable);
}
