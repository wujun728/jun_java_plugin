package com.ibeetl.admin.core.rbac.tree;

import java.util.List;
/**
 *  菜单，功能点，组织机构等跟树有关的结构的接口
 * @author Wujun
 *
 */
public interface TreeItem extends java.io.Serializable {
	public String getName();
	public Long getId();
	public List getChildren();
}
