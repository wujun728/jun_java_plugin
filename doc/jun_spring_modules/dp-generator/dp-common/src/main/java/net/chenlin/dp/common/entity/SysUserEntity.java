package net.chenlin.dp.common.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

/**
 * 系统用户
 *
 * @author ZhouChenglin
 * @email yczclcn@163.com
 * @url www.chenlintech.com
 * @date 2017年8月8日 下午2:42:32
 */
public class SysUserEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;

	/**
	 * 用户id
	 */
	private Long userId;
	
	/**
	 * 机构id
	 */
	private Long orgId;
	
	/**
	 * 机构名称
	 */
	private String orgName;
	
	/**
	 * 用户名
	 */
	private String username;
	
	/**
	 * 密码
	 */
	private String password;
	
	/**
	 * 邮箱
	 */
	private String email;
	
	/**
	 * 手机号
	 */
	private String mobile;
	
	/**
	 * 状态(0：禁用   1：正常)
	 */
	private Integer status;
	
	/**
	 * 创建用户id
	 */
	private Long userIdCreate;
	
	/**
	 * 备注
	 */
	private String remark;
	
	
	/**
	 * 创建时间
	 */
	private Timestamp gmtCreate;
	
	/**
	 * 修改时间
	 */
	private Timestamp gmtModified;
	
	/**
	 * 角色id列表
	 */
	private List<Long> roleIdList;

	public SysUserEntity() {
		super();
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Long getUserIdCreate() {
		return userIdCreate;
	}

	public void setUserIdCreate(Long userIdCreate) {
		this.userIdCreate = userIdCreate;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Timestamp getGmtCreate() {
		return gmtCreate;
	}

	public void setGmtCreate(Timestamp gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	public Timestamp getGmtModified() {
		return gmtModified;
	}

	public void setGmtModified(Timestamp gmtModified) {
		this.gmtModified = gmtModified;
	}

	public List<Long> getRoleIdList() {
		return roleIdList;
	}

	public void setRoleIdList(List<Long> roleIdList) {
		this.roleIdList = roleIdList;
	}

}
