package com.citi.redis.service;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;
import redis.clients.jedis.Tuple;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class Chapter04_test {
    public static void main(String[] args) {
        new Chapter04_test().run();
    }

    public void run(){
        Jedis conn=new Jedis("localhost");
        conn.select(15);

        testListItem(conn,false);
        testPurchaseItem(conn);
    }

    public void testListItem(Jedis conn,boolean nested){
        if(!nested){
            System.out.println("-----------testListItem-------------");
        }
        System.out.println("We need to set up just enough state so that a user can list an item");
        String sellId="userX";
        String itemId="itemX";
        conn.sadd("inventory:"+sellId,itemId);
        Set<String> i=conn.smembers("inventory:"+sellId);
        System.out.println("The user's inventory has:");
        for(String member: i){
            System.out.println(" "+member);
        }
        assert i.size()>0;
        System.out.println();
        System.out.println("Listing the item...");
        boolean l=listItem(conn,itemId,sellId,10);
        System.out.println("Listing the item succeeded?"+l);
        assert l;
        Set<Tuple> r=conn.zrangeWithScores("market:",0,-1);
        System.out.println("The market contains:");
        for(Tuple tuple:r){
            System.out.println("   "+tuple.getElement()+","+tuple.getScore());
        }
        assert r.size()>0;
    }
    public boolean listItem(Jedis conn,String itemId,String sellId,double price){
        String inventory="inventory:"+sellId;
        String item=itemId+"."+sellId;
        long end=System.currentTimeMillis()+5000;
        while(System.currentTimeMillis()<end){
            conn.watch(inventory);
            if(!conn.sismember(inventory,item)){
                conn.unwatch();
                return false;
            }
            Transaction transaction=conn.multi();
            transaction.zadd("market:",price,item);
            transaction.srem(inventory,itemId);
            List<Object> results=transaction.exec();
            if(results==null){
                continue;
            }
            return true;
        }
        return false;
    }
    public void testPurchaseItem(Jedis conn){
        System.out.println("-----------testPurchaseItem-------------");
        testListItem(conn,true);

        System.out.println("we need to set up just enough state so a user can buy an item");
        conn.hset("users:userY","funds","125");
        Map<String,String> r=conn.hgetAll("users:userY");
        System.out.println("The user has some money:");
        for(Map.Entry entry:r.entrySet()){
            System.out.println(" "+entry.getKey()+":"+entry.getValue());
        }
        assert r.size()>0;
        assert r.get("funds")!=null;
        System.out.println();

        System.out.println("Let's purchase an item");
        boolean p=purchaseItem(conn,"userY","itemX","userX",10);
        System.out.println("Purchasing an item succeeded? "+p);
        assert p;
        r=conn.hgetAll("users:userY");
        System.out.println("Their money is now:");
        for(Map.Entry<String,String> entry:r.entrySet()){
            System.out.println("    "+entry.getKey()+":"+entry.getValue());
        }
        assert r.size()>0;
        String buyer="userY";
        Set<String> i=conn.smembers("inventory:"+buyer);
        System.out.println("Their inventory is now:");
        for(String member:i){
            System.out.println("          "+member);
        }
        assert i.size()>0;
        assert i.contains("itemX");
        assert conn.zscore("market:","itemX.userX")==null;
    }

    public boolean purchaseItem(Jedis conn,String buyerId,String itemId,
                                String sellerId,double lprice){
        String buyer="users:"+buyerId;
        String seller="users:"+sellerId;
        String item=itemId+'.'+sellerId;
        String inventory="inventory:"+buyerId;
        long end=System.currentTimeMillis()+10000;
        while(System.currentTimeMillis()<end){
            conn.watch("market:",buyer);
            double price=conn.zscore("market:",item);
            double funds=Double.parseDouble(conn.hget(buyer,"funds"));
            if(price!=lprice||price>funds){
                conn.unwatch();
                return false;
            }
            Transaction transaction=conn.multi();
            transaction.hincrBy(seller,"funds",(int)price);
            transaction.hincrBy(buyer,"funds",(int)-price);
            transaction.sadd(inventory,itemId);
            transaction.zrem("market:",item);
            List<Object> results=transaction.exec();
            //null response indicates that the transaction was aborted due to
            //the watched key changing.
            if(results==null){
                continue;
            }
            return true;
        }
        return false;
    }

}
