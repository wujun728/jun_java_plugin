package com.citi.redis.test;

import redis.clients.jedis.Jedis;

import java.util.UUID;

public class LoginTest {
    public static void main(String[] args) {

    }

    public void run(){
        Jedis conn=new Jedis("localhost");
        conn.select(15);

    }
    public void testLoginCookies(Jedis conn){
        System.out.println("\n------------testLoginCookies----------------");
        String token= UUID.randomUUID().toString();

    }
    public void updateToken(Jedis conn,String token,String user,String item){
        long timestamp=System.currentTimeMillis()/1000;
        conn.hset("login:",token,user);


    }
}
