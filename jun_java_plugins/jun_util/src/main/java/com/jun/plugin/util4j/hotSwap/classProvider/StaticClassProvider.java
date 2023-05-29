package com.jun.plugin.util4j.hotSwap.classProvider;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 静态类生产
 * @author juebanlin
 */
public class StaticClassProvider implements IClassProvider{

	protected final Logger _log = LoggerFactory.getLogger(this.getClass());

	/**
	 * 是否自动重载变更代码
	 */
	protected volatile boolean autoReload;
	private final ReentrantReadWriteLock rwLock=new ReentrantReadWriteLock();
	private final Set<EventListener> listeners=new HashSet<>();
	private final Set<Class<?>> clazzs=new HashSet<>();
	protected volatile State state = State.ready;

	public StaticClassProvider() {
		
	}

	
	public void registClass(Class<?> clazz)
	{
		rwLock.readLock().lock();
		try {
			clazzs.add(clazz);
		} finally {
			rwLock.readLock().unlock();
		}
	}
	
	public void registClass(Class<?> ...clazz)
	{
		rwLock.readLock().lock();
		try {
			for(Class<?> c:clazz)
			{
				clazzs.add(c);
			}
		} finally {
			rwLock.readLock().unlock();
		}
	}
	
	public void registComplete()
	{
		state=State.loaded;
		for(EventListener listener:listeners)
		{
			try {
				listener.onLoaded();
			} catch (Throwable e) {
			}
		}
	}
	 
	public final State getState() {
		return state;
	}
	
	public ClassLoader getClassLoader()
	{
		rwLock.readLock().lock();
		try {
			return null;
		} finally {
			rwLock.readLock().unlock();
		}
	}
	
	public Set<Class<?>> getLoadedClasses()
	{
		rwLock.readLock().lock();
		try {
			return new HashSet<>(clazzs);
		} finally {
			rwLock.readLock().unlock();
		}
	}

	public final void reload() {
	
	}
	
	public final void addListener(EventListener listener)
	{
		rwLock.writeLock().lock();
		try {
			listeners.add(listener);
		} finally {
			rwLock.writeLock().unlock();
		}
	}
	
	public final void removeListener(EventListener listener)
	{
		rwLock.writeLock().lock();
		try {
			listeners.remove(listener);
		} finally {
			rwLock.writeLock().unlock();
		}
	}
	
	public boolean isAutoReload() {
		return autoReload;
	}

	public void setAutoReload(boolean autoReload) {
		this.autoReload = autoReload;
	}
}
