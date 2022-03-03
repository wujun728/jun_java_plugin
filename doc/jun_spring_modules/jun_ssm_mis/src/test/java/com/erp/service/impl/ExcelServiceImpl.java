package com.erp.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.erp.dao.PublicDao;
import com.erp.service.IExcelService;

@Service("excelService")
public class ExcelServiceImpl implements IExcelService
{
	@SuppressWarnings("rawtypes")
	private PublicDao publicDao;

	@SuppressWarnings("rawtypes")
	@Autowired
	public void setPublicDao(PublicDao publicDao )
	{
		this.publicDao = publicDao;
	}
	
	@SuppressWarnings("unchecked")
	public <T> List<T> findExcelExportList(String isCheckedIds,Class<T> clazz)
	{
		List<T> list=new ArrayList<T>();
		String[] ids = isCheckedIds.split(",");
		for (String id : ids)
		{
			list.add((T) publicDao.get(clazz, Integer.valueOf(id)));
		}
		return list;
	}
}
