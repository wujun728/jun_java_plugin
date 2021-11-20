package com.jun.web.biz.service.Impl;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

//������ϰ1
public class MyDate {
	private Date date;
	public MyDate(Date date){
		this.date = date;
	}
	public String toLocaleString(){
		String msg = null;
		DateFormat df = DateFormat.getDateTimeInstance(
				DateFormat.FULL, 
				DateFormat.MEDIUM, 
				Locale.CHINA);
		msg = df.format(date);
		return msg;
	} 
}
