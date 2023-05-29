package com.jun.plugin.util4j.hotSwap.classProvider;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jun.plugin.util4j.hotSwap.classSources.ClassSource;
import com.jun.plugin.util4j.hotSwap.classSources.ClassSource.ClassSourceInfo;

/**
 * 动态类生产
 * @author juebanlin
 */
public class DynamicClassProvider implements IClassProvider{

	protected final Logger _log = LoggerFactory.getLogger(this.getClass());

	/**
	 * 类资源
	 */
	protected final ClassSource classSource;
	/**
	 * 是否自动重载变更代码
	 */
	protected volatile boolean autoReload;
	private final ReentrantReadWriteLock rwLock=new ReentrantReadWriteLock();
	private final Set<EventListener> listeners=new HashSet<>();
	
	private ProviderClassLoader classLoader;
	private ClassLoader parentClassLoader; 
	
	protected volatile State state = State.ready;

	public DynamicClassProvider(ClassSource classSource) {
		this(classSource, true);
	}
	
	public DynamicClassProvider(ClassSource classSource,boolean autoReload) {
		this(classSource, autoReload,Thread.currentThread().getContextClassLoader());
	}
	
	public DynamicClassProvider(ClassSource classSource,ClassLoader parentClassLoader) {
		this(classSource, true,parentClassLoader);
	}

	public DynamicClassProvider(ClassSource classSource, boolean autoReload,ClassLoader parentClassLoader) {
		this.classSource = classSource;
		this.autoReload = autoReload;
		this.parentClassLoader=parentClassLoader;
		init();
	}
	
	private boolean disableReload;
	
	private void init() {
		try {
			loadClasses();//主动加载一次
			classSource.addEventListener(this::onClassSourceScaned);//添加被动加载监听器
		} catch (Exception e) {
			_log.error(e.getMessage(), e);
		}
	}

	protected void onClassSourceScaned()
	{
		if(isAutoReload())
		{
			reload();
		}
	}
	
	/**
	 * 加载所有的脚本类
	 * @throws Exception
	 */
	protected final void loadClasses() throws Exception 
	{
		if (state == State.loading) 
		{
			return;
		}
		rwLock.writeLock().lock();
		boolean success=false;
		try {
			state = State.loading;
			ProviderClassLoader oldClassLoader=this.classLoader;
			ProviderClassLoader newClassLoader = loadClasses(classSource);
			newClassLoader.close();//关闭资源文件引用
			this.classLoader = newClassLoader;
			success=true;
			if(oldClassLoader!=null)
			{
				oldClassLoader.getAllClass().clear();
			}
		} finally {
			state = State.loaded;
			rwLock.writeLock().unlock();
		}
		if(success)
		{
			onLoaded();
			for(EventListener listener:listeners)
			{
				try {
					listener.onLoaded();
				} catch (Throwable e) {
				}
			}
		}
	}
	
	protected class ProviderClassLoader extends DynamicClassLoader
	{
		protected ProviderClassLoader() {
			super();
		}

		protected ProviderClassLoader(ClassLoader parent) {
			super(parent);
		}

		private final Set<Class<?>> allClass= new HashSet<>();

		public Set<Class<?>> getAllClass() {
			return allClass;
		}
	}
	
	/**
	 * 使用loader加载所有class
	 * @return
	 * @throws Exception
	 */
	private ProviderClassLoader loadClasses(ClassSource soruce) throws Exception 
	{
		ProviderClassLoader loader=new ProviderClassLoader(parentClassLoader);
		Set<Class<?>> allClass = loader.getAllClass();
		List<ClassSourceInfo> sources=soruce.getClassSources();
		if(sources.isEmpty())
		{
			return loader;
		}
		for(ClassSourceInfo cs:sources)
		{
			loader.addURL(cs.getUrl());
			for(String className:cs.getClassNames())
			{
				Class<?> clazz=loader.loadClass(className);
				if(clazz!=null)
				{
					allClass.add(clazz);
				}
			}
		}
		_log.debug("classloader init complete,find Class:"+allClass.size());
		return loader;
	}

	public final State getState() {
		return state;
	}
	
	public ClassLoader getClassLoader()
	{
		rwLock.readLock().lock();
		try {
			return classLoader;
		} finally {
			rwLock.readLock().unlock();
		}
	}
	
	public Set<Class<?>> getLoadedClasses()
	{
		rwLock.readLock().lock();
		try {
			return new HashSet<>(classLoader.getAllClass());
		} finally {
			rwLock.readLock().unlock();
		}
	}

	public final void reload() {
		if(disableReload)
		{//脚本源已经删除
			_log.error("disableReload="+disableReload);
			return ;
		}
		try {
			loadClasses();
		} catch (Throwable e) {
			_log.error(e.getMessage(), e);
		}
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
	
	protected void onLoaded()
	{
		
	}
}
