package com.erp.jee.pageModel;

public class UserRole implements java.io.Serializable {

	private String cid;
	private Role trole;
	private User tuser;

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public Role getTrole() {
		return trole;
	}

	public void setTrole(Role trole) {
		this.trole = trole;
	}

	public User getTuser() {
		return tuser;
	}

	public void setTuser(User tuser) {
		this.tuser = tuser;
	}

}