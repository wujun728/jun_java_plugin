package com.ic911.htools.entity.hadoop;
/**
 * 优化向导
 * @author changcheng
 */
public class Optimization {

	private String hostname;
	private int serverType;
	private int networkType;
	private int clusterSize;
	private int fileType;
	public String getHostname() {
		return hostname;
	}
	public void setHostname(String hostname) {
		this.hostname = hostname;
	}
	public int getServerType() {
		return serverType;
	}
	public void setServerType(int serverType) {
		this.serverType = serverType;
	}
	public int getNetworkType() {
		return networkType;
	}
	public void setNetworkType(int networkType) {
		this.networkType = networkType;
	}
	public int getClusterSize() {
		return clusterSize;
	}
	public void setClusterSize(int clusterSize) {
		this.clusterSize = clusterSize;
	}
	public int getFileType() {
		return fileType;
	}
	public void setFileType(int fileType) {
		this.fileType = fileType;
	}
	
}
