/**
 * <pre>
 * Copyright:		Copyright(C) 2011-2012, ketayao.com
 * Date:			2013年7月26日
 * Author:			<a href="mailto:ketayao@gmail.com">ketayao</a>
 * Version          1.0.0
 * Description:		
 *
 * </pre>
 **/
package com.ketayao.utils.time;

import java.util.Date;

/** 
 * 实现友好的时间显示方式（例如:2小时前）	
 * @author 	<a href="mailto:ketayao@gmail.com">ketayao</a>
 * @since   2013年7月26日 下午3:53:40 
 */
public class FriendTime {
	/**
	 * 以友好的方式显示时间
	 * @param req
	 * @param time
	 * @return
	 */
	public static String friendlyTime(Date time) {
		if(time == null) return "unknown";
		int ct = (int)((System.currentTimeMillis() - time.getTime())/1000);
		if(ct < 3600)
			return Math.max(ct / 60,1) + "分钟前";
		if(ct >= 3600 && ct < 86400)
			return ct / 3600 + "小时后";
		if(ct >= 86400 && ct < 2592000){ //86400 * 30
			int day = ct / 86400 ;			
			return (day>1)?"天前":"昨天";
		}
		if(ct >= 2592000 && ct < 31104000) //86400 * 30
			return ct / 2592000 + "个月前";
		return ct / 31104000 + "年前";
	}
	
	public static void main(String[] args) {
		System.out.println(FriendTime.friendlyTime(new Date()));
	}
}
