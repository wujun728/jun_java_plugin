package com.puff.storage;

import java.util.Properties;

public interface Storage {
	public void start(Properties props);

	public void close();

	public void put(String key, Object value, int expiredTime);

	public <T> T get(String key);

	public void remove(String key);

	public void expire(String session_key, int expiredTime);

}