package org.epibolyteam.frequency;

import java.util.concurrent.atomic.AtomicInteger;

public class LifeAccessFrequency {
	/** Time To Live for frequency*/
	private final int ttlInMillis;
	/**访问频率*/
	private AtomicInteger frequency;
	/**创建时间*/
	private long createTime;
	
	public LifeAccessFrequency(int ttlInMillis, AtomicInteger frequency) {
		this.ttlInMillis = ttlInMillis;
		this.frequency = frequency;
		this.createTime = System.currentTimeMillis();
	}
	
	public int getFrequency() {
		long currentTime = System.currentTimeMillis();
		if(currentTime - createTime > ttlInMillis) {
			frequency.set(1);
			createTime = currentTime;
			return 1;
		}else{
			return frequency.addAndGet(1);
		}
	}
	
}
