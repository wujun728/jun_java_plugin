package com.erp.service;

import java.util.List;

import com.erp.model.Organization;
import com.erp.viewModel.TreeModel;

public interface IOrganizationService 
{
	List<TreeModel> findOrganizationList();

	List<Organization> findOrganizationList(Integer id );

	boolean persistenceOrganization(Organization o );

	boolean delOrganization(Integer id );
}
