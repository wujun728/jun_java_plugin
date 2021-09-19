package com.jun.plugin.oa.service;

import java.util.List;

import com.jun.plugin.oa.entity.GroupAndResource;
import com.jun.plugin.oa.entity.Resource;
import com.jun.plugin.oa.pagination.Page;

public interface IResourceService {

	public Resource getPermissions(Integer id) throws Exception;
	
	public List<Resource> getMenus(List<GroupAndResource> gr) throws Exception;
	
	public List<Resource> getAllResource() throws Exception;
	
	public List<Resource> getResourceListPage() throws Exception;
	
	public List<Resource> getResourceList(Page<Resource> p) throws Exception;
	
	public List<Resource> getResourceByType() throws Exception;
	
	public void doAdd(Resource entity) throws Exception;
	
	public void doUpdate(Resource entity) throws Exception;
	
	public void doDelete(Resource entity) throws Exception;
	
	public void doDelete(String id) throws Exception;
	
}
