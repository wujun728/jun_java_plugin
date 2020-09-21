package app.common;

import java.io.Serializable;

public abstract class RedisEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	public abstract void setPK(String pk);

	public abstract String fatchPK();

	public abstract String fatchTableName();

	/**
	 * 是否自增主键
	 * @return
	 */
	public boolean isAutoIncr() {
		return false;
	}

	public String fatchKey() {
		return this.fatchTableName() + ":" + this.fatchPK();
	}

	public String fatchIncrKey() {
		return this.fatchTableName() + ":incr";
	}

}
