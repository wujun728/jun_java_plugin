package org.typroject.tyboot.prototype.order.standard;

/**
 * 商家信息
 * @author Wujun
 *
 */
public interface BaseAgency {
	
	/**
	 * 
	 * 商家编码
	 *
	 * @return
	 */
	String getAgencyCode();
	
	
	/**
	 * 
	 * 商家名称
	 *
	 * @return
	 */
	String getAgencyName();
	
	/**
	 * 
	 * 商家联系电话
	 *
	 * @return
	 */
	String getAgencyPhone();
	
}
