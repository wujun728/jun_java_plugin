package com.kulv.base.service.impl;

import java.util.List;
import java.util.Map;

import com.kulv.base.service.BaseService;

public class SimpleServiceImpl<T>  implements BaseService<T>{

	@Override
	public int addBean(T t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteBeanById(String id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateBean(T t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public T getBeanById(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<T> getBeans() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int deleteBeansByIds(String... ids) {
		// TODO Auto-generated method stub
		return 0;
	}





	@Override
	public T getBeanByT(T t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<T> getBeansByParams(Map<Object, Object> map) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getCount(Map<Object, Object> map) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public T getBeanByParams(Map<Object, Object> map) {
		// TODO Auto-generated method stub
		return null;
	}



}
