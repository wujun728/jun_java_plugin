package app.common;

/**
 * 具有自增主键的实体类
 */
public abstract class RedisIncrEntity extends RedisEntity {
	private static final long serialVersionUID = 1L;

	@Override
	public boolean isAutoIncr() {
		return true;
	}
	
}
