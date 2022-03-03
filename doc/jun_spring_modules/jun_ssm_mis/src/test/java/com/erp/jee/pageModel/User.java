package com.erp.jee.pageModel;

import java.util.Date;

public class User implements java.io.Serializable {

	// 自己添加的属性
	private Date ccreatedatetimeStart;
	private Date cmodifydatetimeStart;
	private Date ccreatedatetimeEnd;
	private Date cmodifydatetimeEnd;
	private String ids;
	private String oldPwd;
	private String roleIds;
	private String roleNames;
	private String q;// 补全登录时用到的属性
	private int page;// 当前页
	private int rows;// 每页显示记录数
	private String sort;// 排序字段名
	private String order;// 按什么排序(asc,desc)

	private String cid;
	private String cname;
	private String realname;
	private String cpwd;
	private String mail;
	private String subcompany;
	private String dept;
	private String mobile;
	private String usertype;
	private String status;
	private Date ccreatedatetime;
	private Date cmodifydatetime;

	public String getQ() {
		return q;
	}

	public void setQ(String q) {
		this.q = q;
	}

	public String getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(String roleIds) {
		this.roleIds = roleIds;
	}

	public String getRoleNames() {
		return roleNames;
	}

	public void setRoleNames(String roleNames) {
		this.roleNames = roleNames;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public String getOldPwd() {
		return oldPwd;
	}

	public void setOldPwd(String oldPwd) {
		this.oldPwd = oldPwd;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public Date getCcreatedatetimeStart() {
		return ccreatedatetimeStart;
	}

	public void setCcreatedatetimeStart(Date ccreatedatetimeStart) {
		this.ccreatedatetimeStart = ccreatedatetimeStart;
	}

	public Date getCmodifydatetimeStart() {
		return cmodifydatetimeStart;
	}

	public void setCmodifydatetimeStart(Date cmodifydatetimeStart) {
		this.cmodifydatetimeStart = cmodifydatetimeStart;
	}

	public Date getCcreatedatetimeEnd() {
		return ccreatedatetimeEnd;
	}

	public void setCcreatedatetimeEnd(Date ccreatedatetimeEnd) {
		this.ccreatedatetimeEnd = ccreatedatetimeEnd;
	}

	public Date getCmodifydatetimeEnd() {
		return cmodifydatetimeEnd;
	}

	public void setCmodifydatetimeEnd(Date cmodifydatetimeEnd) {
		this.cmodifydatetimeEnd = cmodifydatetimeEnd;
	}

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public String getCname() {
		return cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}

	public String getCpwd() {
		return cpwd;
	}

	public void setCpwd(String cpwd) {
		this.cpwd = cpwd;
	}

	public Date getCcreatedatetime() {
		return ccreatedatetime;
	}

	public void setCcreatedatetime(Date ccreatedatetime) {
		this.ccreatedatetime = ccreatedatetime;
	}

	public Date getCmodifydatetime() {
		return cmodifydatetime;
	}

	public void setCmodifydatetime(Date cmodifydatetime) {
		this.cmodifydatetime = cmodifydatetime;
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getUsertype() {
		return usertype;
	}

	public void setUsertype(String usertype) {
		this.usertype = usertype;
	}

	public String getSubcompany() {
		return subcompany;
	}

	public void setSubcompany(String subcompany) {
		this.subcompany = subcompany;
	}

	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}