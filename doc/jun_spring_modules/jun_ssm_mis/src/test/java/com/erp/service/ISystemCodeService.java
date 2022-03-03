package com.erp.service;

import java.util.List;

import com.erp.model.SystemCode;
import com.erp.viewModel.TreeModel;

public interface ISystemCodeService
{

	List<SystemCode> findSystemCodeList(String id);
	
	List<SystemCode> findSystemCodeList(Integer id);

	List<TreeModel> findSystemCodeList();

	boolean persistenceSystemCodeDig(SystemCode systemCode,String permissionName,Integer codePid);

	boolean delSystemCode(Integer codeId );

	List<SystemCode> findSystemCodeByType(String codeMyid );

}
