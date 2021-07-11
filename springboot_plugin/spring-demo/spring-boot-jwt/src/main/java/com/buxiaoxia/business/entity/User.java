package com.buxiaoxia.business.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by xw on 2017/1/5.
 * 2017-01-05 16:13
 */
@Data
@Entity
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private int id;
	private String userName;
	private String loginName;
	private String password;
	private String roles;
	private String email;
	private String location;
	private String signature;
	@JsonIgnore
	private Date createAt;
	@JsonIgnore
	private Date updateAt;

}
