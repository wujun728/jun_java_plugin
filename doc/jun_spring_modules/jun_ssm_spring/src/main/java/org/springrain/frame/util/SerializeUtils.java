package org.springrain.frame.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 序列化/反序列化 工具类
 * 
 * @author caomei
 *
 */
public class SerializeUtils {

    private static final Logger logger = LoggerFactory.getLogger(SerializeUtils.class);

    private SerializeUtils() {
        throw new IllegalAccessError("工具类不能实例化");
    }

    /**
     * 序列化对象
     * 
     * @param object
     * @return
     */
    public static byte[] serialize(Object object) {
        if (object == null) {
            return null;
        }

        ObjectOutputStream oos = null;
        ByteArrayOutputStream baos = null;
        try {
            // 序列化
            baos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(baos);
            oos.writeObject(object);
            byte[] bytes = baos.toByteArray();
            return bytes;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 反序列化对象
     * 
     * @param bytes
     * @return
     */
    public static Object unserialize(byte[] bytes) {
        if (bytes == null) {
            return null;
        }
        ByteArrayInputStream bais = null;
        try {
            // 反序列化
            bais = new ByteArrayInputStream(bytes);
            ObjectInputStream ois = new ObjectInputStream(bais);
            return ois.readObject();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }
}
