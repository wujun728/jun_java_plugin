package io.gitee.tooleek.lock.spring.boot.core;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.gitee.tooleek.lock.spring.boot.constant.LockCommonConstant;

public class LockKey {
	
	private List<String> keyList=new ArrayList<>();
	private long leaseTime=-1;
	private long waitTime=-1;
	private TimeUnit timeUnit=TimeUnit.SECONDS;
	
	private LockKey(List<String> keyList,long leaseTime,long waitTime,TimeUnit timeUnit) {
		this.keyList=keyList;
		this.leaseTime=leaseTime;
		this.waitTime=waitTime;
		this.timeUnit=timeUnit;
	}
	
	public List<String> getKeyList() {
		return keyList;
	}
	
	public long getLeaseTime() {
		return leaseTime;
	}
	
	public long getWaitTime() {
		return waitTime;
	}
	
	public TimeUnit getTimeUnit() {
		return timeUnit;
	}
	
	public static Builder newBuilder() {
		return new Builder();
	}
	
	public static class Builder {
		
		private List<String> keyList=new ArrayList<>();
		private long leaseTime=-1;
		private long waitTime=-1;
		private TimeUnit timeUnit=TimeUnit.SECONDS;
		
		public Builder appendKey(String key) {
			keyList.add(LockCommonConstant.KEY_PREFIX + key);
			return this;
		}
		
		public Builder leaseTime(long leaseTime) {
			this.leaseTime=leaseTime;
			return this;
		}
		
		public Builder waitTime(long waitTime) {
			this.waitTime=waitTime;
			return this;
		}
		
		public Builder timeUnit(TimeUnit timeUnit) {
			this.timeUnit=timeUnit;
			return this;
		}
		
		public boolean isEmptyKeys() {
			return this.keyList.isEmpty();
		}
		
		public LockKey build() {
			return new LockKey(keyList,leaseTime,waitTime,timeUnit);
		}
		
	}
}
