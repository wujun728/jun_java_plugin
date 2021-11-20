package com.jun.plugin.utils.serialization;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jun.plugin.utils.pojo.Location;
import com.jun.plugin.utils.pojo.User;

public class SerializeTest {

    public static void main(String args[]){

        long  start = 0;
        long  end = 0;

        Location location = new Location("深圳");

        List<Integer> list = new ArrayList();
        list.add(1);
        list.add(2);

        Map <String,String> map  = new HashMap<String, String>();
        map.put("a","a1");
        map.put("b","b1");
        User user = User.builder().age(15).desc("libai").name("zhangfei").obj(location).list(list).map(map).build();
        System.out.println(user);


        System.out.println("JDK序列化......");
        start = System.currentTimeMillis();
        byte[]  serData = JdkSerializeUtil.getSingleton().serialize(user);
        System.out.println("JDK序列化占用字节数：" + serData.length);
        User user1 = JdkSerializeUtil.getSingleton().deserialize(serData,null);
        end = System.currentTimeMillis();
        System.out.println("JDK序列化花费时间：" + (end - start));
        System.out.println(user1);

        System.out.println();

        System.out.println("Protostuff序列化......");
        start = System.currentTimeMillis();
        serData = ProtostuffSerializeUtil.getSingleton().serialize(user);
        System.out.println("Protostuff序列化占用字节数：" + serData.length);
        user1 = ProtostuffSerializeUtil.getSingleton().deserialize(serData,User.class);
        end = System.currentTimeMillis();
        System.out.println("Protostuff序列化花费时间：" + (end - start));
        System.out.println(user1);


        System.out.println();


        System.out.println("fastjson序列化......");
        start = System.currentTimeMillis();
        serData = FastjsonSerializeUtil.getSingleton().serialize(user);
        System.out.println("fastjson序列化占用字节数：" + serData.length);
        user1 = FastjsonSerializeUtil.getSingleton().deserialize(serData,User.class);
        end = System.currentTimeMillis();
        System.out.println("fastjson序列化花费时间：" + (end - start));
        System.out.println(user1);




    }
}
