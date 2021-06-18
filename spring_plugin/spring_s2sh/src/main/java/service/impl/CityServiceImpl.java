package service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import po.City;
import service.CityService;
import dao.CityDao;
@Transactional(propagation=Propagation.REQUIRED,isolation=Isolation.DEFAULT,timeout=5)
@Service("cityservice")
public class CityServiceImpl implements CityService{
	@Autowired
	private CityDao cityDao;
	
	public CityDao getCityDao() {
		return cityDao;
	}

	public void setCityDao(CityDao cityDao) {
		this.cityDao = cityDao;
	}

	public List<City> getCountrysGroupbyid(int a, int b) {
		List<City> list=cityDao.findByPage("from City as c join fetch c.country",a,b);
		return list; 
	}

	public List<City> getAllCountrysgroupbyid() {
		List<City> list=cityDao.find("from City as c join fetch c.country");
		return list;
	}

}
