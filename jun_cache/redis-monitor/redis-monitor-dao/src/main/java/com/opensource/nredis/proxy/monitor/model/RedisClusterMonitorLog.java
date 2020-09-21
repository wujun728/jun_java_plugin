package com.opensource.nredis.proxy.monitor.model;

import java.io.Serializable;
import java.util.Date;
/**
* model RedisClusterMonitorLog
*
* @author liubing
* @date 2017/01/11 12:18
* @version v1.0.0
*/
public class RedisClusterMonitorLog implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6287297291623532153L;
	/**
	 * 主键
	 */
    private Integer id;
	/**
	 * 服务器
	 */
    private String redisServerHost;
	/**
	 * 端口号
	 */
    private Integer redisServerPort;
	/**
	 * 描述
	 */
    private String remark;
	/**
	 * 创建时间
	 */
    private Date createTime;
    
    private String createTimeString;
    

    public void setId(Integer id){
        this.id = id;
    }
    public Integer getId(){
        return this.id;
    }

    public void setRedisServerHost(String redisServerHost){
        this.redisServerHost = redisServerHost;
    }
    public String getRedisServerHost(){
        return this.redisServerHost;
    }

    public void setRedisServerPort(Integer redisServerPort){
        this.redisServerPort = redisServerPort;
    }
    public Integer getRedisServerPort(){
        return this.redisServerPort;
    }

    public void setRemark(String remark){
        this.remark = remark;
    }
    public String getRemark(){
        return this.remark;
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
    
    
}