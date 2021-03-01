package net.oschina.j2cache.serializer;

import java.nio.charset.Charset;

/**
 * 缓存序列化接口，允许多种实现<br>
 * 目前两种序列化方式：<br>
 * 1.StringSerializer<br>
 * 2.FstSerializer搭载在Snappy上面实现<br>
 * 3.JdkSerializer
 * 4.
 */
public interface Serializer<T> {

    byte[] EMPTY_BYTES = new byte[0];

    String EMPTY_STR = "";

    Charset UTF_8 = Charset.forName("UTF-8");

    /**
     * Serialize Object
     */
    byte[] serialize(final T value);

    /**
     * Deserialize to object
     */
    T deserialize(final byte[] bytes);
}
