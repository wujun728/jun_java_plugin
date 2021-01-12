package com.java8.newdate;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

class DataFormatThreadLocal{
        public static final ThreadLocal<DateFormat> data = new ThreadLocal<DateFormat>(){
                @Override
                protected DateFormat initialValue() {
                        return new SimpleDateFormat("yyyyMMdd");
                }
        };
        public static Date convert(String source) throws ParseException {
                return data.get().parse(source);
        }


}