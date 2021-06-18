package service;

import java.util.List;

import po.Country;


public interface CountryService {
	public List<Country> getallcountrys();
	public void addCountry(Country c);
	public void updatecountry(Country c);
	public Country findCountry(short id);
	public void deleteCountry(Country c);
	public void updateCountry(Country c);
}
