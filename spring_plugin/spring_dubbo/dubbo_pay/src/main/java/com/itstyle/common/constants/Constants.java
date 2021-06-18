package com.itstyle.common.constants;

import org.springframework.util.ClassUtils;

public class Constants {
	
	public static final String SF_FILE_SEPARATOR = System.getProperty("file.separator");//文件分隔符
	public static final String SF_LINE_SEPARATOR = System.getProperty("line.separator");//行分隔符
	public static final String SF_PATH_SEPARATOR = System.getProperty("path.separator");//路径分隔符
    //此处为支付二维码 存放地址 自行定义
	public static final String QRCODE_PATH = ClassUtils.getDefaultClassLoader().getResource("static").getPath()+SF_FILE_SEPARATOR+"qrcode"; 
	//微信对账单 字段说明
	public static final String WEIXIN_BILL = "tradetime, ghid, mchid, submch, deviceid, wxorder, bzorder, openid, tradetype, tradestatus, bank, currency, totalmoney, redpacketmoney, wxrefund, bzrefund, refundmoney, redpacketrefund, refundtype, refundstatus, productname, bzdatapacket, fee, rate";
	
	public static final String PATH_BASE_INFO_XML = SF_FILE_SEPARATOR+"WEB-INF"+SF_FILE_SEPARATOR+"xmlConfig"+SF_FILE_SEPARATOR;
	
	public static final String CURRENT_USER = "UserInfo";
	
	public static final String SUCCESS = "success";
	
	public static final String FAIL = "fail";
	
}
