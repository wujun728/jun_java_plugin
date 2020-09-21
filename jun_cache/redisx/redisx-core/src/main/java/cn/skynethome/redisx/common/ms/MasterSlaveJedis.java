package cn.skynethome.redisx.common.ms;

import java.io.Closeable;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Pattern;

import redis.clients.jedis.AdvancedBinaryJedisCommands;
import redis.clients.jedis.AdvancedJedisCommands;
import redis.clients.jedis.BasicCommands;
import redis.clients.jedis.BinaryClient.LIST_POSITION;
import redis.clients.jedis.BinaryJedisCommands;
import redis.clients.jedis.BinaryJedisPubSub;
import redis.clients.jedis.BinaryScriptingCommands;
import redis.clients.jedis.BitOP;
import redis.clients.jedis.BitPosParams;
import redis.clients.jedis.Client;
import redis.clients.jedis.DebugParams;
import redis.clients.jedis.GeoCoordinate;
import redis.clients.jedis.GeoRadiusResponse;
import redis.clients.jedis.GeoUnit;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCommands;
import redis.clients.jedis.JedisPubSub;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.MultiKeyBinaryCommands;
import redis.clients.jedis.MultiKeyCommands;
import redis.clients.jedis.ScanParams;
import redis.clients.jedis.ScanResult;
import redis.clients.jedis.ScriptingCommands;
import redis.clients.jedis.SortingParams;
import redis.clients.jedis.Tuple;
import redis.clients.jedis.ZParams;
import redis.clients.jedis.exceptions.JedisConnectionException;
import redis.clients.jedis.params.geo.GeoRadiusParam;
import redis.clients.jedis.params.sortedset.ZAddParams;
import redis.clients.jedis.params.sortedset.ZIncrByParams;
import redis.clients.util.Hashing;
import redis.clients.util.Pool;
import redis.clients.util.Sharded;
import redis.clients.util.Slowlog;

/**
  * 项目名称:[redisx]
  * 包:[cn.skynethome.redisx.common.ms]    
  * 文件名称:[MasterSlaveJedis]  
  * 描述:[MasterSlaveJedis默认情况下,MasterSlaveJedis下的所有方法实际都在Master上执行,
  * 仅仅提供了一个新方法opsForSlave(...)可以获取对应所有Slave中的某个Slave]
  * 创建人:[陆文斌]
  * 创建时间:[2016年12月5日 下午6:00:24]   
  * 修改人:[陆文斌]   
  * 修改时间:[2016年12月5日 下午6:00:24]   
  * 修改备注:[说明本次修改内容]  
  * 版权所有:luwenbin006@163.com
  * 版本:[v1.0]
 */
public class MasterSlaveJedis extends Sharded<Jedis, JedisShardInfo>
		implements BasicCommands, BinaryJedisCommands, MultiKeyBinaryCommands, AdvancedBinaryJedisCommands,
		BinaryScriptingCommands, JedisCommands, MultiKeyCommands, AdvancedJedisCommands, ScriptingCommands, Closeable {

	protected final MasterSlaveHostAndPort connectionDesc;

	protected final JedisShardInfo masterShard;

	protected final List<JedisShardInfo> slaveShards;

	protected final Jedis master;

	protected Pool<MasterSlaveJedis> dataSource = null;

	public MasterSlaveJedis(JedisShardInfo masterShard, List<JedisShardInfo> slaveShards, Hashing algo,
			Pattern tagPattern) {
		super(slaveShards, algo, tagPattern);
		this.masterShard = masterShard;
		this.slaveShards = slaveShards;
		this.master = new Jedis(masterShard);
		Set<HostAndPort> slaves = new LinkedHashSet<HostAndPort>();
		for (JedisShardInfo slaveShard : slaveShards) {
			slaves.add(new HostAndPort(slaveShard.getHost(), slaveShard.getPort()));
		}
		this.connectionDesc = new MasterSlaveHostAndPort(masterShard.getName(),
				new HostAndPort(masterShard.getHost(), masterShard.getPort()), slaves);
	}

	public MasterSlaveJedis(JedisShardInfo masterShard, List<JedisShardInfo> slaveShards, Hashing algo) {
		super(slaveShards, algo);
		this.masterShard = masterShard;
		this.slaveShards = slaveShards;
		this.master = new Jedis(masterShard);
		Set<HostAndPort> slaves = new LinkedHashSet<HostAndPort>();
		for (JedisShardInfo slaveShard : slaveShards) {
			slaves.add(new HostAndPort(slaveShard.getHost(), slaveShard.getPort()));
		}
		this.connectionDesc = new MasterSlaveHostAndPort(masterShard.getName(),
				new HostAndPort(masterShard.getHost(), masterShard.getPort()), slaves);
	}

	public MasterSlaveJedis(JedisShardInfo masterShard, List<JedisShardInfo> slaveShards, Pattern tagPattern) {
		super(slaveShards, tagPattern);
		this.masterShard = masterShard;
		this.slaveShards = slaveShards;
		this.master = new Jedis(masterShard);
		Set<HostAndPort> slaves = new LinkedHashSet<HostAndPort>();
		for (JedisShardInfo slaveShard : slaveShards) {
			slaves.add(new HostAndPort(slaveShard.getHost(), slaveShard.getPort()));
		}
		this.connectionDesc = new MasterSlaveHostAndPort(masterShard.getName(),
				new HostAndPort(masterShard.getHost(), masterShard.getPort()), slaves);
	}

	public MasterSlaveJedis(JedisShardInfo masterShard, List<JedisShardInfo> slaveShards) {
		super(slaveShards);
		this.masterShard = masterShard;
		this.slaveShards = slaveShards;
		this.master = new Jedis(masterShard);
		Set<HostAndPort> slaves = new LinkedHashSet<HostAndPort>();
		for (JedisShardInfo slaveShard : slaveShards) {
			slaves.add(new HostAndPort(slaveShard.getHost(), slaveShard.getPort()));
		}
		this.connectionDesc = new MasterSlaveHostAndPort(masterShard.getName(),
				new HostAndPort(masterShard.getHost(), masterShard.getPort()), slaves);
	}

	/**
	 * 获取当前所有Slave中的某个Slave
	 * 
	 * @param slaveHolder
	 *            - 如果每次调用opsForSlave()方法的入参slaveHolder相同的话,那么其获取的Slave也是相同的
	 * @return - 返回从Jedis
	 */
	public Jedis opsForSlave(String... slaveHolder) {
		String holder = null;
		if (slaveHolder == null || slaveHolder.length == 0) {
			holder = String.valueOf(System.currentTimeMillis());
		} else {
			holder = slaveHolder[0];
			if (holder == null || holder.trim().equals("")) {
				holder = String.valueOf(System.currentTimeMillis());
			}
		}
		return getShard(holder);
	}

	public MasterSlaveHostAndPort getConnectionDesc() {
		return connectionDesc;
	}

	public Client getClient() {
		return master.getClient();
	}

	public Object eval(String script, int keyCount, String... params) {
		return master.eval(script, keyCount, params);
	}

	public Object eval(String script, List<String> keys, List<String> args) {
		return master.eval(script, keys, args);
	}

	public Object eval(String script) {
		return master.eval(script);
	}

	public Object evalsha(String script) {
		return master.evalsha(script);
	}

	public Object evalsha(String sha1, List<String> keys, List<String> args) {
		return master.evalsha(sha1, keys, args);
	}

	public Object evalsha(String sha1, int keyCount, String... params) {
		return master.evalsha(sha1, keyCount, params);
	}

	public Boolean scriptExists(String sha1) {
		return master.scriptExists(sha1);
	}

	public List<Boolean> scriptExists(String... sha1) {
		return master.scriptExists(sha1);
	}

	public String scriptLoad(String script) {
		return master.scriptLoad(script);
	}

	public List<String> configGet(String pattern) {
		return master.configGet(pattern);
	}

	public String configSet(String parameter, String value) {
		return master.configSet(parameter, value);
	}

	public List<Slowlog> slowlogGet() {
		return master.slowlogGet();
	}

	public List<Slowlog> slowlogGet(long entries) {
		return master.slowlogGet(entries);
	}

	public Long objectRefcount(String string) {
		return master.objectRefcount(string);
	}

	public String objectEncoding(String string) {
		return master.objectEncoding(string);
	}

	public Long objectIdletime(String string) {
		return master.objectIdletime(string);
	}

	public Long del(String... keys) {
		return master.del(keys);
	}

	public List<String> blpop(int timeout, String... keys) {
		return master.blpop(timeout, keys);
	}

	public List<String> brpop(int timeout, String... keys) {
		return master.brpop(timeout, keys);
	}

	public List<String> blpop(String... args) {
		return master.blpop(args);
	}

	public List<String> brpop(String... args) {
		return master.brpop(args);
	}

	public Set<String> keys(String pattern) {
		return master.keys(pattern);
	}

	public List<String> mget(String... keys) {
		return master.mget(keys);
	}

	public String mset(String... keysvalues) {
		return master.mset(keysvalues);
	}

	public Long msetnx(String... keysvalues) {
		return master.msetnx(keysvalues);
	}

	public String rename(String oldkey, String newkey) {
		return master.rename(oldkey, newkey);
	}

	public Long renamenx(String oldkey, String newkey) {
		return master.renamenx(oldkey, newkey);
	}

	public String rpoplpush(String srckey, String dstkey) {
		return master.rpoplpush(srckey, dstkey);
	}

	public Set<String> sdiff(String... keys) {
		return master.sdiff(keys);
	}

	public Long sdiffstore(String dstkey, String... keys) {
		return master.sdiffstore(dstkey, keys);
	}

	public Set<String> sinter(String... keys) {
		return master.sinter(keys);
	}

	public Long sinterstore(String dstkey, String... keys) {
		return master.sinterstore(dstkey, keys);
	}

	public Long smove(String srckey, String dstkey, String member) {
		return master.smove(srckey, dstkey, member);
	}

	public Long sort(String key, SortingParams sortingParameters, String dstkey) {
		return master.sort(key, sortingParameters, dstkey);
	}

	public Long sort(String key, String dstkey) {
		return master.sort(key, dstkey);
	}

	public Set<String> sunion(String... keys) {
		return master.sunion(keys);
	}

	public Long sunionstore(String dstkey, String... keys) {
		return master.sunionstore(dstkey, keys);
	}

	public String watch(String... keys) {
		return master.watch(keys);
	}

	public Long zinterstore(String dstkey, String... sets) {
		return master.zinterstore(dstkey, sets);
	}

	public Long zinterstore(String dstkey, ZParams params, String... sets) {
		return master.zinterstore(dstkey, params, sets);
	}

	public Long zunionstore(String dstkey, String... sets) {
		return master.zunionstore(dstkey, sets);
	}

	public Long zunionstore(String dstkey, ZParams params, String... sets) {
		return master.zunionstore(dstkey, params, sets);
	}

	public String brpoplpush(String source, String destination, int timeout) {
		return master.brpoplpush(source, destination, timeout);
	}

	public Long publish(String channel, String message) {
		return master.publish(channel, message);
	}

	public void subscribe(JedisPubSub jedisPubSub, String... channels) {
		master.subscribe(jedisPubSub, channels);
	}

	public void psubscribe(JedisPubSub jedisPubSub, String... patterns) {
		master.psubscribe(jedisPubSub, patterns);
	}

	public String randomKey() {
		return master.randomKey();
	}

	public Long bitop(BitOP op, String destKey, String... srcKeys) {
		return master.bitop(op, destKey, srcKeys);
	}

	@Deprecated
	public ScanResult<String> scan(int cursor) {
		return master.scan(cursor);
	}

	public ScanResult<String> scan(String cursor) {
		return master.scan(cursor);
	}

	public String pfmerge(String destkey, String... sourcekeys) {
		return master.pfmerge(destkey, sourcekeys);
	}

	public long pfcount(String... keys) {
		return master.pfcount(keys);
	}

	public String set(String key, String value) {
		return master.set(key, value);
	}

	public String set(String key, String value, String nxxx, String expx, long time) {
		return master.set(key, value, nxxx, expx, time);
	}

	public String get(String key) {
		return master.get(key);
	}

	public Boolean exists(String key) {
		return master.exists(key);
	}

	public Long persist(String key) {
		return master.persist(key);
	}

	public String type(String key) {
		return master.type(key);
	}

	public Long expire(String key, int seconds) {
		return master.expire(key, seconds);
	}

	public Long expireAt(String key, long unixTime) {
		return master.expireAt(key, unixTime);
	}

	public Long ttl(String key) {
		return master.ttl(key);
	}

	public Boolean setbit(String key, long offset, boolean value) {
		return master.setbit(key, offset, value);
	}

	public Boolean setbit(String key, long offset, String value) {
		return master.setbit(key, offset, value);
	}

	public Boolean getbit(String key, long offset) {
		return master.getbit(key, offset);
	}

	public Long setrange(String key, long offset, String value) {
		return master.setrange(key, offset, value);
	}

	public String getrange(String key, long startOffset, long endOffset) {
		return master.getrange(key, startOffset, endOffset);
	}

	public String getSet(String key, String value) {
		return master.getSet(key, value);
	}

	public Long setnx(String key, String value) {
		return master.setnx(key, value);
	}

	public String setex(String key, int seconds, String value) {
		return master.setex(key, seconds, value);
	}

	public Long decrBy(String key, long integer) {
		return master.decrBy(key, integer);
	}

	public Long decr(String key) {
		return master.decr(key);
	}

	public Long incrBy(String key, long integer) {
		return master.incrBy(key, integer);
	}

	public Double incrByFloat(String key, double integer) {
		return master.incrByFloat(key, integer);
	}

	public Long incr(String key) {
		return master.incr(key);
	}

	public Long append(String key, String value) {
		return master.append(key, value);
	}

	public String substr(String key, int start, int end) {
		return master.substr(key, start, end);
	}

	public Long hset(String key, String field, String value) {
		return master.hset(key, field, value);
	}

	public String hget(String key, String field) {
		return master.hget(key, field);
	}

	public Long hsetnx(String key, String field, String value) {
		return master.hsetnx(key, field, value);
	}

	public String hmset(String key, Map<String, String> hash) {
		return master.hmset(key, hash);
	}

	public List<String> hmget(String key, String... fields) {
		return master.hmget(key, fields);
	}

	public Long hincrBy(String key, String field, long value) {
		return master.hincrBy(key, field, value);
	}

	public Double hincrByFloat(String key, String field, double value) {
		return master.hincrByFloat(key, field, value);
	}

	public Boolean hexists(String key, String field) {
		return master.hexists(key, field);
	}

	public Long hdel(String key, String... fields) {
		return master.hdel(key, fields);
	}

	public Long hlen(String key) {
		return master.hlen(key);
	}

	public Set<String> hkeys(String key) {
		return master.hkeys(key);
	}

	public List<String> hvals(String key) {
		return master.hvals(key);
	}

	public Map<String, String> hgetAll(String key) {
		return master.hgetAll(key);
	}

	public Long rpush(String key, String... strings) {
		return master.rpush(key, strings);
	}

	public Long lpush(String key, String... strings) {
		return master.lpush(key, strings);
	}

	public Long llen(String key) {
		return master.llen(key);
	}

	public List<String> lrange(String key, long start, long end) {
		return master.lrange(key, start, end);
	}

	public String ltrim(String key, long start, long end) {
		return master.ltrim(key, start, end);
	}

	public String lindex(String key, long index) {
		return master.lindex(key, index);
	}

	public String lset(String key, long index, String value) {
		return master.lset(key, index, value);
	}

	public Long lrem(String key, long count, String value) {
		return master.lrem(key, count, value);
	}

	public String lpop(String key) {
		return master.lpop(key);
	}

	public String rpop(String key) {
		return master.rpop(key);
	}

	public Long sadd(String key, String... members) {
		return master.sadd(key, members);
	}

	public Set<String> smembers(String key) {
		return master.smembers(key);
	}

	public Long srem(String key, String... members) {
		return master.srem(key, members);
	}

	public String spop(String key) {
		return master.spop(key);
	}

	public Long scard(String key) {
		return master.scard(key);
	}

	public Boolean sismember(String key, String member) {
		return master.sismember(key, member);
	}

	public String srandmember(String key) {
		return master.srandmember(key);
	}

	public List<String> srandmember(String key, int count) {
		return master.srandmember(key, count);
	}

	public Long strlen(String key) {
		return master.strlen(key);
	}

	public Long zadd(String key, double score, String member) {
		return master.zadd(key, score, member);
	}

	public Long zadd(String key, Map<String, Double> scoreMembers) {
		return master.zadd(key, scoreMembers);
	}

	public Set<String> zrange(String key, long start, long end) {
		return master.zrange(key, start, end);
	}

	public Long zrem(String key, String... members) {
		return master.zrem(key, members);
	}

	public Double zincrby(String key, double score, String member) {
		return master.zincrby(key, score, member);
	}

	public Long zrank(String key, String member) {
		return master.zrank(key, member);
	}

	public Long zrevrank(String key, String member) {
		return master.zrevrank(key, member);
	}

	public Set<String> zrevrange(String key, long start, long end) {
		return master.zrevrange(key, start, end);
	}

	public Set<Tuple> zrangeWithScores(String key, long start, long end) {
		return master.zrangeWithScores(key, start, end);
	}

	public Set<Tuple> zrevrangeWithScores(String key, long start, long end) {
		return master.zrevrangeWithScores(key, start, end);
	}

	public Long zcard(String key) {
		return master.zcard(key);
	}

	public Double zscore(String key, String member) {
		return master.zscore(key, member);
	}

	public List<String> sort(String key) {
		return master.sort(key);
	}

	public List<String> sort(String key, SortingParams sortingParameters) {
		return master.sort(key, sortingParameters);
	}

	public Long zcount(String key, double min, double max) {
		return master.zcount(key, min, max);
	}

	public Long zcount(String key, String min, String max) {
		return master.zcount(key, min, max);
	}

	public Set<String> zrangeByScore(String key, double min, double max) {
		return master.zrangeByScore(key, min, max);
	}

	public Set<String> zrangeByScore(String key, String min, String max) {
		return master.zrangeByScore(key, min, max);
	}

	public Set<String> zrevrangeByScore(String key, double max, double min) {
		return master.zrevrangeByScore(key, max, min);
	}

	public Set<String> zrangeByScore(String key, double min, double max, int offset, int count) {
		return master.zrangeByScore(key, min, max, offset, count);
	}

	public Set<String> zrevrangeByScore(String key, String max, String min) {
		return master.zrevrangeByScore(key, max, min);
	}

	public Set<String> zrangeByScore(String key, String min, String max, int offset, int count) {
		return master.zrangeByScore(key, min, max, offset, count);
	}

	public Set<String> zrevrangeByScore(String key, double max, double min, int offset, int count) {
		return master.zrevrangeByScore(key, max, min, offset, count);
	}

	public Set<Tuple> zrangeByScoreWithScores(String key, double min, double max) {
		return master.zrangeByScoreWithScores(key, min, max);
	}

	public Set<Tuple> zrevrangeByScoreWithScores(String key, double max, double min) {
		return master.zrevrangeByScoreWithScores(key, max, min);
	}

	public Set<Tuple> zrangeByScoreWithScores(String key, double min, double max, int offset, int count) {
		return master.zrangeByScoreWithScores(key, min, max, offset, count);
	}

	public Set<String> zrevrangeByScore(String key, String max, String min, int offset, int count) {
		return master.zrevrangeByScore(key, max, min, offset, count);
	}

	public Set<Tuple> zrangeByScoreWithScores(String key, String min, String max) {
		return master.zrangeByScoreWithScores(key, min, max);
	}

	public Set<Tuple> zrevrangeByScoreWithScores(String key, String max, String min) {
		return master.zrevrangeByScoreWithScores(key, max, min);
	}

	public Set<Tuple> zrangeByScoreWithScores(String key, String min, String max, int offset, int count) {
		return master.zrangeByScoreWithScores(key, min, max, offset, count);
	}

	public Set<Tuple> zrevrangeByScoreWithScores(String key, double max, double min, int offset, int count) {
		return master.zrevrangeByScoreWithScores(key, max, min, offset, count);
	}

	public Set<Tuple> zrevrangeByScoreWithScores(String key, String max, String min, int offset, int count) {
		return master.zrevrangeByScoreWithScores(key, max, min, offset, count);
	}

	public Long zremrangeByRank(String key, long start, long end) {
		return master.zremrangeByRank(key, start, end);
	}

	public Long zremrangeByScore(String key, double start, double end) {
		return master.zremrangeByScore(key, start, end);
	}

	public Long zremrangeByScore(String key, String start, String end) {
		return master.zremrangeByScore(key, start, end);
	}

	public Long zlexcount(String key, String min, String max) {
		return master.zlexcount(key, min, max);
	}

	public Set<String> zrangeByLex(String key, String min, String max) {
		return master.zrangeByLex(key, min, max);
	}

	public Set<String> zrangeByLex(String key, String min, String max, int offset, int count) {
		return master.zrangeByLex(key, min, max, offset, count);
	}

	public Long zremrangeByLex(String key, String min, String max) {
		return master.zremrangeByLex(key, min, max);
	}

	public Long linsert(String key, LIST_POSITION where, String pivot, String value) {
		return master.linsert(key, where, pivot, value);
	}

	public Long lpushx(String key, String... string) {
		return master.lpushx(key, string);
	}

	public Long rpushx(String key, String... string) {
		return master.rpushx(key, string);
	}

	@Deprecated
	public List<String> blpop(String arg) {
		return master.blpop(arg);
	}

	public List<String> blpop(int timeout, String key) {
		return master.blpop(timeout, key);
	}

	@Deprecated
	public List<String> brpop(String arg) {
		return master.brpop(arg);
	}

	public List<String> brpop(int timeout, String key) {
		return master.brpop(timeout, key);
	}

	public Long del(String key) {
		return master.del(key);
	}

	public String echo(String string) {
		return master.echo(string);
	}

	public Long move(String key, int dbIndex) {
		return master.move(key, dbIndex);
	}

	public Long bitcount(String key) {
		return master.bitcount(key);
	}

	public Long bitcount(String key, long start, long end) {
		return master.bitcount(key, start, end);
	}

	@Deprecated
	public ScanResult<Entry<String, String>> hscan(String key, int cursor) {
		return master.hscan(key, cursor);
	}

	@Deprecated
	public ScanResult<String> sscan(String key, int cursor) {
		return master.sscan(key, cursor);
	}

	@Deprecated
	public ScanResult<Tuple> zscan(String key, int cursor) {
		return master.zscan(key, cursor);
	}

	public ScanResult<Entry<String, String>> hscan(String key, String cursor) {
		return master.hscan(key, cursor);
	}

	public ScanResult<String> sscan(String key, String cursor) {
		return master.sscan(key, cursor);
	}

	public ScanResult<Tuple> zscan(String key, String cursor) {
		return master.zscan(key, cursor);
	}

	public Long pfadd(String key, String... elements) {
		return master.pfadd(key, elements);
	}

	public long pfcount(String key) {
		return master.pfcount(key);
	}

	public Object eval(byte[] script, byte[] keyCount, byte[]... params) {
		return master.eval(script, keyCount, params);
	}

	public Object eval(byte[] script, int keyCount, byte[]... params) {
		return master.eval(script, keyCount, params);
	}

	public Object eval(byte[] script, List<byte[]> keys, List<byte[]> args) {
		return master.eval(script, keys, args);
	}

	public Object eval(byte[] script) {
		return master.eval(script);
	}

	public Object evalsha(byte[] script) {
		return master.evalsha(script);
	}

	public Object evalsha(byte[] sha1, List<byte[]> keys, List<byte[]> args) {
		return master.evalsha(sha1, keys, args);
	}

	public Object evalsha(byte[] sha1, int keyCount, byte[]... params) {
		return master.evalsha(sha1, keyCount, params);
	}

	public List<Long> scriptExists(byte[]... sha1) {
		return master.scriptExists(sha1);
	}

	public byte[] scriptLoad(byte[] script) {
		return master.scriptLoad(script);
	}

	public String scriptFlush() {
		return master.scriptFlush();
	}

	public String scriptKill() {
		return master.scriptKill();
	}

	public List<byte[]> configGet(byte[] pattern) {
		return master.configGet(pattern);
	}

	public byte[] configSet(byte[] parameter, byte[] value) {
		return master.configSet(parameter, value);
	}

	public String slowlogReset() {
		return master.slowlogReset();
	}

	public Long slowlogLen() {
		return master.slowlogLen();
	}

	public List<byte[]> slowlogGetBinary() {
		return master.slowlogGetBinary();
	}

	public List<byte[]> slowlogGetBinary(long entries) {
		return master.slowlogGetBinary(entries);
	}

	public Long objectRefcount(byte[] key) {
		return master.objectRefcount(key);
	}

	public byte[] objectEncoding(byte[] key) {
		return master.objectEncoding(key);
	}

	public Long objectIdletime(byte[] key) {
		return master.objectIdletime(key);
	}

	public Long del(byte[]... keys) {
		return master.del(keys);
	}

	public List<byte[]> blpop(int timeout, byte[]... keys) {
		return master.blpop(timeout, keys);
	}

	public List<byte[]> brpop(int timeout, byte[]... keys) {
		return master.brpop(timeout, keys);
	}

	public List<byte[]> blpop(byte[]... args) {
		return master.blpop(args);
	}

	public List<byte[]> brpop(byte[]... args) {
		return master.brpop(args);
	}

	public Set<byte[]> keys(byte[] pattern) {
		return master.keys(pattern);
	}

	public List<byte[]> mget(byte[]... keys) {
		return master.mget(keys);
	}

	public String mset(byte[]... keysvalues) {
		return master.mset(keysvalues);
	}

	public Long msetnx(byte[]... keysvalues) {
		return master.msetnx(keysvalues);
	}

	public String rename(byte[] oldkey, byte[] newkey) {
		return master.rename(oldkey, newkey);
	}

	public Long renamenx(byte[] oldkey, byte[] newkey) {
		return master.renamenx(oldkey, newkey);
	}

	public byte[] rpoplpush(byte[] srckey, byte[] dstkey) {
		return master.rpoplpush(srckey, dstkey);
	}

	public Set<byte[]> sdiff(byte[]... keys) {
		return master.sdiff(keys);
	}

	public Long sdiffstore(byte[] dstkey, byte[]... keys) {
		return master.sdiffstore(dstkey, keys);
	}

	public Set<byte[]> sinter(byte[]... keys) {
		return master.sinter(keys);
	}

	public Long sinterstore(byte[] dstkey, byte[]... keys) {
		return master.sinterstore(dstkey, keys);
	}

	public Long smove(byte[] srckey, byte[] dstkey, byte[] member) {
		return master.smove(srckey, dstkey, member);
	}

	public Long sort(byte[] key, SortingParams sortingParameters, byte[] dstkey) {
		return master.sort(key, sortingParameters, dstkey);
	}

	public Long sort(byte[] key, byte[] dstkey) {
		return master.sort(key, dstkey);
	}

	public Set<byte[]> sunion(byte[]... keys) {
		return master.sunion(keys);
	}

	public Long sunionstore(byte[] dstkey, byte[]... keys) {
		return master.sunionstore(dstkey, keys);
	}

	public String watch(byte[]... keys) {
		return master.watch(keys);
	}

	public String unwatch() {
		return master.unwatch();
	}

	public Long zinterstore(byte[] dstkey, byte[]... sets) {
		return master.zinterstore(dstkey, sets);
	}

	public Long zinterstore(byte[] dstkey, ZParams params, byte[]... sets) {
		return master.zinterstore(dstkey, params, sets);
	}

	public Long zunionstore(byte[] dstkey, byte[]... sets) {
		return master.zunionstore(dstkey, sets);
	}

	public Long zunionstore(byte[] dstkey, ZParams params, byte[]... sets) {
		return master.zunionstore(dstkey, params, sets);
	}

	public byte[] brpoplpush(byte[] source, byte[] destination, int timeout) {
		return master.brpoplpush(source, destination, timeout);
	}

	public Long publish(byte[] channel, byte[] message) {
		return master.publish(channel, message);
	}

	public void subscribe(BinaryJedisPubSub jedisPubSub, byte[]... channels) {
		master.subscribe(jedisPubSub, channels);
	}

	public void psubscribe(BinaryJedisPubSub jedisPubSub, byte[]... patterns) {
		master.psubscribe(jedisPubSub, patterns);
	}

	public byte[] randomBinaryKey() {
		return master.randomBinaryKey();
	}

	public Long bitop(BitOP op, byte[] destKey, byte[]... srcKeys) {
		return master.bitop(op, destKey, srcKeys);
	}

	public String pfmerge(byte[] destkey, byte[]... sourcekeys) {
		return master.pfmerge(destkey, sourcekeys);
	}

	public Long pfcount(byte[]... keys) {
		return master.pfcount(keys);
	}

	public String set(byte[] key, byte[] value) {
		return master.set(key, value);
	}

	public byte[] get(byte[] key) {
		return master.get(key);
	}

	public Boolean exists(byte[] key) {
		return master.exists(key);
	}

	public Long persist(byte[] key) {
		return master.persist(key);
	}

	public String type(byte[] key) {
		return master.type(key);
	}

	public Long expire(byte[] key, int seconds) {
		return master.expire(key, seconds);
	}

	public Long expireAt(byte[] key, long unixTime) {
		return master.expireAt(key, unixTime);
	}

	public Long ttl(byte[] key) {
		return master.ttl(key);
	}

	public Boolean setbit(byte[] key, long offset, boolean value) {
		return master.setbit(key, offset, value);
	}

	public Boolean setbit(byte[] key, long offset, byte[] value) {
		return master.setbit(key, offset, value);
	}

	public Boolean getbit(byte[] key, long offset) {
		return master.getbit(key, offset);
	}

	public Long setrange(byte[] key, long offset, byte[] value) {
		return master.setrange(key, offset, value);
	}

	public byte[] getrange(byte[] key, long startOffset, long endOffset) {
		return master.getrange(key, startOffset, endOffset);
	}

	public byte[] getSet(byte[] key, byte[] value) {
		return master.getSet(key, value);
	}

	public Long setnx(byte[] key, byte[] value) {
		return master.setnx(key, value);
	}

	public String setex(byte[] key, int seconds, byte[] value) {
		return master.setex(key, seconds, value);
	}

	public Long decrBy(byte[] key, long integer) {
		return master.decrBy(key, integer);
	}

	public Long decr(byte[] key) {
		return master.decr(key);
	}

	public Long incrBy(byte[] key, long integer) {
		return master.incrBy(key, integer);
	}

	public Double incrByFloat(byte[] key, double value) {
		return master.incrByFloat(key, value);
	}

	public Long incr(byte[] key) {
		return master.incr(key);
	}

	public Long append(byte[] key, byte[] value) {
		return master.append(key, value);
	}

	public byte[] substr(byte[] key, int start, int end) {
		return master.substr(key, start, end);
	}

	public Long hset(byte[] key, byte[] field, byte[] value) {
		return master.hset(key, field, value);
	}

	public byte[] hget(byte[] key, byte[] field) {
		return master.hget(key, field);
	}

	public Long hsetnx(byte[] key, byte[] field, byte[] value) {
		return master.hsetnx(key, field, value);
	}

	public String hmset(byte[] key, Map<byte[], byte[]> hash) {
		return master.hmset(key, hash);
	}

	public List<byte[]> hmget(byte[] key, byte[]... fields) {
		return master.hmget(key, fields);
	}

	public Long hincrBy(byte[] key, byte[] field, long value) {
		return master.hincrBy(key, field, value);
	}

	public Double hincrByFloat(byte[] key, byte[] field, double value) {
		return master.hincrByFloat(key, field, value);
	}

	public Boolean hexists(byte[] key, byte[] field) {
		return master.hexists(key, field);
	}

	public Long hdel(byte[] key, byte[]... fields) {
		return master.hdel(key, fields);
	}

	public Long hlen(byte[] key) {
		return master.hlen(key);
	}

	public Set<byte[]> hkeys(byte[] key) {
		return master.hkeys(key);
	}

	public Collection<byte[]> hvals(byte[] key) {
		return master.hvals(key);
	}

	public Map<byte[], byte[]> hgetAll(byte[] key) {
		return master.hgetAll(key);
	}

	public Long rpush(byte[] key, byte[]... args) {
		return master.rpush(key, args);
	}

	public Long lpush(byte[] key, byte[]... args) {
		return master.lpush(key, args);
	}

	public Long llen(byte[] key) {
		return master.llen(key);
	}

	public List<byte[]> lrange(byte[] key, long start, long end) {
		return master.lrange(key, start, end);
	}

	public String ltrim(byte[] key, long start, long end) {
		return master.ltrim(key, start, end);
	}

	public byte[] lindex(byte[] key, long index) {
		return master.lindex(key, index);
	}

	public String lset(byte[] key, long index, byte[] value) {
		return master.lset(key, index, value);
	}

	public Long lrem(byte[] key, long count, byte[] value) {
		return master.lrem(key, count, value);
	}

	public byte[] lpop(byte[] key) {
		return master.lpop(key);
	}

	public byte[] rpop(byte[] key) {
		return master.rpop(key);
	}

	public Long sadd(byte[] key, byte[]... members) {
		return master.sadd(key, members);
	}

	public Set<byte[]> smembers(byte[] key) {
		return master.smembers(key);
	}

	public Long srem(byte[] key, byte[]... member) {
		return master.srem(key, member);
	}

	public byte[] spop(byte[] key) {
		return master.spop(key);
	}

	public Long scard(byte[] key) {
		return master.scard(key);
	}

	public Boolean sismember(byte[] key, byte[] member) {
		return master.sismember(key, member);
	}

	public byte[] srandmember(byte[] key) {
		return master.srandmember(key);
	}

	public List<byte[]> srandmember(byte[] key, int count) {
		return master.srandmember(key, count);
	}

	public Long strlen(byte[] key) {
		return master.strlen(key);
	}

	public Long zadd(byte[] key, double score, byte[] member) {
		return master.zadd(key, score, member);
	}

	public Long zadd(byte[] key, Map<byte[], Double> scoreMembers) {
		return master.zadd(key, scoreMembers);
	}

	public Set<byte[]> zrange(byte[] key, long start, long end) {
		return master.zrange(key, start, end);
	}

	public Long zrem(byte[] key, byte[]... members) {
		return master.zrem(key, members);
	}

	public Double zincrby(byte[] key, double score, byte[] member) {
		return master.zincrby(key, score, member);
	}

	public Long zrank(byte[] key, byte[] member) {
		return master.zrank(key, member);
	}

	public Long zrevrank(byte[] key, byte[] member) {
		return master.zrevrank(key, member);
	}

	public Set<byte[]> zrevrange(byte[] key, long start, long end) {
		return master.zrevrange(key, start, end);
	}

	public Set<Tuple> zrangeWithScores(byte[] key, long start, long end) {
		return master.zrangeWithScores(key, start, end);
	}

	public Set<Tuple> zrevrangeWithScores(byte[] key, long start, long end) {
		return master.zrevrangeWithScores(key, start, end);
	}

	public Long zcard(byte[] key) {
		return master.zcard(key);
	}

	public Double zscore(byte[] key, byte[] member) {
		return master.zscore(key, member);
	}

	public List<byte[]> sort(byte[] key) {
		return master.sort(key);
	}

	public List<byte[]> sort(byte[] key, SortingParams sortingParameters) {
		return master.sort(key, sortingParameters);
	}

	public Long zcount(byte[] key, double min, double max) {
		return master.zcount(key, min, max);
	}

	public Long zcount(byte[] key, byte[] min, byte[] max) {
		return master.zcount(key, min, max);
	}

	public Set<byte[]> zrangeByScore(byte[] key, double min, double max) {
		return master.zrangeByScore(key, min, max);
	}

	public Set<byte[]> zrangeByScore(byte[] key, byte[] min, byte[] max) {
		return master.zrangeByScore(key, min, max);
	}

	public Set<byte[]> zrevrangeByScore(byte[] key, double max, double min) {
		return master.zrevrangeByScore(key, max, min);
	}

	public Set<byte[]> zrangeByScore(byte[] key, double min, double max, int offset, int count) {
		return master.zrangeByScore(key, min, max, offset, count);
	}

	public Set<byte[]> zrevrangeByScore(byte[] key, byte[] max, byte[] min) {
		return master.zrevrangeByScore(key, max, min);
	}

	public Set<byte[]> zrangeByScore(byte[] key, byte[] min, byte[] max, int offset, int count) {
		return master.zrangeByScore(key, min, max, offset, count);
	}

	public Set<byte[]> zrevrangeByScore(byte[] key, double max, double min, int offset, int count) {
		return master.zrevrangeByScore(key, max, min, offset, count);
	}

	public Set<Tuple> zrangeByScoreWithScores(byte[] key, double min, double max) {
		return master.zrangeByScoreWithScores(key, min, max);
	}

	public Set<Tuple> zrevrangeByScoreWithScores(byte[] key, double max, double min) {
		return master.zrevrangeByScoreWithScores(key, max, min);
	}

	public Set<Tuple> zrangeByScoreWithScores(byte[] key, double min, double max, int offset, int count) {
		return master.zrangeByScoreWithScores(key, min, max, offset, count);
	}

	public Set<byte[]> zrevrangeByScore(byte[] key, byte[] max, byte[] min, int offset, int count) {
		return master.zrevrangeByScore(key, max, min, offset, count);
	}

	public Set<Tuple> zrangeByScoreWithScores(byte[] key, byte[] min, byte[] max) {
		return master.zrangeByScoreWithScores(key, min, max);
	}

	public Set<Tuple> zrevrangeByScoreWithScores(byte[] key, byte[] max, byte[] min) {
		return master.zrevrangeByScoreWithScores(key, max, min);
	}

	public Set<Tuple> zrangeByScoreWithScores(byte[] key, byte[] min, byte[] max, int offset, int count) {
		return master.zrangeByScoreWithScores(key, min, max, offset, count);
	}

	public Set<Tuple> zrevrangeByScoreWithScores(byte[] key, double max, double min, int offset, int count) {
		return master.zrevrangeByScoreWithScores(key, max, min, offset, count);
	}

	public Set<Tuple> zrevrangeByScoreWithScores(byte[] key, byte[] max, byte[] min, int offset, int count) {
		return master.zrevrangeByScoreWithScores(key, max, min, offset, count);
	}

	public Long zremrangeByRank(byte[] key, long start, long end) {
		return master.zremrangeByRank(key, start, end);
	}

	public Long zremrangeByScore(byte[] key, double start, double end) {
		return master.zremrangeByScore(key, start, end);
	}

	public Long zremrangeByScore(byte[] key, byte[] start, byte[] end) {
		return master.zremrangeByScore(key, start, end);
	}

	public Long zlexcount(byte[] key, byte[] min, byte[] max) {
		return master.zlexcount(key, min, max);
	}

	public Set<byte[]> zrangeByLex(byte[] key, byte[] min, byte[] max) {
		return master.zrangeByLex(key, min, max);
	}

	public Set<byte[]> zrangeByLex(byte[] key, byte[] min, byte[] max, int offset, int count) {
		return master.zrangeByLex(key, min, max, offset, count);
	}

	public Long zremrangeByLex(byte[] key, byte[] min, byte[] max) {
		return master.zremrangeByLex(key, min, max);
	}

	public Long linsert(byte[] key, LIST_POSITION where, byte[] pivot, byte[] value) {
		return master.linsert(key, where, pivot, value);
	}

	public Long lpushx(byte[] key, byte[]... arg) {
		return master.lpushx(key, arg);
	}

	public Long rpushx(byte[] key, byte[]... arg) {
		return master.rpushx(key, arg);
	}

	@Deprecated
	public List<byte[]> blpop(byte[] arg) {
		return master.blpop(arg);
	}

	@Deprecated
	public List<byte[]> brpop(byte[] arg) {
		return master.brpop(arg);
	}

	public Long del(byte[] key) {
		return master.del(key);
	}

	public byte[] echo(byte[] arg) {
		return master.echo(arg);
	}

	public Long move(byte[] key, int dbIndex) {
		return master.move(key, dbIndex);
	}

	public Long bitcount(byte[] key) {
		return master.bitcount(key);
	}

	public Long bitcount(byte[] key, long start, long end) {
		return master.bitcount(key, start, end);
	}

	public Long pfadd(byte[] key, byte[]... elements) {
		return master.pfadd(key, elements);
	}

	public long pfcount(byte[] key) {
		return master.pfcount(key);
	}

	public String ping() {
		return master.ping();
	}

	public String quit() {
		return master.quit();
	}

	public String flushDB() {
		return master.flushDB();
	}

	public Long dbSize() {
		return master.dbSize();
	}

	public String select(int index) {
		return master.select(index);
	}

	public String flushAll() {
		return master.flushAll();
	}

	public String auth(String password) {
		return master.auth(password);
	}

	public String save() {
		return master.save();
	}

	public String bgsave() {
		return master.bgsave();
	}

	public String bgrewriteaof() {
		return master.bgrewriteaof();
	}

	public Long lastsave() {
		return master.lastsave();
	}

	public String shutdown() {
		return master.shutdown();
	}

	public String info() {
		return master.info();
	}

	public String info(String section) {
		return master.info(section);
	}

	public String slaveof(String host, int port) {
		return master.slaveof(host, port);
	}

	public String slaveofNoOne() {
		return master.slaveofNoOne();
	}

	public Long getDB() {
		return master.getDB();
	}

	public String debug(DebugParams params) {
		return master.debug(params);
	}

	public String configResetStat() {
		return master.configResetStat();
	}

	public Long waitReplicas(int replicas, long timeout) {
		return master.waitReplicas(replicas, timeout);
	}

	public void close() {
		if (dataSource != null) {
			boolean broken = getClient().isBroken();
			if (!broken) {
				for (Jedis jedis : getAllShards()) {
					if (jedis.getClient().isBroken()) {
						broken = true;
						break;
					}
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

	public void setDataSource(Pool<MasterSlaveJedis> shardedMasterSlaveJedisPool) {
		this.dataSource = shardedMasterSlaveJedisPool;
	}

	public void resetState() {
		master.resetState();
		for (Jedis jedis : getAllShards()) {
			jedis.resetState();
		}
	}

	public void disconnect() {
		if (master != null) {
			try {
				master.quit();
			} catch (JedisConnectionException e) {
				// ignore the exception node, so that all other normal nodes can
				// release all connections.
			}
		}
		for (Jedis jedis : getAllShards()) {
			try {
				jedis.quit();
			} catch (JedisConnectionException e) {
				// ignore the exception node, so that all other normal nodes can
				// release all connections.
			}
			try {
				jedis.disconnect();
			} catch (JedisConnectionException e) {
				// ignore the exception node, so that all other normal nodes can
				// release all connections.
			}
		}
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("MasterSlaveJedis {master=" + masterShard.getHost() + ":" + masterShard.getPort() + ", slaves=");
		sb.append("[");
		for (int i = 0, len = slaveShards.size(); i < len; i++) {
			sb.append(slaveShards.get(i).getHost() + ":" + slaveShards.get(i).getPort());
			if (i != len - 1) {
				sb.append(", ");
			}
		}
		sb.append("]}");
		return sb.toString();
	}

	@Override
	public Long exists(String... keys) {
		return master.exists(keys);
	}

	@Override
	public ScanResult<String> scan(String cursor, ScanParams params) {
		return master.scan(cursor, params);
	}

	@Override
	public String set(String key, String value, String nxxx) {
		Jedis j = getShard(key);
		return j.set(key, value, nxxx);
	}

	@Override
	public Long pexpireAt(String key, long millisecondsTimestamp) {
		Jedis j = getShard(key);
		return j.pexpireAt(key, millisecondsTimestamp);
	}

	@Override
	public Long pttl(String key) {
		Jedis j = getShard(key);
		return j.pttl(key);
	}

	@Override
	public String psetex(String key, long milliseconds, String value) {
		Jedis j = getShard(key);
		return j.psetex(key, milliseconds, value);
	}

	@Override
	public Set<String> spop(String key, long count) {
		Jedis j = getShard(key);
		return j.spop(key, count);
	}

	@Override
	public Long zadd(String key, double score, String member, ZAddParams params) {
		Jedis j = getShard(key);
		return j.zadd(key, score, member, params);
	}

	@Override
	public Long zadd(String key, Map<String, Double> scoreMembers, ZAddParams params) {
		Jedis j = getShard(key);
		return j.zadd(key, scoreMembers, params);
	}

	@Override
	public Double zincrby(String key, double score, String member, ZIncrByParams params) {
		Jedis j = getShard(key);
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
		Jedis j = getShard(key);
		return j.bitpos(key, value);
	}

	@Override
	public Long bitpos(String key, boolean value, BitPosParams params) {
		Jedis j = getShard(key);
		return j.bitpos(key, value, params);
	}

	@Override
	public ScanResult<Entry<String, String>> hscan(String key, String cursor, ScanParams params) {
		Jedis j = getShard(key);
		return j.hscan(key, cursor, params);
	}

	@Override
	public ScanResult<String> sscan(String key, String cursor, ScanParams params) {
		Jedis j = getShard(key);
		return j.sscan(key, cursor, params);
	}

	@Override
	public ScanResult<Tuple> zscan(String key, String cursor, ScanParams params) {
		Jedis j = getShard(key);
		return j.zscan(key, cursor, params);
	}

	@Override
	public Long geoadd(String key, double longitude, double latitude, String member) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long geoadd(String key, Map<String, GeoCoordinate> memberCoordinateMap) {
		Jedis j = getShard(key);
		return j.geoadd(key, memberCoordinateMap);
	}

	@Override
	public Double geodist(String key, String member1, String member2) {
		Jedis j = getShard(key);
		return j.geodist(key, member1, member2);
	}

	@Override
	public Double geodist(String key, String member1, String member2, GeoUnit unit) {
		Jedis j = getShard(key);
		return j.geodist(key, member1, member2, unit);
	}

	@Override
	public List<String> geohash(String key, String... members) {
		Jedis j = getShard(key);
		return j.geohash(key, members);
	}

	@Override
	public List<GeoCoordinate> geopos(String key, String... members) {
		Jedis j = getShard(key);
		return j.geopos(key, members);
	}

	@Override
	public List<GeoRadiusResponse> georadius(String key, double longitude, double latitude, double radius,
			GeoUnit unit) {
		Jedis j = getShard(key);
		return j.georadius(key, longitude, latitude, radius, unit);
	}

	@Override
	public List<GeoRadiusResponse> georadius(String key, double longitude, double latitude, double radius, GeoUnit unit,
			GeoRadiusParam param) {
		Jedis j = getShard(key);
		return j.georadius(key, longitude, latitude, radius, unit, param);
	}

	@Override
	public List<GeoRadiusResponse> georadiusByMember(String key, String member, double radius, GeoUnit unit) {
		Jedis j = getShard(key);
		return j.georadiusByMember(key, member, radius, unit);
	}

	@Override
	public List<GeoRadiusResponse> georadiusByMember(String key, String member, double radius, GeoUnit unit,
			GeoRadiusParam param) {
		Jedis j = getShard(key);
		return j.georadiusByMember(key, member, radius, unit, param);
	}

	@Override
	public Long exists(byte[]... keys) {
		return master.exists(keys);
	}

	@Override
	public String set(byte[] key, byte[] value, byte[] nxxx) {
		Jedis j = getShard(key);
		return j.set(key, value, nxxx);
	}

	@Override
	public String set(byte[] key, byte[] value, byte[] nxxx, byte[] expx, long time) {
		Jedis j = getShard(key);
		return j.set(key, value, nxxx, expx, time);
	}

	@Override
	public Long pexpire(String key, long milliseconds) {
		Jedis j = getShard(key);
		return j.pexpire(key, milliseconds);
	}

	@Override
	public Long pexpire(byte[] key, long milliseconds) {
		Jedis j = getShard(key);
		return j.pexpire(key, milliseconds);
	}

	@Override
	public Long pexpireAt(byte[] key, long millisecondsTimestamp) {
		Jedis j = getShard(key);
		return j.pexpireAt(key, millisecondsTimestamp);
	}

	@Override
	public Set<byte[]> spop(byte[] key, long count) {
		Jedis j = getShard(key);
		return j.spop(key, count);
	}

	@Override
	public Long zadd(byte[] key, double score, byte[] member, ZAddParams params) {
		Jedis j = getShard(key);
		return j.zadd(key, score, member, params);
	}

	@Override
	public Long zadd(byte[] key, Map<byte[], Double> scoreMembers, ZAddParams params) {
		Jedis j = getShard(key);
		return j.zadd(key, scoreMembers, params);
	}

	@Override
	public Double zincrby(byte[] key, double score, byte[] member, ZIncrByParams params) {
		Jedis j = getShard(key);
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
		Jedis j = getShard(key);
		return j.geoadd(key, longitude, latitude, member);
	}

	@Override
	public Long geoadd(byte[] key, Map<byte[], GeoCoordinate> memberCoordinateMap) {
		Jedis j = getShard(key);
		return j.geoadd(key, memberCoordinateMap);
	}

	@Override
	public Double geodist(byte[] key, byte[] member1, byte[] member2) {
		Jedis j = getShard(key);
		return j.geodist(key, member1, member2);
	}

	@Override
	public Double geodist(byte[] key, byte[] member1, byte[] member2, GeoUnit unit) {
		Jedis j = getShard(key);
		return j.geodist(key, member1, member2, unit);
	}

	@Override
	public List<byte[]> geohash(byte[] key, byte[]... members) {
		Jedis j = getShard(key);
		return j.geohash(key, members);
	}

	@Override
	public List<GeoCoordinate> geopos(byte[] key, byte[]... members) {
		Jedis j = getShard(key);
		return j.geopos(key, members);
	}

	@Override
	public List<GeoRadiusResponse> georadius(byte[] key, double longitude, double latitude, double radius,
			GeoUnit unit) {
		Jedis j = getShard(key);
		return j.georadius(key, longitude, latitude, radius, unit);
	}

	@Override
	public List<GeoRadiusResponse> georadius(byte[] key, double longitude, double latitude, double radius, GeoUnit unit,
			GeoRadiusParam param) {
		Jedis j = getShard(key);
		return j.georadius(key, longitude, latitude, radius, unit, param);
	}

	@Override
	public List<GeoRadiusResponse> georadiusByMember(byte[] key, byte[] member, double radius, GeoUnit unit) {
		Jedis j = getShard(key);
		return j.georadiusByMember(key, member, radius, unit);
	}

	@Override
	public List<GeoRadiusResponse> georadiusByMember(byte[] key, byte[] member, double radius, GeoUnit unit,
			GeoRadiusParam param) {
		Jedis j = getShard(key);
		return j.georadiusByMember(key, member, radius, unit, param);
	}

	@Deprecated
	@Override
	public ScanResult<Entry<byte[], byte[]>> hscan(byte[] key, byte[] cursor) {
		Jedis j = getShard(key);
		return j.hscan(key, cursor);
	}

	@Deprecated
	@Override
	public ScanResult<Entry<byte[], byte[]>> hscan(byte[] key, byte[] cursor, ScanParams params) {
		Jedis j = getShard(key);
		return j.hscan(key, cursor, params);
	}

	@Override
	public ScanResult<byte[]> sscan(byte[] key, byte[] cursor) {
		Jedis j = getShard(key);
		return j.sscan(key, cursor);
	}

	@Override
	public ScanResult<byte[]> sscan(byte[] key, byte[] cursor, ScanParams params) {
		Jedis j = getShard(key);
		return j.sscan(key, cursor, params);
	}

	@Override
	public ScanResult<Tuple> zscan(byte[] key, byte[] cursor) {
		Jedis j = getShard(key);
		return j.zscan(key, cursor);
	}

	@Override
	public ScanResult<Tuple> zscan(byte[] key, byte[] cursor, ScanParams params) {
		Jedis j = getShard(key);
		return j.zscan(key, cursor, params);
	}

}
