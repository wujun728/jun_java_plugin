package cn.springmvc.jpa.web.vo;

import java.util.List;

import cn.springmvc.jpa.entity.BaseEntity;

/**
 * @author Vincent.wang
 *
 */
public class PermissionVo implements BaseEntity<String> {

	private static final long serialVersionUID = -2051933842290600230L;

	private String id;

	/** 菜单名称 **/
	private String name;

	private String key;

	private Integer hide;

	/** URL **/
	private String url;

	/** 显示顺序 **/
	private Integer sort;

	/** 父节点 **/
	private String parentKey;

	/** 子菜单 **/
	private List<PermissionVo> children;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public Integer getHide() {
		return hide;
	}

	public void setHide(Integer hide) {
		this.hide = hide;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getParentKey() {
		return parentKey;
	}

	public void setParentKey(String parentKey) {
		this.parentKey = parentKey;
	}

	public List<PermissionVo> getChildren() {
		return children;
	}

	public void setChildren(List<PermissionVo> children) {
		this.children = children;
	}

}
