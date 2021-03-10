/**  
* <p>Title: SeckillResponse.java</p>  
* <p>Description: </p>  
* <p>Copyright: Copyright (c) 2018</p>  
* <p>Company: www.bluemoon.com</p>  
* @author Wujun
* @date 2018年8月10日  
*/  
package cn.com.bluemoon.common.response;

import cn.com.bluemoon.common.response.BaseResponse;

/**  
* <p>Title: SeckillResponse</p>  
* <p>Description: </p>  
* @author Wujun
* @date 2018年8月10日  
*/
public class SeckillInfoResponse extends BaseResponse {
	
	private int refreshTime;	//下一次请求刷新时间
	private long orderId;		//订单ID
	private String orderCode;	//订单编码
	private String orderQualificationCode;  //下单资格码
	/**
	 * @return the refreshTime
	 */
	public int getRefreshTime() {
		return refreshTime;
	}
	/**
	 * @param refreshTime the refreshTime to set
	 */
	public void setRefreshTime(int refreshTime) {
		this.refreshTime = refreshTime;
	}
	/**
	 * @return the orderId
	 */
	public long getOrderId() {
		return orderId;
	}
	/**
	 * @param orderId the orderId to set
	 */
	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}
	/**
	 * @return the orderCode
	 */
	public String getOrderCode() {
		return orderCode;
	}
	/**
	 * @param orderCode the orderCode to set
	 */
	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}
	public String getOrderQualificationCode() {
		return orderQualificationCode;
	}
	public void setOrderQualificationCode(String orderQualificationCode) {
		this.orderQualificationCode = orderQualificationCode;
	}
	
	
}
