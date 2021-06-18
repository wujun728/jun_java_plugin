package service;

import java.util.List;

import po.City;

public interface CityService {
	List<City> getCountrysGroupbyid(int a,int b);
	List<City> getAllCountrysgroupbyid();
}
