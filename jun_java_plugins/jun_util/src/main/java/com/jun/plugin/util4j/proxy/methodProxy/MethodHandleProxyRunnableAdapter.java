package com.jun.plugin.util4j.proxy.methodProxy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 方法调用句柄
 * @author juebanlin
 */
public class MethodHandleProxyRunnableAdapter implements Runnable{
	
	private Logger log=LoggerFactory.getLogger(getClass());
	private final MethodHandleProxy handle;
	
	public MethodHandleProxyRunnableAdapter(MethodHandleProxy handle) {
		super();
		this.handle = handle;
	}

	@Override
	public void run() {
		try {
			handle.invoke();
		} catch (Throwable e) {
			log.error(e.getMessage(),e);
		}
	}
}