package com.jun.plugin.util4j.hotSwap.classProvider;

import java.util.Set;

/**
 * 类提供者
 * @author juebanlin
 */
public interface IClassProvider {
	
	@FunctionalInterface
	public static interface EventListener{
		/**
		 * 加载完成
		 */
		public void onLoaded();
	}

	public static enum State {
		/**
		 * 脚本未加载
		 */
		ready,
		/**
		 * 脚本加载中
		 */
		loading,
		/**
		 * 脚本加载完成
		 */
		loaded,
	}
	
	public State getState();
	
	public ClassLoader getClassLoader();
	
	public Set<Class<?>> getLoadedClasses();

	public void reload();
	
	public void addListener(EventListener listener);
	
	public void removeListener(EventListener listener);
	
	public boolean isAutoReload();
	
	public void setAutoReload(boolean autoReload);
}
