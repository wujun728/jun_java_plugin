/**
 * Copyright (c) 2015-2017, Winter Lau (javayou@gmail.com).
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.oschina.j2cache.session;

import redis.clients.jedis.*;
import redis.clients.jedis.exceptions.JedisConnectionException;
import redis.clients.jedis.params.geo.GeoRadiusParam;
import redis.clients.jedis.params.sortedset.ZAddParams;
import redis.clients.jedis.params.sortedset.ZIncrByParams;

import java.io.Closeable;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

/**
 * <p>封装各种模式的 Redis 客户端成统一接口</p>
 *
 * <p>Jedis 接口设计真操蛋</p>
 *
 * @author Wujun
 */
class RedisClient implements Closeable, AutoCloseable {

    private final static int CONNECT_TIMEOUT = 5000;    //Redis连接超时时间
    private final static int SO_TIMEOUT = 5000;
    private final static int MAX_ATTEMPTS = 3;

    private ThreadLocal<BinaryJedisCommands> clients;

    private JedisCluster cluster;
    private JedisPool single;
    private JedisSentinelPool sentinel;
    private ShardedJedisPool sharded;
    private String redisPassword;

    /**
     * RedisClient 构造器
     */
    public static class Builder {
        private String mode;
        private String hosts;
        private String password;
        private String cluster;
        private int database;
        private JedisPoolConfig poolConfig;

        public Builder(){}

        public Builder mode(String mode){
            if(mode == null || mode.trim().length() == 0)
                this.mode = "single";
            else
                this.mode = mode;
            return this;
        }
        public Builder hosts(String hosts){
            if(hosts == null || hosts.trim().length() == 0)
                this.hosts = "127.0.0.1:6379";
            else
                this.hosts = hosts;
            return this;
        }
        public Builder password(String password){
            if(password != null && password.trim().length() > 0)
                this.password = password;
            return this;
        }
        public Builder cluster(String cluster) {
            if(cluster == null || cluster.trim().length() == 0)
                this.cluster = "j2cache";
            else
                this.cluster = cluster;
            return this;
        }
        public Builder database(int database){
            this.database = database;
            return this;
        }
        public Builder poolConfig(JedisPoolConfig poolConfig){
            this.poolConfig = poolConfig;
            return this;
        }
        public RedisClient newClient() {
            return new RedisClient(mode, hosts, password, cluster, database, poolConfig);
        }
    }


    /**
     * 各种模式 Redis 客户端的封装
     * @param mode Redis 服务器运行模式
     * @param hosts Redis 主机连接信息
     * @param password  Redis 密码（如果有的话）
     * @param cluster_name  集群名称
     * @param database 数据库
     * @param poolConfig    连接池配置
     */
    private RedisClient(String mode, String hosts, String password, String cluster_name, int database, JedisPoolConfig poolConfig) {
        this.redisPassword = (password != null && password.trim().length() > 0)? password.trim(): null;
        this.clients = new ThreadLocal<>();
        switch(mode){
            case "sentinel":
                Set<String> nodes = new HashSet<>();
                for(String node : hosts.split(","))
                    nodes.add(node);
                this.sentinel = new JedisSentinelPool(cluster_name, nodes, poolConfig, CONNECT_TIMEOUT, password, database);
                break;
            case "cluster":
                Set<HostAndPort> hps = new HashSet<>();
                for(String node : hosts.split(",")){
                    String[] infos = node.split(":");
                    String host = infos[0];
                    int port = (infos.length > 1)?Integer.parseInt(infos[1]):6379;
                    hps.add(new HostAndPort(host, port));
                }
                this.cluster = new JedisCluster(hps, CONNECT_TIMEOUT, SO_TIMEOUT, MAX_ATTEMPTS, password, poolConfig);
                break;
            case "sharded":
                List<JedisShardInfo> shards = new ArrayList<>();
                try {
                    for(String node : hosts.split(","))
                        shards.add(new JedisShardInfo(new URI(node)));
                } catch (URISyntaxException e) {
                    throw new JedisConnectionException(e);
                }
                this.sharded = new ShardedJedisPool(poolConfig, shards);
                break;
            default:
                for(String node : hosts.split(",")) {
                    String[] infos = node.split(":");
                    String host = infos[0];
                    int port = (infos.length > 1)?Integer.parseInt(infos[1]):6379;
                    this.single = new JedisPool(poolConfig, host, port, CONNECT_TIMEOUT, password, database);
                    break;
                }
                if(!"single".equalsIgnoreCase(mode))
                    throw new IllegalArgumentException("Illegal redis mode setting: " + mode);
                break;
        }
    }

    /**
     * 获取客户端接口
     * @return 返回基本的 Jedis 二进制命令接口
     */
    public BinaryJedisCommands get() {
        BinaryJedisCommands client = clients.get();
        if(client == null) {
            if (single != null)
                client = single.getResource();
            else if (sentinel != null)
                client = sentinel.getResource();
            else if (sharded != null)
                client = sharded.getResource();
            else if (cluster != null)
                client = toBinaryJedisCommands(cluster);
            clients.set(client);
        }
        return client;
    }

    /**
     * 释放 Redis 连接
     */
    public void release() {
        BinaryJedisCommands client = clients.get();
        if(client != null) {
            //JedisCluster 会自动释放连接
            if(client instanceof Closeable && !(client instanceof JedisCluster)) {
                try {
                    ((Closeable) client).close();
                } catch(IOException e) {}
            }
            clients.remove();
        }
    }

    @Override
    public void close() throws IOException {
        if(single != null)
            single.close();
        if(sentinel != null)
            sentinel.close();
        if(cluster != null)
            cluster.close();
        if(sharded != null)
            sharded.close();
    }

    /**
     * 为了变态的 jedis 接口设计，搞了五百多行垃圾代码
     * @param cluster Jedis 集群实例
     * @return
     */
    private BinaryJedisCommands toBinaryJedisCommands(JedisCluster cluster) {
        return new BinaryJedisCommands(){
            @Override
            public String set(byte[] bytes, byte[] bytes1) {
                return cluster.set(bytes, bytes1);
            }

            @Override
            public String set(byte[] bytes, byte[] bytes1, byte[] bytes2) {
                return null;
            }

            @Override
            public String set(byte[] bytes, byte[] bytes1, byte[] bytes2, byte[] bytes3, long l) {
                return null;
            }

            @Override
            public byte[] get(byte[] bytes) {
                return cluster.get(bytes);
            }

            @Override
            public Boolean exists(byte[] bytes) {
                return cluster.exists(bytes);
            }

            @Override
            public Long persist(byte[] bytes) {
                return cluster.persist(bytes);
            }

            @Override
            public String type(byte[] bytes) {
                return cluster.type(bytes);
            }

            @Override
            public Long expire(byte[] bytes, int i) {
                return cluster.expire(bytes, i);
            }

            @Override
            public Long pexpire(String s, long l) {
                return cluster.pexpire(s, l);
            }

            @Override
            public Long pexpire(byte[] bytes, long l) {
                return cluster.pexpire(bytes, l);
            }

            @Override
            public Long expireAt(byte[] bytes, long l) {
                return cluster.expireAt(bytes, l);
            }

            @Override
            public Long pexpireAt(byte[] bytes, long l) {
                return cluster.pexpireAt(bytes, l);
            }

            @Override
            public Long ttl(byte[] bytes) {
                return cluster.ttl(bytes);
            }

            @Override
            public Boolean setbit(byte[] bytes, long l, boolean b) {
                return cluster.setbit(bytes, l, b);
            }

            @Override
            public Boolean setbit(byte[] bytes, long l, byte[] bytes1) {
                return cluster.setbit(bytes, l, bytes1);
            }

            @Override
            public Boolean getbit(byte[] bytes, long l) {
                return cluster.getbit(bytes, l);
            }

            @Override
            public Long setrange(byte[] bytes, long l, byte[] bytes1) {
                return cluster.setrange(bytes, l, bytes1);
            }

            @Override
            public byte[] getrange(byte[] bytes, long l, long l1) {
                return cluster.getrange(bytes,l,l1);
            }

            @Override
            public byte[] getSet(byte[] bytes, byte[] bytes1) {
                return cluster.getSet(bytes, bytes1);
            }

            @Override
            public Long setnx(byte[] bytes, byte[] bytes1) {
                return cluster.setnx(bytes, bytes1);
            }

            @Override
            public String setex(byte[] bytes, int i, byte[] bytes1) {
                return cluster.setex(bytes, i, bytes1);
            }

            @Override
            public Long decrBy(byte[] bytes, long l) {
                return cluster.decrBy(bytes, l);
            }

            @Override
            public Long decr(byte[] bytes) {
                return cluster.decr(bytes);
            }

            @Override
            public Long incrBy(byte[] bytes, long l) {
                return cluster.incrBy(bytes, l);
            }

            @Override
            public Double incrByFloat(byte[] bytes, double v) {
                return cluster.incrByFloat(bytes, v);
            }

            @Override
            public Long incr(byte[] bytes) {
                return cluster.incr(bytes);
            }

            @Override
            public Long append(byte[] bytes, byte[] bytes1) {
                return cluster.append(bytes, bytes1);
            }

            @Override
            public byte[] substr(byte[] bytes, int i, int i1) {
                return cluster.substr(bytes, i, i1);
            }

            @Override
            public Long hset(byte[] bytes, byte[] bytes1, byte[] bytes2) {
                return cluster.hset(bytes, bytes1, bytes2);
            }

            @Override
            public byte[] hget(byte[] bytes, byte[] bytes1) {
                return cluster.hget(bytes, bytes1);
            }

            @Override
            public Long hsetnx(byte[] bytes, byte[] bytes1, byte[] bytes2) {
                return cluster.hsetnx(bytes, bytes1, bytes2);
            }

            @Override
            public String hmset(byte[] bytes, Map<byte[], byte[]> map) {
                return cluster.hmset(bytes, map);
            }

            @Override
            public List<byte[]> hmget(byte[] bytes, byte[]... bytes1) {
                return cluster.hmget(bytes, bytes1);
            }

            @Override
            public Long hincrBy(byte[] bytes, byte[] bytes1, long l) {
                return cluster.hincrBy(bytes, bytes1, l);
            }

            @Override
            public Double hincrByFloat(byte[] bytes, byte[] bytes1, double v) {
                return cluster.hincrByFloat(bytes, bytes1, v);
            }

            @Override
            public Boolean hexists(byte[] bytes, byte[] bytes1) {
                return cluster.hexists(bytes, bytes1);
            }

            @Override
            public Long hdel(byte[] bytes, byte[]... bytes1) {
                return cluster.hdel(bytes, bytes1);
            }

            @Override
            public Long hlen(byte[] bytes) {
                return cluster.hlen(bytes);
            }

            @Override
            public Set<byte[]> hkeys(byte[] bytes) {
                return cluster.hkeys(bytes);
            }

            @Override
            public Collection<byte[]> hvals(byte[] bytes) {
                return cluster.hvals(bytes);
            }

            @Override
            public Map<byte[], byte[]> hgetAll(byte[] bytes) {
                return cluster.hgetAll(bytes);
            }

            @Override
            public Long rpush(byte[] bytes, byte[]... bytes1) {
                return cluster.rpush(bytes, bytes1);
            }

            @Override
            public Long lpush(byte[] bytes, byte[]... bytes1) {
                return cluster.lpush(bytes, bytes1);
            }

            @Override
            public Long llen(byte[] bytes) {
                return cluster.llen(bytes);
            }

            @Override
            public List<byte[]> lrange(byte[] bytes, long l, long l1) {
                return cluster.lrange(bytes, l, l1);
            }

            @Override
            public String ltrim(byte[] bytes, long l, long l1) {
                return cluster.ltrim(bytes, l, l1);
            }

            @Override
            public byte[] lindex(byte[] bytes, long l) {
                return cluster.lindex(bytes, l);
            }

            @Override
            public String lset(byte[] bytes, long l, byte[] bytes1) {
                return cluster.lset(bytes, l, bytes1);
            }

            @Override
            public Long lrem(byte[] bytes, long l, byte[] bytes1) {
                return cluster.lrem(bytes, l, bytes1);
            }

            @Override
            public byte[] lpop(byte[] bytes) {
                return cluster.lpop(bytes);
            }

            @Override
            public byte[] rpop(byte[] bytes) {
                return cluster.rpop(bytes);
            }

            @Override
            public Long sadd(byte[] bytes, byte[]... bytes1) {
                return cluster.sadd(bytes, bytes1);
            }

            @Override
            public Set<byte[]> smembers(byte[] bytes) {
                return cluster.smembers(bytes);
            }

            @Override
            public Long srem(byte[] bytes, byte[]... bytes1) {
                return cluster.srem(bytes, bytes1);
            }

            @Override
            public byte[] spop(byte[] bytes) {
                return cluster.spop(bytes);
            }

            @Override
            public Set<byte[]> spop(byte[] bytes, long l) {
                return cluster.spop(bytes, l);
            }

            @Override
            public Long scard(byte[] bytes) {
                return cluster.scard(bytes);
            }

            @Override
            public Boolean sismember(byte[] bytes, byte[] bytes1) {
                return cluster.sismember(bytes, bytes1);
            }

            @Override
            public byte[] srandmember(byte[] bytes) {
                return cluster.srandmember(bytes);
            }

            @Override
            public List<byte[]> srandmember(byte[] bytes, int i) {
                return cluster.srandmember(bytes, i);
            }

            @Override
            public Long strlen(byte[] bytes) {
                return cluster.strlen(bytes);
            }

            @Override
            public Long zadd(byte[] bytes, double v, byte[] bytes1) {
                return cluster.zadd(bytes, v, bytes1);
            }

            @Override
            public Long zadd(byte[] bytes, double v, byte[] bytes1, ZAddParams zAddParams) {
                return cluster.zadd(bytes, v, bytes1, zAddParams);
            }

            @Override
            public Long zadd(byte[] bytes, Map<byte[], Double> map) {
                return cluster.zadd(bytes, map);
            }

            @Override
            public Long zadd(byte[] bytes, Map<byte[], Double> map, ZAddParams zAddParams) {
                return cluster.zadd(bytes, map, zAddParams);
            }

            @Override
            public Set<byte[]> zrange(byte[] bytes, long l, long l1) {
                return cluster.zrange(bytes, l, l1);
            }

            @Override
            public Long zrem(byte[] bytes, byte[]... bytes1) {
                return cluster.zrem(bytes, bytes1);
            }

            @Override
            public Double zincrby(byte[] bytes, double v, byte[] bytes1) {
                return cluster.zincrby(bytes, v, bytes1);
            }

            @Override
            public Double zincrby(byte[] bytes, double v, byte[] bytes1, ZIncrByParams zIncrByParams) {
                return cluster.zincrby(bytes, v, bytes1, zIncrByParams);
            }

            @Override
            public Long zrank(byte[] bytes, byte[] bytes1) {
                return cluster.zrank(bytes, bytes1);
            }

            @Override
            public Long zrevrank(byte[] bytes, byte[] bytes1) {
                return cluster.zrevrank(bytes, bytes1);
            }

            @Override
            public Set<byte[]> zrevrange(byte[] bytes, long l, long l1) {
                return cluster.zrevrange(bytes, l, l1);
            }

            @Override
            public Set<Tuple> zrangeWithScores(byte[] bytes, long l, long l1) {
                return cluster.zrangeWithScores(bytes, l, l1);
            }

            @Override
            public Set<Tuple> zrevrangeWithScores(byte[] bytes, long l, long l1) {
                return cluster.zrevrangeWithScores(bytes, l, l1);
            }

            @Override
            public Long zcard(byte[] bytes) {
                return cluster.zcard(bytes);
            }

            @Override
            public Double zscore(byte[] bytes, byte[] bytes1) {
                return cluster.zscore(bytes, bytes1);
            }

            @Override
            public List<byte[]> sort(byte[] bytes) {
                return cluster.sort(bytes);
            }

            @Override
            public List<byte[]> sort(byte[] bytes, SortingParams sortingParams) {
                return cluster.sort(bytes, sortingParams);
            }

            @Override
            public Long zcount(byte[] bytes, double v, double v1) {
                return cluster.zcount(bytes, v, v1);
            }

            @Override
            public Long zcount(byte[] bytes, byte[] bytes1, byte[] bytes2) {
                return cluster.zcount(bytes, bytes1, bytes2);
            }

            @Override
            public Set<byte[]> zrangeByScore(byte[] bytes, double v, double v1) {
                return cluster.zrangeByScore(bytes, v, v1);
            }

            @Override
            public Set<byte[]> zrangeByScore(byte[] bytes, byte[] bytes1, byte[] bytes2) {
                return cluster.zrangeByScore(bytes, bytes1, bytes2);
            }

            @Override
            public Set<byte[]> zrevrangeByScore(byte[] bytes, double v, double v1) {
                return cluster.zrevrangeByScore(bytes, v, v1);
            }

            @Override
            public Set<byte[]> zrangeByScore(byte[] bytes, double v, double v1, int i, int i1) {
                return cluster.zrangeByScore(bytes, v,v1,i,i1);
            }

            @Override
            public Set<byte[]> zrevrangeByScore(byte[] bytes, byte[] bytes1, byte[] bytes2) {
                return cluster.zrevrangeByScore(bytes, bytes1, bytes2);
            }

            @Override
            public Set<byte[]> zrangeByScore(byte[] bytes, byte[] bytes1, byte[] bytes2, int i, int i1) {
                return cluster.zrangeByScore(bytes, bytes1, bytes2, i,i1);
            }

            @Override
            public Set<byte[]> zrevrangeByScore(byte[] bytes, double v, double v1, int i, int i1) {
                return cluster.zrevrangeByScore(bytes, v,v1,i,i1);
            }

            @Override
            public Set<Tuple> zrangeByScoreWithScores(byte[] bytes, double v, double v1) {
                return cluster.zrangeByScoreWithScores(bytes,v,v1);
            }

            @Override
            public Set<Tuple> zrevrangeByScoreWithScores(byte[] bytes, double v, double v1) {
                return cluster.zrevrangeByScoreWithScores(bytes, v, v1);
            }

            @Override
            public Set<Tuple> zrangeByScoreWithScores(byte[] bytes, double v, double v1, int i, int i1) {
                return cluster.zrangeByScoreWithScores(bytes, v, v1, i, i1);
            }

            @Override
            public Set<byte[]> zrevrangeByScore(byte[] bytes, byte[] bytes1, byte[] bytes2, int i, int i1) {
                return cluster.zrevrangeByScore(bytes, bytes1, bytes2, i, i1);
            }

            @Override
            public Set<Tuple> zrangeByScoreWithScores(byte[] bytes, byte[] bytes1, byte[] bytes2) {
                return cluster.zrangeByScoreWithScores(bytes, bytes1, bytes2);
            }

            @Override
            public Set<Tuple> zrevrangeByScoreWithScores(byte[] bytes, byte[] bytes1, byte[] bytes2) {
                return cluster.zrevrangeByScoreWithScores(bytes, bytes1, bytes2);
            }

            @Override
            public Set<Tuple> zrangeByScoreWithScores(byte[] bytes, byte[] bytes1, byte[] bytes2, int i, int i1) {
                return cluster.zrangeByScoreWithScores(bytes, bytes1, bytes2, i, i1);
            }

            @Override
            public Set<Tuple> zrevrangeByScoreWithScores(byte[] bytes, double v, double v1, int i, int i1) {
                return cluster.zrevrangeByScoreWithScores(bytes, v, v1, i, i1);
            }

            @Override
            public Set<Tuple> zrevrangeByScoreWithScores(byte[] bytes, byte[] bytes1, byte[] bytes2, int i, int i1) {
                return cluster.zrevrangeByScoreWithScores(bytes, bytes1, bytes2, i, i1);
            }

            @Override
            public Long zremrangeByRank(byte[] bytes, long l, long l1) {
                return cluster.zremrangeByRank(bytes, l ,l1);
            }

            @Override
            public Long zremrangeByScore(byte[] bytes, double v, double v1) {
                return cluster.zremrangeByScore(bytes, v, v1);
            }

            @Override
            public Long zremrangeByScore(byte[] bytes, byte[] bytes1, byte[] bytes2) {
                return cluster.zremrangeByScore(bytes, bytes1, bytes2);
            }

            @Override
            public Long zlexcount(byte[] bytes, byte[] bytes1, byte[] bytes2) {
                return cluster.zlexcount(bytes, bytes1, bytes2);
            }

            @Override
            public Set<byte[]> zrangeByLex(byte[] bytes, byte[] bytes1, byte[] bytes2) {
                return cluster.zrangeByLex(bytes, bytes1, bytes2);
            }

            @Override
            public Set<byte[]> zrangeByLex(byte[] bytes, byte[] bytes1, byte[] bytes2, int i, int i1) {
                return cluster.zrangeByLex(bytes, bytes1, bytes2, i, i1);
            }

            @Override
            public Set<byte[]> zrevrangeByLex(byte[] bytes, byte[] bytes1, byte[] bytes2) {
                return cluster.zrevrangeByLex(bytes, bytes1, bytes2);
            }

            @Override
            public Set<byte[]> zrevrangeByLex(byte[] bytes, byte[] bytes1, byte[] bytes2, int i, int i1) {
                return cluster.zrevrangeByLex(bytes, bytes1, bytes2, i, i1);
            }

            @Override
            public Long zremrangeByLex(byte[] bytes, byte[] bytes1, byte[] bytes2) {
                return cluster.zremrangeByLex(bytes, bytes1, bytes2);
            }

            @Override
            public Long linsert(byte[] bytes, BinaryClient.LIST_POSITION list_position, byte[] bytes1, byte[] bytes2) {
                return cluster.linsert(bytes, list_position, bytes1, bytes2);
            }

            @Override
            public Long lpushx(byte[] bytes, byte[]... bytes1) {
                return cluster.lpushx(bytes, bytes1);
            }

            @Override
            public Long rpushx(byte[] bytes, byte[]... bytes1) {
                return cluster.rpushx(bytes, bytes1);
            }

            @Override
            public List<byte[]> blpop(byte[] bytes) {
                return cluster.blpop(0, bytes);
            }

            @Override
            public List<byte[]> brpop(byte[] bytes) {
                return cluster.brpop(0, bytes);
            }

            @Override
            public Long del(byte[] bytes) {
                return cluster.del(bytes);
            }

            @Override
            public byte[] echo(byte[] bytes) {
                return cluster.echo(bytes);
            }

            @Override
            public Long move(byte[] bytes, int i) {
                return cluster.move(new String(bytes), i);
            }

            @Override
            public Long bitcount(byte[] bytes) {
                return cluster.bitcount(bytes);
            }

            @Override
            public Long bitcount(byte[] bytes, long l, long l1) {
                return cluster.bitcount(bytes, l, l1);
            }

            @Override
            public Long pfadd(byte[] bytes, byte[]... bytes1) {
                return cluster.pfadd(bytes, bytes1);
            }

            @Override
            public long pfcount(byte[] bytes) {
                return cluster.pfcount(bytes);
            }

            @Override
            public Long geoadd(byte[] bytes, double v, double v1, byte[] bytes1) {
                return cluster.geoadd(bytes, v, v1, bytes1);
            }

            @Override
            public Long geoadd(byte[] bytes, Map<byte[], GeoCoordinate> map) {
                return cluster.geoadd(bytes, map);
            }

            @Override
            public Double geodist(byte[] bytes, byte[] bytes1, byte[] bytes2) {
                return cluster.geodist(bytes, bytes1, bytes2);
            }

            @Override
            public Double geodist(byte[] bytes, byte[] bytes1, byte[] bytes2, GeoUnit geoUnit) {
                return cluster.geodist(bytes, bytes1, bytes2, geoUnit);
            }

            @Override
            public List<byte[]> geohash(byte[] bytes, byte[]... bytes1) {
                return cluster.geohash(bytes, bytes1);
            }

            @Override
            public List<GeoCoordinate> geopos(byte[] bytes, byte[]... bytes1) {
                return cluster.geopos(bytes, bytes1);
            }

            @Override
            public List<GeoRadiusResponse> georadius(byte[] bytes, double v, double v1, double v2, GeoUnit geoUnit) {
                return cluster.georadius(bytes, v,v1,v2, geoUnit);
            }

            @Override
            public List<GeoRadiusResponse> georadius(byte[] bytes, double v, double v1, double v2, GeoUnit geoUnit, GeoRadiusParam geoRadiusParam) {
                return cluster.georadius(bytes, v, v1, v2, geoUnit, geoRadiusParam);
            }

            @Override
            public List<GeoRadiusResponse> georadiusByMember(byte[] bytes, byte[] bytes1, double v, GeoUnit geoUnit) {
                return cluster.georadiusByMember(bytes, bytes1, v, geoUnit);
            }

            @Override
            public List<GeoRadiusResponse> georadiusByMember(byte[] bytes, byte[] bytes1, double v, GeoUnit geoUnit, GeoRadiusParam geoRadiusParam) {
                return cluster.georadiusByMember(bytes, bytes1, v, geoUnit, geoRadiusParam);
            }

            @Override
            public ScanResult<Map.Entry<byte[], byte[]>> hscan(byte[] bytes, byte[] bytes1) {
                return cluster.hscan(bytes, bytes1);
            }

            @Override
            public ScanResult<Map.Entry<byte[], byte[]>> hscan(byte[] bytes, byte[] bytes1, ScanParams scanParams) {
                return cluster.hscan(bytes, bytes1, scanParams);
            }

            @Override
            public ScanResult<byte[]> sscan(byte[] bytes, byte[] bytes1) {
                return cluster.sscan(bytes, bytes1);
            }

            @Override
            public ScanResult<byte[]> sscan(byte[] bytes, byte[] bytes1, ScanParams scanParams) {
                return cluster.sscan(bytes, bytes1, scanParams);
            }

            @Override
            public ScanResult<Tuple> zscan(byte[] bytes, byte[] bytes1) {
                return cluster.zscan(bytes, bytes1);
            }

            @Override
            public ScanResult<Tuple> zscan(byte[] bytes, byte[] bytes1, ScanParams scanParams) {
                return cluster.zscan(bytes, bytes1, scanParams);
            }

            @Override
            public List<byte[]> bitfield(byte[] bytes, byte[]... bytes1) {
                return cluster.bitfield(bytes, bytes1);
            }
        };
    }

}
