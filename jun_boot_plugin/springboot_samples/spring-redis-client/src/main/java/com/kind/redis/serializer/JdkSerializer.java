package com.kind.redis.serializer;

import org.springframework.core.NestedIOException;

import java.io.*;

public class JdkSerializer<T> implements RedisSerializer<T> {

    public byte[] serialize(T t) throws RuntimeException {
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream(256);
        try {
            serialize(t, byteStream);
            return byteStream.toByteArray();
        } catch (Throwable ex) {
            ex.printStackTrace();
            throw new RuntimeException("序列化失败");
        }
    }

    @SuppressWarnings("unchecked")
    public T deserialize(byte[] bytes) throws RuntimeException {
        ByteArrayInputStream byteStream = new ByteArrayInputStream(bytes);
        try {
            return (T) deserialize(byteStream);
        } catch (Throwable ex) {
            throw new RuntimeException("反序列化失败");
        }
    }

    private void serialize(Object object, OutputStream outputStream)
            throws IOException {
        if (!(object instanceof Serializable)) {
            throw new IllegalArgumentException(getClass().getSimpleName()
                    + " requires a Serializable payload "
                    + "but received an object of type ["
                    + object.getClass().getName() + "]");
        }
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(
                outputStream);
        objectOutputStream.writeObject(object);
        objectOutputStream.flush();
    }

    private Object deserialize(InputStream inputStream) throws IOException {
        ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
        try {
            return objectInputStream.readObject();
        } catch (ClassNotFoundException ex) {
            throw new NestedIOException("Failed to deserialize object type", ex);
        }
    }

}
