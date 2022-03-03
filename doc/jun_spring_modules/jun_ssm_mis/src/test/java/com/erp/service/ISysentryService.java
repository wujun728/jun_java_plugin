package com.erp.service;

import java.util.List;
import java.util.Map;

import com.erp.model.SysEntity;
import com.erp.model.SysField;

public interface ISysentryService {

	public Long getCount(Map<String, Object> param);

	public List<SysEntity> findAllSysEntryList(Map<String, Object> param, Integer page, Integer rows, boolean isPage);

	public List<SysField> getEntryFields(String id);

}
