package com.jun.plugin.common;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Base64;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.PascalNameFilter;
import com.google.gson.Gson;
import com.jun.plugin.util.MsgUtil;

import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;

/**
 * @ClassName: AbstractGatServer
 * @Description:
 * @author: renkai721
 * @date: 2018年6月25日 下午5:28:11
 */
@Slf4j
public abstract class BaseController {
	@Autowired
	public OkHttpClient okHttpClient;
	@Autowired
	public Gson gson;
//	@Autowired
//	public StringRedisTemplate stringRedisTemplate;
	@Autowired
	public RestTemplate restTemplate;

	public String parseRequest(HttpServletRequest request) {
		StringBuffer stringBuffer = new StringBuffer();
		String line = null;
		try {
			BufferedReader reader = request.getReader();
			while ((line = reader.readLine()) != null) {
				stringBuffer.append(line);
			}
		} catch (Exception e) {
			log.error("请求流解析出错");
		}
		String postString = stringBuffer.toString();
		return postString;
	}
	/**
	 * 封装ResponseStatusDcs_s对象JSON
	 * @author: renkai721
	 * @date: 2018年7月3日 下午2:27:59
	 * @Title: grss
	 * @Description:
	 * @param rsds
	 * @return
	 */
	public static String grss(Object obj) {
		return JSON.toJSONString(obj, new PascalNameFilter());
	}
	/**
	 * 获取异常错误消息
	 * @author: renkai721
	 * @date: 2018年6月28日 下午4:20:33
	 * @Title: emsg
	 * @Description:
	 * @param e
	 * @return
	 */
	public static String emsg(Throwable e) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw, true);
		e.printStackTrace(pw);
		pw.flush();
		sw.flush();
		return sw.toString();
	}
	/**
	 * 获取用户请求IP
	 * @author: renkai721
	 * @date: 2018年6月29日 下午4:57:02
	 * @Title: getIPAddress
	 * @Description:
	 * @param request
	 * @return
	 */
	public static String gip(HttpServletRequest request) {
		String ip = null;
		// X-Forwarded-For：Squid 服务代理
		String ipAddresses = request.getHeader("X-Forwarded-For");
		if (ipAddresses == null || ipAddresses.length() == 0 || "unknown".equalsIgnoreCase(ipAddresses)) {
			// Proxy-Client-IP：apache 服务代理
			ipAddresses = request.getHeader("Proxy-Client-IP");
		}
		if (ipAddresses == null || ipAddresses.length() == 0 || "unknown".equalsIgnoreCase(ipAddresses)) {
			// WL-Proxy-Client-IP：weblogic 服务代理
			ipAddresses = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ipAddresses == null || ipAddresses.length() == 0 || "unknown".equalsIgnoreCase(ipAddresses)) {
			// HTTP_CLIENT_IP：有些代理服务器
			ipAddresses = request.getHeader("HTTP_CLIENT_IP");
		}
		if (ipAddresses == null || ipAddresses.length() == 0 || "unknown".equalsIgnoreCase(ipAddresses)) {
			// X-Real-IP：nginx服务代理
			ipAddresses = request.getHeader("X-Real-IP");
		}
		// 有些网络通过多层代理，那么获取到的ip就会有多个，一般都是通过逗号（,）分割开来，并且第一个ip为客户端的真实IP
		if (ipAddresses != null && ipAddresses.length() != 0) {
			ip = ipAddresses.split(",")[0];
		}
		// 还是不能获取到，最后再通过request.getRemoteAddr();获取
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ipAddresses)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
	/**
	 * 对字节数组字符串进行Base64解码并生成图片
	 * @author: renkai721
	 * @date: 2018年7月4日 下午2:45:52
	 * @Title: base64ToImage
	 * @Description:
	 * @param base64
	 * @param deviceId
	 * @param number 图片序号
	 * @param temp_date 日期
	 * @return
	 */
	public static synchronized boolean base64ToImage(String base64, String deviceId, int number, Date temp_date) {
		// 图像数据为空
		if (base64 == null || "".equals(base64)) {
			return true;
		}
		// 图片保存文件夹=盘符:\yyyyMMdd\编码(20位deviceId)\
		String panfu = MsgUtil.rb.getString("dcs.pic.partition");
		panfu = panfu.replace(":", "");
		panfu = panfu.replace("\"", "");
		panfu = panfu.replace("/", "");
		String date = DateFormatUtils.format(temp_date, "yyyyMMdd");
		// 图片文件夹地址
		String mkdir = panfu + ":/" + date + "/" + deviceId;
		// 创建文件夹
		File mkdirs = new File(mkdir);
		if (!mkdirs.exists()) {
			mkdirs.mkdirs();
		}
		// 对字节数组字符串进行Base64解码并生成图片
		// 图片名称
		// 图片后缀=.jpg
		// 设备编码 1-20 车辆识别智能设备统一标识编码；
		// 子类型编码 21-22 车辆图片采用图像02
		// 时间编码 23-36 表示视频图像信息基本对象生成时间，精确到秒级 yyyyMMddHHmmss，年月日时分秒
		// 序号 37-41 视频图像信息基本对象序号 仅一张图片就写00001
		String time = DateFormatUtils.format(temp_date, "yyyyMMddHHmmss");
		String num = number + "";
		String temp_number = "";
		for (int i = 0; i < (5 - num.length()); i++) {
			temp_number += "0";
		}
		temp_number += num;
		String name = deviceId + "02" + time + temp_number + ".jpg";
		String file = mkdir + "/" + name;
		File f = new File(file);
		try {
			if (!f.exists()) {
				// Base64解码
				byte[] b = Base64.getDecoder().decode(base64);
				for (int i = 0; i < b.length; ++i) {
					if (b[i] < 0) {
						b[i] += 256;
					}
				}
				OutputStream out = new FileOutputStream(file);
				out.write(b);
				out.flush();
				out.close();
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	
}
