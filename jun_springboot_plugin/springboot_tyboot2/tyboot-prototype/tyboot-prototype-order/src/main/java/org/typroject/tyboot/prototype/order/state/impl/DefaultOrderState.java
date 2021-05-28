package org.typroject.tyboot.prototype.order.state.impl;


import org.springframework.stereotype.Component;
import org.typroject.tyboot.prototype.order.BaseOrder;
import org.typroject.tyboot.prototype.order.constants.OrderConstants;
import org.typroject.tyboot.prototype.order.state.BaseOrderState;
import org.typroject.tyboot.prototype.order.state.StateHandler;


/**
 * 
 * <pre>
 * 
 *  File: DefaultOrderState.java
 * 
 *  Description:
 *  TODO
 * 
 *  Notes:
 *  DefaultOrderState.java  tyrest\magintursh
 * 
 *  Revision History
 *  &lt;Date&gt;,			&lt;Who&gt;,			&lt;What&gt;
 *  2016年9月29日					magintursh				   Initial.
 *
 * </pre>
 */
@Component(value = "defaultOrderState")
public class DefaultOrderState extends BaseOrderState implements StateHandler{
	
	
	@Override
	public BaseOrder process() throws Exception{
		throw new Exception(OrderConstants.ORDER_STATUS_ERROR);
	}

}

/*
*$Log: av-env.bat,v $
*/