package com.osmp.web.system.sermapping.service;

import java.util.List;

import com.osmp.web.system.sermapping.entity.SerMapping;
import com.osmp.web.utils.Pagination;

public interface SerMappingService {

	public List<SerMapping> getList(Pagination<SerMapping> p, String where);
	
	public void addSerMapping(SerMapping serMapping);
	
	public void deleteSerMapping(SerMapping serMapping);
	
	public void updateSerMapping(SerMapping serMapping);
	
	public SerMapping getSerMapping(SerMapping serMapping);
	
	public List<String> getBundels();
	
	public List<String> getVersions();
	
	public List<String> getServices();
}
