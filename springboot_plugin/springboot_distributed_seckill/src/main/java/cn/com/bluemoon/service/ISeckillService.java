/**  
* <p>Title: ISeckillService.java</p>  
* <p>Description: </p>  
* <p>Copyright: Copyright (c) 2018</p>  
* <p>Company: www.bluemoon.com</p>  
* @author Wujun
* @date 2018年8月10日  
*/  
package cn.com.bluemoon.service;

import cn.com.bluemoon.common.response.SeckillInfoResponse;

/**  
* <p>Title: ISeckillService</p>  
* <p>Description: 秒杀相关方法</p>  
* @author Wujun
* @date 2018年8月10日  
*/
public interface ISeckillService {

	/**
	 * 秒杀处理主要逻辑
	 * <p>Title: startSeckill</p>  
	 * <p>Description: </p>  
	 * @param stallActivityId
	 * @param purchaseNum
	 * @param openId
	 * @param formId
	 * @param addressId
	 * @return
	 */
	public SeckillInfoResponse startSeckill(int stallActivityId, int purchaseNum, String openId, String formId,
			long addressId, String shareCode, String shareSource, String userCode );
	
	/**
	 * 判断秒杀活动是否已经开始
	 * @param stallActivityId
	 * @return
	 */
	public boolean checkStartSeckill(int stallActivityId);
	
}
