package org.springrain.system.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springrain.frame.common.SessionUser;
import org.springrain.frame.util.Finder;
import org.springrain.frame.util.GlobalStatic;
import org.springrain.frame.util.Page;
import org.springrain.system.entity.Menu;
import org.springrain.system.entity.Role;
import org.springrain.system.entity.RoleMenu;
import org.springrain.system.entity.UserRole;
import org.springrain.system.service.BaseSpringrainServiceImpl;
import org.springrain.system.service.IRoleService;
import org.springrain.system.service.IUserOrgService;
import org.springrain.system.service.IUserRoleMenuService;

/**
 * TODO 在此加入类描述
 * @copyright {@link weicms.net}
 * @author springrain<Auto generate>
 * @version  2013-07-06 16:02:59
 * @see org.springrain.springrain.service.impl.Role
 */
@Service("roleService")
public class RoleServiceImpl extends BaseSpringrainServiceImpl implements IRoleService {
	@Resource
    private IUserRoleMenuService userRoleMenuService;
	//@Resource
	//private CacheManager shiroCacheManager;
	@Resource
	private IUserOrgService userOrgService;
   
    @Override
	public String  saveRole(Role entity) throws Exception{
	       return super.save(entity).toString();
	}

    @Override
	@CacheEvict(value=GlobalStatic.qxCacheKey,allEntries=true)  
	public String  saveorupdateRole(Role role) throws Exception{
    	
    	//更新 shiro 的权限缓存
    	//shiroCacheManager.getCache(GlobalStatic.authorizationCacheName).clear();
    	
    	
    	      List<Menu> menus = role.getMenus();
    	                 
    	    String id = super.saveorupdate(role).toString();
    	    String _id=role.getId();
    	    if(StringUtils.isBlank(_id)){
    	    	_id=id;
    	    }
    	    
    	    
    	   Finder f_del=Finder.getDeleteFinder(RoleMenu.class).append(" WHERE roleId=:roleId ");
    	   f_del.setParam("roleId", _id);
    	   super.update(f_del);
    	   
    	   if(CollectionUtils.isEmpty(menus)){
    		   return id;
    	   }
    	   
    	   List<RoleMenu> rms=new ArrayList<>();
    	   for(Menu m:menus){
    		   RoleMenu rm=new RoleMenu();
    		   rm.setRoleId(_id);
    		   rm.setMenuId(m.getId());
    		   rms.add(rm);
    	   }
    	   
    	    super.save(rms);
    	
    	
	       return id;
	}
	
	@Override
    public Integer updateRole(Role entity) throws Exception{
	return super.update(entity);
    }
    @Override
	public Role findRoleById(Object id) throws Exception{
	 return super.findById(id,Role.class);
	}

/**
 * 列表查询,每个service都会重载,要把sql语句封装到service中,Finder只是最后的方案
 * @param finder
 * @param page
 * @param clazz
 * @param o
 * @return
 * @throws Exception
 */
        @Override
    public <T> List<T> findListDataByFinder(Finder finder, Page page, Class<T> clazz,
			Object o) throws Exception{
//			 return super.findListDataByFinder(finder,page,clazz,o);
        	//角色查询加权限
        	Finder f=Finder.getSelectFinder(Role.class);
//        	f.setEscapeSql(false); 
        	f.append(" where 1=1 ");
        	Finder qxfinder=userOrgService.findOrgIdsSQLByManagerUserId(SessionUser.getUserId());
        	if(StringUtils.isNotBlank(qxfinder.getSql())){
        		f.append(" and orgId in ( ").appendFinder(qxfinder).append(")");
        	}
        	 return super.findListDataByFinder(f,page,clazz,o);   
	}
	/**
	 * 根据查询列表的宏,导出Excel
	 * @param finder 为空则只查询 clazz表
	 * @param ftlurl 类表的模版宏
	 * @param page 分页对象
	 * @param clazz 要查询的对象
	 * @param o  querybean
	 * @return
	 * @throws Exception
	 */
		@Override
	public <T> File findDataExportExcel(Finder finder,String ftlurl, Page page,
			Class<T> clazz, Object o)
			throws Exception {
			 return super.findDataExportExcel(finder,ftlurl,page,clazz,o);
		}

	@Override
	public String findNameById(String roleId) throws Exception {
		Finder finder=Finder.getSelectFinder(Role.class,"name").append(" WHERE id=:id ");
		finder.setParam("id", roleId);
		String name=super.queryForObject(finder,String.class);
		return name;
	}

	@Override
	@CacheEvict(value=GlobalStatic.qxCacheKey,allEntries=true)  
	public String deleteRoleById(String roleId) throws Exception {
		if(StringUtils.isEmpty(roleId)){
			return null;
		}
		
		
		Finder finder_del_user=Finder.getDeleteFinder(UserRole.class).append(" where roleId=:roleId ").setParam("roleId", roleId);
		super.update(finder_del_user);
		
		Finder finder_del_menu=Finder.getDeleteFinder(RoleMenu.class).append(" where roleId=:roleId ").setParam("roleId", roleId);
		super.update(finder_del_menu);
		
		super.deleteById(roleId, Role.class);
		
		
		return null;
	}

}
