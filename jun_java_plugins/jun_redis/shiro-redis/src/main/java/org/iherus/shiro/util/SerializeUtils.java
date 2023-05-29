/**
 * Copyright (c) 2016-~, Bosco.Liao (bosco_liao@126.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.iherus.shiro.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.NotSerializableException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.iherus.shiro.exception.SerializationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>序列化工具类</p>
 * <p>Description:基于JDK原生实现，支持序列化和反序列化.</p>
 * @author Wujun
 * @version 1.0.0
 * @date 2016年4月10日-下午8:25:27
 */
public final class SerializeUtils {

	private static final Logger logger = LoggerFactory.getLogger(SerializeUtils.class);

	private static final int DEFAULT_BUFFER_SIZE = 1024; // default buffer size

	private SerializeUtils() {

	}

	/**
	 * <p>Serialize the given object to a byte array.<p>
	 * @param object the object to serialize
	 * @return an array of bytes representing the object in a portable fashion
	 */
	public static byte[] serialize(final Object object) {
		if (object == null) {
			return new byte[0];
		}
		byte[] serializedValue = null;
		ByteArrayOutputStream byteOutStream = null;
		ObjectOutputStream objectOutStream = null;
		try {
			byteOutStream = new ByteArrayOutputStream(DEFAULT_BUFFER_SIZE);
			objectOutStream = new ObjectOutputStream(byteOutStream);
			try {
				objectOutStream.writeObject(object);
			} catch (NotSerializableException ex) {
				throw new SerializationException(object.getClass().getName() 
						+ " does not implement Serializable or externalizable.", ex);
			}
			objectOutStream.flush();
			serializedValue = byteOutStream.toByteArray();
			if (logger.isDebugEnabled()) {
				logger.debug("Object serialzed successfully.");
			}
		} catch (Throwable e) {
			logger.error("Failed to serialize object of type: " + object.getClass().getName() + ".", e);
		} finally {
			if (objectOutStream != null) {
				try {
					objectOutStream.close();
					byteOutStream.close();
				} catch (IOException e) {
					// ignore
				}
			}
		}
		return serializedValue;
	}

	/**
	 * <p>Deserialize the byte array into an object.</p>
	 * @param serializedBytes a serialized object
	 * @return the result of deserializing the bytes
	 */
	public static Object deserialize(final byte[] serializedBytes) {
		if (null == serializedBytes || serializedBytes.length == 0) {
			return null;
		}
		Object source = null;
		ByteArrayInputStream byteInStream = null;
		ObjectInputStream objectInStream = null;
		try {
			byteInStream = new ByteArrayInputStream(serializedBytes);
			objectInStream = new ObjectInputStream(byteInStream);
			try {
				source = objectInStream.readObject();
			} catch (ClassNotFoundException ex) {
				throw new SerializationException("Failed to deserialize object type.", ex);
			}
			if (logger.isDebugEnabled()) {
				logger.debug("SerializedBytes deserialzed successfully.");
			}
		} catch (Throwable e) {
			logger.error("Failed to deserialize.", e);
		} finally {
			if (objectInStream != null) {
				try {
					objectInStream.close();
					byteInStream.close();
				} catch (IOException e) {
					// ignore
				}
			}
		}
		return source;
	}

}
