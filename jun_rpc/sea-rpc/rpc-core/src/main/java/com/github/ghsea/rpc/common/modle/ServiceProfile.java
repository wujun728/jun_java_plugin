package com.github.ghsea.rpc.common.modle;

import java.util.Date;

import com.alibaba.fastjson.JSON;

public class ServiceProfile extends BaseProfile {

	private static final long serialVersionUID = -1325449889555466537L;

	private int weight = 1;

	private NodeId nodeId;

	private Date createTime;

	public ServiceProfile() {

	}

	public ServiceProfile(String pool, String service, String version, NodeId nodeId) {
		super(pool, service, version);

		this.nodeId = nodeId;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public NodeId getNodeId() {
		return nodeId;
	}

	public void setNodeId(NodeId nodeId) {
		this.nodeId = nodeId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String toString() {
		return JSON.toJSONString(this);
	}

}
