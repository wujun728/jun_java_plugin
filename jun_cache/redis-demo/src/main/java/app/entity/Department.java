package app.entity;

import app.common.RedisEntity;

public class Department extends RedisEntity {

	private Integer id;
	private String departmentName;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
	
	

	@Override
	public void setPK(String pk) {
		this.id = Integer.valueOf(pk);
	}

	@Override
	public String fatchPK() {
		return String.valueOf(id);
	}

	@Override
	public String fatchTableName() {
		return "department";
	}

}
