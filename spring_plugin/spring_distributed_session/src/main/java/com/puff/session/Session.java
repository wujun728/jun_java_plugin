package com.puff.session;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 
 * @author dongchao
 *
 */
public class Session implements Serializable {

	private static final long serialVersionUID = -5253809238683262678L;

	private boolean isNew;
	private long lastAccessTime;
	private long createTime;

	private Map<String, Serializable> attribute;

	public Session() {
		this.createTime = System.currentTimeMillis();
		this.attribute = new HashMap<String, Serializable>();
	}

	public long getCreateTime() {
		return createTime;
	}

	public boolean isNew() {
		return isNew;
	}

	public void setNew(boolean isNew) {
		this.isNew = isNew;
	}

	public long getLastAccessTime() {
		return this.lastAccessTime;
	}

	public void setLastAccessTime(long lastAccessTime) {
		this.lastAccessTime = lastAccessTime;
	}

	public void putAttribute(String name, Serializable value) {
		attribute.put(name, value);
	}

	public Serializable removeAttribute(String name) {
		return attribute.remove(name);
	}

	public Serializable getAttribute(String name) {
		return attribute.get(name);
	}

	public Set<String> getAttributeNames() {
		return attribute.keySet();
	}

	public boolean isEmpaty() {
		return attribute == null || attribute.isEmpty();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((attribute == null) ? 0 : attribute.hashCode());
		result = prime * result + (int) (createTime ^ (createTime >>> 32));
		result = prime * result + (int) (lastAccessTime ^ (lastAccessTime >>> 32));
		result = prime * result + (isNew ? 1231 : 1237);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Session other = (Session) obj;
		if (attribute == null) {
			if (other.attribute != null)
				return false;
		} else if (!attribute.equals(other.attribute))
			return false;
		if (createTime != other.createTime)
			return false;
		if (lastAccessTime != other.lastAccessTime)
			return false;
		if (isNew != other.isNew)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Session [isNew=" + isNew + ", lastAccessTime=" + lastAccessTime + ", createTime=" + createTime + ", attribute=" + attribute + "]";
	}

}