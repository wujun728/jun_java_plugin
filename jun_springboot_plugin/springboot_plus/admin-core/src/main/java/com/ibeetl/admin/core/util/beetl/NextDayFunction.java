package com.ibeetl.admin.core.util.beetl;

import java.util.Calendar;
import java.util.Date;

import org.beetl.core.Context;
import org.beetl.core.Function;

public class NextDayFunction implements Function {

	@Override
	public Object call(Object[] paras, Context ctx) {
		Date date = (Date)paras[0];
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DAY_OF_YEAR, 1);// 今天+1天
		return c.getTime();
	}

}
