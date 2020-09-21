package com.opensource.nredis.proxy.monitor.model;

import java.io.Serializable;
/**
* model SystemApplicationComponent
*
* @author liubing
* @date 2017/01/02 15:27
* @version v1.0.0
*/
public class SystemApplicationComponent implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -893489814117047677L;
	/**
	 * 主键
	 */
    private Integer id;
	/**
	 * 应用主键
	 */
    private Integer applicationId;
	/**
	 * 配置主键
	 */
    private Integer componentId;
 

    public void setId(Integer id){
        this.id = id;
    }
    public Integer getId(){
        return this.id;
    }

    public void setApplicationId(Integer applicationId){
        this.applicationId = applicationId;
    }
    public Integer getApplicationId(){
        return this.applicationId;
    }

    public void setComponentId(Integer componentId){
        this.componentId = componentId;
    }
    public Integer getComponentId(){
        return this.componentId;
    }

}