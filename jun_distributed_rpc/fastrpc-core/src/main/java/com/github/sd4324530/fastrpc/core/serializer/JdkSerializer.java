package com.github.sd4324530.fastrpc.core.serializer;

import com.github.sd4324530.fastrpc.core.message.IMessage;

import java.io.*;

/**
 * jdk默认的序列化
 *
 * @author peiyu
 */
public class JdkSerializer implements ISerializer {

    @Override
    public <M extends IMessage> M encoder(final byte[] bytes, final Class<M> messageClass) throws IOException, ClassNotFoundException {
        final M message;
        final ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
        final ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
        message = (M) objectInputStream.readObject();
        objectInputStream.close();
        inputStream.close();
        return message;
    }

    @Override
    public byte[] decoder(final IMessage message) throws IOException {
        final byte[] bytes;
        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        final ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
        objectOutputStream.writeObject(message);
        bytes = outputStream.toByteArray();
        objectOutputStream.close();
        outputStream.close();
        return bytes;
    }
}
