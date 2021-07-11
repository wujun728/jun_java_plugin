package com.utils.serialization;

import com.utils.pojo.Location;
import com.utils.pojo.User;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class JdkSerializeUtil extends  AbstractSerialize {

    private static  JdkSerializeUtil serializeUtil = new JdkSerializeUtil();

    public static JdkSerializeUtil getSingleton(){
        return  serializeUtil;
    }

    public static void main(String args[]){
        Location location = new Location("深圳");
        User user = User.builder().age(15).desc("libai").name("zhangfei").obj(location).build();

        byte[]  serData = JdkSerializeUtil.serializeUtil.serialize(user);
        User user1 = JdkSerializeUtil.serializeUtil.deserialize(serData,null);

        System.out.println(user1);
    }
    public <T> byte[] serialize(T obj) {

        if (obj  == null){
            throw new NullPointerException();
        }

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            ObjectOutputStream oos = new ObjectOutputStream(bos);

            oos.writeObject(obj);
            return bos.toByteArray();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new byte[0];
    }

    public <T> T deserialize(byte[] data, Class<T> clazz) {
        ByteArrayInputStream bis = new ByteArrayInputStream(data);

        try {
            ObjectInputStream ois = new ObjectInputStream(bis);
            T obj = (T)ois.readObject();
            return obj;
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return  null;
    }
}
