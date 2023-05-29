package net.jueb.util4j.beta.disruptor;

import org.apache.commons.lang.math.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Task implements Runnable{
	private static Logger log=LoggerFactory.getLogger(Task.class);
	private String name;
	public Task(String name) {
		this.name=name;
	}
	@Override
	public void run() {
		long time=RandomUtils.nextInt(5000);
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		log.debug("name="+name+",sleep="+time);
	}
}
