package com.erp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.erp.dao.PublicDao;
import com.erp.model.Currency;
import com.erp.service.ICurrencyService;
@Service("currencyService")
public class CurrencyServiceImpl implements ICurrencyService
{
	private PublicDao<Currency> publicDao;
	@Autowired
	public void setPublicDao(PublicDao<Currency> publicDao )
	{
		this.publicDao = publicDao;
	}
	
	public List<Currency> findCurrencyList()
	{
		String hql="from Currency t";
		return publicDao.find(hql);
	}
}
