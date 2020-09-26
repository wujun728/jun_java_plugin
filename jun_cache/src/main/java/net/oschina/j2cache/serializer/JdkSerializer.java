package net.oschina.j2cache.serializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

/**
 * JDK Serializer 实现
 */
public class JdkSerializer<T> implements Serializer<T> {
	private static final Logger log = LoggerFactory.getLogger(JdkSerializer.class);

    @Override
    public byte[] serialize(final T value) {
        if (value == null) return EMPTY_BYTES;
        ByteArrayOutputStream os = null;
        ObjectOutputStream oos = null;
        try {
            os = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(os);
            oos.writeObject(value);
            oos.flush();

            return os.toByteArray();
        } catch (Exception e) {
            log.warn("序列化失败, value = " + value, e);
            return EMPTY_BYTES;
        } finally {
    		try {
    			if (os != null)
    				os.close();
    			if (oos != null)
    				oos.close();
			} catch (IOException e) {
				log.warn("序列化失败, value = " + value, e);
			}
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public T deserialize(final byte[] bytes) {
        if (SerializerTools.isEmpty(bytes))
            return null;
        ByteArrayInputStream is = null;
        ObjectInputStream ois = null;
        try {
            is = new ByteArrayInputStream(bytes);
            ois = new ObjectInputStream(is);
            return (T) ois.readObject();
        } catch (Exception e) {
            log.warn("反序列化 bytes 失败.", e);
            return null;
        } finally {
    		try {
    			if (is != null)
    				is.close();
    			if (ois != null)
    				ois.close();
			} catch (IOException e) {
				log.warn("反序列化 bytes 失败.", e);
			}
        }
    }
}