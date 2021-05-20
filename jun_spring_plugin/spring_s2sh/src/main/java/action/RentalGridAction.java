package action;

import java.util.List;

import org.apache.struts2.json.annotations.JSON;

import po.Rental;
import service.RentalService;
import com.opensymphony.xwork2.ActionSupport;

public class RentalGridAction extends ActionSupport {
	private int current;//当前页面号
	private int rowCount;//每页行数
	private int total;//总行数
	private String searchPhrase;
	private List<Rental> rows;
	private RentalService rentalservice;
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
	public List<Rental> getRows() {
		return rows;
	}
	public void setRows(List<Rental> rows) {
		this.rows = rows;
	}
	@JSON(serialize=false)
	public RentalService getRentalservice() {
		return rentalservice;
	}
	public void setRentalservice(RentalService rentalservice) {
		this.rentalservice = rentalservice;
	}
	
	public String execute(){
		rows=rentalservice.getpageRentals(current, rowCount);
		total=rentalservice.getAllRenters().size();
		return "success";
	}
}
