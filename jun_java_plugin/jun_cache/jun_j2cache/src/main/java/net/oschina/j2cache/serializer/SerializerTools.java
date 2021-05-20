package net.oschina.j2cache.serializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

/**
 * 序列化工具类
 */
public class SerializerTools {
    private static final Logger log = LoggerFactory.getLogger(SerializerTools.class);

    private static final StringSerializer keySerializer = new StringSerializer();
    private static final FstSnappySerializer<Object> valueSerializer = new FstSnappySerializer<Object>();

    static boolean isEmpty(byte[] data) {
        return (data == null || data.length == 0);
    }

    /**
     * 序列化KEY
     * @param key {Object}
     * @return byte
     */
    public static byte[] serializeKey(final Object key) {
        return keySerializer.serialize(key.toString());
    }

    /**
     * 反序列化KEY
     * @param key {byte[]}
     * @return byte
     */
    public static Object deserializeKey(final byte[] key) {
        return keySerializer.deserialize(key);
    }

    /**
     * 序列化VALUE
     * @param value {Object}
     * @return byte
     */
    public static byte[] serializeValue(final Object value) {
        try {
            return valueSerializer.serialize(value);
        } catch (Exception e) {
            log.warn("value 序列化失败. value = {}", value, e);
            return null;
        }
    }

    /**
     * 反序列化VALUE
     * @param value {byte[]}
     * @return Object
     */
    public static Object deserializeValue(final byte[] value) {
        return valueSerializer.deserialize(value);
    }

    /**
     * 返回序列Collection的值
     * @param values {Collection<byte[]>}
     * @param clazz {Class<T>}
     * @param serializer {Serializer} 序列化工具
     * @param <T> 返回类型
     * @return T
     */
    @SuppressWarnings("unchecked")
    private static <T extends Collection<Object>> T deserializeValues(Collection<byte[]> values,
        Class<T> clazz, Serializer<Object> serializer) {
        if (values == null)
            return null;

        int valueCount = values.size();
        Collection<Object> _values =
                List.class.isAssignableFrom(clazz)
                        ? new ArrayList<Object>(valueCount)
                        : new HashSet<Object>(valueCount);

        for (byte[] bs : values) {
            _values.add(serializer.deserialize(bs));
        }
        return (T) _values;
    }

}
