package com.osmp.web.system.sermapping.dao;

import java.util.List;

import com.osmp.web.core.mybatis.BaseMapper;
import com.osmp.web.system.sermapping.entity.SerMapping;

/**
 * Description:
 * 
 * @author: chenbenhui
 * @date: 2014年12月1日 下午3:09:56
 */

public interface SerMappingMapper extends BaseMapper<SerMapping> {
	
	public List<String> getBundles();

	public List<String> getVersions();
	
	public List<String> getServices();
}
