package com.opensource.nredis.proxy.monitor.model;

import java.io.Serializable;
import java.util.Date;
/**
* model RedisClusterSlave
*
* @author liubing
* @date 2017/01/11 12:18
* @version v1.0.0
*/
public class RedisClusterSlave implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5603521331920527600L;
	/**
	 * 主键
	 */
    private Integer id;
	/**
	 * redis主机
	 */
    private String redisServerHost;
	/**
	 * redis端口号
	 */
    private Integer redisServerPort;
	/**
	 * 运行状态
	 */
    private Integer runnerStatus;
    
    private String runnerStatusString;
	/**
	 * 机器状态
	 */
    private Integer redisServerStatus;
    
    private String redisServerStatusString;
	/**
	 * 创建时间
	 */
    private Date createTime;
    
    private String createTimeString;
	/**
	 * 主服务主键
	 */
    private Integer redisMasterId;
	/**
	 * 操作状态
	 */
    private Integer oprateStatus;
	/**
	 * 版本号
	 */
    private Integer version;
 
    /**
     * 权重
     */
    private Integer weight ;
    
    /**
     * zk 路径
     */
    private String path;
    
    private String redisMasterHost;
    
    private int redisMasterPort;
    
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

    public void setRunnerStatus(Integer runnerStatus){
        this.runnerStatus = runnerStatus;
    }
    public Integer getRunnerStatus(){
        return this.runnerStatus;
    }

    public void setRedisServerStatus(Integer redisServerStatus){
        this.redisServerStatus = redisServerStatus;
    }
    public Integer getRedisServerStatus(){
        return this.redisServerStatus;
    }

    public void setCreateTime(Date createTime){
        this.createTime = createTime;
    }
    public Date getCreateTime(){
        return this.createTime;
    }

    public void setRedisMasterId(Integer redisMasterId){
        this.redisMasterId = redisMasterId;
    }
    public Integer getRedisMasterId(){
        return this.redisMasterId;
    }

    public void setOprateStatus(Integer oprateStatus){
        this.oprateStatus = oprateStatus;
    }
    public Integer getOprateStatus(){
        return this.oprateStatus;
    }

    public void setVersion(Integer version){
        this.version = version;
    }
    public Integer getVersion(){
        return this.version;
    }
	/**
	 * @return the runnerStatusString
	 */
	public String getRunnerStatusString() {
		return runnerStatusString;
	}
	/**
	 * @param runnerStatusString the runnerStatusString to set
	 */
	public void setRunnerStatusString(String runnerStatusString) {
		this.runnerStatusString = runnerStatusString;
	}
	/**
	 * @return the redisServerStatusString
	 */
	public String getRedisServerStatusString() {
		return redisServerStatusString;
	}
	/**
	 * @param redisServerStatusString the redisServerStatusString to set
	 */
	public void setRedisServerStatusString(String redisServerStatusString) {
		this.redisServerStatusString = redisServerStatusString;
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
	 * @return the redisMasterHost
	 */
	public String getRedisMasterHost() {
		return redisMasterHost;
	}
	/**
	 * @param redisMasterHost the redisMasterHost to set
	 */
	public void setRedisMasterHost(String redisMasterHost) {
		this.redisMasterHost = redisMasterHost;
	}
	/**
	 * @return the redisMasterPort
	 */
	public int getRedisMasterPort() {
		return redisMasterPort;
	}
	/**
	 * @param redisMasterPort the redisMasterPort to set
	 */
	public void setRedisMasterPort(int redisMasterPort) {
		this.redisMasterPort = redisMasterPort;
	}
	/**
	 * @return the weight
	 */
	public Integer getWeight() {
		return weight;
	}
	/**
	 * @param weight the weight to set
	 */
	public void setWeight(Integer weight) {
		this.weight = weight;
	}
	/**
	 * @return the path
	 */
	public String getPath() {
		return path;
	}
	/**
	 * @param path the path to set
	 */
	public void setPath(String path) {
		this.path = path;
	}
    
    
}