package net.jueb.util4j.beta.disruptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.lmax.disruptor.EventHandler;

public class LongEventHandler implements EventHandler<LongEvent<Runnable>>
{
	private Logger log=LoggerFactory.getLogger(getClass());
	private final String name;
	
	public LongEventHandler(String name) {
		super();
		this.name = name;
	}

	public void onEvent(LongEvent<Runnable> event, long sequence, boolean endOfBatch)
    {
    	log.debug(name+": " + event);
    }
}