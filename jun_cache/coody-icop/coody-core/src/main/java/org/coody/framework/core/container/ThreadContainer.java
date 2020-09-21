package org.coody.framework.core.container;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("unchecked")
public class ThreadContainer {
	
	private static ThreadLocal<Map<String, Object>> THREAD_LOCAL = new ThreadLocal<Map<String, Object>>();
	


	public static  void clear(){
		THREAD_LOCAL.remove();
	}
	
	public static <T> T get(String fieldName){
		initThreadContainer();
		return (T) THREAD_LOCAL.get().get(fieldName);
	}
	
	public static void initThreadContainer(){
		if(THREAD_LOCAL.get()!=null){
			return ;
		}
		THREAD_LOCAL.set(new HashMap<String, Object>());
	}
	
	public static void set(String fieldName,Object value){
		initThreadContainer();
		THREAD_LOCAL.get().put(fieldName, value);
	}
	
	public static boolean containsKey(String fieldName){
		initThreadContainer();
		return  THREAD_LOCAL.get().containsKey(fieldName);
	}
	
}
