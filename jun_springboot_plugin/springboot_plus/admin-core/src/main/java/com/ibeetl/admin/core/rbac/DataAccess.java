package com.ibeetl.admin.core.rbac;

import com.ibeetl.admin.core.service.CorePlatformService;

/**
 * 数据权限接口类
 * @author Wujun
 *
 */
public interface DataAccess {
	 DataAccessResullt getOrg(Long userId,Long orgId );
	 String getName();
	 Integer getType();
}
