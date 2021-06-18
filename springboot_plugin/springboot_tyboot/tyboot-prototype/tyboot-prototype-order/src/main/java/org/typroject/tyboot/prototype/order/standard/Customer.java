package org.typroject.tyboot.prototype.order.standard;


/**
 * 
 * <pre>
 *  Tyrest
 *  File: Customer.java
 * 
 *  Tyrest, Inc.
 *  Copyright (C): 2016
 * 
 *  Description:
 *  TODO
 * 
 *  Notes:
 *  $Id: Customer.java  Tyrest\magintrursh $ 
 * 
 *  Revision History
 *  &lt;Date&gt;,			&lt;Who&gt;,			&lt;What&gt;
 *  2016年11月1日		magintrursh		Initial.
 *
 * </pre>
 */
public interface Customer {
	
	/**
	 * 公网用户的业务主键
	 * @return
	 */
	String  getUserId();
	
	/**
	 * 公网用户全名、姓名
	 * @return
	 */
	String getUserName();
	
	/**
	 * 公网用户昵称
	 * @return
	 */
	String getNickName();

	/**
	 * 公网用户的分类类型
	 * @return
	 */
	String getCustomerType();
	
	/**
	 * 公网用户的会员号
	 * @return
	 */
	String getVipNo();
	
	
}
