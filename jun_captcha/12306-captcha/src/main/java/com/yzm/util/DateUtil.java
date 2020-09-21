package com.yzm.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

	/**
	 * 查看是否是正常的订票时间
	 * @return true 正常订票时间 false：维护时间
	 */
    public static boolean isNormalTime(){
    	boolean runFlag = false;
    	String startTimeStr = "06:00:00";
    	String endTimeStr ="23:00:00";
		String format = "HH:mm:ss";
		SimpleDateFormat sf = new SimpleDateFormat("HH:mm:ss");
		String now = sf.format(new Date());
//		now = "05:58:00";
		Date nowTime;
		try {
			nowTime = new SimpleDateFormat(format).parse(now);
			Date startTime = new SimpleDateFormat(format).parse(startTimeStr);
			Date endTime = new SimpleDateFormat(format).parse(endTimeStr);
			if (isEffectiveDate(nowTime, startTime, endTime)) {
				runFlag = true;
				
			} else {
				runFlag = false;
			
			}
		} catch (java.text.ParseException e) {
			e.printStackTrace();
		}
		return runFlag;
	}
    
    /**
	 * 判断当前时间是否在[startTime, endTime]区间，注意时间格式要一致
	 *
	 * @param nowTime   当前时间
	 * @param startTime 开始时间
	 * @param endTime   结束时间
	 * @return
	 * @author jqlin
	 */
	public static boolean isEffectiveDate(Date nowTime, Date startTime, Date endTime) {
		if (nowTime.getTime() == startTime.getTime()
				|| nowTime.getTime() == endTime.getTime()) {
			return true;
		}

		Calendar date = Calendar.getInstance();
		date.setTime(nowTime);

		Calendar begin = Calendar.getInstance();
		begin.setTime(startTime);

		Calendar end = Calendar.getInstance();
		end.setTime(endTime);

		if (date.after(begin) && date.before(end)) {
			return true;
		} else {
			return false;
		}
	}

}
