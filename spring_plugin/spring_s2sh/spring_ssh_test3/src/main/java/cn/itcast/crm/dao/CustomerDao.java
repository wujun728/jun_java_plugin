package cn.itcast.crm.dao;

import cn.itcast.crm.entity.Customer;

/**
 * 文件名字:CustomerDao.java<br>
 * 文件作用:Dao层<br>
 */
public interface CustomerDao {
	/**
	 * 根据ID获取客户信息
	 * 
	 * @param id
	 * @return
	 */
	public Customer findById(Long id);

}
