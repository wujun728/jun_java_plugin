package com.opensource.nredis.proxy.monitor.model;

import java.io.Serializable;
import java.util.Date;
/**
* model SystemApplication
*com.opensource.nredis.proxy.monitor
* @author liubing
* @date 2016/12/31 08:09
* @version v1.0.0
*/
public class SystemApplication implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4642156180622904056L;
	/**
	 * 主键
	 */
    private Integer id;
	/**
	 * 应用名称
	 */
    private String applicationName;
	/**
	 * 应用主机
	 */
    private String applicationHost;
	/**
	 * 应用端口
	 */
    private Integer applicationPort;
	/**
	 * jmx主机
	 */
    private String jmxHost;
	/**
	 * jmx端口号
	 */
    private Integer jmxPort;
	/**
	 * jmx用户名
	 */
    private String jmxUserName;
	/**
	 * jmx密码
	 */
    private String jmxPassWord;
	/**
	 * 创建时间
	 */
    private Date createTime;
    
    private String createTimeString;
	/**
	 * 创建名称
	 */
    private String createUserName;
	/**
	 * 版本号
	 */
    private Integer version;
    /**
     * 状态
     */
    private Integer status;
    
    private Integer jmxStatus;
    
    private String jmxStatusString;
    
    public void setId(Integer id){
        this.id = id;
    }
    public Integer getId(){
        return this.id;
    }

    public void setApplicationName(String applicationName){
        this.applicationName = applicationName;
    }
    public String getApplicationName(){
        return this.applicationName;
    }

    public void setApplicationHost(String applicationHost){
        this.applicationHost = applicationHost;
    }
    public String getApplicationHost(){
        return this.applicationHost;
    }

    public void setApplicationPort(Integer applicationPort){
        this.applicationPort = applicationPort;
    }
    public Integer getApplicationPort(){
        return this.applicationPort;
    }

    public void setJmxHost(String jmxHost){
        this.jmxHost = jmxHost;
    }
    public String getJmxHost(){
        return this.jmxHost;
    }

    public void setJmxPort(Integer jmxPort){
        this.jmxPort = jmxPort;
    }
    public Integer getJmxPort(){
        return this.jmxPort;
    }

    public void setJmxUserName(String jmxUserName){
        this.jmxUserName = jmxUserName;
    }
    public String getJmxUserName(){
        return this.jmxUserName;
    }

    public void setJmxPassWord(String jmxPassWord){
        this.jmxPassWord = jmxPassWord;
    }
    public String getJmxPassWord(){
        return this.jmxPassWord;
    }

    public void setCreateTime(Date createTime){
        this.createTime = createTime;
    }
    public Date getCreateTime(){
        return this.createTime;
    }

    public void setCreateUserName(String createUserName){
        this.createUserName = createUserName;
    }
    public String getCreateUserName(){
        return this.createUserName;
    }

    public void setVersion(Integer version){
        this.version = version;
    }
    public Integer getVersion(){
        return this.version;
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
	 * @return the status
	 */
	public Integer getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
	/**
	 * @return the jmxStatus
	 */
	public Integer getJmxStatus() {
		return jmxStatus;
	}
	/**
	 * @param jmxStatus the jmxStatus to set
	 */
	public void setJmxStatus(Integer jmxStatus) {
		this.jmxStatus = jmxStatus;
	}
	/**
	 * @return the jmxStatusString
	 */
	public String getJmxStatusString() {
		return jmxStatusString;
	}
	/**
	 * @param jmxStatusString the jmxStatusString to set
	 */
	public void setJmxStatusString(String jmxStatusString) {
		this.jmxStatusString = jmxStatusString;
	}
    
    
}