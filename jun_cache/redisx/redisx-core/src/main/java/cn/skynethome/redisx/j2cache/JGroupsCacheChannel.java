package cn.skynethome.redisx.j2cache;

import java.net.URL;
import java.util.List;

import org.jgroups.Address;
import org.jgroups.JChannel;
import org.jgroups.Message;
import org.jgroups.ReceiverAdapter;
import org.jgroups.View;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
  * 项目名称:[redisx-core]
  * 包:[cn.skynethome.redisx.j2cache]    
  * 文件名称:[JGroupsCacheChannel]  
  * 描述:[使用 JGroups 进行组播]
  * 创建人:[陆文斌]
  * 创建时间:[2017年1月11日 上午9:41:39]   
  * 修改人:[陆文斌]   
  * 修改时间:[2017年1月11日 上午9:41:39]   
  * 修改备注:[说明本次修改内容]  
  * 版权所有:luwenbin006@163.com
  * 版本:[v1.0]
 */
public class JGroupsCacheChannel extends ReceiverAdapter implements CacheExpiredListener, CacheChannel {

	private final static Logger log = LoggerFactory.getLogger(JGroupsCacheChannel.class);
	private final static String CONFIG_XML = "/properties/network.xml";
	
	private String name;
	private JChannel channel;
	private final static JGroupsCacheChannel instance = new JGroupsCacheChannel("default");
	
	/**
	 * 单例方法
	 * @return 返回 CacheChannel 单实例
	 */
	public final static JGroupsCacheChannel getInstance(){
		return instance;
	}
	
	/**
	 * 初始化缓存通道并连接
	 * @param name: 缓存实例名称
	 */
	private JGroupsCacheChannel(String name) throws CacheException {
		this.name = name;
		try{
			CacheManager.initCacheProvider(this);
			
			long ct = System.currentTimeMillis();
			
			URL xml = CacheChannel.class.getResource(CONFIG_XML);
			if(xml == null)
				xml = getClass().getClassLoader().getParent().getResource(CONFIG_XML);
			channel = new JChannel(xml);
			channel.setReceiver(this);
			channel.connect(this.name);
			
			log.info("Connected to channel:" + this.name + ", time " + (System.currentTimeMillis()-ct) + " ms.");

		}catch(Exception e){
			throw new CacheException(e);
		}
	}

	/**
	 * 获取缓存中的数据
	 * @param region: Cache Region name
	 * @param key: Cache key
	 * @return cache object
	 */
	public CacheObject get(String region, Object key){
		CacheObject obj = new CacheObject();
		obj.setRegion(region);
		obj.setKey(key);
        if(region!=null && key != null){
        	obj.setValue(CacheManager.get(LEVEL_1, region, key));
            if(obj.getValue() == null) {
            	obj.setValue(CacheManager.get(LEVEL_2, region, key));
                if(obj.getValue() != null){
                	obj.setLevel(LEVEL_2);
                    CacheManager.set(LEVEL_1, region, key, obj.getValue());
                }
            }
            else
            	obj.setLevel(LEVEL_1);
        }
        return obj;
	}
	
	/**
	 * 写入缓存
	 * @param region: Cache Region name
	 * @param key: Cache key
	 * @param value: Cache value
	 */
	public void set(String region, Object key, Object value){
		if(region!=null && key != null){
			if(value == null)
				evict(region, key);
			else{
				//分几种情况
				//Object obj1 = CacheManager.get(LEVEL_1, region, key);
				//Object obj2 = CacheManager.get(LEVEL_2, region, key);
				//1. L1 和 L2 都没有
				//2. L1 有 L2 没有（这种情况不存在，除非是写 L2 的时候失败
				//3. L1 没有，L2 有
				//4. L1 和 L2 都有
				_sendEvictCmd(region, key);//清除原有的一级缓存的内容
				CacheManager.set(LEVEL_1, region, key, value);
				CacheManager.set(LEVEL_2, region, key, value);
			}
		}
		//log.info("write data to cache region="+region+",key="+key+",value="+value);
	}
	
	/**
	 * 删除缓存
	 * @param region:  Cache Region name
	 * @param key: Cache key
	 */
	public void evict(String region, Object key) {
		CacheManager.evict(LEVEL_1, region, key); //删除一级缓存
		CacheManager.evict(LEVEL_2, region, key); //删除二级缓存
		_sendEvictCmd(region, key); //发送广播
	}

	/**
	 * 批量删除缓存
	 * @param region: Cache region name
	 * @param keys: Cache key
	 */
	@SuppressWarnings({ "rawtypes" })
	public void batchEvict(String region, List keys) {
		CacheManager.batchEvict(LEVEL_1, region, keys);
		CacheManager.batchEvict(LEVEL_2, region, keys);
		_sendEvictCmd(region, keys);
	}

	/**
	 * Clear the cache
	 * @param region: Cache region name
	 */
	public void clear(String region) throws CacheException {
		CacheManager.clear(LEVEL_1, region);
		CacheManager.clear(LEVEL_2, region);
		_sendClearCmd(region);
	}
	
	/**
	 * Get cache region keys
	 * @param region: Cache region name
	 * @return key list
	 */
	@SuppressWarnings("rawtypes")
	public List keys(String region) throws CacheException {
		return CacheManager.keys(LEVEL_1, region);
	}
	
	/**
	 * 为了保证每个节点缓存的一致，当某个缓存对象因为超时被清除时，应该通知群组其他成员
	 * @param region: Cache region name
	 * @param key: cache key
	 */
	@Override
	@SuppressWarnings("rawtypes")
	public void notifyElementExpired(String region, Object key) {

		log.debug("Cache data expired, region="+region+",key="+key);
		
		//删除二级缓存
		if(key instanceof List)
			CacheManager.batchEvict(LEVEL_2, region, (List)key);
		else
			CacheManager.evict(LEVEL_2, region, key);
		
		//发送广播
		_sendEvictCmd(region, key);
	}
	
	/**
	 * 发送删除缓存的广播命令
	 * @param region: Cache region name
	 * @param key: cache key
	 */
	private void _sendEvictCmd(String region, Object key) {
		//发送广播
		Command cmd = new Command(Command.OPT_DELETE_KEY, region, key);
		try {
			Message msg = new Message(null, null, cmd.toBuffers());
			channel.send(msg);
		} catch (Exception e) {
			log.error("Unable to delete cache,region="+region+",key="+key, e);
		}
	}

	/**
	 * 发送清除缓存的广播命令
	 * @param region: Cache region name
	 * @param key: cache key
	 */
	private void _sendClearCmd(String region) {
		//发送广播
		Command cmd = new Command(Command.OPT_CLEAR_KEY, region, "");
		try {
			Message msg = new Message(null, null, cmd.toBuffers());
			channel.send(msg);
		} catch (Exception e) {
			log.error("Unable to clear cache,region="+region, e);
		}
	}

	/**
	 * 删除一级缓存的键对应内容
	 * @param region: Cache region name
	 * @param key: cache key
	 */
	@SuppressWarnings("rawtypes")
	protected void onDeleteCacheKey(String region, Object key){
		if(key instanceof List)
			CacheManager.batchEvict(LEVEL_1, region, (List)key);
		else
			CacheManager.evict(LEVEL_1, region, key);
		log.debug("Received cache evict message, region="+region+",key="+key);
	}

	/**
	 * 清除一级缓存的键对应内容
	 * @param region
	 * 		 Cache region name
	 */
	protected void onClearCacheKey(String region){
		CacheManager.clear(LEVEL_1, region);
		log.debug("Received cache clear message, region="+region);
	}

	/**
	 * 消息接收
	 * @param msg 接收到的消息
	 */
	@Override
	public void receive(Message msg) {
		//无效消息
		byte[] buffers = msg.getBuffer();
		if(buffers.length < 1){
			log.warn("Message is empty.");
			return;
		}
		
		//不处理发送给自己的消息
		if(msg.getSrc().equals(channel.getAddress()))
			return ;
		
		try{
			Command cmd = Command.parse(buffers);
			
			if(cmd == null)
				return;
			
			switch(cmd.getOperator()){
			case Command.OPT_DELETE_KEY:
				onDeleteCacheKey(cmd.getRegion(), cmd.getKey());
				break;
			case Command.OPT_CLEAR_KEY:
				onClearCacheKey(cmd.getRegion());
				break;
			default:
				log.warn("Unknown message type = " + cmd.getOperator());
			}
		}catch(Exception e){
			log.error("Unable to handle received msg" , e);
		}
	}
	
	/**
	 * 组中成员变化时
	 * @param view group view
	 */
	public void viewAccepted(View view) {
		StringBuffer sb = new StringBuffer("Group Members Changed, LIST: ");
		List<Address> addrs = view.getMembers();
 		for(int i=0; i<addrs.size(); i++){
 			if(i > 0)
 				sb.append(',');
			sb.append(addrs.get(i).toString());
		}
		log.info(sb.toString());
	}
	
	/**
	 * 关闭到通道的连接
	 */
	public void close(){
		CacheManager.shutdown(LEVEL_1);
		CacheManager.shutdown(LEVEL_2);
		channel.close();
	}
	
}
