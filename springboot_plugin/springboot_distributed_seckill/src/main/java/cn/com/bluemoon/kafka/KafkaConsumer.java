package cn.com.bluemoon.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;

import cn.com.bluemoon.common.response.SeckillInfoResponse;
import cn.com.bluemoon.redis.lock.RedissonDistributedLocker;
import cn.com.bluemoon.redis.repository.RedisRepository;
import cn.com.bluemoon.utils.SerialNo;

/**
 * 消费者 spring-kafka 2.0 + 依赖JDK8
 * @author Wujun
 */
@Component
public class KafkaConsumer {
	
	private Logger logger = LoggerFactory.getLogger(KafkaConsumer.class);
	@Autowired
	private RedisRepository redisRepository;
	@Autowired
	private RedissonDistributedLocker redissonDistributedLocker;
	
    /**
     * 监听seckill主题,有消息就读取
     * 主要消费秒杀进入到下订单操作的队列数据，此处的数据已经过滤了绝大部分请求，只有真正得到下单机会的用户才会进入到这一环节
     * @param message
     */
    @KafkaListener(topics = {"demo_seckill"})
    public void receiveMessage(String message){
    	try {
			//收到通道的消息之后执行秒杀操作
			logger.info(message);
			JSONObject json = JSONObject.parseObject(message);
			long stallActivityId = json.getLong("stallActivityId");
			int purchaseNum = json.getInteger("purchaseNum");
			String openId = json.getString("openId");
//			long addressId = json.getLong("addressId");
//			String formId = json.getString("formId");
//			String shareCode = json.getString("shareCode");
//			String shareSource = json.getString("shareSource");
//			String userCode = json.getString("userCode");
			//生成订单，模拟生成订单编码
			String orderCode = "J"+SerialNo.getUNID();
			//删除redis中的key，让轮询接口发现，该订单已经处理完成
			redisRepository.del("BM_MARKET_LOCK_POLLING_" + stallActivityId + "_" + openId);
			//并将orderId_orderCode放入缓存，有效时间10分钟（因为支付有效时间为10分钟）
			redisRepository.setExpire("BM_MARKET_SECKILL_ORDERID_" + stallActivityId + "_" + openId, orderCode, 600);
			String lockKey = "marketOrder"+stallActivityId;	//控制锁的颗粒度(摊位活动ID)
			boolean isGetLock = redissonDistributedLocker.tryLock(lockKey, 1L, 1L);	//最多等待1S，每次操作预计的超时时间1S
        	if(isGetLock) {
        		try {
					//扣减真实库存
					redisRepository.decrBy("BM_MARKET_SECKILL_REAL_STOCKNUM_" + stallActivityId, purchaseNum);
				}finally{
					redissonDistributedLocker.unlock(lockKey);
				}
        	}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
    }
    
    /**
     * 与上述方法不同，该方法还包含库存校验等逻辑操作
     */
    @KafkaListener(topics = {"demo_seckill_queue"})
    public void receiveMessage2(String message) {
    	JSONObject json = JSONObject.parseObject(message);
		long stallActivityId = json.getLong("stallActivityId");
		int purchaseNum = json.getInteger("purchaseNum");
		String openId = json.getString("openId");
//		long addressId = json.getLong("addressId");
//		String formId = json.getString("formId");
//		String shareCode = json.getString("shareCode");
//		String shareSource = json.getString("shareSource");
//		String userCode = json.getString("userCode");
		String lockKey = "marketOrder"+stallActivityId;//控制锁的颗粒度(摊位活动ID)
		redissonDistributedLocker.lock(lockKey, 1L);
		try{
			JSONObject result = new JSONObject();
			SeckillInfoResponse response = new SeckillInfoResponse();
			String redisStock = redisRepository.get("BM_MARKET_SECKILL_STOCKNUM_" + stallActivityId);
			int surplusStock = Integer.parseInt(redisStock == null ? "0" : redisStock);	//剩余库存
			//如果剩余库存大于购买数量，则获得下单资格，并生成唯一下单资格码
			if( surplusStock >= purchaseNum ) {
				response.setIsSuccess(true);
				response.setResponseCode(0);
				response.setResponseMsg("您已获得下单资格，请尽快下单");
				response.setRefreshTime(0);
				String code = SerialNo.getUNID();
				response.setOrderQualificationCode(code);
				//将下单资格码维护到redis中，用于下单时候的检验；有效时间10分钟；
				redisRepository.setExpire("BM_MARKET_SECKILL_QUALIFICATION_CODE_" + stallActivityId + "_" + openId, code, 10*60);
				//维护一个key，防止获得下单资格用户重新抢购，当支付过期之后应该维护删除该标志
				redisRepository.setExpire("BM_MARKET_SECKILL_LIMIT_" + stallActivityId + "_" + openId, "true", 3600*24*7);
				//扣减锁定库存
				redisRepository.decrBy("BM_MARKET_SECKILL_STOCKNUM_" + stallActivityId, purchaseNum);
			}else {
				response.setIsSuccess(false);
				response.setResponseCode(6102);
				response.setResponseMsg("秒杀失败，商品已经售罄");
				response.setRefreshTime(0);
			}
			result.put("response", response);
			//将信息维护到redis中
			redisRepository.setExpire("BM_MARKET_SECKILL_QUEUE_"+stallActivityId+"_"+openId, result.toJSONString(), 3600*24*7);
		}finally{
			redissonDistributedLocker.unlock(lockKey);
		}
    }
    
}