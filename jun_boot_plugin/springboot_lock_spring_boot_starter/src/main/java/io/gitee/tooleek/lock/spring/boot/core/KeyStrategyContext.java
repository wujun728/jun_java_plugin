package io.gitee.tooleek.lock.spring.boot.core;


import io.gitee.tooleek.lock.spring.boot.core.LockKey.Builder;
import io.gitee.tooleek.lock.spring.boot.core.strategy.KeyStrategy;

public class KeyStrategyContext {
	
	private KeyStrategy keyStrategy;
	

	public KeyStrategyContext(KeyStrategy keyStrategy) {
		this.keyStrategy=keyStrategy;
	}
	
	public Builder generateBuilder() {
		return this.keyStrategy.generateBuilder();
	}
	
}
