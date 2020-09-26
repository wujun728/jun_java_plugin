package net.oschina.j2cache.serializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xerial.snappy.Snappy;

import java.io.IOException;

/**
 * Fast-Serialization搭载Snappy实现,对序列化数据做压缩
 */
public class FstSnappySerializer<T> implements Serializer<T> {
    private static final Logger log = LoggerFactory.getLogger(FstSnappySerializer.class);

    private final Serializer<T> inner;

    public FstSnappySerializer() {
        this(new FstSerializer<T>());
    }

    public FstSnappySerializer(Serializer<T> innerSerializer) {
        this.inner = innerSerializer;
    }

    @Override
    public byte[] serialize(T value) {
        try {
            return Snappy.compress(inner.serialize(value));
        } catch (IOException e) {
            log.warn("序列化失败, value = " + value, e);
            return EMPTY_BYTES;
        }
    }

    @Override
    public T deserialize(byte[] bytes) {
        if (SerializerTools.isEmpty(bytes))
            return null;
        try {
            return inner.deserialize(Snappy.uncompress(bytes));
        } catch (IOException e) {
            log.warn("反序列化 bytes 失败.", e);
            return null;
        }
    }

}
