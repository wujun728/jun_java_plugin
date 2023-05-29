package com.jun.plugin.util4j.lock.waiteStrategy;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * YieldingWaitStrategy是可以被用在低延迟系统中的两个策略之一，
 * 这种策略在减低系统延迟的同时也会增加CPU运算量。
 * YieldingWaitStrategy策略会循环等待sequence增加到合适的值。
 * 循环中调用Thread.yield()允许其他准备好的线程执行。
 * 如果需要高性能而且事件消费者线程比逻辑内核少的时候，
 * 推荐使用YieldingWaitStrategy策略。例如：在开启超线程的时候。
 * @author juebanlin
 */
public final class YieldingWaitConditionStrategy implements WaitConditionStrategy
{
	protected final Logger log=LoggerFactory.getLogger(getClass());
    private static final int SPIN_TRIES = 1000;

    @Override
	public <T> T waitFor(WaitCondition<T> waitCondition) throws InterruptedException {
    	int counter = SPIN_TRIES;
    	while (!waitCondition.isComplete())
        {
            counter = applyWaitMethod(counter);
        }
    	return waitCondition.getAttach();
	}
    
    @Override
	public <T> T waitFor(WaitCondition<T> waitCondition, long timeOut, TimeUnit unit) throws InterruptedException {
    	long endTime=System.nanoTime()+unit.toNanos(timeOut);
    	int counter = SPIN_TRIES;
    	while (!waitCondition.isComplete())
        {
    		if(System.nanoTime()>=endTime)
    		{
    			break;
    		}
            counter = applyWaitMethod(counter);//等待
        }
    	return waitCondition.getAttach();
	}
    
    @Override
    public void signalAllWhenBlocking()
    {
    }

	private int applyWaitMethod(int counter)
    {
        if (0 == counter)
        {
            Thread.yield();
        }
        else
        {
            --counter;
        }
        return counter;
    }
}
