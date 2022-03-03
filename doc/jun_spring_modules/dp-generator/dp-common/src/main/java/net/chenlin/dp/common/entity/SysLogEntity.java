package net.chenlin.dp.common.entity;

import java.io.Serializable;
import java.sql.Timestamp;


/**
 * 系统日志
 *
 * @author ZhouChenglin
 * @email yczclcn@163.com
 * @url www.chenlintech.com
 * @date 2017年8月14日 下午8:05:17
 */
public class SysLogEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * 日志id
	 */
	private Long id;
	
	/**
	 * 操作用户id
	 */
	private Long userId;
	
	/**
	 * 操作用户
	 */
	private String username;
	
	/**
	 * 操作
	 */
	private String operation;
	
	/**
	 * 方法
	 */
	private String method;
	
	/**
	 * 参数
	 */
	private String params;
	
	/**
	 * 耗时
	 */
	private Long time;
	
	/**
	 * 操作ip地址
	 */
	private String ip;
	
	/**
	 * 创建时间
	 */
	private Timestamp gmtCreate;

	public SysLogEntity() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}

	public Long getTime() {
		return time;
	}

	public void setTime(Long time) {
		this.time = time;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Timestamp getGmtCreate() {
		return gmtCreate;
	}

	public void setGmtCreate(Timestamp gmtCreate) {
		this.gmtCreate = gmtCreate;
	}
	
}
