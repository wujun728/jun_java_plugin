package com.jun.plugin.util4j.hotSwap.classSources;

import java.net.URL;
import java.util.List;

/**
 *  脚本源
 * @author jaci
 */
public interface ClassSource {

	/**
	 * 脚本源事件监听器
	 * @author jaci
	 */
	@FunctionalInterface
	public static interface ClassSourceListener{
		public void onSourcesFind();
	}
	
	public static interface ClassSourceInfo{
		public URL getUrl();
		public List<String> getClassNames();
	}
	
	/**
	 * 主动扫描资源
	 * @param event
	 */
	public void scanClassSources();
	
	public List<ClassSourceInfo> getClassSources();
	
	/**
	 * 添加脚本源监听器
	 * @param listener
	 */
	public void addEventListener(ClassSourceListener listener);
	
	/**
	 * 移除脚本源监听器
	 * @param listener
	 */
	public void removeEventListener(ClassSourceListener listener);
}
