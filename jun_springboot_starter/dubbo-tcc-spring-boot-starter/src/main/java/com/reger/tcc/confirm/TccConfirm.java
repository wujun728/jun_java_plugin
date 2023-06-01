package com.reger.tcc.confirm;

import java.util.Arrays;
import java.util.Set;

import com.alibaba.dubbo.common.utils.ConcurrentHashSet;
import com.reger.tcc.annotation.Commit;

public class TccConfirm {
	private final Commit confirm;
	private final String name;
	private final String rollBackName;
	private final int retry;
	private Set<Class<? extends Throwable>> rollBackThrowable = null;

	public TccConfirm(Commit confirm, String rollBackName) {
		super();
		this.confirm = confirm;
		this.rollBackName = rollBackName;
		if (this.confirm == null) {
			this.retry = 0;
			this.name = null;
		} else {
			this.retry = confirm.retry();
			this.name = confirm.value();
			this.rollBackThrowable=new ConcurrentHashSet<Class<? extends Throwable>>(confirm.noRollbackFor().length);
			this.rollBackThrowable.addAll(Arrays.asList(confirm.noRollbackFor()));
		}
	}

	/**
	 * 判断异常是否需要回滚
	 */
	public boolean isRollBack(Throwable throwable) {
		if (throwable == null||rollBackThrowable==null) {
			return false;
		}
		if (rollBackThrowable.contains(throwable.getClass())) {
			return false;
		}
		return true;
	}
	
	public Commit getConfirm() {
		return confirm;
	}

	public int getRetry() {
		return retry;
	}

	public String getName() {
		return name;
	}

	public String getRollBackName() {
		return rollBackName;
	}

}
