package org.springrain.system.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springrain.frame.common.SessionUser;
import org.springrain.frame.util.Finder;
import org.springrain.frame.util.GlobalStatic;
import org.springrain.frame.util.Page;
import org.springrain.system.entity.Role;
import org.springrain.system.entity.User;
import org.springrain.system.entity.UserOrg;
import org.springrain.system.entity.UserRole;
import org.springrain.system.service.BaseSpringrainServiceImpl;
import org.springrain.system.service.IUserOrgService;
import org.springrain.system.service.IUserRoleMenuService;
import org.springrain.system.service.IUserService;

/**
 * TODO 在此加入类描述
 * 
 * @copyright {@link weicms.net}
 * @author springrain<Auto generate>
 * @version 2013-07-06 16:03:00
 * @see org.springrain.springrain.service.impl.User
 */
@Service("userService")
public class UserServiceImpl extends BaseSpringrainServiceImpl implements IUserService {
	
	@Resource
	private IUserOrgService userOrgService;
	
	@Resource
	private IUserRoleMenuService userRoleMenuService;

	@Override
	public String saveUser(User entity) throws Exception {
		String id=super.save(entity).toString();
		updateUserInfo(entity);
		
		return id;
	}

	@Override
	@CacheEvict(value=GlobalStatic.qxCacheKey,allEntries=true)  
	public Integer updateUser(User entity) throws Exception {
		 Integer update = super.update(entity,true);
		 updateUserInfo(entity);
		return update;
	}
	
	private void  updateUserInfo(User user)throws Exception {
		//韩彦阳   20160123 start
		String userId=user.getId();
		//删除原有的组织结构+管理部门
		Finder f_del=Finder.getDeleteFinder(UserOrg.class).append(" WHERE userId=:userId");
		f_del.setParam("userId",userId);
		super.update(f_del);
		//删除原有的管理权限
		Finder f_del_role=Finder.getDeleteFinder(UserRole.class).append(" WHERE userId=:userId ");
		f_del_role.setParam("userId",userId);
		super.update(f_del_role);
		//开始处理部门和门门主管数据
		List<UserOrg> managerOrgs=user.getManagerOrgs();
		if(CollectionUtils.isEmpty(managerOrgs)){
			return;
		}
		for(UserOrg e:managerOrgs){
			e.setUserId(userId);
		}
		super.save(managerOrgs);//保存  管理的部门
		//hyy   20160123  end
		//老代码不动
		List<Role> listRole = user.getUserRoles();
		List<UserRole> listur=new ArrayList<>();
		for(Role role:listRole){
			UserRole ur=new UserRole();
			ur.setUserId(userId);
			ur.setRoleId(role.getId());
			listur.add(ur);
		}
		if(CollectionUtils.isEmpty(listur)){
			return;
		}
		
		super.save(listur);
		
		
		
		
		
		
	}
	
	

	@Override
	public User findUserById(Object id) throws Exception {
		
		User u=super.findById(id, User.class);
		
		String userId=u.getId();
		
		List<UserOrg> managerOrgs = userOrgService.findManagerOrgByUserId(userId);
		u.setManagerOrgs(managerOrgs);
		List<Role> roleByUserId = userRoleMenuService.findRoleByUserId(userId);
		u.setUserRoles(roleByUserId);
		
		
		return u;
	}

	/**
	 * 列表查询,每个service都会重载,要把sql语句封装到service中,Finder只是最后的方案
	 * 
	 * @param finder
	 * @param page
	 * @param clazz
	 * @param o
	 * @return
	 * @throws Exception
	 */
	@Override
	public <T> List<T> findListDataByFinder(Finder finder, Page page, Class<T> clazz, Object o)
			throws Exception {
		User user = (User) o;
		// ==执行分页查询
		// user.setFrameTableAlias("tu");
		// finder=Finder.getSelectFinder(User.class,"tu.*,tg.name gradeName ").append(" tu,").append(Finder.getTableName(DicData.class)).append(" tg WHERE tu.gradeId=tg.id and tg.typekey='grade' ");
      
		Finder qxfinder=userOrgService.findUserIdsSQLByManagerUserId(SessionUser.getUserId());
        
        user.setFrameTableAlias("u");
        
		finder = Finder.getSelectFinder(User.class).append(" u ").append(" WHERE 1=1 ");
//		finder.setEscapeSql(false); 
		
//		finder = new Finder("select u.* from "+Finder.getTableName(User.class));
//		finder.append("(select userId from "+Finder.getTableName(User.class)+" u ,"+Finder.getTableName(UserOrg.class)+" re where u.id=re.userId ")
//		.append(" and re.orgId in (").append(qxsql).append(")").append("  group by userId ")
//		.append(") m where m.userId=u.id ");
		
        if(StringUtils.isBlank(qxfinder.getSql())){
        	//非管理员查看自己的
        	finder.append(" and id=:currid ").setParam("currid", SessionUser.getUserId());
        }else{
        	finder.append(" and id in ").append("(").appendFinder(qxfinder).append(")");
        }
		
		
		super.getFinderWhereByQueryBean(finder, user);
		super.getFinderOrderBy(finder, page);
		List<T> queryForList = super.queryForList(finder, clazz, page);
		
		return queryForList;
	}

	/**
	 * 根据查询列表的宏,导出Excel
	 * 
	 * @param finder
	 *            为空则只查询 clazz表
	 * @param ftlurl
	 *            类表的模版宏
	 * @param page
	 *            分页对象
	 * @param clazz
	 *            要查询的对象
	 * @param o
	 *            querybean
	 * @return
	 * @throws Exception
	 */
	@Override
	public <T> File findDataExportExcel(Finder finder, String ftlurl, Page page, Class<T> clazz,
			Object o) throws Exception {
		return super.findDataExportExcel(finder, ftlurl, page, clazz, o);
	}

	@Override
	
	@Caching(evict = {
			@CacheEvict(value = GlobalStatic.cacheKey, key = "'findRoleByUserId_'+#userId"),
			@CacheEvict(value = GlobalStatic.cacheKey, key = "'getRolesAsString_'+#userId"),
			@CacheEvict(value = GlobalStatic.cacheKey, key = "'findUserByRoleId_'+#userId"),
			@CacheEvict(value = GlobalStatic.cacheKey, key = "'findAllRoleAndMenu'") })
	
	public void updateRoleUser(String userId, String roleId) throws Exception {
		// 删除
		// Finder finder=new
		// Finder("delete from t_user_role where userId=:userId");
		Finder finder = Finder.getDeleteFinder(UserRole.class).append(" WHERE userId=:userId");

		finder.setParam("userId", userId);
		this.update(finder);
		// 添加
		String[] roleIds = roleId.split(",");
		// finder=new
		// Finder("insert into t_user_role(id,userId,roleId) values(:id,:userId,:roleId)");
		// finder.setParam("userId", userId);

		List<UserRole> list = new ArrayList<>();

		for (String str : roleIds) {
			if (StringUtils.isBlank(str)) {
				continue;
			}
			UserRole ur = new UserRole();
			ur.setUserId(userId);
			ur.setRoleId(str);
			list.add(ur);
		}

		if (!CollectionUtils.isEmpty(list)) {
			super.save(list);
		}

	}

	@Override
	public String deleteUserById(String userId) throws Exception {
		if(StringUtils.isBlank(userId)){
			return null;
		}
		//删除前判断
		
		Finder f_del_role=Finder.getDeleteFinder(UserRole.class).append(" WHERE userId=:userId ").setParam("userId", userId);
		super.update(f_del_role);
		
		Finder f_del_org=Finder.getDeleteFinder(UserOrg.class).append(" WHERE userId=:userId ").setParam("userId", userId);
		super.update(f_del_org);
		
		
		Finder f_update=Finder.getUpdateFinder(User.class," active=:active ").append(" WHERE id=:id ").setParam("id", userId).setParam("active", 0);
		
		super.update(f_update);
		
		
		return null;
	}

}
