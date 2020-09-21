/**<p>项目名：</p>
 * <p>包名：	com.zttx.redis.core</p>
 * <p>文件名：RedisSerializer.java</p>
 * <p>版本信息：</p>
 * <p>日期：2015年1月14日-上午11:40:33</p>
 * Copyright (c) 2015singno公司-版权所有
 */
package com.zttx.redis.serializer;


/**<p>名称：RedisSerializer.java</p>
 * <p>描述：</p>
 * <pre>
 *         
 * </pre>
 * @author 鲍建明
 * @date 2015年1月14日 上午11:40:33
 * @version 1.0.0
 */
public interface RedisSerializer<T> {

	/**
	 * Serialize the given object to binary data.
	 * 
	 * @param t object to serialize
	 * @return the equivalent binary data
	 */
	byte[] serialize(T t) throws RuntimeException;

	/**
	 * Deserialize an object from the given binary data.
	 * 
	 * @param bytes object binary representation
	 * @return the equivalent object instance
	 */
	T deserialize(byte[] bytes) throws RuntimeException;
}
