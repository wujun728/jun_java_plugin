package app.entity;

import app.common.RedisIncrEntity;

public class Student extends RedisIncrEntity {

	private int id; // incr
	private String stuName;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getStuName() {
		return stuName;
	}

	public void setStuName(String stuName) {
		this.stuName = stuName;
	}

	@Override
	public void setPK(String pk) {
		if(pk != null) {
			this.id = Integer.valueOf(pk);
		}
	}

	@Override
	public String fatchPK() {
		return String.valueOf(id);
	}

	@Override
	public String fatchTableName() {
		return "student";
	}

}
