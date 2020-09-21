package com.opensource.nredis.proxy.monitor.model;

import java.io.Serializable;
import java.util.Date;
/**
* model SystemApplicationMonitor
*
* @author liubing
* @date 2016/12/31 08:09
* @version v1.0.0
*/
public class SystemApplicationMonitor implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5517428562415890098L;
	/**
	 * 主键
	 */
    private Integer id;
	/**
	 * 监控模块
	 */
    private Integer monitorMoudle;
	/**
	 * 监控名称
	 */
    private String monitorApplicationName;
	/**
	 * 监控状态
	 */
    private Integer monitorStatus;
    
    private String monitorStatusString;
	/**
	 * 监控主机
	 */
    private String monitorHost;
	/**
	 * 监控端口号
	 */
    private Integer monitorPort;
	/**
	 * 创建时间
	 */
    private Date createTime;
    
    private String createTimeString;
    
    private String monitorMoudleString;

    public void setId(Integer id){
        this.id = id;
    }
    public Integer getId(){
        return this.id;
    }

    public void setMonitorMoudle(Integer monitorMoudle){
        this.monitorMoudle = monitorMoudle;
    }
    public Integer getMonitorMoudle(){
        return this.monitorMoudle;
    }

    public void setMonitorApplicationName(String monitorApplicationName){
        this.monitorApplicationName = monitorApplicationName;
    }
    public String getMonitorApplicationName(){
        return this.monitorApplicationName;
    }

    public void setMonitorStatus(Integer monitorStatus){
        this.monitorStatus = monitorStatus;
    }
    public Integer getMonitorStatus(){
        return this.monitorStatus;
    }

    public void setMonitorHost(String monitorHost){
        this.monitorHost = monitorHost;
    }
    public String getMonitorHost(){
        return this.monitorHost;
    }

    public void setMonitorPort(Integer monitorPort){
        this.monitorPort = monitorPort;
    }
    public Integer getMonitorPort(){
        return this.monitorPort;
    }

    public void setCreateTime(Date createTime){
        this.createTime = createTime;
    }
    public Date getCreateTime(){
        return this.createTime;
    }
	/**
	 * @return the createTimeString
	 */
	public String getCreateTimeString() {
		return createTimeString;
	}
	/**
	 * @param createTimeString the createTimeString to set
	 */
	public void setCreateTimeString(String createTimeString) {
		this.createTimeString = createTimeString;
	}
	/**
	 * @return the monitorMoudleString
	 */
	public String getMonitorMoudleString() {
		return monitorMoudleString;
	}
	/**
	 * @param monitorMoudleString the monitorMoudleString to set
	 */
	public void setMonitorMoudleString(String monitorMoudleString) {
		this.monitorMoudleString = monitorMoudleString;
	}
	/**
	 * @return the monitorStatusString
	 */
	public String getMonitorStatusString() {
		return monitorStatusString;
	}
	/**
	 * @param monitorStatusString the monitorStatusString to set
	 */
	public void setMonitorStatusString(String monitorStatusString) {
		this.monitorStatusString = monitorStatusString;
	}
    
    
}