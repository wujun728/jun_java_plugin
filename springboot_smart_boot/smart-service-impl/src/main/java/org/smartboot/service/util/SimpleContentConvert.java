package org.smartboot.service.util;

import org.smartboot.shared.converter.Convert;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * 简化版的内容转码器
 * 
 * @author Wujun
 * @version SimpleContentConvert.java, v 0.1 2017年1月4日 下午8:46:19 Seer Exp.
 */
public class SimpleContentConvert implements Convert<JSONObject, String> {
	private DefaultContentConvert convert = new DefaultContentConvert();

	@Override
	public JSONObject convert(String s) {
		JSONArray array = convert.convert(s);
		return array == null || array.size() == 0 ? null : array.getJSONObject(0);
	}

}
