package cn.itcast.mis.req.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import cn.itcast.mis.req.bo.UserBO;
import cn.itcast.mis.req.hibernate.UserT;

public class UserAction extends ActionSupport{

	private UserBO userBO;
	private List<UserT> results = new ArrayList<UserT>();
	
	public String showUserList(){
		results = userBO.queryUsersList();
		return "welcome";
	}
	public void setUserBO(UserBO userBO) {
		this.userBO = userBO;
	}
	public UserBO getUserBO() {
		return userBO;
	}
	public void setResults(List<UserT> results) {
		this.results = results;
	}
	public List<UserT> getResults() {
		return results;
	}
	
}
