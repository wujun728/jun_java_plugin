package com.jun.plugin.util4j.common.game;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractService implements IService{
	protected final Logger log = LoggerFactory.getLogger(getClass());
	private  class ThreadHolder implements Runnable 
	{
		private volatile CountDownLatch cd;
		@Override
		public void run() {
			cd=new CountDownLatch(1);
			try {
				cd.await();
			} catch (Throwable e) {
				log.error(e.getMessage(),e);
			}
		}
		
		public void exit()
		{
			if(cd!=null && cd.getCount()>0)
			{
				cd.countDown();
			}
		}
	}
	
	{
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
			@Override
			public void run() {
				log.info("jvm进程关闭");
				doJvmClose();
			}
		}));
	}
	
	/**
	 * 执行启动服务逻辑
	 */
	protected abstract void doStart()throws Throwable;
	/**
	 * 执行关闭服务逻辑
	 */
	protected abstract void doClose()throws Throwable;
	/**
	 * 是否后台
	 */
	protected boolean daemon;
	
	protected boolean isDaemon() {
		return daemon;
	}
	protected void setDaemon(boolean daemon) {
		this.daemon = daemon;
	}
	/**
	 * 非后台进程防止jvm退出
	 */
	private final ThreadHolder threadHolder=new ThreadHolder();
	
	protected void daemonInit()
	{
		Thread t=new Thread(threadHolder);
		t.setDaemon(daemon);
		t.setName("GameService-ThreadHolder");
		t.start();
	}
	
	protected void daemonStop()
	{
		threadHolder.exit();
	}
	
	public void startService(){
		switch (getState()) {
		case Stoped:
			setState(ServiceState.Starting);
			try {
				doStart();
				daemonInit();
				setState(ServiceState.Active);
			} catch (Throwable e) {
				setState(ServiceState.Stoped);
				log.error(e.getMessage(),e);
			}
			break;
		case Starting:
		case Active:
			break;
		case Stoping:
			break;
		default:
			break;
		}
	}
	
	@Override
	public void closeService() {
		switch (getState()) {
		case Stoped:
			break;
		case Starting:
		case Active:
			setState(ServiceState.Stoping);
			try {
				doClose();
				daemonStop();
				setState(ServiceState.Stoped);
			} catch (Throwable e) {
				setState(ServiceState.Active);
				log.error(e.getMessage(),e);
			}
			break;
		case Stoping:
			break;
		default:
			break;
		}
	}
	
	@Override
	public ServiceState getState() {
		return state;
	}
	
	private ServiceState state=ServiceState.Stoped;
	
	private void setState(ServiceState state)
	{
		this.state=state;
	}
	
	private final Map<String,Object> attributes=new HashMap<String,Object>();
	public boolean hasAttribute(String key) {
		return attributes.containsKey(key);
	}

	public void setAttribute(String key, Object value) {
		attributes.put(key, value);
	}

	public Object getAttribute(String key) {
		return attributes.get(key);
	}

	public Object removeAttribute(String key) {
		return attributes.remove(key);
	}

	public void clearAttributes() {
		attributes.clear();
	}
	
	/**
	 * 执行jvm关闭
	 */
	protected void doJvmClose()
	{
		closeService();
	}
}
