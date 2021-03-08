package dao.impl;

import org.springframework.stereotype.Repository;

import po.City;
import common.dao.BaseDaoImpl;
import dao.CityDao;
@Repository("cityDao")
public class CityDaoImpl extends BaseDaoImpl<City> implements CityDao{

}
