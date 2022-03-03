package com.erp.jee.pageModel;

public class RoleAuth implements java.io.Serializable {

	private String cid;
	private Auth tauth;
	private Role trole;

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public Auth getTauth() {
		return tauth;
	}

	public void setTauth(Auth tauth) {
		this.tauth = tauth;
	}

	public Role getTrole() {
		return trole;
	}

	public void setTrole(Role trole) {
		this.trole = trole;
	}

}