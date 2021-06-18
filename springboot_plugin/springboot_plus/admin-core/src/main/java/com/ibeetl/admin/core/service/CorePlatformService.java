package com.ibeetl.admin.core.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.beetl.sql.core.SQLManager;
import org.beetl.sql.core.engine.SQLPlaceholderST;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.ibeetl.admin.core.dao.CoreFunctionDao;
import com.ibeetl.admin.core.dao.CoreMenuDao;
import com.ibeetl.admin.core.dao.CoreOrgDao;
import com.ibeetl.admin.core.dao.CoreRoleFunctionDao;
import com.ibeetl.admin.core.dao.CoreRoleMenuDao;
import com.ibeetl.admin.core.dao.CoreUserDao;
import com.ibeetl.admin.core.entity.CoreFunction;
import com.ibeetl.admin.core.entity.CoreMenu;
import com.ibeetl.admin.core.entity.CoreOrg;
import com.ibeetl.admin.core.entity.CoreRoleFunction;
import com.ibeetl.admin.core.entity.CoreUser;
import com.ibeetl.admin.core.rbac.DataAccessFactory;
import com.ibeetl.admin.core.rbac.tree.FunctionItem;
import com.ibeetl.admin.core.rbac.tree.MenuItem;
import com.ibeetl.admin.core.rbac.tree.OrgItem;
import com.ibeetl.admin.core.util.FunctionBuildUtil;
import com.ibeetl.admin.core.util.HttpRequestLocal;
import com.ibeetl.admin.core.util.MenuBuildUtil;
import com.ibeetl.admin.core.util.OrgBuildUtil;
import com.ibeetl.admin.core.util.PlatformException;
import com.ibeetl.admin.core.util.beetl.DataAccessFunction;
import com.ibeetl.admin.core.util.beetl.NextDayFunction;
import com.ibeetl.admin.core.util.enums.DelFlagEnum;

/**
 * 系统平台功能访问入口，所有方法应该支持缓存或者快速访问
 * @author Wujun
 */
@Service
public class CorePlatformService {


    //菜单树，组织机构树，功能树缓存标记
    public static final String MENU_TREE_CACHE = "cache:core:menuTree";
    public static final String ORG_TREE_CACHE = "cache:core:orgTree";
    public static final String FUNCTION_TREE_CACHE = "cache:core:functionTree";
    //字典列表
    public static final String DICT_CACHE_TYPE = "cache:core:dictType";
    public static final String DICT_CACHE_VALUE = "cache:core:dictValue";
    public static final String DICT_CACHE_SAME_LEVEL = "cache:core:ditcSameLevel";
    public static final String DICT_CACHE_CHILDREN = "cache:core:dictChildren";
    public static final String USER_FUNCTION_ACCESS_CACHE = "cache:core:userFunctionAccess";
    public static final String USER_FUNCTION_CHIDREN_CACHE = "ccache:core:functionChildren";
    public static final String FUNCTION_CACHE = "cache:core:function";

    public static final String USER_DATA_ACCESS_CACHE = "cache:core:userDataAccess";
    public static final String USER_MENU_CACHE = "cache:core:userMenu";

    /*当前用户会话*/
    public static final String ACCESS_CURRENT_USER = "core:user";
    /*当前登录用户所在部门*/
    public static final String ACCESS_CURRENT_ORG = "core:currentOrg";
    /*用户可选部门*/
    public static final String ACCESS_USER_ORGS = "core:orgs";

    public static final String ACCESS_SUPPER_ADMIN = "admin";
    
	@Autowired
	HttpRequestLocal httpRequestLocal;


    @Autowired
    CoreRoleFunctionDao roleFunctionDao;

    @Autowired
    CoreRoleMenuDao sysRoleMenuDao;

    @Autowired
    CoreOrgDao sysOrgDao;

    @Autowired
    CoreRoleFunctionDao sysRoleFunctionDao;

    @Autowired
    CoreMenuDao sysMenuDao;

    @Autowired
    CoreUserDao sysUserDao;

    @Autowired
    CoreFunctionDao sysFunctionDao;

    @Autowired
    SQLManager sqlManager;

    @Autowired
    DataAccessFunction dataAccessFunction;

    @Autowired
    CorePlatformService self;
    @Autowired
    DataAccessFactory dataAccessFactory;

    @PostConstruct
    @SuppressWarnings("unchecked")
    public void init() {
        SQLPlaceholderST.textFunList.add("function");
        //sql语句里带有此函数来判断数据权限
        sqlManager.getBeetl().getGroupTemplate().registerFunction("function", dataAccessFunction);
        sqlManager.getBeetl().getGroupTemplate().registerFunction("nextDay", new NextDayFunction());
    }


    public CoreUser getCurrentUser() {
    	checkSession();
        CoreUser user =  (CoreUser) httpRequestLocal.getSessionValue(ACCESS_CURRENT_USER);
        return user;

    }
    
    public void changeOrg(Long orgId) {
    		List<CoreOrg> orgs = this.getCurrentOrgs();
    		for(CoreOrg org:orgs) {
    			if(org.getId().equals(orgId)) {
    			 	httpRequestLocal.setSessionValue(CorePlatformService.ACCESS_CURRENT_ORG, org);
    			}
    		}
	}


    public Long getCurrentOrgId() {
    	checkSession();
        CoreOrg org = (CoreOrg) httpRequestLocal.getSessionValue(ACCESS_CURRENT_ORG);
        return org.getId();

    }
    
    public CoreOrg getCurrentOrg() {
    	checkSession();
        CoreOrg org = (CoreOrg) httpRequestLocal.getSessionValue(ACCESS_CURRENT_ORG);
        return org;

    }

    public List<CoreOrg> getCurrentOrgs() {
        List<CoreOrg> orgs = (List<CoreOrg>) httpRequestLocal.getSessionValue(ACCESS_USER_ORGS);
        return orgs;

    }
    
    protected void checkSession() {
    	  CoreOrg org = (CoreOrg) httpRequestLocal.getSessionValue(ACCESS_CURRENT_ORG);
          if(org==null) {
          	throw new PlatformException("会话过期，重新登录");
          }
    }

    public void setLoginUser(CoreUser user, CoreOrg currentOrg, List<CoreOrg> orgs) {
    	httpRequestLocal.setSessionValue(CorePlatformService.ACCESS_CURRENT_USER, user);
    	httpRequestLocal.setSessionValue(CorePlatformService.ACCESS_CURRENT_ORG, currentOrg);
    	httpRequestLocal.setSessionValue(CorePlatformService.ACCESS_USER_ORGS, orgs);

    }

    public MenuItem getMenuItem(long userId, long orgId) {
        CoreUser user = this.sysUserDao.unique(userId);
        if (this.isSupperAdmin(user)) {
            return self.buildMenu();
        }
        Set<Long> allows = self.getCurrentMenuIds(userId, orgId);
        MenuItem menu = this.buildMenu();
        menu.filter(allows);
        return menu;

    }

    public OrgItem getUserOrgTree() {
        if (this.isCurrentSupperAdmin()) {
            OrgItem root = self.buildOrg();
            return root;
        }
        OrgItem current = getCurrentOrgItem();
        //搞不懂，忘记了
        OrgItem item= dataAccessFactory.getUserOrgTree(current);
       
        return item;

    }

   


    @Cacheable(FUNCTION_CACHE)
    public CoreFunction getFunction(String functionCode) {

        return sysFunctionDao.getFunctionByCode(functionCode);
    }


    public OrgItem getCurrentOrgItem() {
        //@TODO 无法缓存orgItem，因为组织机构在调整
        OrgItem root = buildOrg();
        OrgItem item = root.findChild(getCurrentOrgId());
        if (item == null) {
            throw new PlatformException("未找到组织机构");
        }
        return item;
    }


    /**
     * 判断用户是否是超级管理员
     * @param user
     * @return
     */
    public boolean isSupperAdmin(CoreUser user) {
        return user.getCode().startsWith(ACCESS_SUPPER_ADMIN);
    }

    public boolean isCurrentSupperAdmin() {
        CoreUser user = this.getCurrentUser();
        return isSupperAdmin(user);
    }

    public boolean isAllowUserName(String name){
    	return !name.startsWith(ACCESS_SUPPER_ADMIN);
    }

    /**
     * 获取用户在指定功能点的数据权限配置，如果没有，返回空集合
     * @param userId
     * @param orgId
     * @param fucntionCode
     * @return
     */
    @Cacheable(USER_DATA_ACCESS_CACHE)
    public List<CoreRoleFunction> getRoleFunction(Long userId, Long orgId, String fucntionCode) {
        List<CoreRoleFunction> list = sysRoleFunctionDao.getRoleFunction(userId, orgId, fucntionCode);
        return list;
    }


    /**
     * 当前用户是否能访问功能，用于后台功能验证,functionCode 目前只支持二级域名方式，不支持更多级别
     * @param functionCode "user.add","user"
     * @return
     */
    
    

    @Cacheable(USER_FUNCTION_ACCESS_CACHE)
    public boolean canAcessFunction(Long userId, Long orgId, String functionCode) {

        CoreUser user = getCurrentUser();
        if (user.getId() == userId && isSupperAdmin(user)) {
            return true;
        }
        String str = functionCode;
        List<CoreRoleFunction> list = sysRoleFunctionDao.getRoleFunction(userId, orgId, str);
        boolean canAccess = !list.isEmpty();
        if (canAccess) {
            return true;
        }else{
        	return false;
        }

        


    }

    /**
     * 当前功能的子功能，如果有，则页面需要做按钮级别的过滤
     * @param userId
     * @param orgId
     * @param parentFunction 菜单对应的function
     * @return
     */
    @Cacheable(USER_FUNCTION_CHIDREN_CACHE)
    public List<String> getChildrenFunction(Long userId, Long orgId, String parentFunction) {
        CoreFunction template = new CoreFunction();
        template.setCode(parentFunction);
        List<CoreFunction> list = sysFunctionDao.template(template);
        if (list.size() != 1) {
            throw new PlatformException("访问权限未配置");
        }

        Long id = list.get(0).getId();
        return sysRoleFunctionDao.getRoleChildrenFunction(userId, orgId, id);

    }


    /**
     * 查询当前用户有用的菜单项目，可以在随后验证是否能显示某项菜单
     * @return
     */
    @Cacheable(USER_MENU_CACHE)
    public Set<Long> getCurrentMenuIds(Long userId, Long orgId) {
        List<Long> list = sysRoleMenuDao.queryMenuByUser(userId, orgId);
        return new HashSet<Long>(list);
    }


    /**
     * 验证菜单是否能被显示
     * @param item
     * @param allows
     * @return
     */
    public boolean canShowMenu(CoreUser user, MenuItem item, Set<Long> allows) {
        if (isSupperAdmin(user)) {
            return true;
        }
        return allows.contains(item.getData().getId());
    }

    @Cacheable(MENU_TREE_CACHE)
    public MenuItem buildMenu() {
        List<CoreMenu> list = sysMenuDao.allMenuWithURL();
        return MenuBuildUtil.buildMenuTree(list);

    }

    @Cacheable(ORG_TREE_CACHE)
    public OrgItem buildOrg() {
       
       
        CoreOrg root = sysOrgDao.getRoot();
        OrgItem rootItem = new OrgItem(root);
        CoreOrg org = new CoreOrg();
        org.setDelFlag(DelFlagEnum.NORMAL.getValue());
        List<CoreOrg> list = sysOrgDao.template(org);
        OrgBuildUtil.buildTreeNode(rootItem,list);
        //集团
        return rootItem;

    }

    @Cacheable(FUNCTION_TREE_CACHE)
    public FunctionItem buildFunction() {
        List<CoreFunction> list = sysFunctionDao.all();
        return FunctionBuildUtil.buildOrgTree(list);

    }
    /**
     * 用户信息被管理员修改，重置会话，让用户操作重新登录
     * @param name
     */
    public void restUserSession(String name){
    	//TODO
    }


    @CacheEvict(cacheNames = {FUNCTION_CACHE, FUNCTION_TREE_CACHE, /*功能点本身缓存*/
            MENU_TREE_CACHE, USER_MENU_CACHE,/*功能点关联菜单缓存*/
            USER_FUNCTION_ACCESS_CACHE, USER_FUNCTION_CHIDREN_CACHE, USER_DATA_ACCESS_CACHE,/*功能点相关权限缓存*/}, allEntries = true)
    public void clearFunctionCache() {
        //没有做任何事情，交给spring cache来处理了
    }


    @CacheEvict(cacheNames = {CorePlatformService.MENU_TREE_CACHE, CorePlatformService.USER_MENU_CACHE}, allEntries = true)
    public void clearMenuCache() {
        //没有做任何事情，交给spring cache来处理了
    }

    @CacheEvict(cacheNames = {CorePlatformService.DICT_CACHE_CHILDREN,CorePlatformService.DICT_CACHE_SAME_LEVEL,CorePlatformService.DICT_CACHE_TYPE,CorePlatformService.DICT_CACHE_VALUE}, allEntries = true)
    public void clearDictCache() {
    }
    
    @CacheEvict(cacheNames = {CorePlatformService.ORG_TREE_CACHE}, allEntries = true)
    public void clearOrgCache() {
    }

    /**
     * 得到类型为系统的菜单，通常就是根菜单下面
     * @return
     */
    public List<MenuItem> getSysMenu() {
        MenuItem root = buildMenu();
        List<MenuItem> list = root.getChildren();
        for (MenuItem item : list) {
            if (!item.getData().getType() .equals(CoreMenu.TYPE_SYSTEM)) {
                throw new IllegalArgumentException("本系统没有系统模块");
            }
        }
        return list;
    }

    /**
     * 得到菜单的子菜单
     * @param menuId
     * @return
     */
    public List<MenuItem> getChildMenu(Long menuId) {
        MenuItem root = buildMenu();
        List<MenuItem> list = root.findChild(menuId).getChildren();
        return list;
    }
    
    
  


}
