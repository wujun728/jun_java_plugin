package org.typroject.tyboot.prototype.order;

/**
 * 
 * <pre>
 * 
 *  File: OrderType.java
 * 
 *  Description:
 *  TODO
 * 
 *  Notes:
 *  OrderType.java  tyrest\magintursh
 * 
 *  Revision History
 *  &lt;Date&gt;,			&lt;Who&gt;,			&lt;What&gt;
 *  2016年9月29日					magintursh				   Initial.
 *
 * </pre>
 */
public interface OrderType
{
	/**
	 * 
	 * 订单类型标识字符串
	 *
	 * @return
	 */
	String getTypeCode();

	/**
	 * 
	 * 解析名称（一般为中文解释字符穿）
	 *
	 * @return
	 */
	String getTypeName();
}

/*
*$Log: av-env.bat,v $
*/