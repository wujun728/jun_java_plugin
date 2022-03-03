/**   
 * @Title: LoginServiceImpl.java TODO:
 * @Package com.erp.serviceImpl
 * @Description: TODO
 * @author Wujun
 * @date 2013-4-19 下午03:21:57
 * @version V1.0   
 */
package com.erp.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.erp.dao.PublicDao;
import com.erp.service.ILoginService;
import com.erp.shiro.ShiroUser;
import com.erp.viewModel.MenuModel;
import com.jun.plugin.utils.Constants;

/**
 * 类功能说明 TODO: 类修改者 修改日期 修改说明
 * <p>
 * Title: LoginServiceImpl.java
 * </p>
 * <p>
 * Description:福产流通科技
 * </p>
 * <p>
 * Copyright: Copyright (c) 2012
 * </p>
 * <p>
 * Company:福产流通科技
 * </p>
 * 
 * @author Wujun
 * @date 2013-4-19 下午03:21:57
 * @version V1.0
 */
@SuppressWarnings("rawtypes")
@Service("loginService")
public class LoginServiceImpl implements ILoginService {

	private PublicDao publicDaoSQL;

	@Autowired
	public void setPublicDaoSQL(PublicDao publicDaoSQL) {
		this.publicDaoSQL = publicDaoSQL;
	}

	/*
	 * (非 Javadoc) <p>Title: findMenuList</p> <p>Description:根据权限获取菜单 </p>
	 * 
	 * @return
	 * 
	 * @see com.erp.service.LoginService#findMenuList()
	 */
	public List<MenuModel> findMenuList() {
		ShiroUser user = Constants.getCurrendUser();
		System.out.println("user====>" + user);
		String sql = null;
		if(null==user){
			return null;
		}
		// 超级管理员默认拥有所有功能权限
		if (Constants.SYSTEM_ADMINISTRATOR.equals(user.getAccount())) {
			sql = "SELECT p.PERMISSION_ID,p.PID,p.NAME,p.ICONCLS,p.URL FROM SYS_PERMISSION AS p\n" + "where p.STATUS='A' and p.TYPE='F' and p.ISUSED='Y'";
		} else {
			sql = "SELECT DISTINCT p.PERMISSION_ID,p.PID,p.NAME,p.ICONCLS,p.URL FROM\n" + "SYS_ROLE_PERMISSION AS rp\n" + "INNER JOIN SYS_ROLE AS r ON rp.ROLE_ID = r.ROLE_ID\n"
					+ "INNER JOIN SYS_USER_ROLE AS ur ON rp.ROLE_ID = ur.ROLE_ID\n" + "INNER JOIN SYS_USERS AS u ON u.USER_ID = ur.USER_ID\n"
					+ "INNER JOIN SYS_PERMISSION AS p ON rp.PERMISSION_ID = p.PERMISSION_ID\n"
					+ "WHERE rp.STATUS='A' and r.STATUS='A' and ur.STATUS='A' and u.STATUS='A' and p.STATUS='A' and p.TYPE='F' and p.ISUSED='Y'\n" + "and u.USER_ID="
					+ user.getUserId() + "";
		}
		List listmenu = publicDaoSQL.findBySQL(sql);
		List<MenuModel> parentList = new ArrayList<MenuModel>();
		for (Object object : listmenu) {
			Object[] objs = (Object[]) object;
			String id = String.valueOf(objs[0]);
			if (objs[1] == null) {
				MenuModel menuModel = new MenuModel();
				menuModel.setName(String.valueOf(objs[2]));
				menuModel.setIconCls(String.valueOf(objs[3]));
				menuModel.setUrl(String.valueOf(objs[4]));
				List<MenuModel> childList = new ArrayList<MenuModel>();
				for (Object obj2 : listmenu) {
					MenuModel menuChildModel = new MenuModel();
					Object[] objs2 = (Object[]) obj2;
					String sid = String.valueOf(objs2[1]);
					if (sid.equals(id)) {
						menuChildModel.setName(String.valueOf(objs2[2]));
						menuChildModel.setIconCls(String.valueOf(objs2[3]));
						menuChildModel.setUrl(String.valueOf(objs2[4]));
						childList.add(menuChildModel);
					}
				}
				menuModel.setChild(childList);
				parentList.add(menuModel);
			}
		}
		return parentList;
	}
}
