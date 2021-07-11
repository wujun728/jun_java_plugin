package com.example.demo.pojo.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "sys_user")
@Data
public class SysUser implements Serializable {

	@Id
	@Column(name = "id", nullable = false)
	private String id;

	@Column(name = "login_name", nullable = false)
	private String loginName;

	@Column(name = "email", nullable = false)
	private String email;

	@Column(name = "delete_enum", nullable = false)
	private Integer deleteEnum;

	@Column(name = "create_date", nullable = false)
	private Date createDate;

	@Version
	@Column(name = "lock_version", nullable = true)
	private Long lockVersion;
	
}