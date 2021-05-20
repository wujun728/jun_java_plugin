package mapper;

import java.util.List;

import po.City;
import po.Country;

public interface CityMapper {
	List<City> getCitys();
	List<City> getCountrycity(String countryname);//获取某国家城市列表
	Country getCitysbyCountry(String countryname);
}
