package com.utils.test;

import com.utils.pojo.TestPojo;
import com.utils.serialization.*;
import org.springframework.util.StopWatch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *功能描述
 * @author lgj  序列化性能测试
 * @Description
 * @date 3/26/19

序列化对象类：java.util.ArrayList  序列化类：com.utils.serialization.JdkSerializeUtil  序列化花费时间：369 字节长度 =  788948
序列化对象类：java.util.ArrayList  序列化类：com.utils.serialization.FastjsonSerializeUtil  序列化花费时间：417 字节长度 =  788891
序列化对象类：java.util.ArrayList  序列化类：com.utils.serialization.HessianSerializeUtil  序列化花费时间：242 字节长度 =  788897

序列化对象类：java.util.HashMap  序列化类：com.utils.serialization.JdkSerializeUtil  序列化花费时间：284 字节长度 =  1577862
序列化对象类：java.util.HashMap  序列化类：com.utils.serialization.FastjsonSerializeUtil  序列化花费时间：393 字节长度 =  1577781
序列化对象类：java.util.HashMap  序列化类：com.utils.serialization.HessianSerializeUtil  序列化花费时间：184 字节长度 =  1577785

序列化对象类：com.utils.pojo.TestPojo  序列化类：com.utils.serialization.JdkSerializeUtil  序列化花费时间：21 字节长度 =  977
序列化对象类：com.utils.pojo.TestPojo  序列化类：com.utils.serialization.FastjsonSerializeUtil  序列化花费时间：58 字节长度 =  1192
序列化对象类：com.utils.pojo.TestPojo  序列化类：com.utils.serialization.HessianSerializeUtil  序列化花费时间：4 字节长度 =  1319
序列化对象类：com.utils.pojo.TestPojo  序列化类：com.utils.serialization.ProtostuffSerializeUtil  序列化花费时间：67 字节长度 =  825

 综合来看,hessian 的效率是比较高的。fastjson效率相对偏低。
 */

public class SerializeTest {

    public static void main(String args[]){

        JdkSerializeUtil jdkSerializeUtil = JdkSerializeUtil.getSingleton();
        FastjsonSerializeUtil fastjsonSerializeUtil = FastjsonSerializeUtil.getSingleton();
        HessianSerializeUtil hessianSerializeUtil = HessianSerializeUtil.getSingleton();
        ProtostuffSerializeUtil protostuffSerializeUtil = ProtostuffSerializeUtil.getSingleton();

        // for list test
        List<String>   list = new ArrayList<String>();

        for(int  i = 0;i < 100000; i++){
            list.add(String.valueOf(i));
        }
        int testCount = 1;

        performanceTest(jdkSerializeUtil,list,testCount,ArrayList.class);
        performanceTest(fastjsonSerializeUtil,list,testCount,ArrayList.class);
        performanceTest(hessianSerializeUtil,list,testCount,ArrayList.class);
        System.out.println();


        //for map test
        Map<String,String> map = new HashMap();

        for(int  i = 0;i < 100000; i++){
            map.put(String.valueOf(i),String.valueOf(i));
        }

        performanceTest(jdkSerializeUtil,map,testCount,HashMap.class);
        performanceTest(fastjsonSerializeUtil,map,testCount,HashMap.class);
        performanceTest(hessianSerializeUtil,map,testCount,HashMap.class);
        System.out.println();

        //for other object test
        TestPojo  testPojo = new TestPojo();
        performanceTest(jdkSerializeUtil,testPojo,testCount,TestPojo.class);
        performanceTest(fastjsonSerializeUtil,testPojo,testCount,TestPojo.class);
        performanceTest(hessianSerializeUtil,testPojo,testCount,TestPojo.class);
        performanceTest(protostuffSerializeUtil,testPojo,testCount,TestPojo.class);

        System.out.println();
    }
    /**
     *功能描述 
     * @author lgj
     * @Description  
     * @date 3/26/19
     * @param: 
     * @return: 
     *
    */
    public static <T> void performanceTest(AbstractSerialize serialize , Object object , int tesCount, Class<T> clazz){

        long  start = 0;
        long  end = 0;
        long totalTime = 0;
        byte[]  serData = null;

        for(int i = 0; i< tesCount ; i++){
            StopWatch stopWatch = new StopWatch("my-watch");
            stopWatch.start();
            serData = serialize.serialize(object);
            T obj  = serialize.deserialize(serData,clazz);
            stopWatch.stop();
            totalTime += stopWatch.getTotalTimeMillis();
           // System.out.println(obj);
        }

        System.out.println("序列化对象类：" + clazz.getName()
                + "  序列化类：" + serialize.getClass().getName()
                +  "  序列化花费时间：" + (totalTime/tesCount)
                + " 字节长度 =  " + serData.length);

    }




}
