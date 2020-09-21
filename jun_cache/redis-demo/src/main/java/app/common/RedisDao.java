package app.common;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.BoundZSetOperations;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.util.StringUtils;

public abstract class RedisDao<Entity extends RedisEntity> {

	private RedisTemplate<String, Entity> redisTemplate;

	@Autowired
	public void setRedisTemplate(RedisTemplate<String, Entity> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}
	
	/**
	 * 获取一个对象
	 * @param id 必须是app.common.RedisEntity的子类
	 */
	public Entity get(Object id) {
		Entity entity = (Entity) id;
		if (entity != null) {
			ValueOperations<String, Entity> ops = redisTemplate.opsForValue();
			return ops.get(getKey(entity));
		}
		return null;
	}
	
	/**
	 * 列表查询
	 * @param tableName 表名
	 * @param start 
	 * @param limit
	 * @return 返回集合
	 */
	public List<Object> find(final String tableName,final int start,final int limit) {	
		int end = start + limit - 1;
		ZSetOperations opsZset = redisTemplate.opsForZSet();
		final Set dataSet = opsZset.range(getListKey(tableName), start, end);
		if(dataSet == null) {
			return Collections.emptyList();
		}
		// 使用管道
		return redisTemplate.executePipelined(new SessionCallback() { 
			
			public Object execute(RedisOperations operations)
					throws DataAccessException {
				BoundValueOperations valueOps = null;
				List<Object> ret = new ArrayList<Object>(dataSet.size());
				for (Object pk : dataSet) {
					valueOps = operations.boundValueOps(getKey(tableName,pk.toString()));
					ret.add(valueOps.get());
				}
				return null; // !注意:当使用管道时,这里必须返回null
			}
		});
	}
	
	/**
	 * 获取表记录数
	 * @param tableName 表名
	 * @return
	 */
	public long getTotal(String tableName) {
		BoundZSetOperations<String, Entity> ops = redisTemplate.boundZSetOps(this.getListKey(tableName));
		return ops.size();
	}
	
	/**
	 * 保存记录,支持自增主键
	 * @param entity 必须是app.common.RedisEntity的子类
	 */
	public void save(final Entity entity) {
		// 不是自增主键,检查主键值
		if(!entity.isAutoIncr() && !StringUtils.hasText(entity.fatchPK())) {
			throw new RuntimeException(entity.fatchTableName() + "主键不能为空");
		}
		
		redisTemplate.execute(new SessionCallback<Entity>() {
			
			public Entity execute(RedisOperations operations)
					throws DataAccessException {
				String incrKey = getIncrKey(entity);
				// 如果是有自增属性,则添加自增ID值
				if (entity.isAutoIncr()) {
					long nextId = buildNextId(incrKey, operations);
					entity.setPK(String.valueOf(nextId));
				}

				String key = getKey(entity);
				boolean hasKey = entity.fatchPK() != null
						&& operations.hasKey(key);
				if (hasKey) {
					throw new RuntimeException(entity.fatchTableName() + "主键值已存在:" + entity.fatchPK());
				}else{
					String listKey = getListKey(entity);
					
					operations.multi();
					// 保存数据
					ValueOperations opsVal = operations.opsForValue();
					opsVal.set(key, entity);
					// 同时保存一份ID到ZSET中
					ZSetOperations opsZset = operations.opsForZSet();
					opsZset.add(listKey, entity.fatchPK(), System.currentTimeMillis());
					operations.exec();
				}

				return entity;
			}
		});
	}

	// 构建自增ID
	private long buildNextId(String incrKey, RedisOperations operations) {
		// watch total
		operations.watch(incrKey);

		BoundValueOperations valOpsCntKey = operations.boundValueOps(incrKey);
		Long nextId = (Long) valOpsCntKey.get();
		if (nextId == null) {
			nextId = 0L;
		}
		nextId++;

		operations.multi();

		// 生成自增ID
		valOpsCntKey.set(nextId);

		operations.exec();

		return nextId.longValue();
	}

	/**
	 * 修改记录
	 * @param entity 必须是app.common.RedisEntity的子类
	 */
	public void update(final Entity entity) {
		redisTemplate.execute(new SessionCallback<Entity>() {
			
			public Entity execute(RedisOperations operations)
					throws DataAccessException {
				String key = getKey(entity);
				boolean hasKey = operations.hasKey(key);
				if (hasKey) {
					operations.multi();
					BoundValueOperations valueOps = operations
							.boundValueOps(key);
					valueOps.set(entity);
					operations.exec();
				}

				return entity;
			}
		});
	}

	/**
	 * 删除记录
	 * @param entity 必须是app.common.RedisEntity的子类
	 * @return 返回ZREM 结果
	 */
	public long del(final Entity entity) {
		return redisTemplate.execute(new SessionCallback<Long>() {
			
			public Long execute(RedisOperations operations)
					throws DataAccessException {
				String key = getKey(entity);
				String listKey = getListKey(entity);
				boolean hasKey = operations.hasKey(key);
				long retVal = 0;
				if (hasKey) {
					operations.multi();
					operations.delete(key);
					// 删除有序集合中的ID
					ZSetOperations opsZset = operations.opsForZSet();
					opsZset.remove(listKey, entity.fatchPK());
					// list中存放的是operations.delete(key)和opsZset.remove(listKey, entity.fatchPK())这两条执行结果
					// 正常情况下list中是[1,1]
					// 因为一旦开启multi,执行结果不会立即返回,需要执行exec()才能返回
					List<Long> list = (List<Long>)operations.exec();
					
					for (Long ret : list) {
						retVal +=ret;
					}
				}

				return retVal > 0 ? 1L : 0;
			}
		});
	}
	

	public RedisTemplate<String, Entity> getRedisTemplate() {
		return redisTemplate;
	}

	/**
	 * 获取 RedisSerializer <br>
	 */
	protected RedisSerializer<String> getStringSerializer() {
		return redisTemplate.getStringSerializer();
	}
	
	protected String getListKey(Entity entity) {
		return getListKey(entity.fatchTableName());
	}
	
	protected String getListKey(String tableName) {
		return tableName + ":zset";
	}
	
	protected String getKey(Entity entity) {
		return this.getKey(entity.fatchTableName(), entity.fatchPK());
	}
	
	protected String getKey(String tableName,String pk) {
		return tableName + ":" + pk;
	}
	

	protected String getIncrKey(Entity entity) {
		return entity.fatchTableName() + ":incr";
	}

}