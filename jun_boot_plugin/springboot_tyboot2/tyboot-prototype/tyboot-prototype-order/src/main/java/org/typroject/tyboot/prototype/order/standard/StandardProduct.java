package org.typroject.tyboot.prototype.order.standard;


import java.math.BigDecimal;

/**
 * 商品信息顶级接口
 * @author Wujun
 *
 */
public  interface StandardProduct {

	/**
	 * 获得商品名称
	 * @return
	 */
	String getProductName();
	
	/**
	 * 获得商品编号（商品唯一标识）
	 * @return
	 */
	String getProductCode();
	
	
	/**
	 * 归属的商家
	 * @return
	 */
	String getAgencyCode();
	
	/**
	 * 商品类型
	 * @return
	 */
	String  getProductType();
	
	
	/**
	 * 商品数
	 * TODO.
	 *
	 * @return
	 */
	Integer getCount();
	
	/**
	 * 商品单价
	 *
	 * @return
	 */
	BigDecimal getPrice();
}
