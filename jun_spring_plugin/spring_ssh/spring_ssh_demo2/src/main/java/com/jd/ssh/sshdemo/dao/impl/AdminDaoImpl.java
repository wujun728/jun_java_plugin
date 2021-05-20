package com.jd.ssh.sshdemo.dao.impl;

import org.springframework.stereotype.Repository;

import com.jd.ssh.sshdemo.dao.AdminDao;
import com.jd.ssh.sshdemo.entity.Admin;

/**
 * Dao实现类 - 管理员
 * ============================================================================
 * 版权所有 2013 qshihua
 * 
 * @author Wujun
 * @version 0.1 2013-1-16
 * ============================================================================
 */

@Repository
public class AdminDaoImpl extends BaseDaoImpl<Admin, String> implements AdminDao {
	
	public boolean isExistByUsername(String username) {
		String hql = "from Admin admin where lower(admin.username) = lower(?)";
		Admin admin = (Admin) getSession().createQuery(hql).setParameter(0, username).uniqueResult();
		if (admin != null) {
			return true;
		} else {
			return false;
		}
	}
	
	public Admin getAdminByUsername(String username) {
		String hql = "from Admin admin where lower(admin.username) = lower(?)";
		return (Admin) getSession().createQuery(hql).setParameter(0, username).uniqueResult();
	}
	
	// 处理关联
	@Override
	public void delete(Admin admin) {
		
		super.delete(admin);
	}
	
	// 处理关联，忽略isSystem=true的对象
	@Override
	public void delete(String id) {
		Admin admin = load(id);
		this.delete(admin);
	}

	// 处理关联，忽略isSystem=true的对象。
	@Override
	public void delete(String[] ids) {
		for (String id : ids) {
			this.delete(id);
		}
	}
	
	//重写保存默认启用/未锁定/未过期/凭证未过期
	@Override
	public String save(Admin admin) {
		//admin.setIsAccountEnabled(true);
		admin.setIsAccountLocked(false);
		admin.setIsAccountExpired(false);
		admin.setIsCredentialsExpired(false);
		admin.setLoginFailureCount(0);
		return super.save(admin);
	}
}