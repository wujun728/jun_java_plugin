package com.opensource.nredis.proxy.monitor.model;

import java.io.Serializable;
/**
* model PsDict
*
* @author liubing
* @date 2016/12/21 17:32
* @version v1.0.0
*/
public class PsDict implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8802437236322663753L;
	/**
	 * 主键
	 */
    private Integer id;
	/**
	 * 字段名称
	 */
    private String psKey;
	/**
	 * 字段值
	 */
    private String psValue;
	/**
	 * 模块
	 */
    private String psMoudle;
	/**
	 * 状态
	 */
    private Integer psStatus;
    
    private String psStatusName;
    
    public void setId(Integer id){
        this.id = id;
    }
    public Integer getId(){
        return this.id;
    }

    public void setPsKey(String psKey){
        this.psKey = psKey;
    }
    public String getPsKey(){
        return this.psKey;
    }

    public void setPsValue(String psValue){
        this.psValue = psValue;
    }
    public String getPsValue(){
        return this.psValue;
    }

    public void setPsMoudle(String psMoudle){
        this.psMoudle = psMoudle;
    }
    public String getPsMoudle(){
        return this.psMoudle;
    }

    public void setPsStatus(Integer psStatus){
        this.psStatus = psStatus;
    }
    public Integer getPsStatus(){
        return this.psStatus;
    }
	/**
	 * @return the psStatusName
	 */
	public String getPsStatusName() {
		return psStatusName;
	}
	/**
	 * @param psStatusName the psStatusName to set
	 */
	public void setPsStatusName(String psStatusName) {
		this.psStatusName = psStatusName;
	}
    
}