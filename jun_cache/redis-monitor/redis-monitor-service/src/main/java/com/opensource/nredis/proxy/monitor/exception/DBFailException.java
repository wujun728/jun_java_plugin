package com.opensource.nredis.proxy.monitor.exception;
/**
* my exception
*
* @author liubing
* @date 2016/12/22 20:05
* @version v1.0.0
*/
public class DBFailException extends Exception {
    /**
	 * 
	 */
	private static final long serialVersionUID = 7561802528716460870L;

	public DBFailException(){
    super("未知错误");
    }

    public DBFailException(String errorMsg){
    super(errorMsg);
    }

    public DBFailException(String errorMsg, Throwable cause){
    super(errorMsg, cause);
    }

}
