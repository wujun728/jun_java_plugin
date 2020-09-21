package com.opensource.nredis.proxy.monitor.model;

import java.io.Serializable;
import java.util.Date;
/**
* model PsUser
*
* @author liubing
* @date 2016/12/12 12:20
* @version v1.0.0
*/
public class PsUser implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = -6031604957989162744L;
	private Integer id;
    private String username;
    private String password;
    private String userEmail;
    private Date createTime;
    private String createTimestring;
    private String createUser;

    public void setId(Integer id){
        this.id = id;
    }
    public Integer getId(){
        return this.id;
    }

    public void setUsername(String username){
        this.username = username;
    }
    public String getUsername(){
        return this.username;
    }

    public void setPassword(String password){
        this.password = password;
    }
    public String getPassword(){
        return this.password;
    }

    public void setUserEmail(String userEmail){
        this.userEmail = userEmail;
    }
    public String getUserEmail(){
        return this.userEmail;
    }

    public void setCreateTime(Date createTime){
        this.createTime = createTime;
    }
    public Date getCreateTime(){
        return this.createTime;
    }

    public void setCreateUser(String createUser){
        this.createUser = createUser;
    }
    public String getCreateUser(){
        return this.createUser;
    }
	/**
	 * @return the createTimestring
	 */
	public String getCreateTimestring() {
		return createTimestring;
	}
	/**
	 * @param createTimestring the createTimestring to set
	 */
	public void setCreateTimestring(String createTimestring) {
		this.createTimestring = createTimestring;
	}
    
}