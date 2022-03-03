package com.erp.service;

import java.util.List;

import com.erp.model.City;
import com.erp.model.Province;
import com.erp.viewModel.TreeModel;

public interface IAreaService
{

	List<TreeModel> findCities();

	List<Province> findProvinces();

	boolean addCities(City city );

}
