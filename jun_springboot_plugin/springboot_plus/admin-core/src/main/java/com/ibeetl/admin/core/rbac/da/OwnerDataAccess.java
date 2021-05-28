package com.ibeetl.admin.core.rbac.da;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ibeetl.admin.core.rbac.AccessType;
import com.ibeetl.admin.core.rbac.DataAccess;
import com.ibeetl.admin.core.rbac.DataAccessResullt;
import com.ibeetl.admin.core.service.CorePlatformService;
/**
 * 只查看自己
 * @author Wujun
 *
 */
@Component
public class OwnerDataAccess implements DataAccess {
	
	@Autowired
	CorePlatformService platformService;

	@Override
	public DataAccessResullt getOrg(Long userId, Long orgId) {
		DataAccessResullt ret = new DataAccessResullt();
		ret.setStatus(AccessType.OnlyUser);
		ret.setUserIds(Arrays.asList(userId));
		return ret;
		
	}

	@Override
	public String getName() {
		return "只查看自己";
	}

	@Override
	public Integer getType() {
		return 1;
	}

}
