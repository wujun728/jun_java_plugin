package com.jun.plugin.json.json_lib;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.PropertyFilter;

public class JsonUtil {

	private static String top = "{";
	private static String top_ = "}";

	public JsonUtil() {
	}

	public static String getJsonByList(List dList) {
		String data = "";
		if (dList != null) {
			JSONArray ja = JSONArray.fromObject(dList);
			data = ja.toString();
		}
		return data;
	}

	public static String getObjectJsonData(HashMap params) {
		StringBuffer data = new StringBuffer();
		int i = 0;
		if (params != null) {
			data.append(top);
			for (Iterator it = params.entrySet().iterator(); it.hasNext();) {
				java.util.Map.Entry element = (java.util.Map.Entry) it.next();
				String key = (String) element.getKey();
				List dList = (ArrayList) element.getValue();
				if (i > 0)
					data.append(",");
				data.append((new StringBuilder()).append("\"").append(key).append("\"").append(":").toString());
				data.append(getJsonByList(dList));
				i++;
			}

			data.append(top_);
		}
		return data.toString();
	}

	public static String getExtGridJsonData(List dList) {
		StringBuffer data = new StringBuffer();
		if (dList != null) {
			data.append((new StringBuilder()).append("{\"totalCount\":").append(dList.size()).append(", \"Body\":")
					.toString());
			data.append(getJsonByList(dList));
			data.append("}");
		}
		return data.toString();
	}

	public static String getGridJsonData(int rowNumber, List dList) {
		StringBuffer data = new StringBuffer();
		if (dList != null) {
			data.append((new StringBuilder()).append("{\"totalCount\":").append(rowNumber).append(", \"Body\":")
					.toString());
			data.append(getJsonByList(dList));
			data.append("}");
		}
		return data.toString();
	}

	public static String getDefineJsonData(String firstParam, String listParam, String firstValue, List dList) {
		StringBuffer data = new StringBuffer();
		if (dList != null) {
			data.append((new StringBuilder()).append("{\"").append(firstParam).append("\":").append(firstValue)
					.append(", \"").append(listParam).append("\":").toString());
			data.append(getJsonByList(dList));
			data.append("}");
		}
		return data.toString();
	}

	public static String getBasetJsonData(List dList) {
		StringBuffer data = new StringBuffer();
		if (dList != null) {
			JSONArray ja = JSONArray.fromObject(dList);
			data.append(ja.toString());
		}
		return data.toString();
	}

	public static String getEasyUIJsonData(List dList) {
		StringBuffer data = new StringBuffer();
		if (dList != null)
			if (dList.size() > 0) {
				String total = ((Map) dList.get(0)).get("IRECCOUNT").toString();
				data.append((new StringBuilder()).append("{\"total\": ").append(total).append(", \"rows\": ")
						.append(getJsonByList(dList)).append("}").toString());
			} else {
				data.append("{\"total\": 0, \"rows\": []}");
			}
		System.out.println("json=" + data.toString());
		return data.toString();
	}

	public static String getEasyUIJsonDataV2(List dList, String total) {
		StringBuffer data = new StringBuffer();
		if (dList != null)
			if (dList.size() > 0) {
				// String total =
				// StringUtil.toString(((Map)dList.get(0)).get("IRECCOUNT"));
				data.append((new StringBuilder()).append("{\"total\": ").append(total).append(", \"rows\": ")
						.append(getJsonByList(dList)).append("}").toString());
			} else {
				data.append("{\"total\": 0, \"rows\": []}");
			}
		System.out.println("total=" + total + "json=" + data.toString());
		return data.toString();
	}

	public static String getJsonDataByObject(Object obj) {
		StringBuffer data = new StringBuffer();
		if (obj != null)
			data.append(JSONArray.fromObject(obj).toString());
		return data.toString();
	}

	// *************************************************************************************
	// *************************************************************************************
	/**
	 * List转换成Json字符�?
	 */
	public static String listToJson(List list, final String[] filters) {
		if (null == list || list.size() == 0) {
			return "";
		}
		JSONArray jsonArray = null;
		JsonConfig config = new JsonConfig();
		// config.registerJsonValueProcessor(Date.class, new
		// DateJsonValueProcessor(Globarle.DEFAULT_DATE_FORMAT));
		if (null != filters && filters.length != 0) {
			config.setJsonPropertyFilter(new PropertyFilter() {
				public boolean apply(Object source, String name, Object value) {
					for (String field : filters) {
						if (name.equals(field)) {
							return true;
						}
						if (value instanceof Set && ((Set) value).size() == 0) {
							return true;
						}
						if (value instanceof Object[] && ((Object[]) value).length == 0) {
							return true;
						}
					}
					return false;
				}
			});
			jsonArray = JSONArray.fromObject(list, config);
		} else {
			jsonArray = JSONArray.fromObject(list, config);
		}
		return jsonArray.toString();
	}

	/**
	 * Object转换成Json字符�?
	 * 
	 * @param object
	 *            源对�?
	 * @param ignorFields
	 *            忽略属�?�数�?
	 * @return
	 */
	public static String objectToJson(Object object, final String[] ignorFields) {
		if (null == object) {
			return "";
		}
		JSONObject jsonObject = null;
		if (null != ignorFields && ignorFields.length != 0) {
			JsonConfig config = new JsonConfig();
			config.setJsonPropertyFilter(new PropertyFilter() {
				public boolean apply(Object source, String name, Object value) {
					for (String field : ignorFields) {
						if (name.equals(field)) {
							return true;
						}
						if (value instanceof Set && ((Set) value).size() == 0) {
							return true;
						}
						if (value.getClass().isArray() && ((Object[]) value).length == 0) {
							return true;
						}
					}
					return false;
				}
			});
			jsonObject = JSONObject.fromObject(object, config);
		} else {
			jsonObject = JSONObject.fromObject(object);
		}
		return jsonObject.toString();
	}

	/**
	 * 输出Json字符串到客户�?
	 * 
	 * @param jsonModel
	 *            json模型
	 * @param response
	 *            输出对象
	 */
	// public static void outputJsonString(, HttpServletResponse response)
	// throws IOException {
	// StringBuffer sb = new StringBuffer();
	// if (!model.isQueryAction()) {
	// sb.append("{").append(model.SUCCESS_FIELD).append(":").append(model.isSuccess()).append(",")
	// .append(model.MESSAGE_FIELD).append(":'").append(model.getMessage()).append("'}");
	// } else {
	// sb.append("{").append(model.TOTAL_FIELD).append(":").append(model.getTotalCount()).append(",")
	// .append(model.ROOT_FIELD).append(":").append(model.getJsonString()).append("}");
	// }
	// response.setCharacterEncoding("UTF-8");
	// response.setContentType("application/x-json");
	// // response.setContentType("text/html");
	// response.getWriter().write(sb.toString());
	// }
	// *************************************************************************************
	// *************************************************************************************
	// *************************************************************************************

	/**
	 * jquery-Treeview树形菜单json数据生成工具�? @描述�?
	 * 由于jquery_treeview插件�?要的json数据属�?�格式是固定的，无法用json-lib自动生成，除非实体类属�?�与之对�?
	 * 采用ajax异步加载子节点方式，初始阶段只加载顶级节�? 生成的json格式如下�? { "id":"1", //id "text":"aaa",
	 * //显示�? "value":"1", //提交�? "expanded":true, //是否展开 "hasChildren":true,
	 * //是否有子节点 "ChildNodes":[{},{}], //子节点集�? "showcheck":true, //是否显示checkbox
	 * "complete":false //是否已加载子节点 }
	 * 
	 * @author Lanxiaowei
	 * @createTime 2011-9-1 下午08:50:14
	 */

	private Map<String, Object> parseStr(String str) {
		Map<String, Object> map = new HashMap<String, Object>();
		for (String strPart : str.split(",")) {
			String[] ss = strPart.split(":");
			if (ss == null || ss.length != 2) {
				continue;
			}
			String key = ss[0];
			String value = ss[1].trim();
			if (value.startsWith("'") && value.endsWith("'")) {
				map.put(key, value.substring(1, value.length() - 1));
			} else if (value.startsWith("\"") && value.endsWith("\"")) {
				map.put(key, value.substring(1, value.length() - 1));
			} else if (value.equals("true") || value.equals("false")) {
				map.put(key, Boolean.valueOf(value));
			} else if (value.indexOf(".") == -1) {
				try {
					int val = Integer.parseInt(value);
					map.put(key, val);
				} catch (Exception e) {
					map.put(key, value);
				}
			} else {
				try {
					BigDecimal val = new BigDecimal(value);
					map.put(key, val);
				} catch (Exception e) {
					map.put(key, value);
				}
			}
		}
		return map;
	}

	// *************************************************************************************
	// *************************************************************************************
	// *************************************************************************************
	// *************************************************************************************
	// *************************************************************************************
	// *************************************************************************************
	// *************************************************************************************
	// *************************************************************************************
	// *************************************************************************************

}
