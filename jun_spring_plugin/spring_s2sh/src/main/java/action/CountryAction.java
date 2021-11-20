package action;

import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.struts2.json.annotations.JSON;
import org.springframework.beans.factory.annotation.Autowired;

import po.City;
import po.Country;
import service.CountryService;

public class CountryAction {
	List<Country> list;
	@Autowired
	private CountryService countryservice;
	@JSON(serialize=false)
	public CountryService getCountryservice() {
		return countryservice;
	}
	
	public void setCountryservice(CountryService countryservice) {
		this.countryservice = countryservice;
	}
	
	public List<Country> getList() {
		return list;
	}

	public void setList(List<Country> list) {
		this.list = list;
	}

	public String getcountry(){
		list=countryservice.getallcountrys();
		System.out.println(list.get(0).getCitys().size()+"dddd");
		return "success";
	}
	//级联保存
	public String addcountry(){
		Country c=new Country();
		c.setCountry("wsz");
		c.setLast_update(new Date());
		Set<City> citys=new HashSet<City>();
		City c1=new City();
		City c2=new City();
		City c3=new City();
		c1.setCity("java");
		c1.setCountry(c);//注意在从表中设置映射关系，否则无法级联保存
		c1.setLast_update(new Date());
		c2.setCity("python");
		c2.setCountry(c);//注意在从表中设置映射关系，否则无法级联保存
		c2.setLast_update(new Date());
		c3.setCity("js");
		c3.setCountry(c);//注意在从表中设置映射关系，否则无法级联保存
		c3.setLast_update(new Date());
		citys.add(c2);
		citys.add(c1);
		citys.add(c3);
		c.setCitys(citys);
		countryservice.addCountry(c);
		return "success";
	}
	//级联删除，删除主表，自动删除从表
	public String deleteCountry(){
		Country c=countryservice.findCountry((short)123);
		countryservice.deleteCountry(c);
		return "success";
	}
	//级联更新
	public String updatecountry(){
		Country c=countryservice.findCountry((short) 125);
		Set<City> citys=c.getCitys();
//		City a=new City();
//		a.setCity("mysql");
//		a.setCountry(c);//注意在从表中设置映射关系，否则无法级联保存
//		a.setLast_update(new Date());
//		citys.add(a);
	    for (Iterator<City> it = citys.iterator(); it.hasNext();) {
	        City val = it.next();
//			if(city.getCity().equals("java"))
//			city.setCity("java-ee");
//		if(city.getCity().equals("js"))
//			city=null;
	        if (val.getCity().equals("mysql")) {
	           it.remove();
	        }
	    }
	    c.setCitys(citys);
		countryservice.updatecountry(c);
		return "success";
	}
}
