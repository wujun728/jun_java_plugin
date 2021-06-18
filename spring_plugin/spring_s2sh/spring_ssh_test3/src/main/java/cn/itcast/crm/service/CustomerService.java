package cn.itcast.crm.service;

import cn.itcast.crm.entity.Customer;

/**
 * 文件名字:CustomerService.java<br>
 * 文件作用:Customer服务层的接口<br>
 */
public interface CustomerService {

	/**
	 * 根据ID获取客户信息
	 * 
	 * @param id
	 * @return
	 */
	public Customer findById(Long id);
}
