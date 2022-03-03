package com.erp.service;

import java.util.List;
import java.util.Map;

import com.erp.model.Permission;
import com.erp.model.Role;
import com.erp.viewModel.TreeGrid;

/**
 * 类功能说明 TODO:
 * 类修改者
 * 修改日期
 * 修改说明
 * <p>Title: PermissionAssignmentService.java</p>
 * <p>Description:福产流通科技</p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company:福产流通科技有限公司</p>
 * @author Wujun
 * @date 2013-5-17 下午2:49:38
 * @version V1.0
 */

public interface IPermissionAssignmentService
{

	List<TreeGrid> findAllFunctionsList(Integer pid );

	Long getCount(Map<String, Object> param );

	boolean savePermission(Integer roleId, String checkedIds );

	List<Permission> getRolePermission(Integer roleId );

	boolean persistenceRole(Map<String, List<Role>> map );

	List<Role> findAllRoleList(Map<String, Object> param, Integer page, Integer rows, boolean isPage );

	boolean persistenceRole(Role r );

	boolean persistenceRole(Integer roleId );

}
