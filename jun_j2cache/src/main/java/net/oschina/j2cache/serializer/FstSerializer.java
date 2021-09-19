package net.oschina.j2cache.serializer;

import org.nustaq.serialization.FSTConfiguration;
import org.nustaq.serialization.FSTObjectInput;
import org.nustaq.serialization.FSTObjectOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Fast-Serialization的实现
 */
public class FstSerializer<T> implements Serializer<T> {
    private static final Logger log = LoggerFactory.getLogger(FstSerializer.class);
    private static final FSTConfiguration conf = FSTConfiguration.createDefaultConfiguration();

    @Override
    public byte[] serialize(final T value) {
        if (value == null)
            return EMPTY_BYTES;
        ByteArrayOutputStream os = null;
        try {
            os = new ByteArrayOutputStream();
            FSTObjectOutput oos = conf.getObjectOutput(os);
            oos.writeObject(value);
            oos.flush();

            return os.toByteArray();
        } catch (Exception e) {
            log.warn("序列化失败, value = {}", value, e);
            return EMPTY_BYTES;
        } finally {
            try {
                if (os != null)
                    os.close();
            } catch (IOException e) {
                log.warn("序列化失败, value = {}", value, e);
            }
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public T deserialize(final byte[] bytes) {
        if (SerializerTools.isEmpty(bytes)) return null;
        ByteArrayInputStream is = null;
        try {
            is = new ByteArrayInputStream(bytes);
            FSTObjectInput ois = conf.getObjectInput(is);
            return (T) ois.readObject();
        } catch (Exception e) {
            log.warn("反序列化 bytes 失败.", e);
            return null;
        } finally {
            try {
                if (is != null)
                    is.close();
            } catch (IOException e) {
                log.warn("反序列化 bytes 失败.", e);
            }
        }
    }
}
