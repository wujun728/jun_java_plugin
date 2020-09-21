package com.opensource.nredis.proxy.monitor.model;

import java.io.Serializable;

/**
* model PsUserMenu
*
* @author liubing
* @date 2016/12/20 18:45
* @version v1.0.0
*/
public class PsUserMenu implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = -1518643547053318380L;
	private Integer id;
    private Integer userId;
    private Integer menuId;

    public void setId(Integer id){
        this.id = id;
    }
    public Integer getId(){
        return this.id;
    }

    public void setUserId(Integer userId){
        this.userId = userId;
    }
    public Integer getUserId(){
        return this.userId;
    }

    public void setMenuId(Integer menuId){
        this.menuId = menuId;
    }
    public Integer getMenuId(){
        return this.menuId;
    }

}