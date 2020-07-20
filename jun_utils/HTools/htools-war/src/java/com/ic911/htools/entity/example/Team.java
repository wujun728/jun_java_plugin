package com.ic911.htools.entity.example;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.ic911.core.domain.BaseEntity;

@Entity
@Table(name = "t_team")
public class Team extends BaseEntity{
	
	private static final long serialVersionUID = -1193535438777132900L;
	@NotNull(message="团队名称不能为空!")
	@Column(unique=true,nullable = false)
	private String name;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
