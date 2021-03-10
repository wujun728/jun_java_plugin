package com.citi.redis.test;


import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

import java.util.List;

public class TestTransaction {
    public static void main(String[] args) {
        Jedis conn=new Jedis("127.0.0.1");
        conn.select(15);
        try {
            Transaction transaction=conn.multi();
            transaction.lpush("key","11");
            transaction.lpush("key","13");
            transaction.lpush("key","14");
//            int i=1/0;
//        transaction.hset("hash-set","hash1","value1");
//        transaction.hset("hash-set","hash2","value2");
//        transaction.hset("hash-set","hash3","value3");
            List<Object> list=transaction.exec();
            System.out.println(list);
            System.out.println(conn.lrange("key",0,-1));
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
