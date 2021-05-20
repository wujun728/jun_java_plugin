package dao.impl;

import org.springframework.stereotype.Repository;

import po.Country;
import common.dao.BaseDaoImpl;
import dao.CountryDao;
@Repository("countryDao")
public class CountryDaoImpl extends BaseDaoImpl<Country> implements CountryDao{

}
