package com.osmp.web.user.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 * 
 * @author: wangkaiping
 * @date: 2014年10月13日 上午11:25:25
 */

public class PrivilegeTreeGrid {
	private int id;
	private String text;
	private boolean checked;
	private List<PrivilegeTreeGrid> children = new ArrayList<PrivilegeTreeGrid>();
	private int pid;

	public int getPid() {
		return pid;
	}

	public void setPid(int pid) {
		this.pid = pid;
	}

	public List<PrivilegeTreeGrid> getChildren() {
		return children;
	}

	public void setChildren(List<PrivilegeTreeGrid> children) {
		this.children = children;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public int getId() {
		return id;
	}

	public String getText() {
		return text;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setText(String text) {
		this.text = text;
	}

}
