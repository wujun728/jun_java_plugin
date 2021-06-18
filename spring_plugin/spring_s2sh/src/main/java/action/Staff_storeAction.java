package action;

import java.util.List;

import org.apache.struts2.json.annotations.JSON;
import po.Staff;
import service.StaffService;

import com.opensymphony.xwork2.ActionSupport;

public class Staff_storeAction extends ActionSupport{
	private int current;//当前页面号
	private int rowCount;//每页行数
	private int total;//总行数
	private String searchPhrase;
	private List<Staff> rows;
	private StaffService staffservice;
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
	public List<Staff> getRows() {
		return rows;
	}
	public void setRows(List<Staff> rows) {
		this.rows = rows;
	}
	@JSON(serialize=false)
	public StaffService getStaffservice() {
		return staffservice;
	}
	public void setStaffservice(StaffService staffservice) {
		this.staffservice = staffservice;
	}
	public String execute(){
		rows=staffservice.getStaffandstore(current, rowCount);
		total=staffservice.getstaffnum();
		return "success";
	}
}
