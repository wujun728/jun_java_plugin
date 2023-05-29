package com.jun.plugin.utils.serialization;

import io.protostuff.LinkedBuffer;
import io.protostuff.ProtostuffIOUtil;
import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeSchema;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.jun.plugin.utils.pojo.Location;
import com.jun.plugin.utils.pojo.User;



public class ProtostuffSerializeUtil  extends  AbstractSerialize{

    private static  ProtostuffSerializeUtil serializeUtil = new ProtostuffSerializeUtil();

    public static ProtostuffSerializeUtil getSingleton(){
        return  serializeUtil;
    }


    /**
     * 避免每次序列化都重新申请Buffer空间
     */
    private static LinkedBuffer buffer = LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE);
    /**
     * 缓存Schema
     */
    private static Map<Class<?>, Schema<?>> schemaCache = new ConcurrentHashMap<Class<?>, Schema<?>>();



    public static void main(String args[]){

        Location  location = new Location("深圳");
        User user = User.builder().age(15).desc("libai").name("zhangfei").obj(location).build();

        byte[]  serData = ProtostuffSerializeUtil.serializeUtil.serialize(user);
        User user1 = ProtostuffSerializeUtil.serializeUtil.deserialize(serData,User.class);

        System.out.println(user1);

    }

    public   <T> byte[] serialize(T obj) {

        if (obj  == null){
            throw new NullPointerException();
        }
        Class<T> clazz = (Class<T>) obj.getClass();
        Schema<T> schema = getSchema(clazz);
        byte[] data;
        try {
            data = ProtostuffIOUtil.toByteArray(obj, schema, buffer);
        } finally {
            buffer.clear();
        }

        return data;
    }

    public <T> T deserialize(byte[] data, Class<T> clazz) {
        Schema<T> schema = getSchema(clazz);
        T obj = schema.newMessage();
        ProtostuffIOUtil.mergeFrom(data, obj, schema);
        return obj;
    }


    private static <T> Schema<T> getSchema(Class<T> clazz) {
        Schema<T> schema = (Schema<T>) schemaCache.get(clazz);
        if (schema == null) {
            //这个schema通过RuntimeSchema进行懒创建并缓存
            //所以可以一直调用RuntimeSchema.getSchema(),这个方法是线程安全的
            schema = RuntimeSchema.getSchema(clazz);
            if (schema != null) {
                schemaCache.put(clazz, schema);
            }
        }

        return schema;
    }


}
