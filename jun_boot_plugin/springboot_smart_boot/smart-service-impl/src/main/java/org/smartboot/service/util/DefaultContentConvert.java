package org.smartboot.service.util;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.smartboot.shared.converter.Convert;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * 内容转换器
 * 
 * @author Wujun
 * @version DefaultContentConvert.java, v 0.1 2017年1月4日 下午8:24:54 Seer Exp.
 */
public class DefaultContentConvert implements Convert<JSONArray, String> {
	private static final Logger LOGGER = LogManager.getLogger(DefaultContentConvert.class);

	public JSONArray convert(String s) {
		String[] strAppy = StringUtils.split(s, "\r\n");
		JSONArray jsonArray = new JSONArray();
		if (strAppy == null) {
			return jsonArray;
		}
		for (String line : strAppy) {
			for (FlagEnum flag : FlagEnum.values()) {
				if (StringUtils.startsWith(line, flag.getFlag())) {
					try {
						jsonArray.add(flag.getJsonObject(StringUtils.substring(line, flag.getFlag().length())));
					} catch (Exception e) {
						LOGGER.catching(e);
					}
				}
			}

		}
		return jsonArray;
	}

	enum FlagEnum {

		/**
		 * 文本[txt:文字介绍]
		 * 
		 **/
		TEXT_FLAG(1, "txt:", new Convert<JSONObject, String>() {

			@Override
			public JSONObject convert(String s) {
				JSONObject json = new JSONObject();
				json.put("txt", s);
				return json;
			}

		}),
		/**
		 * 图片[img:http://www.banbai.com/1.jpg]
		 * 
		 */
		IMAGE_FLAG(2, "img:", new Convert<JSONObject, String>() {

			@Override
			public JSONObject convert(String s) {
				JSONObject json = new JSONObject();
				String[] array = StringUtils.split(s, " ");
				if (ArrayUtils.getLength(array) > 1) {
					json.put("url", StringUtils.trim(array[0]));
					json.put("href", StringUtils.trim(array[1]));// 支持图片点击跳转
				} else {
					json.put("url", StringUtils.trim(s));
				}
				return json;
			}

		}),
		/**
		 * 视频[mp4:http://www.banbai.com/1.jpg http://www.danbai.com/1.mp4]
		 */
		VIDEO_FLAG(3, "mp4:", new Convert<JSONObject, String>() {

			@Override
			public JSONObject convert(String s) {
				JSONObject json = new JSONObject();
				String[] array = StringUtils.split(s, " ");
				AssertUtils.isTrue(array != null && array.length == 2, "配置有误");
				json.put("img", StringUtils.trim(array[0]));
				json.put("url", StringUtils.trim(array[1]));
				return json;
			}

		}),

		VIDEO_FLAG_MP3(4, "mp3:", new Convert<JSONObject, String>() {

			@Override
			public JSONObject convert(String s) {
				JSONObject json = new JSONObject();
				String[] array = StringUtils.split(s, " ");
				AssertUtils.isTrue(array != null && array.length == 2, "配置有误");
				json.put("img", StringUtils.trim(array[0]));
				json.put("url", StringUtils.trim(array[1]));
				return json;
			}

		}),

		VIDEO_FLAG_HTTP(6, "http:", new Convert<JSONObject, String>() {

			@Override
			public JSONObject convert(String s) {
				JSONObject json = new JSONObject();
				String[] array = StringUtils.split(s, " ");
				AssertUtils.isTrue(array != null && array.length == 2, "配置有误");
				json.put("url", StringUtils.trim(array[0]));
				json.put("pwd", StringUtils.trim(array[1]));
				return json;
			}

		}),

		IMAGE_FLAG_GIF(5, "gif:", new Convert<JSONObject, String>() {

			@Override
			public JSONObject convert(String s) {
				JSONObject json = new JSONObject();
				json.put("url", StringUtils.trim(s));
				return json;
			}

		}),
		/**
		 * 文本[txt:文字介绍]
		 * 
		 **/
		TEX_Tag(7, "title:", new Convert<JSONObject, String>() {

			@Override
			public JSONObject convert(String s) {
				JSONObject json = new JSONObject();
				json.put("txt", s);
				return json;
			}

		});

		private <T, S> FlagEnum(int type, String flag, Convert<JSONObject, String> convert) {
			this.type = type;
			this.flag = flag;
			this.convert = convert;
		}

		int type;
		String flag;
		Convert<JSONObject, String> convert;

		/**
		 * Getter method for property <tt>type</tt>.
		 *
		 * @return property value of type
		 */
		public final int getType() {
			return type;
		}

		/**
		 * Getter method for property <tt>flag</tt>.
		 *
		 * @return property value of flag
		 */
		public final String getFlag() {
			return flag;
		}

		public JSONObject getJsonObject(String line) {
			JSONObject json = convert.convert(line);
			json.put("type", type);
			return json;
		}
	}

}
