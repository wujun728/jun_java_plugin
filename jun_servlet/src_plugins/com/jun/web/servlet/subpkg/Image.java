package com.jun.web.servlet.subpkg;


public class Image {
	private String id;
	private String oldname;
	private String newname;
	private String dt;
	private String ip;
	private String note;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getOldname() {
		return oldname;
	}
	public void setOldname(String oldname) {
		this.oldname = oldname;
	}
	public String getNewname() {
		return newname;
	}
	public void setNewname(String newname) {
		this.newname = newname;
	}
	public String getDt() {
		return dt;
	}
	public void setDt(String dt) {
		this.dt = dt;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	
	public Image() {
	}
	public Image(String id, String oldname, String newname, String dt,
			String ip, String note) {
		this.id = id;
		this.oldname = oldname;
		this.newname = newname;
		this.dt = dt;
		this.ip = ip;
		this.note = note;
	}
	@Override
	public String toString() {
		return "Image [id=" + id + ", oldname=" + oldname + ", newname="
				+ newname + ", dt=" + dt + ", ip=" + ip + ", note=" + note
				+ "]";
	}
}
