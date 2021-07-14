package com.jun.plugin.utils.serialization;

public abstract class AbstractSerialize {

    public  abstract   <T> byte[] serialize(T obj);
    public abstract  <T> T deserialize(byte[] data, Class<T> clazz);



}
