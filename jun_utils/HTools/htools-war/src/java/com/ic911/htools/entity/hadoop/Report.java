package com.ic911.htools.entity.hadoop;
/**
 * HDFS使用情况报表
 * @author changcheng
 */
public class Report {

	private long capacitySumByte = 0; //总容量字节数
	private long dfsUsedByte = 0; //总DFS使用字节数
	private long dfsUnUsedByte = 0; //总的非DFS使用字节数
	private long remainingSumByte = 0; //DFS剩余字节数
	
	private String capacitySum; //总容量
	private String dfsUsed; //总DFS使用量
	private String dfsUnUsed; //总的非DFS使用量
	private String remainingSum; //DFS剩余量
	
	private String dfsUsedRate; //DFS使用率
	private String dfsUnUsedRate; //非DFS使用率
	private String remainingSumRate; //DFS剩余率
	
	private String hours; //小时
	private int allNodes; //总节点数
	private int healthNodes; //健康节点个数
	private int badNodes; //故障节点个数
	
	public long getCapacitySumByte() {
		return capacitySumByte;
	}
	public void setCapacitySumByte(long capacitySumByte) {
		this.capacitySumByte = capacitySumByte;
	}
	public long getDfsUsedByte() {
		return dfsUsedByte;
	}
	public void setDfsUsedByte(long dfsUsedByte) {
		this.dfsUsedByte = dfsUsedByte;
	}
	public long getDfsUnUsedByte() {
		return dfsUnUsedByte;
	}
	public void setDfsUnUsedByte(long dfsUnUsedByte) {
		this.dfsUnUsedByte = dfsUnUsedByte;
	}
	public long getRemainingSumByte() {
		return remainingSumByte;
	}
	public void setRemainingSumByte(long remainingSumByte) {
		this.remainingSumByte = remainingSumByte;
	}
	public String getCapacitySum() {
		return capacitySum;
	}
	public void setCapacitySum(String capacitySum) {
		this.capacitySum = capacitySum;
	}
	public String getDfsUsed() {
		return dfsUsed;
	}
	public void setDfsUsed(String dfsUsed) {
		this.dfsUsed = dfsUsed;
	}
	public String getDfsUnUsed() {
		return dfsUnUsed;
	}
	public void setDfsUnUsed(String dfsUnUsed) {
		this.dfsUnUsed = dfsUnUsed;
	}
	public String getRemainingSum() {
		return remainingSum;
	}
	public void setRemainingSum(String remainingSum) {
		this.remainingSum = remainingSum;
	}
	public String getDfsUsedRate() {
		return dfsUsedRate;
	}
	public void setDfsUsedRate(String dfsUsedRate) {
		this.dfsUsedRate = dfsUsedRate;
	}
	public String getDfsUnUsedRate() {
		return dfsUnUsedRate;
	}
	public void setDfsUnUsedRate(String dfsUnUsedRate) {
		this.dfsUnUsedRate = dfsUnUsedRate;
	}
	public String getRemainingSumRate() {
		return remainingSumRate;
	}
	public void setRemainingSumRate(String remainingSumRate) {
		this.remainingSumRate = remainingSumRate;
	}
	
	public String getHours() {
		return hours;
	}
	public void setHours(String hours) {
		this.hours = hours;
	}
	public int getAllNodes() {
		return allNodes;
	}
	public void setAllNodes(int allNodes) {
		this.allNodes = allNodes;
	}
	public int getHealthNodes() {
		return healthNodes;
	}
	public void setHealthNodes(int healthNodes) {
		this.healthNodes = healthNodes;
	}
	public int getBadNodes() {
		return badNodes;
	}
	public void setBadNodes(int badNodes) {
		this.badNodes = badNodes;
	}
	
}
