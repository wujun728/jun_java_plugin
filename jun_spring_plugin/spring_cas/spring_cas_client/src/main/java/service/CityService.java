package service;

import java.util.List;

import po.City;
import po.Country;

public interface CityService {
	List<City> getCitylist();
	List<City> getpagecitylist(int pagenum, int pagesize);
	List<City> getCountryCity(String Countryname);
	List<City> getpageCountryCity(String Countryname,int pagenum, int pagesize);
	Country getCountryCitys(String Countryname);//一对多
}
