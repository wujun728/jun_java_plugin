package org.typroject.tyboot.component.event;

import org.springframework.context.ApplicationEvent;
import org.typroject.tyboot.core.foundation.context.RequestContextModel;

/**
 * <pre>
 * 
 *  Tyrest
 *  File: TyrestEvent.java
 * 
 *  Tyrest, Inc.
 *  Copyright (C): 2015
 * 
 *  Description:系统中的所有业务事件的定义(如分享应用，首次注册登陆等)
 *  TODO
 * 
 *  Notes:
 *  $Id: TyrestEvent.java 31101200-9 2014-10-14 16:43:51Z Tyrest\magintursh $
 * 
 *  Revision History
 *  &lt;Date&gt;,			&lt;Who&gt;,			&lt;What&gt;
 *  2015年6月23日		magintursh		Initial.
 *
 * </pre>
 */
public class RestEvent extends ApplicationEvent
{

	private static final long serialVersionUID = -238780245049547377L;
	
	private String restEventName;

	private RequestContextModel requestContextModel;

	private String methodName;

	private String methodLabel;

	private Object [] params;

	public RestEvent(Object source) {
		super(source);
	}

	public RestEvent(String restEventName, Object [] params, Object eventSource, RequestContextModel contextModel,String methodName,String methodLabel){
		super(eventSource);
		this.restEventName = restEventName;
		this.requestContextModel = contextModel;
		this.params = params;
		this.methodName = methodName;
		this.methodLabel = methodLabel;
	}

	public RequestContextModel getRequestContextModel() {
		return requestContextModel;
	}

	public String getRestEventName()
	{
		return restEventName;
	}

	public Object[] getParams() {
		return params;
	}


	public String getMethodName() {
		return methodName;
	}

    public String getMethodLabel() {
        return methodLabel;
    }
}
