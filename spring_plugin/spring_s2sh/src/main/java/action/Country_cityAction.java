package action;

import java.util.List;

import org.apache.struts2.json.annotations.JSON;

import po.City;
import service.CityService;

import com.opensymphony.xwork2.ActionSupport;

public class Country_cityAction extends ActionSupport{
	private int current;//当前页面号
	private int rowCount;//每页行数
	private int total;//总行数
	private String searchPhrase;
	private List<City> rows;
	private CityService cityservice;
	public int getCurrent() {
		return current;
	}
	public void setCurrent(int current) {
		this.current = current;
	}
	public int getRowCount() {
		return rowCount;
	}
	public void setRowCount(int rowCount) {
		this.rowCount = rowCount;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public String getSearchPhrase() {
		return searchPhrase;
	}
	public void setSearchPhrase(String searchPhrase) {
		this.searchPhrase = searchPhrase;
	}
	
	public List<City> getRows() {
		return rows;
	}
	public void setRows(List<City> rows) {
		this.rows = rows;
	}
	@JSON(serialize=false)
	public CityService getCityservice() {
		return cityservice;
	}
	public void setCityservice(CityService cityservice) {
		this.cityservice = cityservice;
	}
	
	public String execute(){
		rows=cityservice.getCountrysGroupbyid(current, rowCount);
		total=cityservice.getAllCountrysgroupbyid().size();
		return "success";
	}
	
}
