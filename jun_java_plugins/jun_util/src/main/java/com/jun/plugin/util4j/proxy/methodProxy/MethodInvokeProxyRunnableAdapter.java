package com.jun.plugin.util4j.proxy.methodProxy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 方法调用句柄
 * @author juebanlin
 */
public class MethodInvokeProxyRunnableAdapter implements Runnable{
	
	private Logger log=LoggerFactory.getLogger(getClass());
	private final MethodInvokeProxy handle;
	
	public MethodInvokeProxyRunnableAdapter(MethodInvokeProxy handle) {
		super();
		this.handle = handle;
	}

	@Override
	public void run() {
		try {
			handle.invoke();
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
}