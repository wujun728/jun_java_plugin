/**
 * @filename:User 2019年6月1日
 * @project webFlux-redis  V1.0
 * Copyright(c) 2018 BianPeng Co. Ltd. 
 * All right reserved. 
 */
package com.flying.cattle.wf.entity;

import java.io.Serializable;
import java.util.Date;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**   
 * Copyright: Copyright (c) 2019 
 * 
 * <p>说明： 用户实体</P>
 * @version: V1.0
 * @author: BianPeng
 * 
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class User extends ParentEntity implements Serializable{

	private static final long serialVersionUID = -3440675839637783638L;
	
	/**
	 *	用户账号 
	 */
	@NotNull(message = "账号不能为空")
	@Pattern(regexp = "^[a-zA-Z0-9]{6,20}$",message = "账号必须是无特殊字符6-20位字符串")
	private String account;
	
	/**
	 *	用户密码 
	 */
	@NotNull(message = "密码不能为空")
	@Pattern(regexp = "^[a-zA-Z0-9]{6,20}$",message = "秘密必须是无特殊字符6-20位字符串")
	private String password;
	
	/**
	 *	用户昵称 
	 */
	@NotNull(message = "昵称不能为空")
	@Pattern(regexp = "^.{0,20}$",message = "昵称不能超过20个字节")
	private String nickname;
	
	/**
	 *	用户邮箱 
	 */
	@Pattern(regexp = "^(.+)@(.+)$",message = "邮箱的格式不合法")
	private String email;
	
	/**
	 *	用户电话
	 */
	@Pattern(regexp = "^1([38][0-9]|4[579]|5[0-3,5-9]|6[6]|7[0135678]|9[89])\\d{8}$",message = "手机号的格式不合法")
	private String phone;
	
	/**
	 *	用户性别（男：true）
	 */
	private Boolean sex;
	
	
	/**
	 *	用户生日
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(pattern="yyyy-MM-dd")
	@Past(message = "生日必须在当前时间之前")
	private Date birthday;
	
	/**
	 *	用户的省
	 */
	private String Province; 
	
	/**
	 *	用户的市
	 */
	private String city; 
	
	/**
	 *	用户的县（区）
	 */
	private String county;

	/**
	 *	用户的详细地址
	 */
	@Pattern(regexp = "^.{0,200}$",message = "地址不能超过200个字节")
	private String address;
	
	/**
	 *	用户状态
	 */
	private String state;
	
	/**
	 *	保留地段
	 */
	private String keep;
	
	/**
	 *	创建时间
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date createTime;
	
	/**
	 *	更新时间
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	private Date updateTime;
}
