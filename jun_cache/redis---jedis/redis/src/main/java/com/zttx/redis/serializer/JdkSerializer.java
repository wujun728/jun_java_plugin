/**<p>项目名：</p>
 * <p>包名：	com.zttx.redis.core</p>
 * <p>文件名：JdkSerializer.java</p>
 * <p>版本信息：</p>
 * <p>日期：2015年1月14日-下午1:11:10</p>
 * Copyright (c) 2015singno公司-版权所有
 */
package com.zttx.redis.serializer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;

import org.springframework.core.NestedIOException;

/**<p>名称：JdkSerializer.java</p>
 * <p>描述：</p>
 * <pre>
 *         
 * </pre>
 * @author 鲍建明
 * @date 2015年1月14日 下午1:11:10
 * @version 1.0.0
 */
public class JdkSerializer<T> implements RedisSerializer<T> {

	
	public byte[] serialize(T t) throws RuntimeException {
		ByteArrayOutputStream byteStream = new ByteArrayOutputStream(256);
		try  {
			serialize(t, byteStream);
			return byteStream.toByteArray();
		}
		catch (Throwable ex) {
			ex.printStackTrace();
			throw new RuntimeException("序列化失败");
		}
	}

	@SuppressWarnings("unchecked")
	public T deserialize(byte[] bytes) throws RuntimeException {
		ByteArrayInputStream byteStream = new ByteArrayInputStream(bytes);
		try {
			return (T) deserialize(byteStream);
		}
		catch (Throwable ex) {
			throw new RuntimeException("反序列化失败");
		}
	}
	
	
	private void serialize(Object object, OutputStream outputStream) throws IOException {
		if (!(object instanceof Serializable)) {
			throw new IllegalArgumentException(getClass().getSimpleName() + " requires a Serializable payload " +
					"but received an object of type [" + object.getClass().getName() + "]");
		}
		ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
		objectOutputStream.writeObject(object);
		objectOutputStream.flush();
	}
	
	private Object deserialize(InputStream inputStream) throws IOException {
		ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
		try {
			return objectInputStream.readObject();
		}
		catch (ClassNotFoundException ex) {
			throw new NestedIOException("Failed to deserialize object type", ex);
		}
	}
	
}
