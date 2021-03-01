package net.oschina.j2cache;

/**
 * 缓存相关异常容器
 */
@SuppressWarnings("serial")
public class CacheException extends RuntimeException {
	
	public CacheException(String s) {
		super(s);
	}

	public CacheException(String s, Throwable e) {
		super(s, e);
	}

	public CacheException(Throwable e) {
		super(e);
	}

}
