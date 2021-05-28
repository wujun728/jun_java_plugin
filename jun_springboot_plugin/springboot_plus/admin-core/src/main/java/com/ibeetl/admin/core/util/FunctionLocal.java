package com.ibeetl.admin.core.util;

import javax.servlet.http.HttpSession;

/**
 *  用户Controller对应的功能
 *  {@link MVCConf}
 * @author Wujun
 *
 */
public class FunctionLocal {
	
	private FunctionLocal(){
		
	}
	
	  private static final ThreadLocal<String> sessions =
		         new ThreadLocal<String>() {
		             @Override protected String initialValue() {
		                 return null;
		         }
		     };
		     
	 public static String get(){
		 return  sessions.get();
	 }
	 
	 public static void set(String session){
		 sessions.set(session);
	 }
	 
	
}
