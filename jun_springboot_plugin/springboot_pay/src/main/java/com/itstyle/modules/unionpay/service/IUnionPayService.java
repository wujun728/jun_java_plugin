package com.itstyle.modules.unionpay.service;

import com.itstyle.common.model.Product;

import java.util.Map;

public interface IUnionPayService {
	/**
	 * 银联支付
	 * @author Wujun
	 * @param product
	 * @return  String
	 * @Date	2017年8月2日
	 * 更新日志
	 * 2017年8月2日  科帮网 首次创建
	 *
	 */
	String unionPay(Product product);
	/**
	 * 前台回调验证
	 * @author Wujun
	 * @param valideData
	 * @param encoding
	 * @return  String
	 * @Date	2017年8月2日
	 * 更新日志
	 * 2017年8月2日  科帮网 首次创建
	 *
	 */
	String validate(Map<String, String> valideData, String encoding);
	/**
	 * 对账单下载
	 * @author Wujun
	 * @Date	2017年8月2日
	 * 更新日志
	 * 2017年8月2日  科帮网 首次创建
	 *
	 */
	void fileTransfer();
}
