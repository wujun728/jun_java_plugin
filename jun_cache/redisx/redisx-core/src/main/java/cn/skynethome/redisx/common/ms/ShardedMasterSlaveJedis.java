package cn.skynethome.redisx.common.ms;

import java.io.Closeable;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Pattern;

import redis.clients.jedis.BinaryClient.LIST_POSITION;
import redis.clients.jedis.BinaryJedisCommands;
import redis.clients.jedis.BitPosParams;
import redis.clients.jedis.GeoCoordinate;
import redis.clients.jedis.GeoRadiusResponse;
import redis.clients.jedis.GeoUnit;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCommands;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ScanParams;
import redis.clients.jedis.ScanResult;
import redis.clients.jedis.SortingParams;
import redis.clients.jedis.Tuple;
import redis.clients.jedis.params.geo.GeoRadiusParam;
import redis.clients.jedis.params.sortedset.ZAddParams;
import redis.clients.jedis.params.sortedset.ZIncrByParams;
import redis.clients.util.Hashing;
import redis.clients.util.Pool;
import redis.clients.util.Sharded;

/**
  * 项目名称:[redisx]
  * 包:[cn.skynethome.redisx.common.ms]    
  * 文件名称:[ShardedMasterSlaveJedis]  
  * 描述:[一句话描述该文件的作用]
  * 创建人:[陆文斌]
  * 创建时间:[2017年1月3日 上午11:37:52]   
  * 修改人:[陆文斌]   
  * 修改时间:[2017年1月3日 上午11:37:52]   
  * 修改备注:[说明本次修改内容]  
  * 版权所有:luwenbin006@163.com
  * 版本:[v1.0]
 */
public class ShardedMasterSlaveJedis extends
		Sharded<MasterSlaveJedis, MasterSlaveJedisShardInfo> implements
		JedisCommands, BinaryJedisCommands, Closeable {

	protected final Set<MasterSlaveHostAndPort> connectionDesc;
	
	protected Pool<ShardedMasterSlaveJedis> dataSource = null;

	protected final List<MasterSlaveJedisShardInfo> shards;
	
	public ShardedMasterSlaveJedis(List<MasterSlaveJedisShardInfo> shards) {
		super(shards);
		this.shards = shards;
		this.connectionDesc = new LinkedHashSet<MasterSlaveHostAndPort>();
		for(MasterSlaveJedisShardInfo shard : shards){
			Set<HostAndPort> slaves = new LinkedHashSet<HostAndPort>();
			for(JedisShardInfo slaveShard : shard.getSlaveShards()){
				slaves.add(new HostAndPort(slaveShard.getHost(), slaveShard.getPort()));
			}
			this.connectionDesc.add(new MasterSlaveHostAndPort(shard.getMasterName(), new HostAndPort(shard.getMasterShard().getHost(), shard.getMasterShard().getPort()), slaves));
		}
	}

	public ShardedMasterSlaveJedis(List<MasterSlaveJedisShardInfo> shards,
			Hashing algo) {
		super(shards, algo);
		this.shards = shards;
		this.connectionDesc = new LinkedHashSet<MasterSlaveHostAndPort>();
		for(MasterSlaveJedisShardInfo shard : shards){
			Set<HostAndPort> slaves = new LinkedHashSet<HostAndPort>();
			for(JedisShardInfo slaveShard : shard.getSlaveShards()){
				slaves.add(new HostAndPort(slaveShard.getHost(), slaveShard.getPort()));
			}
			this.connectionDesc.add(new MasterSlaveHostAndPort(shard.getMasterName(), new HostAndPort(shard.getMasterShard().getHost(), shard.getMasterShard().getPort()), slaves));
		}
	}

	public ShardedMasterSlaveJedis(List<MasterSlaveJedisShardInfo> shards,
			Pattern keyTagPattern) {
		super(shards, keyTagPattern);
		this.shards = shards;
		this.connectionDesc = new LinkedHashSet<MasterSlaveHostAndPort>();
		for(MasterSlaveJedisShardInfo shard : shards){
			Set<HostAndPort> slaves = new LinkedHashSet<HostAndPort>();
			for(JedisShardInfo slaveShard : shard.getSlaveShards()){
				slaves.add(new HostAndPort(slaveShard.getHost(), slaveShard.getPort()));
			}
			this.connectionDesc.add(new MasterSlaveHostAndPort(shard.getMasterName(), new HostAndPort(shard.getMasterShard().getHost(), shard.getMasterShard().getPort()), slaves));
		}
	}

	public ShardedMasterSlaveJedis(List<MasterSlaveJedisShardInfo> shards,
			Hashing algo, Pattern keyTagPattern) {
		super(shards, algo, keyTagPattern);
		this.shards = shards;
		this.connectionDesc = new LinkedHashSet<MasterSlaveHostAndPort>();
		for(MasterSlaveJedisShardInfo shard : shards){
			Set<HostAndPort> slaves = new LinkedHashSet<HostAndPort>();
			for(JedisShardInfo slaveShard : shard.getSlaveShards()){
				slaves.add(new HostAndPort(slaveShard.getHost(), slaveShard.getPort()));
			}
			this.connectionDesc.add(new MasterSlaveHostAndPort(shard.getMasterName(), new HostAndPort(shard.getMasterShard().getHost(), shard.getMasterShard().getPort()), slaves));
		}
	}
	
	public Set<MasterSlaveHostAndPort> getConnectionDesc() {
		return connectionDesc;
	}

	public String set(String key, String value) {
		MasterSlaveJedis j = getShard(key);
		return j.set(key, value);
	}
	
	public Long publish(byte[] channel, byte[] message){
	    MasterSlaveJedis j = getShard(message);
        return j.publish(channel, message);
    }
	
	public Long publish(String channel, String message){
        MasterSlaveJedis j = getShard(message);
        return j.publish(channel, message);
    }

	public String set(String key, String value, String nxxx, String expx,
			long time) {
		MasterSlaveJedis j = getShard(key);
		return j.set(key, value, nxxx, expx, time);
	}

	public String get(String key) {
		MasterSlaveJedis j = getShard(key);
		return j.get(key);
	}

	public String echo(String string) {
		MasterSlaveJedis j = getShard(string);
		return j.echo(string);
	}

	public Boolean exists(String key) {
		MasterSlaveJedis j = getShard(key);
		return j.exists(key);
	}

	public String type(String key) {
		MasterSlaveJedis j = getShard(key);
		return j.type(key);
	}

	public Long expire(String key, int seconds) {
		MasterSlaveJedis j = getShard(key);
		return j.expire(key, seconds);
	}

	public Long expireAt(String key, long unixTime) {
		MasterSlaveJedis j = getShard(key);
		return j.expireAt(key, unixTime);
	}

	public Long ttl(String key) {
		MasterSlaveJedis j = getShard(key);
		return j.ttl(key);
	}

	public Boolean setbit(String key, long offset, boolean value) {
		MasterSlaveJedis j = getShard(key);
		return j.setbit(key, offset, value);
	}

	public Boolean setbit(String key, long offset, String value) {
		MasterSlaveJedis j = getShard(key);
		return j.setbit(key, offset, value);
	}

	public Boolean getbit(String key, long offset) {
		MasterSlaveJedis j = getShard(key);
		return j.getbit(key, offset);
	}

	public Long setrange(String key, long offset, String value) {
		MasterSlaveJedis j = getShard(key);
		return j.setrange(key, offset, value);
	}

	public String getrange(String key, long startOffset, long endOffset) {
		MasterSlaveJedis j = getShard(key);
		return j.getrange(key, startOffset, endOffset);
	}

	public String getSet(String key, String value) {
		MasterSlaveJedis j = getShard(key);
		return j.getSet(key, value);
	}

	public Long setnx(String key, String value) {
		MasterSlaveJedis j = getShard(key);
		return j.setnx(key, value);
	}

	public String setex(String key, int seconds, String value) {
		MasterSlaveJedis j = getShard(key);
		return j.setex(key, seconds, value);
	}

	@Deprecated
	public List<String> blpop(String arg) {
		MasterSlaveJedis j = getShard(arg);
		return j.blpop(arg);
	}

	public List<String> blpop(int timeout, String key) {
		MasterSlaveJedis j = getShard(key);
		return j.blpop(timeout, key);
	}

	@Deprecated
	public List<String> brpop(String arg) {
		MasterSlaveJedis j = getShard(arg);
		return j.brpop(arg);
	}

	public List<String> brpop(int timeout, String key) {
		MasterSlaveJedis j = getShard(key);
		return j.brpop(timeout, key);
	}

	public Long decrBy(String key, long integer) {
		MasterSlaveJedis j = getShard(key);
		return j.decrBy(key, integer);
	}

	public Long decr(String key) {
		MasterSlaveJedis j = getShard(key);
		return j.decr(key);
	}

	public Long incrBy(String key, long integer) {
		MasterSlaveJedis j = getShard(key);
		return j.incrBy(key, integer);
	}

	public Double incrByFloat(String key, double integer) {
		MasterSlaveJedis j = getShard(key);
		return j.incrByFloat(key, integer);
	}

	public Long incr(String key) {
		MasterSlaveJedis j = getShard(key);
		return j.incr(key);
	}

	public Long append(String key, String value) {
		MasterSlaveJedis j = getShard(key);
		return j.append(key, value);
	}

	public String substr(String key, int start, int end) {
		MasterSlaveJedis j = getShard(key);
		return j.substr(key, start, end);
	}

	public Long hset(String key, String field, String value) {
		MasterSlaveJedis j = getShard(key);
		return j.hset(key, field, value);
	}

	public String hget(String key, String field) {
		MasterSlaveJedis j = getShard(key);
		return j.hget(key, field);
	}

	public Long hsetnx(String key, String field, String value) {
		MasterSlaveJedis j = getShard(key);
		return j.hsetnx(key, field, value);
	}

	public String hmset(String key, Map<String, String> hash) {
		MasterSlaveJedis j = getShard(key);
		return j.hmset(key, hash);
	}

	public List<String> hmget(String key, String... fields) {
		MasterSlaveJedis j = getShard(key);
		return j.hmget(key, fields);
	}

	public Long hincrBy(String key, String field, long value) {
		MasterSlaveJedis j = getShard(key);
		return j.hincrBy(key, field, value);
	}

	public Double hincrByFloat(String key, String field, double value) {
		MasterSlaveJedis j = getShard(key);
		return j.hincrByFloat(key, field, value);
	}

	public Boolean hexists(String key, String field) {
		MasterSlaveJedis j = getShard(key);
		return j.hexists(key, field);
	}

	public Long del(String key) {
		MasterSlaveJedis j = getShard(key);
		return j.del(key);
	}

	public Long hdel(String key, String... fields) {
		MasterSlaveJedis j = getShard(key);
		return j.hdel(key, fields);
	}

	public Long hlen(String key) {
		MasterSlaveJedis j = getShard(key);
		return j.hlen(key);
	}

	public Set<String> hkeys(String key) {
		MasterSlaveJedis j = getShard(key);
		return j.hkeys(key);
	}

	public List<String> hvals(String key) {
		MasterSlaveJedis j = getShard(key);
		return j.hvals(key);
	}

	public Map<String, String> hgetAll(String key) {
		MasterSlaveJedis j = getShard(key);
		return j.hgetAll(key);
	}

	public Long rpush(String key, String... strings) {
		MasterSlaveJedis j = getShard(key);
		return j.rpush(key, strings);
	}

	public Long lpush(String key, String... strings) {
		MasterSlaveJedis j = getShard(key);
		return j.lpush(key, strings);
	}

	public Long lpushx(String key, String... string) {
		MasterSlaveJedis j = getShard(key);
		return j.lpushx(key, string);
	}

	public Long strlen(final String key) {
		MasterSlaveJedis j = getShard(key);
		return j.strlen(key);
	}

	public Long move(String key, int dbIndex) {
		MasterSlaveJedis j = getShard(key);
		return j.move(key, dbIndex);
	}

	public Long rpushx(String key, String... string) {
		MasterSlaveJedis j = getShard(key);
		return j.rpushx(key, string);
	}

	public Long persist(final String key) {
		MasterSlaveJedis j = getShard(key);
		return j.persist(key);
	}

	public Long llen(String key) {
		MasterSlaveJedis j = getShard(key);
		return j.llen(key);
	}

	public List<String> lrange(String key, long start, long end) {
		MasterSlaveJedis j = getShard(key);
		return j.lrange(key, start, end);
	}

	public String ltrim(String key, long start, long end) {
		MasterSlaveJedis j = getShard(key);
		return j.ltrim(key, start, end);
	}

	public String lindex(String key, long index) {
		MasterSlaveJedis j = getShard(key);
		return j.lindex(key, index);
	}

	public String lset(String key, long index, String value) {
		MasterSlaveJedis j = getShard(key);
		return j.lset(key, index, value);
	}

	public Long lrem(String key, long count, String value) {
		MasterSlaveJedis j = getShard(key);
		return j.lrem(key, count, value);
	}

	public String lpop(String key) {
		MasterSlaveJedis j = getShard(key);
		return j.lpop(key);
	}

	public String rpop(String key) {
		MasterSlaveJedis j = getShard(key);
		return j.rpop(key);
	}

	public Long sadd(String key, String... members) {
		MasterSlaveJedis j = getShard(key);
		return j.sadd(key, members);
	}

	public Set<String> smembers(String key) {
		MasterSlaveJedis j = getShard(key);
		return j.smembers(key);
	}

	public Long srem(String key, String... members) {
		MasterSlaveJedis j = getShard(key);
		return j.srem(key, members);
	}

	public String spop(String key) {
		MasterSlaveJedis j = getShard(key);
		return j.spop(key);
	}

	public Long scard(String key) {
		MasterSlaveJedis j = getShard(key);
		return j.scard(key);
	}

	public Boolean sismember(String key, String member) {
		MasterSlaveJedis j = getShard(key);
		return j.sismember(key, member);
	}

	public String srandmember(String key) {
		MasterSlaveJedis j = getShard(key);
		return j.srandmember(key);
	}

	public List<String> srandmember(String key, int count) {
		MasterSlaveJedis j = getShard(key);
		return j.srandmember(key, count);
	}

	public Long zadd(String key, double score, String member) {
		MasterSlaveJedis j = getShard(key);
		return j.zadd(key, score, member);
	}

	public Long zadd(String key, Map<String, Double> scoreMembers) {
		MasterSlaveJedis j = getShard(key);
		return j.zadd(key, scoreMembers);
	}

	public Set<String> zrange(String key, long start, long end) {
		MasterSlaveJedis j = getShard(key);
		return j.zrange(key, start, end);
	}

	public Long zrem(String key, String... members) {
		MasterSlaveJedis j = getShard(key);
		return j.zrem(key, members);
	}

	public Double zincrby(String key, double score, String member) {
		MasterSlaveJedis j = getShard(key);
		return j.zincrby(key, score, member);
	}

	public Long zrank(String key, String member) {
		MasterSlaveJedis j = getShard(key);
		return j.zrank(key, member);
	}

	public Long zrevrank(String key, String member) {
		MasterSlaveJedis j = getShard(key);
		return j.zrevrank(key, member);
	}

	public Set<String> zrevrange(String key, long start, long end) {
		MasterSlaveJedis j = getShard(key);
		return j.zrevrange(key, start, end);
	}

	public Set<Tuple> zrangeWithScores(String key, long start, long end) {
		MasterSlaveJedis j = getShard(key);
		return j.zrangeWithScores(key, start, end);
	}

	public Set<Tuple> zrevrangeWithScores(String key, long start, long end) {
		MasterSlaveJedis j = getShard(key);
		return j.zrevrangeWithScores(key, start, end);
	}

	public Long zcard(String key) {
		MasterSlaveJedis j = getShard(key);
		return j.zcard(key);
	}

	public Double zscore(String key, String member) {
		MasterSlaveJedis j = getShard(key);
		return j.zscore(key, member);
	}

	public List<String> sort(String key) {
		MasterSlaveJedis j = getShard(key);
		return j.sort(key);
	}

	public List<String> sort(String key, SortingParams sortingParameters) {
		MasterSlaveJedis j = getShard(key);
		return j.sort(key, sortingParameters);
	}

	public Long zcount(String key, double min, double max) {
		MasterSlaveJedis j = getShard(key);
		return j.zcount(key, min, max);
	}

	public Long zcount(String key, String min, String max) {
		MasterSlaveJedis j = getShard(key);
		return j.zcount(key, min, max);
	}

	public Set<String> zrangeByScore(String key, double min, double max) {
		MasterSlaveJedis j = getShard(key);
		return j.zrangeByScore(key, min, max);
	}

	public Set<String> zrevrangeByScore(String key, double max, double min) {
		MasterSlaveJedis j = getShard(key);
		return j.zrevrangeByScore(key, max, min);
	}

	public Set<String> zrangeByScore(String key, double min, double max,
			int offset, int count) {
		MasterSlaveJedis j = getShard(key);
		return j.zrangeByScore(key, min, max, offset, count);
	}

	public Set<String> zrevrangeByScore(String key, double max, double min,
			int offset, int count) {
		MasterSlaveJedis j = getShard(key);
		return j.zrevrangeByScore(key, max, min, offset, count);
	}

	public Set<Tuple> zrangeByScoreWithScores(String key, double min, double max) {
		MasterSlaveJedis j = getShard(key);
		return j.zrangeByScoreWithScores(key, min, max);
	}

	public Set<Tuple> zrevrangeByScoreWithScores(String key, double max,
			double min) {
		MasterSlaveJedis j = getShard(key);
		return j.zrevrangeByScoreWithScores(key, max, min);
	}

	public Set<Tuple> zrangeByScoreWithScores(String key, double min,
			double max, int offset, int count) {
		MasterSlaveJedis j = getShard(key);
		return j.zrangeByScoreWithScores(key, min, max, offset, count);
	}

	public Set<Tuple> zrevrangeByScoreWithScores(String key, double max,
			double min, int offset, int count) {
		MasterSlaveJedis j = getShard(key);
		return j.zrevrangeByScoreWithScores(key, max, min, offset, count);
	}

	public Set<String> zrangeByScore(String key, String min, String max) {
		MasterSlaveJedis j = getShard(key);
		return j.zrangeByScore(key, min, max);
	}

	public Set<String> zrevrangeByScore(String key, String max, String min) {
		MasterSlaveJedis j = getShard(key);
		return j.zrevrangeByScore(key, max, min);
	}

	public Set<String> zrangeByScore(String key, String min, String max,
			int offset, int count) {
		MasterSlaveJedis j = getShard(key);
		return j.zrangeByScore(key, min, max, offset, count);
	}

	public Set<String> zrevrangeByScore(String key, String max, String min,
			int offset, int count) {
		MasterSlaveJedis j = getShard(key);
		return j.zrevrangeByScore(key, max, min, offset, count);
	}

	public Set<Tuple> zrangeByScoreWithScores(String key, String min, String max) {
		MasterSlaveJedis j = getShard(key);
		return j.zrangeByScoreWithScores(key, min, max);
	}

	public Set<Tuple> zrevrangeByScoreWithScores(String key, String max,
			String min) {
		MasterSlaveJedis j = getShard(key);
		return j.zrevrangeByScoreWithScores(key, max, min);
	}

	public Set<Tuple> zrangeByScoreWithScores(String key, String min,
			String max, int offset, int count) {
		MasterSlaveJedis j = getShard(key);
		return j.zrangeByScoreWithScores(key, min, max, offset, count);
	}

	public Set<Tuple> zrevrangeByScoreWithScores(String key, String max,
			String min, int offset, int count) {
		MasterSlaveJedis j = getShard(key);
		return j.zrevrangeByScoreWithScores(key, max, min, offset, count);
	}

	public Long zremrangeByRank(String key, long start, long end) {
		MasterSlaveJedis j = getShard(key);
		return j.zremrangeByRank(key, start, end);
	}

	public Long zremrangeByScore(String key, double start, double end) {
		MasterSlaveJedis j = getShard(key);
		return j.zremrangeByScore(key, start, end);
	}

	public Long zremrangeByScore(String key, String start, String end) {
		MasterSlaveJedis j = getShard(key);
		return j.zremrangeByScore(key, start, end);
	}

	public Long zlexcount(final String key, final String min, final String max) {
		return getShard(key).zlexcount(key, min, max);
	}

	public Set<String> zrangeByLex(final String key, final String min,
			final String max) {
		return getShard(key).zrangeByLex(key, min, max);
	}

	public Set<String> zrangeByLex(final String key, final String min,
			final String max, final int offset, final int count) {
		return getShard(key).zrangeByLex(key, min, max, offset, count);
	}

	public Long zremrangeByLex(final String key, final String min,
			final String max) {
		return getShard(key).zremrangeByLex(key, min, max);
	}

	public Long linsert(String key, LIST_POSITION where, String pivot,
			String value) {
		MasterSlaveJedis j = getShard(key);
		return j.linsert(key, where, pivot, value);
	}

	public Long bitcount(final String key) {
		MasterSlaveJedis j = getShard(key);
		return j.bitcount(key);
	}

	public Long bitcount(final String key, long start, long end) {
		MasterSlaveJedis j = getShard(key);
		return j.bitcount(key, start, end);
	}

	@Deprecated
	/**
	 * This method is deprecated due to bug (scan cursor should be unsigned long)
	 * And will be removed on next major release
	 * @see https://github.com/xetorthio/jedis/issues/531 
	 */
	public ScanResult<Entry<String, String>> hscan(String key, int cursor) {
		MasterSlaveJedis j = getShard(key);
		return j.hscan(key, cursor);
	}

	@Deprecated
	/**
	 * This method is deprecated due to bug (scan cursor should be unsigned long)
	 * And will be removed on next major release
	 * @see https://github.com/xetorthio/jedis/issues/531 
	 */
	public ScanResult<String> sscan(String key, int cursor) {
		MasterSlaveJedis j = getShard(key);
		return j.sscan(key, cursor);
	}

	@Deprecated
	/**
	 * This method is deprecated due to bug (scan cursor should be unsigned long)
	 * And will be removed on next major release
	 * @see https://github.com/xetorthio/jedis/issues/531 
	 */
	public ScanResult<Tuple> zscan(String key, int cursor) {
		MasterSlaveJedis j = getShard(key);
		return j.zscan(key, cursor);
	}

	public ScanResult<Entry<String, String>> hscan(String key,
			final String cursor) {
		MasterSlaveJedis j = getShard(key);
		return j.hscan(key, cursor);
	}

	public ScanResult<String> sscan(String key, final String cursor) {
		MasterSlaveJedis j = getShard(key);
		return j.sscan(key, cursor);
	}

	public ScanResult<Tuple> zscan(String key, final String cursor) {
		MasterSlaveJedis j = getShard(key);
		return j.zscan(key, cursor);
	}

	public Long pfadd(String key, String... elements) {
		MasterSlaveJedis j = getShard(key);
		return j.pfadd(key, elements);
	}

	public long pfcount(String key) {
		MasterSlaveJedis j = getShard(key);
		return j.pfcount(key);
	}

	public String set(byte[] key, byte[] value) {
		MasterSlaveJedis j = getShard(key);
		return j.set(key, value);
	}

	public byte[] get(byte[] key) {
		MasterSlaveJedis j = getShard(key);
		return j.get(key);
	}

	public Boolean exists(byte[] key) {
		MasterSlaveJedis j = getShard(key);
		return j.exists(key);
	}

	public String type(byte[] key) {
		MasterSlaveJedis j = getShard(key);
		return j.type(key);
	}

	public Long expire(byte[] key, int seconds) {
		MasterSlaveJedis j = getShard(key);
		return j.expire(key, seconds);
	}

	public Long expireAt(byte[] key, long unixTime) {
		MasterSlaveJedis j = getShard(key);
		return j.expireAt(key, unixTime);
	}

	public Long ttl(byte[] key) {
		MasterSlaveJedis j = getShard(key);
		return j.ttl(key);
	}

	public byte[] getSet(byte[] key, byte[] value) {
		MasterSlaveJedis j = getShard(key);
		return j.getSet(key, value);
	}

	public Long setnx(byte[] key, byte[] value) {
		MasterSlaveJedis j = getShard(key);
		return j.setnx(key, value);
	}

	public String setex(byte[] key, int seconds, byte[] value) {
		MasterSlaveJedis j = getShard(key);
		return j.setex(key, seconds, value);
	}

	public Long decrBy(byte[] key, long integer) {
		MasterSlaveJedis j = getShard(key);
		return j.decrBy(key, integer);
	}

	public Long decr(byte[] key) {
		MasterSlaveJedis j = getShard(key);
		return j.decr(key);
	}

	public Long del(byte[] key) {
		MasterSlaveJedis j = getShard(key);
		return j.del(key);
	}

	public Long incrBy(byte[] key, long integer) {
		MasterSlaveJedis j = getShard(key);
		return j.incrBy(key, integer);
	}

	public Double incrByFloat(byte[] key, double integer) {
		MasterSlaveJedis j = getShard(key);
		return j.incrByFloat(key, integer);
	}

	public Long incr(byte[] key) {
		MasterSlaveJedis j = getShard(key);
		return j.incr(key);
	}

	public Long append(byte[] key, byte[] value) {
		MasterSlaveJedis j = getShard(key);
		return j.append(key, value);
	}

	public byte[] substr(byte[] key, int start, int end) {
		MasterSlaveJedis j = getShard(key);
		return j.substr(key, start, end);
	}

	public Long hset(byte[] key, byte[] field, byte[] value) {
		MasterSlaveJedis j = getShard(key);
		return j.hset(key, field, value);
	}

	public byte[] hget(byte[] key, byte[] field) {
		MasterSlaveJedis j = getShard(key);
		return j.hget(key, field);
	}

	public Long hsetnx(byte[] key, byte[] field, byte[] value) {
		MasterSlaveJedis j = getShard(key);
		return j.hsetnx(key, field, value);
	}

	public String hmset(byte[] key, Map<byte[], byte[]> hash) {
		MasterSlaveJedis j = getShard(key);
		return j.hmset(key, hash);
	}

	public List<byte[]> hmget(byte[] key, byte[]... fields) {
		MasterSlaveJedis j = getShard(key);
		return j.hmget(key, fields);
	}

	public Long hincrBy(byte[] key, byte[] field, long value) {
		MasterSlaveJedis j = getShard(key);
		return j.hincrBy(key, field, value);
	}

	public Double hincrByFloat(byte[] key, byte[] field, double value) {
		MasterSlaveJedis j = getShard(key);
		return j.hincrByFloat(key, field, value);
	}

	public Boolean hexists(byte[] key, byte[] field) {
		MasterSlaveJedis j = getShard(key);
		return j.hexists(key, field);
	}

	public Long hdel(byte[] key, byte[]... fields) {
		MasterSlaveJedis j = getShard(key);
		return j.hdel(key, fields);
	}

	public Long hlen(byte[] key) {
		MasterSlaveJedis j = getShard(key);
		return j.hlen(key);
	}

	public Set<byte[]> hkeys(byte[] key) {
		MasterSlaveJedis j = getShard(key);
		return j.hkeys(key);
	}

	public Collection<byte[]> hvals(byte[] key) {
		MasterSlaveJedis j = getShard(key);
		return j.hvals(key);
	}

	public Map<byte[], byte[]> hgetAll(byte[] key) {
		MasterSlaveJedis j = getShard(key);
		return j.hgetAll(key);
	}

	public Long rpush(byte[] key, byte[]... strings) {
		MasterSlaveJedis j = getShard(key);
		return j.rpush(key, strings);
	}

	public Long lpush(byte[] key, byte[]... strings) {
		MasterSlaveJedis j = getShard(key);
		return j.lpush(key, strings);
	}

	public Long strlen(final byte[] key) {
		MasterSlaveJedis j = getShard(key);
		return j.strlen(key);
	}

	public Long lpushx(byte[] key, byte[]... string) {
		MasterSlaveJedis j = getShard(key);
		return j.lpushx(key, string);
	}

	public Long persist(final byte[] key) {
		MasterSlaveJedis j = getShard(key);
		return j.persist(key);
	}

	public Long rpushx(byte[] key, byte[]... string) {
		MasterSlaveJedis j = getShard(key);
		return j.rpushx(key, string);
	}

	public Long llen(byte[] key) {
		MasterSlaveJedis j = getShard(key);
		return j.llen(key);
	}

	public List<byte[]> lrange(byte[] key, long start, long end) {
		MasterSlaveJedis j = getShard(key);
		return j.lrange(key, start, end);
	}

	public String ltrim(byte[] key, long start, long end) {
		MasterSlaveJedis j = getShard(key);
		return j.ltrim(key, start, end);
	}

	public byte[] lindex(byte[] key, long index) {
		MasterSlaveJedis j = getShard(key);
		return j.lindex(key, index);
	}

	public String lset(byte[] key, long index, byte[] value) {
		MasterSlaveJedis j = getShard(key);
		return j.lset(key, index, value);
	}

	public Long lrem(byte[] key, long count, byte[] value) {
		MasterSlaveJedis j = getShard(key);
		return j.lrem(key, count, value);
	}

	public byte[] lpop(byte[] key) {
		MasterSlaveJedis j = getShard(key);
		return j.lpop(key);
	}

	public byte[] rpop(byte[] key) {
		MasterSlaveJedis j = getShard(key);
		return j.rpop(key);
	}

	public Long sadd(byte[] key, byte[]... members) {
		MasterSlaveJedis j = getShard(key);
		return j.sadd(key, members);
	}

	public Set<byte[]> smembers(byte[] key) {
		MasterSlaveJedis j = getShard(key);
		return j.smembers(key);
	}

	public Long srem(byte[] key, byte[]... members) {
		MasterSlaveJedis j = getShard(key);
		return j.srem(key, members);
	}

	public byte[] spop(byte[] key) {
		MasterSlaveJedis j = getShard(key);
		return j.spop(key);
	}

	public Long scard(byte[] key) {
		MasterSlaveJedis j = getShard(key);
		return j.scard(key);
	}

	public Boolean sismember(byte[] key, byte[] member) {
		MasterSlaveJedis j = getShard(key);
		return j.sismember(key, member);
	}

	public byte[] srandmember(byte[] key) {
		MasterSlaveJedis j = getShard(key);
		return j.srandmember(key);
	}

	public List<byte[]> srandmember(byte[] key, int count) {
		MasterSlaveJedis j = getShard(key);
		return j.srandmember(key, count);
	}

	public Long zadd(byte[] key, double score, byte[] member) {
		MasterSlaveJedis j = getShard(key);
		return j.zadd(key, score, member);
	}

	public Long zadd(byte[] key, Map<byte[], Double> scoreMembers) {
		MasterSlaveJedis j = getShard(key);
		return j.zadd(key, scoreMembers);
	}

	public Set<byte[]> zrange(byte[] key, long start, long end) {
		MasterSlaveJedis j = getShard(key);
		return j.zrange(key, start, end);
	}

	public Long zrem(byte[] key, byte[]... members) {
		MasterSlaveJedis j = getShard(key);
		return j.zrem(key, members);
	}

	public Double zincrby(byte[] key, double score, byte[] member) {
		MasterSlaveJedis j = getShard(key);
		return j.zincrby(key, score, member);
	}

	public Long zrank(byte[] key, byte[] member) {
		MasterSlaveJedis j = getShard(key);
		return j.zrank(key, member);
	}

	public Long zrevrank(byte[] key, byte[] member) {
		MasterSlaveJedis j = getShard(key);
		return j.zrevrank(key, member);
	}

	public Set<byte[]> zrevrange(byte[] key, long start, long end) {
		MasterSlaveJedis j = getShard(key);
		return j.zrevrange(key, start, end);
	}

	public Set<Tuple> zrangeWithScores(byte[] key, long start, long end) {
		MasterSlaveJedis j = getShard(key);
		return j.zrangeWithScores(key, start, end);
	}

	public Set<Tuple> zrevrangeWithScores(byte[] key, long start, long end) {
		MasterSlaveJedis j = getShard(key);
		return j.zrevrangeWithScores(key, start, end);
	}

	public Long zcard(byte[] key) {
		MasterSlaveJedis j = getShard(key);
		return j.zcard(key);
	}

	public Double zscore(byte[] key, byte[] member) {
		MasterSlaveJedis j = getShard(key);
		return j.zscore(key, member);
	}

	public List<byte[]> sort(byte[] key) {
		MasterSlaveJedis j = getShard(key);
		return j.sort(key);
	}

	public List<byte[]> sort(byte[] key, SortingParams sortingParameters) {
		MasterSlaveJedis j = getShard(key);
		return j.sort(key, sortingParameters);
	}

	public Long zcount(byte[] key, double min, double max) {
		MasterSlaveJedis j = getShard(key);
		return j.zcount(key, min, max);
	}

	public Long zcount(byte[] key, byte[] min, byte[] max) {
		MasterSlaveJedis j = getShard(key);
		return j.zcount(key, min, max);
	}

	public Set<byte[]> zrangeByScore(byte[] key, double min, double max) {
		MasterSlaveJedis j = getShard(key);
		return j.zrangeByScore(key, min, max);
	}

	public Set<byte[]> zrangeByScore(byte[] key, double min, double max,
			int offset, int count) {
		MasterSlaveJedis j = getShard(key);
		return j.zrangeByScore(key, min, max, offset, count);
	}

	public Set<Tuple> zrangeByScoreWithScores(byte[] key, double min, double max) {
		MasterSlaveJedis j = getShard(key);
		return j.zrangeByScoreWithScores(key, min, max);
	}

	public Set<Tuple> zrangeByScoreWithScores(byte[] key, double min,
			double max, int offset, int count) {
		MasterSlaveJedis j = getShard(key);
		return j.zrangeByScoreWithScores(key, min, max, offset, count);
	}

	public Set<byte[]> zrangeByScore(byte[] key, byte[] min, byte[] max) {
		MasterSlaveJedis j = getShard(key);
		return j.zrangeByScore(key, min, max);
	}

	public Set<Tuple> zrangeByScoreWithScores(byte[] key, byte[] min, byte[] max) {
		MasterSlaveJedis j = getShard(key);
		return j.zrangeByScoreWithScores(key, min, max);
	}

	public Set<Tuple> zrangeByScoreWithScores(byte[] key, byte[] min,
			byte[] max, int offset, int count) {
		MasterSlaveJedis j = getShard(key);
		return j.zrangeByScoreWithScores(key, min, max, offset, count);
	}

	public Set<byte[]> zrangeByScore(byte[] key, byte[] min, byte[] max,
			int offset, int count) {
		MasterSlaveJedis j = getShard(key);
		return j.zrangeByScore(key, min, max, offset, count);
	}

	public Set<byte[]> zrevrangeByScore(byte[] key, double max, double min) {
		MasterSlaveJedis j = getShard(key);
		return j.zrevrangeByScore(key, max, min);
	}

	public Set<byte[]> zrevrangeByScore(byte[] key, double max, double min,
			int offset, int count) {
		MasterSlaveJedis j = getShard(key);
		return j.zrevrangeByScore(key, max, min, offset, count);
	}

	public Set<Tuple> zrevrangeByScoreWithScores(byte[] key, double max,
			double min) {
		MasterSlaveJedis j = getShard(key);
		return j.zrevrangeByScoreWithScores(key, max, min);
	}

	public Set<Tuple> zrevrangeByScoreWithScores(byte[] key, double max,
			double min, int offset, int count) {
		MasterSlaveJedis j = getShard(key);
		return j.zrevrangeByScoreWithScores(key, max, min, offset, count);
	}

	public Set<byte[]> zrevrangeByScore(byte[] key, byte[] max, byte[] min) {
		MasterSlaveJedis j = getShard(key);
		return j.zrevrangeByScore(key, max, min);
	}

	public Set<byte[]> zrevrangeByScore(byte[] key, byte[] max, byte[] min,
			int offset, int count) {
		MasterSlaveJedis j = getShard(key);
		return j.zrevrangeByScore(key, max, min, offset, count);
	}

	public Set<Tuple> zrevrangeByScoreWithScores(byte[] key, byte[] max,
			byte[] min) {
		MasterSlaveJedis j = getShard(key);
		return j.zrevrangeByScoreWithScores(key, max, min);
	}

	public Set<Tuple> zrevrangeByScoreWithScores(byte[] key, byte[] max,
			byte[] min, int offset, int count) {
		MasterSlaveJedis j = getShard(key);
		return j.zrevrangeByScoreWithScores(key, max, min, offset, count);
	}

	public Long zremrangeByRank(byte[] key, long start, long end) {
		MasterSlaveJedis j = getShard(key);
		return j.zremrangeByRank(key, start, end);
	}

	public Long zremrangeByScore(byte[] key, double start, double end) {
		MasterSlaveJedis j = getShard(key);
		return j.zremrangeByScore(key, start, end);
	}

	public Long zremrangeByScore(byte[] key, byte[] start, byte[] end) {
		MasterSlaveJedis j = getShard(key);
		return j.zremrangeByScore(key, start, end);
	}

	public Long zlexcount(final byte[] key, final byte[] min, final byte[] max) {
		MasterSlaveJedis j = getShard(key);
		return j.zlexcount(key, min, max);
	}

	public Set<byte[]> zrangeByLex(final byte[] key, final byte[] min,
			final byte[] max) {
		MasterSlaveJedis j = getShard(key);
		return j.zrangeByLex(key, min, max);
	}

	public Set<byte[]> zrangeByLex(final byte[] key, final byte[] min,
			final byte[] max, final int offset, final int count) {
		MasterSlaveJedis j = getShard(key);
		return j.zrangeByLex(key, min, max, offset, count);
	}

	public Long zremrangeByLex(final byte[] key, final byte[] min,
			final byte[] max) {
		MasterSlaveJedis j = getShard(key);
		return j.zremrangeByLex(key, min, max);
	}

	public Long linsert(byte[] key, LIST_POSITION where, byte[] pivot,
			byte[] value) {
		MasterSlaveJedis j = getShard(key);
		return j.linsert(key, where, pivot, value);
	}

	public Long objectRefcount(byte[] key) {
		MasterSlaveJedis j = getShard(key);
		return j.objectRefcount(key);
	}

	public byte[] objectEncoding(byte[] key) {
		MasterSlaveJedis j = getShard(key);
		return j.objectEncoding(key);
	}

	public Long objectIdletime(byte[] key) {
		MasterSlaveJedis j = getShard(key);
		return j.objectIdletime(key);
	}

	public Boolean setbit(byte[] key, long offset, boolean value) {
		MasterSlaveJedis j = getShard(key);
		return j.setbit(key, offset, value);
	}

	public Boolean setbit(byte[] key, long offset, byte[] value) {
		MasterSlaveJedis j = getShard(key);
		return j.setbit(key, offset, value);
	}

	public Boolean getbit(byte[] key, long offset) {
		MasterSlaveJedis j = getShard(key);
		return j.getbit(key, offset);
	}

	public Long setrange(byte[] key, long offset, byte[] value) {
		MasterSlaveJedis j = getShard(key);
		return j.setrange(key, offset, value);
	}

	public byte[] getrange(byte[] key, long startOffset, long endOffset) {
		MasterSlaveJedis j = getShard(key);
		return j.getrange(key, startOffset, endOffset);
	}

	public Long move(byte[] key, int dbIndex) {
		MasterSlaveJedis j = getShard(key);
		return j.move(key, dbIndex);
	}

	public byte[] echo(byte[] arg) {
		MasterSlaveJedis j = getShard(arg);
		return j.echo(arg);
	}

	@Deprecated
	public List<byte[]> brpop(byte[] arg) {
		MasterSlaveJedis j = getShard(arg);
		return j.brpop(arg);
	}

	@Deprecated
	public List<byte[]> blpop(byte[] arg) {
		MasterSlaveJedis j = getShard(arg);
		return j.blpop(arg);
	}

	public Long bitcount(byte[] key) {
		MasterSlaveJedis j = getShard(key);
		return j.bitcount(key);
	}

	public Long bitcount(byte[] key, long start, long end) {
		MasterSlaveJedis j = getShard(key);
		return j.bitcount(key, start, end);
	}

	public Long pfadd(final byte[] key, final byte[]... elements) {
		MasterSlaveJedis j = getShard(key);
		return j.pfadd(key, elements);
	}

	public long pfcount(final byte[] key) {
		MasterSlaveJedis j = getShard(key);
		return j.pfcount(key);
	}

	public void close() {
		if (dataSource != null) {
			boolean broken = false;

			for (MasterSlaveJedis jedis : getAllShards()) {
				if (jedis.getClient().isBroken()) {
					broken = true;
					break;
				}
			}

			if (broken) {
				dataSource.returnBrokenResource(this);
			} else {
				dataSource.returnResource(this);
			}

		} else {
			disconnect();
		}
	}

	public void setDataSource(Pool<ShardedMasterSlaveJedis> shardedJedisPool) {
		this.dataSource = shardedJedisPool;
	}

	public void resetState() {
		for (MasterSlaveJedis jedis : getAllShards()) {
			jedis.resetState();
		}
	}

	public void disconnect() {
		for (MasterSlaveJedis jedis : getAllShards()) {
			jedis.disconnect();
		}
	}

	public boolean ping() {
		boolean b = true;
		try {
			for (MasterSlaveJedis jedis : getAllShards()) {
				b = b && "PONG".equals(jedis.ping());
				if(!b){
					break;
				}
			}
		} catch (Exception e) {
			b = false;
		}
		return b;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("ShardedMasterSlaveJedis@" + Integer.toHexString(hashCode()) + " ");
		sb.append("[");
		for(int i = 0, len = shards.size(); i < len; i++){
			sb.append(shards.get(i));
			if(i != len - 1){
				sb.append(", ");
			}
		}
		sb.append("]}");
		return sb.toString();
	}



	@Override
	public String set(String key, String value, String nxxx) {
		MasterSlaveJedis j = getShard(key);
		return j.set(key, value, nxxx);
	}

	@Override
	public Long pexpireAt(String key, long millisecondsTimestamp) {
		MasterSlaveJedis j = getShard(key);
		return j.pexpireAt(key, millisecondsTimestamp);
	}

	@Override
	public Long pttl(String key) {
		MasterSlaveJedis j = getShard(key);
		return j.pttl(key);
	}

	@Override
	public String psetex(String key, long milliseconds, String value) {
		MasterSlaveJedis j = getShard(key);
		return j.psetex(key, milliseconds, value);
	}

	@Override
	public Set<String> spop(String key, long count) {
		MasterSlaveJedis j = getShard(key);
		return j.spop(key, count);
	}

	@Override
	public Long zadd(String key, double score, String member, ZAddParams params) {
		MasterSlaveJedis j = getShard(key);
		return j.zadd(key, score, member, params);
	}

	@Override
	public Long zadd(String key, Map<String, Double> scoreMembers, ZAddParams params) {
		MasterSlaveJedis j = getShard(key);
		return j.zadd(key, scoreMembers, params);
	}

	@Override
	public Double zincrby(String key, double score, String member, ZIncrByParams params) {
		MasterSlaveJedis j = getShard(key);
		return j.zincrby(key, score, member, params);
	}

	@Override
	public Set<String> zrevrangeByLex(String key, String max, String min) {
		return getShard(key).zrevrangeByLex(key, max, min);
	}

	@Override
	public Set<String> zrevrangeByLex(String key, String max, String min, int offset, int count) {
		return getShard(key).zrevrangeByLex(key, max, min, offset, count);
	}

	@Override
	public Long bitpos(String key, boolean value) {
		MasterSlaveJedis j = getShard(key);
		return j.bitpos(key, value);
	}

	@Override
	public Long bitpos(String key, boolean value, BitPosParams params) {
		MasterSlaveJedis j = getShard(key);
		return j.bitpos(key, value, params);
	}

	@Override
	public ScanResult<Entry<String, String>> hscan(String key, String cursor, ScanParams params) {
		MasterSlaveJedis j = getShard(key);
		return j.hscan(key, cursor, params);
	}

	@Override
	public ScanResult<String> sscan(String key, String cursor, ScanParams params) {
		MasterSlaveJedis j = getShard(key);
		return j.sscan(key, cursor, params);
	}

	@Override
	public ScanResult<Tuple> zscan(String key, String cursor, ScanParams params) {
		MasterSlaveJedis j = getShard(key);
		return j.zscan(key, cursor, params);
	}

	@Override
	public Long geoadd(String key, double longitude, double latitude, String member) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long geoadd(String key, Map<String, GeoCoordinate> memberCoordinateMap) {
		MasterSlaveJedis j = getShard(key);
		return j.geoadd(key, memberCoordinateMap);
	}

	@Override
	public Double geodist(String key, String member1, String member2) {
		MasterSlaveJedis j = getShard(key);
		return j.geodist(key, member1, member2);
	}

	@Override
	public Double geodist(String key, String member1, String member2, GeoUnit unit) {
		MasterSlaveJedis j = getShard(key);
		return j.geodist(key, member1, member2, unit);
	}

	@Override
	public List<String> geohash(String key, String... members) {
		MasterSlaveJedis j = getShard(key);
		return j.geohash(key, members);
	}

	@Override
	public List<GeoCoordinate> geopos(String key, String... members) {
		MasterSlaveJedis j = getShard(key);
		return j.geopos(key, members);
	}

	@Override
	public List<GeoRadiusResponse> georadius(String key, double longitude, double latitude, double radius,
			GeoUnit unit) {
		MasterSlaveJedis j = getShard(key);
		return j.georadius(key, longitude, latitude, radius, unit);
	}

	@Override
	public List<GeoRadiusResponse> georadius(String key, double longitude, double latitude, double radius, GeoUnit unit,
			GeoRadiusParam param) {
		MasterSlaveJedis j = getShard(key);
		return j.georadius(key, longitude, latitude, radius, unit, param);
	}

	@Override
	public List<GeoRadiusResponse> georadiusByMember(String key, String member, double radius, GeoUnit unit) {
		MasterSlaveJedis j = getShard(key);
		return j.georadiusByMember(key, member, radius, unit);
	}

	@Override
	public List<GeoRadiusResponse> georadiusByMember(String key, String member, double radius, GeoUnit unit,
			GeoRadiusParam param) {
		MasterSlaveJedis j = getShard(key);
		return j.georadiusByMember(key, member, radius, unit, param);
	}


	@Override
	public String set(byte[] key, byte[] value, byte[] nxxx) {
		MasterSlaveJedis j = getShard(key);
		return j.set(key, value, nxxx);
	}

	@Override
	public String set(byte[] key, byte[] value, byte[] nxxx, byte[] expx, long time) {
		MasterSlaveJedis j = getShard(key);
		return j.set(key, value, nxxx, expx, time);
	}

	@Override
	public Long pexpire(String key, long milliseconds) {
		MasterSlaveJedis j = getShard(key);
		return j.pexpire(key, milliseconds);
	}

	@Override
	public Long pexpire(byte[] key, long milliseconds) {
		MasterSlaveJedis j = getShard(key);
		return j.pexpire(key, milliseconds);
	}

	@Override
	public Long pexpireAt(byte[] key, long millisecondsTimestamp) {
		MasterSlaveJedis j = getShard(key);
		return j.pexpireAt(key, millisecondsTimestamp);
	}

	@Override
	public Set<byte[]> spop(byte[] key, long count) {
		MasterSlaveJedis j = getShard(key);
		return j.spop(key, count);
	}

	@Override
	public Long zadd(byte[] key, double score, byte[] member, ZAddParams params) {
		MasterSlaveJedis j = getShard(key);
		return j.zadd(key, score, member, params);
	}

	@Override
	public Long zadd(byte[] key, Map<byte[], Double> scoreMembers, ZAddParams params) {
		MasterSlaveJedis j = getShard(key);
		return j.zadd(key, scoreMembers, params);
	}

	@Override
	public Double zincrby(byte[] key, double score, byte[] member, ZIncrByParams params) {
		MasterSlaveJedis j = getShard(key);
		return j.zincrby(key, score, member, params);
	}

	@Override
	public Set<byte[]> zrevrangeByLex(byte[] key, byte[] max, byte[] min) {
		return getShard(key).zrevrangeByLex(key, max, min);
	}

	@Override
	public Set<byte[]> zrevrangeByLex(byte[] key, byte[] max, byte[] min, int offset, int count) {
		return getShard(key).zrevrangeByLex(key, max, min, offset, count);
	}

	@Override
	public Long geoadd(byte[] key, double longitude, double latitude, byte[] member) {
		MasterSlaveJedis j = getShard(key);
		return j.geoadd(key, longitude, latitude, member);
	}

	@Override
	public Long geoadd(byte[] key, Map<byte[], GeoCoordinate> memberCoordinateMap) {
		MasterSlaveJedis j = getShard(key);
		return j.geoadd(key, memberCoordinateMap);
	}

	@Override
	public Double geodist(byte[] key, byte[] member1, byte[] member2) {
		MasterSlaveJedis j = getShard(key);
		return j.geodist(key, member1, member2);
	}

	@Override
	public Double geodist(byte[] key, byte[] member1, byte[] member2, GeoUnit unit) {
		MasterSlaveJedis j = getShard(key);
		return j.geodist(key, member1, member2, unit);
	}

	@Override
	public List<byte[]> geohash(byte[] key, byte[]... members) {
		MasterSlaveJedis j = getShard(key);
		return j.geohash(key, members);
	}

	@Override
	public List<GeoCoordinate> geopos(byte[] key, byte[]... members) {
		MasterSlaveJedis j = getShard(key);
		return j.geopos(key, members);
	}

	@Override
	public List<GeoRadiusResponse> georadius(byte[] key, double longitude, double latitude, double radius,
			GeoUnit unit) {
		MasterSlaveJedis j = getShard(key);
		return j.georadius(key, longitude, latitude, radius, unit);
	}

	@Override
	public List<GeoRadiusResponse> georadius(byte[] key, double longitude, double latitude, double radius, GeoUnit unit,
			GeoRadiusParam param) {
		MasterSlaveJedis j = getShard(key);
		return j.georadius(key, longitude, latitude, radius, unit, param);
	}

	@Override
	public List<GeoRadiusResponse> georadiusByMember(byte[] key, byte[] member, double radius, GeoUnit unit) {
		MasterSlaveJedis j = getShard(key);
		return j.georadiusByMember(key, member, radius, unit);
	}

	@Override
	public List<GeoRadiusResponse> georadiusByMember(byte[] key, byte[] member, double radius, GeoUnit unit,
			GeoRadiusParam param) {
		MasterSlaveJedis j = getShard(key);
		return j.georadiusByMember(key, member, radius, unit, param);
	}

	@Deprecated
	@Override
	public ScanResult<Entry<byte[], byte[]>> hscan(byte[] key, byte[] cursor) {
		MasterSlaveJedis j = getShard(key);
		return j.hscan(key, cursor);
	}

	@Deprecated
	@Override
	public ScanResult<Entry<byte[], byte[]>> hscan(byte[] key, byte[] cursor, ScanParams params) {
		MasterSlaveJedis j = getShard(key);
		return j.hscan(key, cursor, params);
	}

	@Override
	public ScanResult<byte[]> sscan(byte[] key, byte[] cursor) {
		MasterSlaveJedis j = getShard(key);
		return j.sscan(key, cursor);
	}

	@Override
	public ScanResult<byte[]> sscan(byte[] key, byte[] cursor, ScanParams params) {
		MasterSlaveJedis j = getShard(key);
		return j.sscan(key, cursor, params);
	}

	@Override
	public ScanResult<Tuple> zscan(byte[] key, byte[] cursor) {
		MasterSlaveJedis j = getShard(key);
		return j.zscan(key, cursor);
	}

	@Override
	public ScanResult<Tuple> zscan(byte[] key, byte[] cursor, ScanParams params) {
		MasterSlaveJedis j = getShard(key);
		return j.zscan(key, cursor, params);
	}

}
