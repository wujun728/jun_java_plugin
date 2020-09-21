package com.opensource.nredis.proxy.monitor.model;

import java.io.Serializable;
import java.util.Date;
/**
* model SystemComponent
*
* @author liubing
* @date 2017/01/02 15:27
* @version v1.0.0
*/
public class SystemComponent implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4302035575835725256L;
	/**
	 * 主键
	 */
    private Integer id;
	/**
	 * 组件名称
	 */
    private String componentKey;
	/**
	 * 组件值
	 */
    private String componentValue;
	/**
	 * 关键配置
	 */
    private String keyConfig;
	/**
	 * 创建人
	 */
    private String createUserName;
	/**
	 * 创建时间
	 */
    private Date createTime;
 
    private String createTimeString;//
    
    public void setId(Integer id){
        this.id = id;
    }
    public Integer getId(){
        return this.id;
    }

    public void setComponentKey(String componentKey){
        this.componentKey = componentKey;
    }
    public String getComponentKey(){
        return this.componentKey;
    }

    public void setComponentValue(String componentValue){
        this.componentValue = componentValue;
    }
    public String getComponentValue(){
        return this.componentValue;
    }

    public void setKeyConfig(String keyConfig){
        this.keyConfig = keyConfig;
    }
    public String getKeyConfig(){
        return this.keyConfig;
    }

    public void setCreateUserName(String createUserName){
        this.createUserName = createUserName;
    }
    public String getCreateUserName(){
        return this.createUserName;
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