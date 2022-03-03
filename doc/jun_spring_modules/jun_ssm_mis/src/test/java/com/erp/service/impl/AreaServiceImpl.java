package com.erp.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.erp.dao.PublicDao;
import com.erp.model.City;
import com.erp.model.Province;
import com.erp.service.IAreaService;
import com.erp.viewModel.Attributes;
import com.erp.viewModel.TreeModel;
import com.jun.plugin.utils.Constants;
@SuppressWarnings("unchecked")
@Service("areaService")
public class AreaServiceImpl implements IAreaService
{
	@SuppressWarnings("rawtypes")
	private PublicDao publicDao;
	@SuppressWarnings("rawtypes")
	@Autowired
	public void setPublicDao(PublicDao publicDao )
	{
		this.publicDao = publicDao;
	}
	
	
	public List<TreeModel> findCities()
	{
		List<TreeModel> list=new ArrayList<TreeModel>();
		String hql=" from Province t where t.status='A'";
		String hql2=" from City t where t.status='A'";
		List<Province> list1 = publicDao.find(hql);
		for (Province province : list1)
		{
			TreeModel t=new TreeModel();
			t.setId(province.getProvinceId()+"");
			t.setName(province.getName());
			t.setPid(null);
			t.setState(Constants.TREE_STATUS_CLOSED);
			Attributes attributes=new Attributes();
			attributes.setStatus("p");
			t.setAttributes(attributes);
			list.add(t);	
		}
		List<City> list2 = publicDao.find(hql2);
		for (City city : list2)
		{
			TreeModel t=new TreeModel();
			t.setId("0"+city.getCityId());
			t.setName(city.getName());
			t.setPid(city.getProvinceId()+"");
			t.setState(Constants.TREE_STATUS_OPEN);
			Attributes attributes=new Attributes();
			attributes.setStatus("c");
			t.setAttributes(attributes);
			list.add(t);
		}
		return list;
	}
	
	public List<Province> findProvinces()
	{
		String hql="from Province t where t.status='A'";
		return publicDao.find(hql);
	}
	
	public boolean addCities(City city)
	{
		Integer userId=Constants.getCurrendUser().getUserId();
		city.setCreated(new Date());
		city.setLastmod(new Date());
		city.setStatus(Constants.PERSISTENCE_STATUS);
		city.setCreater(userId);
		city.setModifyer(userId);
		publicDao.save(city);
		return true;
	}
}
