package cn.com.bluemoon.threads;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 线程工厂
 * @author Wujun
 * @date 2019年5月24日 上午11:20:52
 *
 */
public class UserThreadFactory implements ThreadFactory{

	private final String namePrefix;
	private final AtomicInteger nextId = new AtomicInteger(1);
	
	public UserThreadFactory(String whatFeatureOfGroup) {
		this.namePrefix = "UserThreadFactory's " + whatFeatureOfGroup + "-Worker-";
	}

	@Override
	public Thread newThread(Runnable r) {
		String name = namePrefix + nextId.getAndIncrement();
		Thread thread = new Thread(null, r, name, 0);
		System.out.println(thread.getName());
		return thread;
	}
	
}
