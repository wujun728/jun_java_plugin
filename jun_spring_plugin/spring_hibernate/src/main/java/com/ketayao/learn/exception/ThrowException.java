/**
 * <pre>
 * Copyright:		Copyright(C) 2011-2012, ketayao.com
 * Filename:		com.ketayao.learn.exception.ThrowException.java
 * Class:			ThrowException
 * Date:			2012-12-25
 * Author:			<a href="mailto:ketayao@gmail.com">ketayao</a>
 * Version          1.1.0
 * Description:		
 *
 * </pre>
 **/
 
package com.ketayao.learn.exception;

/** 
 * 	
 * @author Wujun
 * Version  1.1.0
 * @since   2012-12-25 下午7:31:21 
 */

public class ThrowException {
	
	public String t1() {
		throw new IllegalAccessError("sdf");
	}
	
	public String t2() throws Exception {
		throw new Exception("sdf---");
	}

	/**  
	 * 描述
	 * @param args  
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
