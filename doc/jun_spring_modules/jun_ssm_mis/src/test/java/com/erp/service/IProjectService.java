package com.erp.service;

import java.util.List;
import java.util.Map;

import com.erp.model.Customer;
import com.erp.model.Project;
import com.erp.model.ProjectFollow;
import com.jun.plugin.utils.biz.PageUtil;

public interface IProjectService
{

	boolean delProject(Integer projectId );

	List<Project> findProjectList(Map<String, Object> param, PageUtil pageUtil );

	Long getCount(Map<String, Object> param, PageUtil pageUtil );

	boolean persistenceProject(Project p, Map<String, List<ProjectFollow>> map );

	List<ProjectFollow> findProjectFollowsList(Integer projectId );

	List<Customer> findCustomers();

	List<Project> findProjectListCombobox();

}
