package com.jun.plugin.utils.serialization;

import com.caucho.hessian.io.HessianInput;
import com.caucho.hessian.io.HessianOutput;
import com.jun.plugin.utils.pojo.Location;
import com.jun.plugin.utils.pojo.User;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

public class HessianSerializeUtil extends  AbstractSerialize {

    public static  HessianSerializeUtil serializeUtil = new HessianSerializeUtil();
    public static HessianSerializeUtil getSingleton(){
        return  serializeUtil;
    }

    public static void main(String args[]){
        Location location = new Location("深圳");
        User user = User.builder().age(15).desc("libai").name("zhangfei").obj(location).build();

        byte[]  serData = HessianSerializeUtil.serializeUtil.serialize(user);
        User user1 = HessianSerializeUtil.serializeUtil.deserialize(serData,null);

        System.out.println(user1);
    }
    public <T> byte[] serialize(T obj) {

        if (obj  == null){
            throw new NullPointerException();
        }
        try{
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            HessianOutput ho = new HessianOutput(bos);
            ho.writeObject(obj);

            return  bos.toByteArray();
        }
        catch(Exception ex){
            throw new  RuntimeException();
        }

    }

    public <T> T deserialize(byte[] data, Class<T> clazz) {

        if (data == null){
            throw  new  NullPointerException();
        }
        try{
            ByteArrayInputStream bis = new ByteArrayInputStream(data);
            HessianInput hi = new HessianInput(bis);
            return (T)hi.readObject();

        }
        catch(Exception ex){
            throw new  RuntimeException();
        }

    }
}
