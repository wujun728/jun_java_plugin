package org.springrain.system.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springrain.frame.util.Finder;
import org.springrain.frame.util.GlobalStatic;
import org.springrain.frame.util.Page;
import org.springrain.system.entity.Menu;
import org.springrain.system.entity.RoleMenu;
import org.springrain.system.service.BaseSpringrainServiceImpl;
import org.springrain.system.service.IMenuService;

/**
 * TODO 在此加入类描述
 * 
 * @copyright {@link weicms.net}
 * @author springrain<Auto generate>
 * @version 2013-07-06 16:02:58
 * @see org.springrain.springrain.service.impl.Menu
 */
@Service("menuService")
public class MenuServiceImpl extends BaseSpringrainServiceImpl implements
		IMenuService {

	@Override
	public String saveMenu(Menu entity) throws Exception {
		return super.save(entity).toString();
	}

	@Override
	@CacheEvict(value=GlobalStatic.qxCacheKey,allEntries=true)  
	public String saveorupdateMenu(Menu entity) throws Exception {
		return super.saveorupdate(entity).toString();
	}

	@Override
	public Integer updateMenu(Menu entity) throws Exception {
		return super.update(entity);
	}

	@Override
	public Menu findMenuById(Object id) throws Exception {
		return super.findById(id, Menu.class);
	}
	
	
	@Override
    public List<Menu> findListById(Object id) throws Exception {
		List<Menu> menuList=new ArrayList<>();
		Finder finder = Finder.getSelectFinder(Menu.class);
	
		if(id==null || StringUtils.isBlank(id.toString())){
			finder.append(" where pid is :pid");
		}else{
			finder.append(" where pid=:pid ");
		}
		finder.setParam("pid", id);
		List<Menu> list = super.queryForList(finder,Menu.class);
		if(CollectionUtils.isEmpty(list)){
			return null;
		}
		for(Menu m:list){
			menuList.add(addLeafMenu(m));
		}
		return menuList;
	}

	private Menu addLeafMenu(Menu menu) throws Exception{
		if(menu==null){
			return null;
		}
		String id=menu.getId();
		if(StringUtils.isBlank(id)){
			return null;
		}
		
		Finder finder = Finder.getSelectFinder(Menu.class).append(" where pid=:pid ");
		finder.setParam("pid", id);
		List<Menu> list = super.queryForList(finder,Menu.class);
		if(CollectionUtils.isEmpty(list)){
			return menu;
		}
		menu.setLeaf(list);
		return menu;
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
	public <T> List<T> findListDataByFinder(Finder finder, Page page,
			Class<T> clazz, Object o) throws Exception {
		finder=Finder.getSelectFinder(Menu.class).append(" WHERE 1=1  order by sortno asc ");
		return super.queryForList(finder, clazz);
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
	public <T> File findDataExportExcel(Finder finder, String ftlurl,
			Page page, Class<T> clazz, Object o) throws Exception {
		return super.findDataExportExcel(finder, ftlurl, page, clazz, o);
	}

	@Override
	@Cacheable(value = GlobalStatic.cacheKey, key = "'getNameByPageurl_'+#pageurl")
	public String getNameByPageurl(String pageurl) throws Exception {
		if(StringUtils.isBlank(pageurl)){
			return null;
		}
		Finder finder = Finder.getSelectFinder(Menu.class,"name").append(" WHERE pageurl=:pageurl ");
		finder.setParam("pageurl", pageurl);
		List<String> list = queryForList(finder,String.class);
		if(CollectionUtils.isEmpty(list)){
			return null;
		}
		
		return list.toString();
	}

	@Override
	@CacheEvict(value=GlobalStatic.qxCacheKey,allEntries=true)  
	public String deleteMenuById(String menuId) throws Exception {
		if(StringUtils.isBlank(menuId)){
			return null;
		}
		Finder finder_del_user=Finder.getDeleteFinder(RoleMenu.class).append(" WHERE menuId=:menuId ").setParam("menuId", menuId);
		super.update(finder_del_user);
		super.deleteById(menuId, Menu.class);
		return null;
	}

}
