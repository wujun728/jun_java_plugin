package com.opensource.nredis.proxy.monitor.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
/**
* model PsMenu
*
* @author liubing
* @date 2016/12/12 12:20
* @version v1.0.0
*/
public class PsMenu implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = -1063673134095434210L;
	private Integer id;
    private String title;
    private String icon;
    private String href;
    private Integer parentId;
    private String isLeaf;
    private Integer spread;
    private List<PsMenu> children =new ArrayList<PsMenu>();
    private Integer psUserId;
    
    public void setId(Integer id){
        this.id = id;
    }
    public Integer getId(){
        return this.id;
    }

    public void setTitle(String title){
        this.title = title;
    }
    public String getTitle(){
        return this.title;
    }

    public void setIcon(String icon){
        this.icon = icon;
    }
    public String getIcon(){
        return this.icon;
    }

    public void setHref(String href){
        this.href = href;
    }
    public String getHref(){
        return this.href;
    }

    public void setParentId(Integer parentId){
        this.parentId = parentId;
    }
    public Integer getParentId(){
        return this.parentId;
    }

    public void setIsLeaf(String isLeaf){
        this.isLeaf = isLeaf;
    }
    public String getIsLeaf(){
        return this.isLeaf;
    }

    public void setSpread(Integer spread){
        this.spread = spread;
    }
    public Integer getSpread(){
        return this.spread;
    }
	/**
	 * @return the children
	 */
	public List<PsMenu> getChildren() {
		return children;
	}
	/**
	 * @param children the children to set
	 */
	public void setChildren(List<PsMenu> children) {
		this.children = children;
	}
	/**
	 * @return the psUserId
	 */
	public Integer getPsUserId() {
		return psUserId;
	}
	/**
	 * @param psUserId the psUserId to set
	 */
	public void setPsUserId(Integer psUserId) {
		this.psUserId = psUserId;
	}
    
    
}