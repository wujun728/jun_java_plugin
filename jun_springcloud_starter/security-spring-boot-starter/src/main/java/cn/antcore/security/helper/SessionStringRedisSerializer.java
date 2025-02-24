package cn.antcore.security.helper;

import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 *
 * <br/>
 * <p>Created by Hong.</p>
 * <p>2021/3/24</p>
 **/
public class SessionStringRedisSerializer extends StringRedisSerializer {

    private String prefix() {
        return "session:";
    }

    @Override
    public String deserialize(byte[] bytes) {
        String str = super.deserialize(bytes);
        str = str.substring(prefix().length());
        return str;
    }

    @Override
    public byte[] serialize(String string) {
        return super.serialize(prefix() + string);
    }
}
