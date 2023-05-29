package com.jun.plugin.util4j.hotSwap.classFactory.v1;

import java.net.URL;
import java.util.List;
import java.util.Set;

/**
 *  脚本源
 * @author jaci
 */
public interface ScriptSource {

	/**
	 * 脚本源事件监听器
	 * @author jaci
	 */
	@FunctionalInterface
	public static interface ScriptSourceEventListener{
		public void onEvent(ScriptSourceEvent event);
	}
	/**
	 * jdk8非匿名类监听器实现
	 */
	ScriptSourceEventListener jdk8ListenerDemo=event->{
		switch (event) {
		case Delete:
			break;
		case Change:
			break;
		default:
			break;
		}
	};
	/**
	 * 脚本源事件
	 * @author jaci
	 */
	public static enum ScriptSourceEvent{
		/**
		 * 脚本源删除
		 */
		Delete,
		/**
		 * 脚本源改变
		 */
		Change;
	}
	
	/**
	 * URL路径的.class文件,class可以是本地文件,可以是网络文件
	 * @author jaci
	 */
	public static interface URLClassFile
	{
		/**
		 * 类名 com.xx.xxxx
		 * @return
		 */
		public String getClassName();
		/**
		 * d:/Test.class or http://www.xx.com/Test.class
		 * @return
		 */
		public URL getURL();
	}
	
	/**
	 * 目录中的classFile
	 * rootDir=d:/
	 * d:Test1.class and d:/net/jueb/test/Test2.class and d:/net/jueb/core/Test3.class
	 * @author jaci
	 */
	public static interface DirClassFile
	{
		/**
		 * 类名 com.xx.xxxx
		 * @return
		 */
		public List<String> getClassNames();
		/**
		 * d:/net/jueb/test/
		 * @return
		 */
		public URL getRootDir();
	}
	
	/**
	 * 添加脚本源监听器
	 * for jdk8 ==> addEventListener(event->{});
	 * @param listener
	 */
	public void addEventListener(ScriptSourceEventListener listener);
	
	/**
	 * 移除脚本源监听器
	 * @param listener
	 */
	default void removeEventListener(ScriptSourceEventListener listener){
		getEventListeners().remove(listener);
	}
	
	/**
	 * 获取所有脚本源监听器
	 * @param listener
	 */
	public Set<ScriptSourceEventListener> getEventListeners();
	
	/**
	 * 主动抛出脚本源事件
	 * @param event
	 */
	public void throwEvent(ScriptSourceEvent event);
	
	/**
	 * 获取所有的jar包路径
	 * @return
	 */
	public List<URL> getJars();
	
	/**
	 * 获取任意位置的单个class文件
	 * @return
	 */
	public List<URLClassFile> getUrlClassFiles();
	
	/**
	 * 获取目录下面以及子目录的所有class文件
	 * @return
	 */
	public List<DirClassFile> getDirClassFiles();
}
