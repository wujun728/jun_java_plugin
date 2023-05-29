package net.jueb.util4j.beta.disruptor;
import com.lmax.disruptor.dsl.Disruptor;
import com.jun.plugin.util4j.thread.NamedThreadFactory;
import com.lmax.disruptor.RingBuffer;
import java.nio.ByteBuffer;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.commons.lang.math.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LongEventMain
{
    public static void main(String[] args) throws Exception
    {
    	final Logger log=LoggerFactory.getLogger("");
        // Executor that will be used to construct new threads for consumers
        Executor executor = new ThreadPoolExecutor(1,1,0L, TimeUnit.MILLISECONDS,new LinkedBlockingQueue<Runnable>(),new NamedThreadFactory("disruptor"));

        // The factory for the event
        LongEventFactory factory = new LongEventFactory();

        // Specify the size of the ring buffer, must be power of 2.
        int bufferSize = 1024;

        // Construct the Disruptor
        final Disruptor<LongEvent<Runnable>> disruptor = new Disruptor<>(factory, bufferSize, executor);

        //注册消费者
        // Connect the handler
//        disruptor.handleEventsWith(new LongEventHandler("消费者A"),new LongEventHandler("消费者B"));
        disruptor.handleEventsWithWorkerPool(new LongEventWorkHandler("消费者C"),new LongEventWorkHandler("消费者D"));
        // Start the Disruptor, starts all threads running
        disruptor.start();
        final AtomicLong seq=new AtomicLong();
        //生产者1
        new Thread(new Runnable() {
			@Override
			public void run() {
				// Get the ring buffer from the Disruptor to be used for publishing.
				RingBuffer<LongEvent<Runnable>> ringBuffer = disruptor.getRingBuffer();
				LongEventProducer producer = new LongEventProducer(ringBuffer);
		        for (long l = 0; true; l++)
		        {
		            producer.publish(new Task("task:"+seq.incrementAndGet()));
		            try {
						Thread.sleep(RandomUtils.nextInt(1000));
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
		        }
			}
		}).start();
    }
}