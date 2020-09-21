/*   
 * Project: OSMP
 * FileName: RedisDao.java
 * version: V1.0
 */
package com.osmp.intf.define.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 
 * Description:
 * @author: wangkaiping
 * @date: 2016年8月8日 上午11:42:54上午10:51:30
 */
public interface RedisDao {

	public boolean addObject(final String key, final Object object, final Long timeout, Class<?> clazz) throws Exception;

	public Object getObject(final String key);

	public boolean addString(final String key, final String value, final Long timeout);

	public boolean addString(final Map<String, String> keyValueList, final Long timeout);

	public String getString(final String key);

	public boolean addHash(final String key, final String field, final String value, final Long timeout);

	public boolean addHash(final String key, final Map<String, String> fieldValueList, final Long timeout);

	public Object getHashField(final String key, final String field);

	public Map<byte[], byte[]> getHashAll(final String key, final String field);

	public void delete(final String key);

	public Long push(String key, String value);

	public String pop(String key);

	public Long in(String key, String value);

	public String out(String key);

	public Long length(String key);

	public List<Object> range(String key, int start, int end);

	public void remove(String key, long i, String value);

	public String index(String key, long index);

	public void set(String key, long index, String value);

	public void trim(String key, long start, int end);

	public Long addSet(final String key, final String value, final Long timeout);

	public Set<byte[]> getSet(final String key);
}
