package cn.skynethome.redisx.common.ms;

import java.util.List;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.util.Hashing;
import redis.clients.util.ShardInfo;
import redis.clients.util.Sharded;
/**
  * 项目名称:[redisx]
  * 包:[cn.skynethome.redisx.common.ms]    
  * 文件名称:[MasterSlaveJedisShardInfo]  
  * 描述:[一句话描述该文件的作用]
  * 创建人:[陆文斌]
  * 创建时间:[2017年1月3日 上午11:37:39]   
  * 修改人:[陆文斌]   
  * 修改时间:[2017年1月3日 上午11:37:39]   
  * 修改备注:[说明本次修改内容]  
  * 版权所有:luwenbin006@163.com
  * 版本:[v1.0]
 */
public class MasterSlaveJedisShardInfo extends ShardInfo<MasterSlaveJedis> {

	private final String masterName;
	
	private final JedisShardInfo masterShard;
	
	private final List<JedisShardInfo> slaveShards;
	
	private final String name;
	
	public MasterSlaveJedisShardInfo(String masterName, JedisShardInfo masterShard, List<JedisShardInfo> slaveShards) {
		this(masterName, masterShard, slaveShards, Sharded.DEFAULT_WEIGHT);
	}
	
	public MasterSlaveJedisShardInfo(String masterName, JedisShardInfo masterShard, List<JedisShardInfo> slaveShards, int weight) {
		this(masterName, masterShard, slaveShards, Sharded.DEFAULT_WEIGHT, null);
	}
	
	public MasterSlaveJedisShardInfo(String masterName, JedisShardInfo masterShard, List<JedisShardInfo> slaveShards, int weight, String name) {
		super(weight);
		this.masterName = masterName;
		this.masterShard = masterShard;
		this.slaveShards = slaveShards;
		this.name = name;
	}

	protected MasterSlaveJedis createResource() {
		return new MasterSlaveJedis(masterShard, slaveShards, Hashing.MURMUR_HASH, null);
	}

	public String getName() {
		return name;
	}

	public String getMasterName() {
		return masterName;
	}

	public JedisShardInfo getMasterShard() {
		return masterShard;
	}

	public List<JedisShardInfo> getSlaveShards() {
		return slaveShards;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("{masterName=" + masterName + ", master=" + masterShard.getHost() + ":" + masterShard.getPort() + ", slaves=");
		sb.append("[");
		for(int i = 0, len = slaveShards.size(); i < len; i++){
			sb.append(slaveShards.get(i).getHost() + ":" + slaveShards.get(i).getPort());
			if(i != len - 1){
				sb.append(", ");
			}
		}
		sb.append("]}");
		return sb.toString();
	}

}
