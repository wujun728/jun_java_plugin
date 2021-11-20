package com.jd.ssh.sshdemo.data;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.jd.ssh.sshdemo.entity.Admin;
import com.jd.ssh.sshdemo.service.AdminService;

/**
 * 服务器管理员单元测试，初始化执行本单元测试
 * min_xu
 * 2014-01-23
 * */
public class SystemAdminTest {
	private ApplicationContext context;
	private AdminService adminService;
	
	private static final Log logger = LogFactory
			.getLog(SystemAdminTest.class);
	
	@Before
	public void before() {
		context = new ClassPathXmlApplicationContext("applicationContext-test.xml");
		adminService =   (AdminService) context.getBean("adminServiceImpl");
	}
	
	/*数据初始化顺序
	1、生成Admin登陆账号
	2、生成企业账号Company 绑定到Admin，生成Role Resource
	3、生成所有其他数据 绑定到Company
	*/
	/**
	 * 一、初始化Admin 
	 * admin
	 * password
	 * */
	@Test
	public void initDefaultLoginAdmin(){
		Admin admin = new Admin();
		admin.setPassword("password");
		admin.setUsername("admin");
		admin.setName("系统管理员");
		admin.setIsAccountEnabled(true);
		admin.setIsAccountExpired(false);
		admin.setIsCredentialsExpired(false);
		admin.setIsAccountLocked(false);
		adminService.save(admin);
		
		logger.error("----> initDefaultLoginAdmin finished");
	}
	
}
