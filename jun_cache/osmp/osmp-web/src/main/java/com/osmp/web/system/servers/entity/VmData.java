package com.osmp.web.system.servers.entity;

import java.util.Date;

/**
 * Description: 服务器性能
 * 
 * @author: wangkaiping
 * @date: 2015年4月28日 下午5:47:52
 */
public class VmData {

	private String ip;
	private double memTotal;
	private double memUse;
	private double cpuUse;
	private Date time;

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public double getMemTotal() {
		return memTotal;
	}

	public void setMemTotal(double memTotal) {
		this.memTotal = memTotal;
	}

	public double getMemUse() {
		return memUse;
	}

	public void setMemUse(double memUse) {
		this.memUse = memUse;
	}

	public double getCpuUse() {
		return cpuUse;
	}

	public void setCpuUse(double cpuUse) {
		this.cpuUse = cpuUse;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}
}
