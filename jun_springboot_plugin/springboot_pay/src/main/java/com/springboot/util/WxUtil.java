package com.springboot.util;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class WxUtil {

	// 通过code换取网页授权access_token与openid
	public static final String get_user_openId = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";

	/**
	 * 
	 * 获得ACCESS_TOKEN
	 * 
	 * @Title: getAccess_token
	 * 
	 * @Description: 获得ACCESS_TOKEN
	 * 
	 * @param @return 设定文件
	 * 
	 * @return String 返回类型
	 * 
	 * @throws
	 * 
	 */
	public static String getAccessToken() {
		String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="
				+ PropKit.get("weixin.pay.appid") + "&secret=" + PropKit.get("weixin.pay.appsecret");
		String accessToken = null;
		try {
			URL urlGet = new URL(url);
			HttpURLConnection http = (HttpURLConnection) urlGet.openConnection();
			http.setRequestMethod("GET"); // 必须是get方式请求
			http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			http.setDoOutput(true);
			http.setDoInput(true);
			System.setProperty("sun.net.client.defaultConnectTimeout", "30000");// 连接超时30秒
			System.setProperty("sun.net.client.defaultReadTimeout", "30000"); // 读取超时30秒
			http.connect();
			InputStream is = http.getInputStream();
			int size = is.available();
			byte[] jsonBytes = new byte[size];
			is.read(jsonBytes);
			String message = new String(jsonBytes, "UTF-8");
			JSONObject demoJson = JSON.parseObject(message);
			accessToken = demoJson.getString("access_token");
			is.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("获取微信access_token出错");
		}
		return accessToken;
	}

	public static String getTicket() {
		final String jsapi_ticket_url = String.format(
				"https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=%s&type=jsapi",
				WxUtil.getAccessToken());
		String ticket = null;
		try {
			URL urlGet = new URL(jsapi_ticket_url);
			HttpURLConnection http = (HttpURLConnection) urlGet.openConnection();
			http.setRequestMethod("GET"); // 必须是get方式请求
			http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			http.setDoOutput(true);
			http.setDoInput(true);
			System.setProperty("sun.net.client.defaultConnectTimeout", "30000");// 连接超时30秒
			System.setProperty("sun.net.client.defaultReadTimeout", "30000"); // 读取超时30秒
			http.connect();
			InputStream is = http.getInputStream();
			int size = is.available();
			byte[] jsonBytes = new byte[size];
			is.read(jsonBytes);
			String message = new String(jsonBytes, "UTF-8");
			JSONObject demoJson = JSON.parseObject(message);
			ticket = demoJson.getString("ticket");
			is.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("获取微信ticket出错");
		}
		return ticket;
	}

	public static Map<String, Object> cresateSignature() {
		try {
			// 1、获取AccessToken
			String accessToken = getAccessToken();
			// 2、获取Ticket
			String jsapi_ticket = null;
			if (JedisUtil.get("ticket") == null) {
				String ticket = getTicket();
				JedisUtil.setex("ticket", 7200, ticket);
				jsapi_ticket = ticket;
			} else {

				jsapi_ticket = JedisUtil.get("ticket");
			}
			System.out.println("缓存中的数据：" + JedisUtil.get("ticket"));
			// 3、时间戳和随机字符串
			String noncestr = UUID.randomUUID().toString().replace("-", "").substring(0, 16);// 随机字符串
			String timestamp = String.valueOf(System.currentTimeMillis() / 1000);// 时间戳

			System.out.println("accessToken:" + accessToken + "\njsapi_ticket:" + jsapi_ticket + "\n时间戳：" + timestamp
					+ "\n随机字符串：" + noncestr);

			// 4、获取url
			String url = "http://test.jiedanba.cn/pay/";
			/*
			 * 根据JSSDK上面的规则进行计算，这里比较简单，我就手动写啦 String[] ArrTmp =
			 * {"jsapi_ticket","timestamp","nonce","url"}; Arrays.sort(ArrTmp);
			 * StringBuffer sf = new StringBuffer(); for(int
			 * i=0;i<ArrTmp.length;i++){ sf.append(ArrTmp[i]); }
			 */

			// 5、将参数排序并拼接字符串
			String str = "jsapi_ticket=" + jsapi_ticket + "&noncestr=" + noncestr + "&timestamp=" + timestamp + "&url="
					+ url;

			// 6、将字符串进行sha1加密
			String signature = HashKit.sha1(str);
			System.out.println("参数：" + str + "\n签名：" + signature);
			Map<String, Object> result = new HashMap<String, Object>();
			result.put("jsapi_ticket", jsapi_ticket);
			result.put("noncestr", noncestr);
			result.put("timestamp", timestamp);
			result.put("url", url);
			result.put("signature", signature);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("生成微信Signature出错");
		}

	}

	/**
	 * 
	 * getUserOpenId(根据code获取网页授权token)
	 * 
	 * @param appid
	 * @param appsecret
	 * @param code
	 * @return
	 * @throws @author
	 *             chenlin
	 * @date 2015年10月8日 下午5:14:50
	 */
	public static Map<String, Object> getUserOpenId(String appid, String appsecret, String code) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if (StringUtils.isNotBlank(code)) {
			String url = get_user_openId.replace("APPID", appid).replace("SECRET", appsecret).replace("CODE", code);
			String result = HttpKit.get(url);
			JSONObject jsonObject = JSON.parseObject(result);
			if (null != jsonObject) {
				if (jsonObject.containsKey("errcode")) {

				} else {
					resultMap.put("access_token", jsonObject.getString("access_token"));
					resultMap.put("openid", jsonObject.getString("openid"));
				}
			}
		}
		return resultMap;
	}

}
