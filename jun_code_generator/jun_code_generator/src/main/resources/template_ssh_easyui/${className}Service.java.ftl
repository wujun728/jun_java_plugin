package com.erp.service;

import java.util.List;
import java.util.Map;

import com.erp.model.${Table};
import com.erp.util.PageUtil;



public interface ${Table}Service {

	boolean persistence${Table}(Map<String, List<${Table}>> map);

	List<${Table}> findAll${Table}List(Map<String, Object> param,PageUtil pageUtil);

	Long getCount(Map<String, Object> param,PageUtil pageUtil);

	boolean add${Table}(List<${Table}> addlist );

	boolean upd${Table}(List<${Table}> updlist );

	boolean del${Table}(List<${Table}> dellist );

	boolean del${Table}(Integer companyId );

}
