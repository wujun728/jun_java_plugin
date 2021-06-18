package com.jd.ssh.sshdemo.dao;

import com.jd.ssh.sshdemo.entity.Admin;

/**
 * Dao接口 - 管理员
 * ============================================================================
 * 版权所有 2013 qshihua。
 * 
 * @author Wujun
 * @version 0.1 2013-1-16
 * ============================================================================
 */

public interface AdminDao extends BaseDao<Admin, String> {
	
	/**
	 * 根据用户名判断此用户是否存在（不区分大小写）
	 * 
	 */
	public boolean isExistByUsername(String username);
	
	/**
	 * 根据用户名获取管理员对象，若管理员不存在，则返回null（不区分大小写）
	 * 
	 */
	public Admin getAdminByUsername(String username);

}