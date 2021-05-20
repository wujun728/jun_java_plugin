package net.oschina.j2cache.serializer;

/**
 * UTF-8 String serializer
 */
public class StringSerializer implements Serializer<String> {

    @Override
    public byte[] serialize(String str) {
        return (str == null || str.length() == 0) ? EMPTY_BYTES : str.getBytes(UTF_8);
    }

    @Override
    public String deserialize(byte[] bytes) {
        return (bytes == null || bytes.length == 0) ? EMPTY_STR : new String(bytes, UTF_8);
    }

}
