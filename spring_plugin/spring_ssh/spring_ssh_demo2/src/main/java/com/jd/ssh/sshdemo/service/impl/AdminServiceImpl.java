package com.jd.ssh.sshdemo.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.jd.ssh.sshdemo.dao.AdminDao;
import com.jd.ssh.sshdemo.entity.Admin;
import com.jd.ssh.sshdemo.service.AdminService;
import com.jd.ssh.sshdemo.util.Digests;
import com.jd.ssh.sshdemo.util.Encodes;

/**
 * Service实现类 - 管理员
 * ----------------------------------------------------------------------------
 * 版权所有 2013 qshihua。
 * ----------------------------------------------------------------------------
 * 
 * @author qshihua
 * 
 * @version 0.1 2013-01-06
 */

@Service
public class AdminServiceImpl extends BaseServiceImpl<Admin, String> implements AdminService {

	public static final String HASH_ALGORITHM = "SHA-1";
	public static final int HASH_INTERATIONS = 1024;
	private static final int SALT_SIZE = 8;
	
	@Resource
	private AdminDao adminDao;
	@Resource
	public void setBaseDao(AdminDao adminDao) {
		super.setBaseDao(adminDao);
	}

	/**
	 * 设定安全的密码，生成随机的salt并经过1024次 sha-1 hash
	 */
	public void entryptPassword(Admin user) {
		byte[] salt = Digests.generateSalt(SALT_SIZE);
		user.setSalt(Encodes.encodeHex(salt));

		byte[] hashPassword = Digests.sha1(user.getPassword().getBytes(), salt, HASH_INTERATIONS);
		user.setPassword(Encodes.encodeHex(hashPassword));
	}
	
	public Admin getLoginAdmin() {
		/*SecurityContextImpl securityContextImpl = (SecurityContextImpl) ServletActionContext.getRequest().getSession().getAttribute("SPRING_SECURITY_CONTEXT");
		if(securityContextImpl==null){
			System.out.println("======================securityContextImpl null=================");
			return null;
		}
		if(securityContextImpl.getAuthentication()!=null){
			String loginUsername = securityContextImpl.getAuthentication().getName();
			System.out.println("======================loginUsername "+loginUsername +"=================");
			Admin loadedUser = (Admin) adminDetailsServiceImpl.loadUserByUsername(loginUsername);
			return loadedUser;
		}else{*/
			return null;
		//}
	}
	
	public Admin loadLoginAdmin() {
		Admin admin = getLoginAdmin();
		if (admin == null) {
			return null;
		} else {
			return adminDao.load(admin.getId());
		}
	}
	
	public boolean isExistByUsername(String username) {
		return adminDao.isExistByUsername(username);
	}
	
	public Admin getAdminByUsername(String username) {
		return adminDao.getAdminByUsername(username);
	}

}