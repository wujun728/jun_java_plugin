package com.ic911.htools.entity.hadoop;

import java.io.Serializable;
/**
 * 节点健康状况报表
 * @author changcheng
 */
public class NodeStatusReport implements Serializable{

	private static final long serialVersionUID = 6513183299035120023L;
	private Integer hours; //小时
	private Integer allnodes; //总节点数
	private Integer healthnodes; //健康节点个数
	private Integer badnodes; //故障节点个数
	public Integer getHours() {
		return hours;
	}
	public void setHours(Integer hours) {
		this.hours = hours;
	}
	public Integer getAllnodes() {
		return allnodes;
	}
	public void setAllnodes(Integer allnodes) {
		this.allnodes = allnodes;
	}
	public Integer getHealthnodes() {
		return healthnodes;
	}
	public void setHealthnodes(Integer healthnodes) {
		this.healthnodes = healthnodes;
	}
	public Integer getBadnodes() {
		return badnodes;
	}
	public void setBadnodes(Integer badnodes) {
		this.badnodes = badnodes;
	}

}
