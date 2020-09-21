package com.osmp.web.system.strategy.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 * Description: 
 *
 * @author: chelongquan
 * @date: 2015年5月4日 上午9:18:37
 */
@Table(name="t_strategy")
public class Strategy {
	
	@Id
    @Column
	private String id;
	
	@Column
	private String name;
	
	@Column(name="forward_ip")
	private String forwardIp;
	
	@Column
	private String remark;
	
	@Column
	private int status;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getForwardIp() {
		return forwardIp;
	}

	public void setForwardIp(String forwardIp) {
		this.forwardIp = forwardIp;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

}
