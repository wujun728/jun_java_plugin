package net.jueb.util4j.beta.disruptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lmax.disruptor.WorkHandler;

public class LongEventWorkHandler implements WorkHandler<LongEvent<Runnable>>
{
	private Logger log=LoggerFactory.getLogger(getClass());
	private final String name;
	
	public LongEventWorkHandler(String name) {
		super();
		this.name = name;
	}

	@Override
	public void onEvent(LongEvent<Runnable> event) throws Exception {
		event.getValue().run();
	}
}