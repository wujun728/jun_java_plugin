/**
 * 
 */
package com.opensource.nredis.proxy.monitor.platform;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liubing
 *
 */
public class Menu {
	
	private Integer id;
    private String title;
    private String icon;
    private String href;
    private Integer spread;
    private Integer psUserId;
    private Integer parentId;

    private List<Menu> children =new ArrayList<Menu>();

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the icon
	 */
	public String getIcon() {
		return icon;
	}

	/**
	 * @param icon the icon to set
	 */
	public void setIcon(String icon) {
		this.icon = icon;
	}

	/**
	 * @return the href
	 */
	public String getHref() {
		return href;
	}

	/**
	 * @param href the href to set
	 */
	public void setHref(String href) {
		this.href = href;
	}

	/**
	 * @return the spread
	 */
	public Integer getSpread() {
		return spread;
	}

	/**
	 * @param spread the spread to set
	 */
	public void setSpread(Integer spread) {
		this.spread = spread;
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

	/**
	 * @return the children
	 */
	public List<Menu> getChildren() {
		return children;
	}

	/**
	 * @param children the children to set
	 */
	public void setChildren(List<Menu> children) {
		this.children = children;
	}

	/**
	 * @return the parentId
	 */
	public Integer getParentId() {
		return parentId;
	}

	/**
	 * @param parentId the parentId to set
	 */
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	
    
    
}
