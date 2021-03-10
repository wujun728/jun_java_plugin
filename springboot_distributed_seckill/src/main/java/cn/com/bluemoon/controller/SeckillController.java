/**  
* <p>Title: SeckillApiController.java</p>  
* <p>Description: </p>  
* <p>Copyright: Copyright (c) 2018</p>  
* <p>Company: www.bluemoon.com</p>  
* @author Wujun
* @date 2018年8月10日  
*/  
package cn.com.bluemoon.controller;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;

import cn.com.bluemoon.common.response.BaseResponse;
import cn.com.bluemoon.common.response.SeckillInfoResponse;
import cn.com.bluemoon.common.response.StockNumResponse;
import cn.com.bluemoon.kafka.KafkaSender;
import cn.com.bluemoon.redis.lock.RedissonDistributedLocker;
import cn.com.bluemoon.redis.repository.RedisRepository;
import cn.com.bluemoon.service.ISeckillService;
import cn.com.bluemoon.utils.AssertUtil;
import cn.com.bluemoon.utils.StringUtil;
import cn.hutool.system.SystemUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**  
* <p>Title: SeckillApiController</p>  
* <p>Description: 秒杀相关接口</p>  
* @author Wujun
* @date 2018年8月10日  
*/
@Api(tags="分布式秒杀")
@RestController
@CrossOrigin
@RequestMapping(value = "/api/seckill")
public class SeckillController {
	
	@Autowired
	private RedisRepository redisRepository;
	@Autowired
	private ISeckillService seckillService;
	@Autowired
	private KafkaSender kafkaSender;
	@Autowired
	private RedissonDistributedLocker redissonDistributedLocker;

	Logger logger = LoggerFactory.getLogger(SeckillController.class);
	
	/**
	 * 设置活动库存
	 * @param jsonObject
	 * @return
	 */
	@ApiOperation(value="设置活动库存",nickname="Guoqing")
	@RequestMapping(value="/setStockNum", method=RequestMethod.POST)
	public BaseResponse setStockNum(@RequestBody JSONObject jsonObject) {
		
		int stockNum = jsonObject.containsKey("stockNum")?jsonObject.getInteger("stockNum"):0;
		int stallActivityId = jsonObject.containsKey("stallActivityId") ? jsonObject.getInteger("stallActivityId") : -1;
		AssertUtil.isTrue(stallActivityId != -1, "非法参数");
		redisRepository.incrBy("BM_MARKET_SECKILL_STOCKNUM_" + stallActivityId, stockNum);
		redisRepository.incrBy("BM_MARKET_SECKILL_REAL_STOCKNUM_" + stallActivityId, stockNum);
		
		return new BaseResponse();
	}
	
	/**
	 * 查看活动库存情况
	 * @param jsonObject
	 * @return
	 */
	@ApiOperation(value="查看活动库存",nickname="Guoqing")
	@RequestMapping(value="/getStockNum", method=RequestMethod.POST)
	public StockNumResponse getStockNum(@RequestBody JSONObject jsonObject) {
		StockNumResponse response = new StockNumResponse();
		int stallActivityId = jsonObject.containsKey("stallActivityId") ? jsonObject.getInteger("stallActivityId") : -1;
		AssertUtil.isTrue(stallActivityId != -1, "非法参数");
		String stockNum = redisRepository.get("BM_MARKET_SECKILL_STOCKNUM_" + stallActivityId);
		String realStockNum = redisRepository.get("BM_MARKET_SECKILL_REAL_STOCKNUM_" + stallActivityId);
		response.setStockNum(Long.parseLong(stockNum));
		response.setRealStockNum(Long.parseLong(realStockNum));
		return response;
	}

	/**
	 * 06.04-去秒杀，创建秒杀订单
	 * 通过分布式锁的方式控制，控制库存不超卖
	 * <p>Title: testSeckill</p>  
	 * <p>Description: 秒杀下单</p>  
	 * @param jsonObject
	 * @return
	 */
	@ApiOperation(value="去秒杀--先分布式锁模式",nickname="Guoqing")
	@RequestMapping(value="/goSeckill", method=RequestMethod.POST)
	public SeckillInfoResponse goSeckill(@RequestBody JSONObject jsonObject) {
		int stallActivityId = jsonObject.containsKey("stallActivityId") ? jsonObject.getInteger("stallActivityId") : -1;		//活动Id
		AssertUtil.isTrue(stallActivityId != -1, "非法參數");
		int purchaseNum = jsonObject.containsKey("purchaseNum") ? jsonObject.getInteger("purchaseNum") : 1;		//购买数量
		AssertUtil.isTrue(purchaseNum != -1, "非法參數");
		String openId = jsonObject.containsKey("openId") ? jsonObject.getString("openId") : null;
		AssertUtil.isTrue(!StringUtil.isEmpty(openId), 1101, "非法參數");
		String formId = jsonObject.containsKey("formId") ? jsonObject.getString("formId") : null;
		AssertUtil.isTrue(!StringUtil.isEmpty(formId), 1101, "非法參數");
		long addressId = jsonObject.containsKey("addressId") ? jsonObject.getLong("addressId") : -1;
		AssertUtil.isTrue(addressId != -1, "非法參數");
		//通过分享入口进来的参数
		String shareCode =  jsonObject.getString("shareCode");
        String shareSource =  jsonObject.getString("shareSource");
        String userCode =  jsonObject.getString("userId");
        
        //这里拒绝多余的请求，比如库存100，那么超过500或者1000的请求都可以拒绝掉，利用redis的原子自增
        long count = redisRepository.incr("BM_MARKET_SECKILL_COUNT_" + stallActivityId);
		if( count > 1000 ) {
			SeckillInfoResponse response = new SeckillInfoResponse();
			response.setIsSuccess(false);
			response.setResponseCode(6405);
			response.setResponseMsg( "活动太火爆，已经售罄啦！");
			return response;
		}
		logger.info("第" + count + "个请求进入到了消息队列");
		
		return seckillService.startSeckill(stallActivityId, purchaseNum, openId, formId, addressId, shareCode, shareSource, userCode);
	}
	
	/**
	 * 秒杀接口，先将请求放入队列模式
	 * @param jsonObject
	 * @return
	 */
	@ApiOperation(value="去秒杀--先队列模式",nickname="Guoqing")
	@RequestMapping(value="/goSeckillByQueue", method=RequestMethod.POST)
	public BaseResponse goSeckillByQueue(@RequestBody JSONObject jsonObject) {
		int stallActivityId = jsonObject.containsKey("stallActivityId") ? jsonObject.getInteger("stallActivityId") : -1;		//活动Id
		AssertUtil.isTrue(stallActivityId != -1, "非法參數");
		int purchaseNum = jsonObject.containsKey("purchaseNum") ? jsonObject.getInteger("purchaseNum") : 1;		//购买数量
		AssertUtil.isTrue(purchaseNum != -1, "非法參數");
		String openId = jsonObject.containsKey("openId") ? jsonObject.getString("openId") : null;
		AssertUtil.isTrue(!StringUtil.isEmpty(openId), 1101, "非法參數");
		String formId = jsonObject.containsKey("formId") ? jsonObject.getString("formId") : null;
		AssertUtil.isTrue(!StringUtil.isEmpty(formId), 1101, "非法參數");
		long addressId = jsonObject.containsKey("addressId") ? jsonObject.getLong("addressId") : -1;
		AssertUtil.isTrue(addressId != -1, "非法參數");
		//通过分享入口进来的参数
		String shareCode =  jsonObject.getString("shareCode");
		String shareSource =  jsonObject.getString("shareSource");
		String userCode =  jsonObject.getString("userId");
		
		JSONObject jsonStr = new JSONObject();
		jsonStr.put("stallActivityId", stallActivityId);
		jsonStr.put("purchaseNum", purchaseNum);
		jsonStr.put("openId", openId);
		jsonStr.put("addressId", addressId);
		jsonStr.put("formId", formId);
		jsonStr.put("shareCode", shareCode);
		jsonStr.put("shareSource", shareSource);
		jsonStr.put("userCode", userCode);
		//判断秒杀活动是否开始
		if( !seckillService.checkStartSeckill(stallActivityId) ) {
			return new BaseResponse(false, 6205, "秒杀活动尚未开始，请稍等！");
		}
		//这里拒绝多余的请求，比如库存100，那么超过500或者1000的请求都可以拒绝掉，利用redis的原子自增操作
		long count = redisRepository.incr("BM_MARKET_SECKILL_COUNT_" + stallActivityId);
		if( count > 500 ) {
			return new BaseResponse(false, 6405, "活动太火爆，已经售罄啦！");
		}
		logger.info("第" + count + "个请求进入到了消息队列");
		//做用户重复购买校验
		if( redisRepository.exists("BM_MARKET_SECKILL_LIMIT_" + stallActivityId + "_" + openId) ) {
			return new BaseResponse(false, 6105, "您正在参与该活动，不能重复购买！");
		}
		//放入kafka消息队列
		kafkaSender.sendChannelMess("demo_seckill_queue", jsonStr.toString());
		return new BaseResponse();
	}
	
	/**
	 * 06.05-轮询请求当前用户是否秒杀下单成功
	 * <p>Title: seckillPolling</p>  
	 * <p>Description: </p>  
	 * @param jsonObject
	 * @return
	 */
	@ApiOperation(value="轮询接口--先分布式锁模式",nickname="Guoqing")
	@RequestMapping(value="/seckillPolling", method=RequestMethod.POST)
	public SeckillInfoResponse seckillPolling(@RequestBody JSONObject jsonObject) {
		int stallActivityId = jsonObject.containsKey("stallActivityId") ? jsonObject.getInteger("stallActivityId") : -1;		//活动Id
		AssertUtil.isTrue(stallActivityId != -1, "非法參數");
		String openId = jsonObject.containsKey("openId") ? jsonObject.getString("openId") : null;
		AssertUtil.isTrue(!StringUtil.isEmpty(openId), 1101, "非法參數");
		
		SeckillInfoResponse response = new SeckillInfoResponse();
		if( redisRepository.exists("BM_MARKET_LOCK_POLLING_" + stallActivityId + "_" + openId) ) {
			//如果缓存中存在锁定秒杀和用户ID的key，则证明该订单尚未处理完成，需要继续等待
			response.setIsSuccess(true);
			response.setResponseCode(6103);
			response.setResponseMsg("排队中，请稍后");
			response.setRefreshTime(1000);
		} else {
			//如果缓存中该key已经不存在，则表明该订单已经下单成功，可以进入支付操作，并取出orderId返回
			String redisOrderInfo = redisRepository.get("BM_MARKET_SECKILL_ORDERID_" + stallActivityId + "_" + openId);
			if( redisOrderInfo == null ) {
				response.setIsSuccess(false);
				response.setResponseCode(6106);
				response.setResponseMsg("秒杀失败，下单出现异常，请重试！");
				response.setOrderCode(null);
				response.setRefreshTime(0);
			}else {
				response.setIsSuccess(true);
				response.setResponseCode(6104);
				response.setResponseMsg("秒杀成功");
				response.setOrderCode(redisOrderInfo);
				response.setRefreshTime(0);
			}
		}
		return response;
	}
	
	/**
	 * 轮询请求  判断是否获得下单资格
	 * @param jsonObject
	 * @return
	 */
	@ApiOperation(value="轮询接口--先队列模式",nickname="Guoqing")
	@RequestMapping(value="/seckillPollingQueue", method=RequestMethod.POST)
	public SeckillInfoResponse seckillPollingQueue(@RequestBody JSONObject jsonObject) {
		int stallActivityId = jsonObject.containsKey("stallActivityId") ? jsonObject.getInteger("stallActivityId") : -1;		//活动Id
		AssertUtil.isTrue(stallActivityId != -1, "非法參數");
		String openId = jsonObject.containsKey("openId") ? jsonObject.getString("openId") : null;
		AssertUtil.isTrue(!StringUtil.isEmpty(openId), 1101, "非法參數");
		
		SeckillInfoResponse response = new SeckillInfoResponse();
		//是否存在下单资格码的key
		if( redisRepository.exists("BM_MARKET_SECKILL_QUEUE_"+stallActivityId+"_"+openId) ){
			String result = redisRepository.get("BM_MARKET_SECKILL_QUEUE_"+stallActivityId+"_"+openId);
			response = JSONObject.parseObject(JSONObject.parseObject(result).getJSONObject("response").toJSONString(), SeckillInfoResponse.class);
		} else {
			response.setIsSuccess(true);
			response.setResponseCode(0);
			response.setResponseMsg("活动太火爆，排队中...");
			response.setRefreshTime(0);
		}
		return response;
	}
	
	/**
	 * 根据获取到的下单资格码创建订单
	 * @param jsonObject
	 * @return
	 */
	@ApiOperation(value="先队列模式--下单接口",nickname="Guoqing")
	@RequestMapping(value="/createOrder", method=RequestMethod.POST)
	public BaseResponse createOrder(@RequestBody JSONObject jsonObject) {
		int stallActivityId = jsonObject.containsKey("stallActivityId") ? jsonObject.getInteger("stallActivityId") : -1;		//活动Id
		AssertUtil.isTrue(stallActivityId != -1, "非法參數");
		String openId = jsonObject.containsKey("openId") ? jsonObject.getString("openId") : null;
		AssertUtil.isTrue(!StringUtil.isEmpty(openId), 1101, "非法參數");
		String orderQualificationCode = jsonObject.containsKey("orderQualificationCode") ? jsonObject.getString("orderQualificationCode") : null;
		AssertUtil.isTrue(!StringUtil.isEmpty(orderQualificationCode), 1101, "非法參數");
		
		//校验下单资格码
		String redisQualificationCode = redisRepository.get("BM_MARKET_SECKILL_QUALIFICATION_CODE_" + stallActivityId + "_" + openId);
		if(StringUtils.isEmpty(redisQualificationCode) || !orderQualificationCode.equals(redisQualificationCode) ) {
			return new BaseResponse(false, 6305, "您的资格码已经过期！");
		}else {
			//走后续的下单流程，并校验真实库存；该接口的流量已经是与真实库存几乎相匹配的流量值，按理不应该存在超高并发
			return new BaseResponse();
		}
	}
	
	@ApiOperation(value="test",nickname="Guoqing")
	@GetMapping(value="/test")
	public void test() throws InterruptedException {
		final int[] counter = {0};

        for (int i= 0; i < 300; i++){
        
        	new Thread(new Runnable() {

                @Override

                public void run() {
                	boolean isGetLock = redissonDistributedLocker.tryLock("test0001", 3L, 1L);
                	logger.info(isGetLock + "");
                	if(isGetLock) {
                		try {
							int a = counter[0];
							counter[0] = a + 1;
							logger.info(a + "");
						} finally {
							redissonDistributedLocker.unlock("test0001");
						}
                	}
                }
            }).start();
        	
        }

        // 主线程休眠，等待结果
        Thread.sleep(10000);
        logger.info(counter[0] + "");
	}
	
	@ApiOperation(value="test1",nickname="Guoqing")
	@GetMapping(value="/test1")
	public void test1() throws InterruptedException {
		final int[] counter = {0};

        for (int i= 0; i < 100; i++){
        
        	new Thread(new Runnable() {

                @Override

                public void run() {
            		try {
            			redissonDistributedLocker.lock("test0002", 1L);
            			logger.info(redissonDistributedLocker.isLocked("test0002") + "");
						int a = counter[0];
						counter[0] = a + 1;
						logger.info(a + "");
					} finally {
						redissonDistributedLocker.unlock("test0002");
					}
                }
            }).start();
        	
        }

        // 主线程休眠，等待结果
        Thread.sleep(10000);
        logger.info(counter[0] + "");
	}
	
	@ApiOperation(value="test2",nickname="Guoqing")
	@GetMapping(value="/test2")
	public void test2() throws InterruptedException {
		logger.info(SystemUtil.getJavaRuntimeInfo().toString());
		logger.info(SystemUtil.getJavaInfo().toString());
		logger.info(SystemUtil.getJvmInfo().toString());
		logger.info(SystemUtil.getJavaSpecInfo().toString());
		logger.info(SystemUtil.getRuntimeInfo().toString());
	}
}
