package org.smartboot.service.util;

/**
 * 业务回调
 * @author Wujun
 * @version ServiceCallback.java, v 0.1 2015年11月4日 下午3:19:10 Seer Exp. 
 */
public abstract class ServiceCallback {

	/**
	 * 对本次进行数据合法性校验
	 */
	public void doCheck() {
	}

	/**
	 * 操作具体业务
	 */
	public abstract void doOperate();

	public void doAfterOperate() {
	}
}
