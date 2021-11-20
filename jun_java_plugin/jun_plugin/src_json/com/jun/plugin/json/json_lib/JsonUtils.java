package com.jun.plugin.json.json_lib;


import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;


import net.sf.json.JSONArray;

/**
 * jquery-Treeview树形菜单json数据生成工具�?
 * @描述�?
 *       由于jquery_treeview插件�?要的json数据属�?�格式是固定的，无法用json-lib自动生成，除非实体类属�?�与之对�?
 *       采用ajax异步加载子节点方式，初始阶段只加载顶级节�?
 *       生成的json格式如下�?
 *                       {
 *                       "id":"1",           //id
 *                       "text":"aaa",       //显示�?
 *                       "value":"1",        //提交�?
 *                       "expanded":true,     //是否展开
 *                       "hasChildren":true,   //是否有子节点
 *                       "ChildNodes":[{},{}], //子节点集�?
 *                       "showcheck":true,  //是否显示checkbox
 *                       "complete":false   //是否已加载子节点
 *                       } 
 * @author       Lanxiaowei
 * @createTime   2011-9-1 下午08:50:14
 */
public class JsonUtils {
	private static String top = "{";
	private static String top_ = "}";

//	public JsonUtil() {}

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
				if (i > 0) data.append(",");
				data.append((new StringBuilder()).append("\"").append(key)
						.append("\"").append(":").toString());
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
			data.append((new StringBuilder()).append("{\"totalCount\":")
					.append(dList.size()).append(", \"Body\":").toString());
			data.append(getJsonByList(dList));
			data.append("}");
		}
		return data.toString();
	}

	public static String getGridJsonData(int rowNumber, List dList) {
		StringBuffer data = new StringBuffer();
		if (dList != null) {
			data.append((new StringBuilder()).append("{\"totalCount\":")
					.append(rowNumber).append(", \"Body\":").toString());
			data.append(getJsonByList(dList));
			data.append("}");
		}
		return data.toString();
	}

	public static String getDefineJsonData(String firstParam, String listParam,
			String firstValue, List dList) {
		StringBuffer data = new StringBuffer();
		if (dList != null) {
			data.append((new StringBuilder()).append("{\"").append(firstParam)
					.append("\":").append(firstValue).append(", \"")
					.append(listParam).append("\":").toString());
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
				String total = String.valueOf(((Map) dList.get(0))
						.get("IRECCOUNT"));
				data.append((new StringBuilder()).append("{\"total\": ")
						.append(total).append(", \"rows\": ")
						.append(getJsonByList(dList)).append("}").toString());
			} else {
				data.append("{\"total\": 0, \"rows\": []}");
			}
		System.out.println("json=" + data.toString());
		return data.toString();
	}

	public static String getEasyUIJsonDataV2(List dList, String total) {
		StringBuffer data = new StringBuffer();
		if (dList != null) if (dList.size() > 0) {
			// String total =
			// StringUtil.toString(((Map)dList.get(0)).get("IRECCOUNT"));
			data.append((new StringBuilder()).append("{\"total\": ")
					.append(total).append(", \"rows\": ")
					.append(getJsonByList(dList)).append("}").toString());
		} else {
			data.append("{\"total\": 0, \"rows\": []}");
		}
		System.out.println("total=" + total + "json=" + data.toString());
		return data.toString();
	}

	public static String getJsonDataByObject(Object obj) {
		StringBuffer data = new StringBuffer();
		if (obj != null) data.append(JSONArray.fromObject(obj).toString());
		return data.toString();
	}
	
	
	
	
	
	
	
	
	
	
	

    private String json;
    
    public JsonUtils(){
        json = "{}";
    }
    
    public JsonUtils(Map<String,Object> map){
        json = JsonUtils.toJson(map);
    }
    
    public JsonUtils(List<?> list){
        json = JsonUtils.toJson(list);
    }
    
    public JsonUtils(Object[] objects){
        json = JsonUtils.toJson(objects);
    }
    
    public JsonUtils(Object obj){
        json = JsonUtils.toJson(obj);
    }
    
    /**
     * 用字符串构�?�JSON视图
     * @param str 字符串表示的JSON表达式，�?"success:true,age:32,salary:2000.50,name:'名称'"
     */
    public JsonUtils(String str){
        Map<String,Object> map = parseStr(str);
        json = JsonUtils.toJson(map);
    }
    
    @Override
    public String toString(){
        return json;
    }
    
    private Map<String,Object> parseStr(String str){
        Map<String,Object> map = new HashMap<String,Object>();
        for(String strPart: str.split(",")){
            String[] ss = strPart.split(":");
            if (ss == null || ss.length != 2){
                continue;
            }
            String key = ss[0];
            String value = ss[1].trim();
            if (value.startsWith("'") && value.endsWith("'")){
                map.put(key, value.substring(1, value.length()-1));
            }
            else if (value.startsWith("\"") && value.endsWith("\"")){
                map.put(key, value.substring(1, value.length()-1));
            }
            else if (value.equals("true") || value.equals("false")){
                map.put(key, Boolean.valueOf(value));
            }
            else if (value.indexOf(".") == -1){
                try{
                    int val = Integer.parseInt(value);
                    map.put(key, val);
                }
                catch(Exception e){
                    map.put(key, value);
                }
            }
            else{
                try{
                    BigDecimal val = new BigDecimal(value);
                    map.put(key, val);
                }
                catch(Exception e){
                    map.put(key, value);
                }
            }
        }
        return map;
    }
    
    
    
    

	public static String toJson(Object obj) {
		String s = castToJson(obj);
		if (s != null) {
			return s;
		} else {
			return toJson(getAttributes(obj));
		}
	}

	public static String toJson(Map<String, Object> map) {
		String result = "";
		for (String name : map.keySet()) {
			Object value = map.get(name);
			String s = castToJson(value);
			if (s != null) {
				result += "\"" + name + "\":" + s + ",";
			} else if (value instanceof List<?>) {
				String v = toJson((List<?>) value);
				result += "\"" + name + "\":" + v + ",";
			} else if (value instanceof Object[]) {
				String v = toJson((Object[]) value);
				result += "\"" + name + "\":" + v + ",";
			} else if (value instanceof Map<?, ?>) {
				Map<String, Object> attr = castMap((Map<?, ?>) value);
				attr = removeListAttr(attr);
				result += "\"" + name + "\":" + JsonUtils.toJson(attr) + ",";
			} else if (value.getClass().getName().startsWith("java") == false) {
				Map<String, Object> attr = getAttributes(value);
				attr = removeListAttr(attr);
				result += "\"" + name + "\":" + JsonUtils.toJson(attr) + ",";
			} else {
				result += "\"" + name + "\":" + "\"" + value.toString() + "\",";
			}
		}
		if (result.length() == 0) {
			return "{}";
		} else {
			return "{" + result.substring(0, result.length() - 1) + "}";
		}
	}

	public static String toJson(Object[] aa) {
		if (aa.length == 0) {
			return "[]";
		} else {
			String result = "";
			for (Object obj : aa) {
				String s = castToJson(obj);
				if (s != null) {
					result += s + ",";
				} else if (obj instanceof Map<?, ?>) {
					Map<String, Object> map = castMap((Map<?, ?>) obj);
					map = removeListAttr(map);
					result += toJson(map) + ",";
				} else {
					Map<String, Object> attr = getAttributes(obj);
					attr = removeListAttr(attr);
					result += toJson(attr) + ",";
				}
			}
			return "[" + result.substring(0, result.length() - 1) + "]";
		}
	}

	public static String toJson(List<?> ll) {
		return toJson(ll.toArray());
	}

	/**
	 * 取得对象的属�?
	 * 
	 * @param obj
	 * @return 对象属�?�表
	 */
	public static Map<String, Object> getAttributes(Object obj) {
		Class<?> c = obj.getClass();
		try {
			Method method = c.getMethod("isProxy");
			Boolean result = (Boolean) method.invoke(obj);
			if (result == true) {
				c = c.getSuperclass();
			}
		} catch (Exception e) {
		}
		Map<String, Object> attr = new HashMap<String, Object>();

		// 取得�?有公共字�?
		for (Field f : c.getFields()) {
			try {
				Object value = f.get(obj);
				attr.put(f.getName(), value);
			} catch (Exception e) {
			}
		}

		// 取得�?有本类方�?
		for (Method m : c.getDeclaredMethods()) {
			String mname = m.getName();
			if (mname.equals("getClass")) {
				continue;
			} else if (mname.startsWith("get")) {
				String pname = mname.substring(3);
				if (pname.length() == 1) {
					pname = pname.toLowerCase();
				} else {
					pname = pname.substring(0, 1).toLowerCase()
							+ pname.substring(1);
				}

				try {
					Object value = m.invoke(obj);
					attr.put(pname, value);
				} catch (Exception e) {
				}
			} else if (mname.startsWith("is")) {
				String pname = mname.substring(2);
				if (pname.length() == 1) {
					pname = pname.toLowerCase();
				} else {
					pname = pname.substring(0, 1).toLowerCase()
							+ pname.substring(1);
				}

				try {
					Object value = m.invoke(obj);
					attr.put(pname, value);
				} catch (Exception e) {
				}
			}
		}
		return attr;
	}

	/**
	 * 将简单对象转换成JSON�?
	 * 
	 * @param obj
	 * @return 如果是简单对象则返回JSON，如果是复杂对象则返回null
	 */
	private static String castToJson(Object obj) {
		if (obj == null) {
			return "null";
		} else if (obj instanceof Boolean) {
			return obj.toString();
		} else if (obj instanceof Integer || obj instanceof Long
				|| obj instanceof Float || obj instanceof Double
				|| obj instanceof Short || obj instanceof java.math.BigInteger
				|| obj instanceof java.math.BigDecimal) {
			return obj.toString();
		} else if (obj instanceof String) {
			String v = (String) obj;
			v = v.replaceAll("\\\\", "\\\\\\\\");
			v = v.replaceAll("\n", "\\\\n");
			v = v.replaceAll("\r", "\\\\r");
			v = v.replaceAll("\t", "\\\\t");
			v = v.replaceAll("\"", "\\\\\"");
//			v = v.replaceAll("'", "\\\\\'");
			return "\"" + v + "\"";
		} else if (obj instanceof java.sql.Date) {
			java.text.SimpleDateFormat df = new java.text.SimpleDateFormat(
					"yyyy-MM-dd");
			java.sql.Date v = (java.sql.Date) obj;
			String s = df.format(new java.util.Date(v.getTime()));
			return "\"" + s + "\"";
		} else if (obj instanceof java.sql.Timestamp) {
			java.text.SimpleDateFormat df = new java.text.SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
			java.sql.Timestamp v = (java.sql.Timestamp) obj;
			String s = df.format(new java.util.Date(v.getTime()));
			return "\"" + s + "\"";
		} else if (obj instanceof java.util.Date) {
			java.text.SimpleDateFormat df = new java.text.SimpleDateFormat(
			"yyyy-MM-dd");
			java.util.Date v = (java.util.Date) obj;
			String s = df.format(v);
			return "\"" + s + "\"";
		} else {
			return null;
		}

	}

	public static Map<String, Object> castMap(Map<?, ?> map) {
		Map<String, Object> newMap = new HashMap<String, Object>();
		for (Object key : map.keySet()) {
			newMap.put(key.toString(), map.get(key));
		}
		return newMap;
	}

	/**
	 * 删除属�?�中类型是List的属�?
	 * 
	 * @param map
	 * @return
	 */
	private static Map<String, Object> removeListAttr(Map<String, Object> map) {
		Map<String, Object> newMap = new HashMap<String, Object>();
		for (Entry<String, Object> en : map.entrySet()) {
			if (!(en.getValue() instanceof List<?>)) {
				newMap.put((String) en.getKey(), en.getValue());
			}
		}
		return newMap;
	}
	/**
	 * 为菜单名称添加超链接
	 * @描述：便于为菜单添加执行动作�?
	 *       如点击后执行某个action并在target指定目标内显示执行返回的结果
	 * @param name 菜单名称
	 * @param url 链接地址
	 * @param target 链接目标
	 */
	public static String getLinkName(String name,String url,String target){
		StringBuffer buffer = new StringBuffer();
		if(null == url || url.trim().equals("")){
			return name;
		}
		buffer.append("<a href='").append(url).append("'");
		if(null != target && !target.trim().equals("")){
			buffer.append(" target='").append(target).append("'");
		}
		buffer.append(">").append(name).append("</a>");
		return buffer.toString();
	}
	
	

	/** 
	  * @Title: format 
	  * @Description: 该方法给集合转变成的JSON串中的各个属性添加变量名 
	  * @param @param varName 要添加的变量�?
	  * @param @param list 要添加的集合
	  * @param @param excepts 哪些字段不需要添加变量名
	  * @param @return
	  * @param @throws Exception
	  * @return String
	  * @throws 
	  */
	public static String format(String varName, List list,String excepts) throws Exception{
		StringBuffer result = new StringBuffer("[");
		for(Object obj:list){
			Field[] fields = obj.getClass().getDeclaredFields();
			Field.setAccessible(fields, true);
			result.append("{");
			for(Field att:fields){
				Object o = att.get(obj);
				String value = JsonUtils.toJson(o);
				if(value!=null && value.indexOf("{")==-1 ){
					boolean flag = false;
					if(null!=excepts){
						for(String ex:excepts.split(";")){
							if(att.getName().equals(ex)){
								flag = true;
								break;
							}
						}
					}
					if(!flag){
						result.append("\""+varName+"."+att.getName()+"\":");
					}else{
						result.append("\""+att.getName()+"\":");
					}
					result.append(value);
				}else{
					result.append("\""+att.getName()+"\":");
//					JSONArray json = JSONArray.fromObject(o);
//					String str = json.toString();
					if(value.indexOf("[")==0){
						value=value.substring(1, value.length()-1);
					}
					result.append(value);
				}
				result.append(",");
			}
			result.deleteCharAt(result.toString().length()-1);
			result.append("},");
		}
		result.deleteCharAt(result.toString().length()-1).append("]");
		return result.toString();
	}
	
	
	
	
	public static void main(String[] args) {
		//System.out.println(getLinkName("权限管理","resourceAction!findResources.do","leftFrame"));
		
		/*Resource resource = new Resource();
		resource.setId(1l);
		resource.setName("用户管理");
		resource.setLeaf(false);
		System.out.println(objectToJsonString(resource));*/
		
		/*Resource resource1 = new Resource();
		resource1.setId(1l);
		resource1.setName("用户管理");
		resource1.setLeaf(false);
		Resource resource2 = new Resource();
		resource2.setId(2l);
		resource2.setName("角色管理");
		resource2.setLeaf(false);
		Resource resource3 = new Resource();
		resource3.setId(3l);
		resource3.setName("权限管理");
		resource3.setLeaf(false);
		List<Resource> children = new ArrayList<Resource>();
		children.add(resource1);
		children.add(resource2);
		children.add(resource3);
		System.out.println(gernerateTreeChildJsonString(children));*/
	}
}
