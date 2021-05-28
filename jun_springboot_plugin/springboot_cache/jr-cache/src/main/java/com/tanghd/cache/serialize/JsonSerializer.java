package com.tanghd.cache.serialize;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonSerializer implements Serializer {

    private String CHARSET = "UTF-8";
    private static final ObjectMapper mapper = new ObjectMapper();
    static {
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES); // 忽略不存在的属性
        mapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
        mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss,SSS"));
    }

    public static String ObjToStr(Object obj) {
        if (obj == null) {
            throw new RuntimeException("Failed to map object, which is null");
        }
        try {
            return mapper.writeValueAsString(obj);
        } catch (Exception e) {
            String msg = String.format("Failed to map object {}", obj);
            throw new RuntimeException(msg, e);
        }
    }

    public static <T> T strToObj(String jsonStr, Class<T> type) {
        try {
            return mapper.readValue(jsonStr, type);
        } catch (Exception e) {
            String msg = String.format("Failed to parse json %s", jsonStr);
            throw new RuntimeException(msg, e);
        }
    }

    public String getCHARSET() {
        return CHARSET;
    }

    public void setCHARSET(String cHARSET) {
        CHARSET = cHARSET;
    }

    @Override
    public byte[] serialize(Object o) throws Exception {
        return ObjToStr(o).getBytes(CHARSET);
    }

    @Override
    public String serializeToString(Object o) throws Exception {
        return ObjToStr(o);
    }

    private String getString(byte[] buf) throws Exception {
        return new String(buf, CHARSET);
    }

    @Override
    public <T> T deserialize(byte[] buf, Class<T> clz) throws Exception {
        return strToObj(getString(buf), clz);
    }

    @Override
    public <T> List<T> deserializeList(byte[] buf, Class<T> clz) throws Exception {
        return deserializeList(getString(buf), clz);
    }

    @Override
    public <T> Set<T> deserializeSet(byte[] buf, Class<T> clz) throws Exception {
        return deserializeSet(getString(buf), clz);
    }

    @Override
    public <K, V> Map<K, V> deserializeMap(byte[] buf, Class<K> keyClz, Class<V> valueClz) throws Exception {
        return deserializeMap(getString(buf), keyClz, valueClz);
    }

    @Override
    public <T> T deserialize(String content, Class<T> clz) throws Exception {
        return strToObj(content, clz);
    }

    @Override
    public <T> List<T> deserializeList(String content, Class<T> clz) throws Exception {
        JavaType type = mapper.getTypeFactory().constructCollectionType(ArrayList.class, clz);
        return mapper.readValue(content, type);
    }

    @Override
    public <T> Set<T> deserializeSet(String content, Class<T> clz) throws Exception {
        JavaType type = mapper.getTypeFactory().constructCollectionLikeType(ArrayList.class, clz);
        return mapper.readValue(content, type);
    }

    @Override
    public <K, V> Map<K, V> deserializeMap(String content, Class<K> keyClz, Class<V> valueClz) throws Exception {
        JavaType type = mapper.getTypeFactory().constructMapType(HashMap.class, keyClz, valueClz);
        return mapper.readValue(content, type);
    }

}
