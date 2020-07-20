package com.ic911.core.hadoop.domain;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import org.hibernate.validator.constraints.NotBlank;

import com.ic911.core.commons.NetUtils;
import com.ic911.core.domain.BaseEntity;
/**
 * 基本节点信息公用继承
 * @author caoyang
 */
@SuppressWarnings("serial")
@MappedSuperclass
public abstract class BaseNodeInfo extends BaseEntity implements Comparable<BaseNodeInfo>{
	
	@NotBlank(message="主机名不能为空!")
	@Column(nullable=false)
	private String hostname;
	
	@NotBlank(message="节点状态不能为空!")
	@Column(nullable=false)
	private NodeStatus status = NodeStatus.LIVE;
	
	private String ip;

	public String getHostname() {
		return this.hostname;
	}

	public void setHostname(String hostname) {
		this.hostname = hostname;
	}

	public String getIp() {
		return ip==null?NetUtils.getIpByHostname(hostname):ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public NodeStatus getStatus() {
		return status;
	}

	public void setStatus(NodeStatus status) {
		this.status = status;
	}
	
	@Override
	public String toString() {
		return getIp();
	}

	@Override
	public int compareTo(BaseNodeInfo node){
		return getIp().compareTo(node.getIp());
	}
	
	@Override
	public boolean equals(Object obj) {
		BaseNodeInfo node = (BaseNodeInfo)obj;
		return getIp().equals(node.getIp());
	}
	
	@Override
	public int hashCode() {
		return getIp().hashCode();
	}
	
}
