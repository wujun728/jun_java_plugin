package com.jun.plugin.util;

import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import com.alibaba.fastjson.JSON;
import com.jun.plugin.entity.Header;

/**
 * 获得properties文件中key对应的value
 * @ClassName: MsgUtil
 * @Description:
 * @author: renkai721
 * @date: 2018年6月24日 上午12:21:17
 */
public class MsgUtil {
	public static ResourceBundle rb = ResourceBundle.getBundle("application");
	public static String _R_N = "\r\n";

	/**
	 * 
	 * @author: renkai721
	 * @date: 2018年6月24日 上午12:21:30
	 * @Title: outJSON 
	 * @Description: 
	 * @param header 消息头部
	 * @param data 消息主体
	 * @return
	 *
	 */
	public static String outJSON(Header header, Object data, Socket socket) {
		String message = "";
		Map<String, Object> json = new HashMap<String, Object>();
		String flag = "###HisenseNetMessage###";
		String pm = "1  ";
		json.put("header", header);
		json.put("data", data);
		try {
			String temp_str = JSON.toJSON(json).toString();
			int size = temp_str.length();
			String length = formatStr((size + ""), 10);
			message = flag + _R_N + pm + _R_N + length + _R_N + temp_str;
//			DataOutputStream out = new DataOutputStream(socket.getOutputStream());
//			out.writeUTF(message); 
			System.out.println("============================发送的信息：============================"+message);
//			socket.getOutputStream().write(message.getBytes("UTF-8"));
//            socket.getOutputStream().flush();
		} catch (Exception e) {
			
		}
		return message;
	}
	public static String outJSON(Header header, Object data) {
		String message = "";
		Map<String, Object> json = new HashMap<String, Object>();
		String flag = "###HisenseNetMessage###";
		String pm = "1  ";
		json.put("header", header);
		json.put("data", data);
		try {
			String temp_str = JSON.toJSON(json).toString();
			int size = temp_str.length();
			String length = formatStr((size + ""), 10);
			message = flag + _R_N + pm + _R_N + length + _R_N + temp_str;
//			DataOutputStream out = new DataOutputStream(socket.getOutputStream());
//			out.writeUTF(message); 
			System.out.println("============================发送的信息：============================"+message);
//			socket.getOutputStream().write(message.getBytes("UTF-8"));
//            socket.getOutputStream().flush();
		} catch (Exception e) {
			
		}
		return message;
	}
	/**
	 * 字符串长度不够右补空格
	 * @author: renkai721
	 * @date: 2018年6月24日 上午12:21:52
	 * @Title: formatStr 
	 * @Description: 
	 * @param str
	 * @param length
	 * @return
	 *
	 */
	public static String formatStr(String str, int length) {
		if (str == null) {
			str = "";
		}
		int strLen = str.getBytes().length;
		if (strLen == length) {
			return str;
		} else if (strLen < length) {
			int temp = length - strLen;
			String tem = "";
			for (int i = 0; i < temp; i++) {
				tem = tem + " ";
			}
			return str + tem;
		} else {
			return str.substring(0, length);
		}
	}
}
