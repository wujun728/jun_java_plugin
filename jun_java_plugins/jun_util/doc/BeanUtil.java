package org.myframework.util;

import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * ( MAP ,JSONSTRING ,XML )-- > POJO
 * 
 * @author Administrator
 * 
 */
public class BeanUtil {
	private static Log log = LogFactory.getLog(BeanUtil.class);

	/**
	 * Map的相关属性复制到POJO
	 * 
	 * @param model
	 * @param srcMap
	 */
	public static void copyProperties(Object model, Map<String, Object> srcMap) {
		long startTime = System.currentTimeMillis();
		try {
			PropertyUtils.copyProperties(model, srcMap);
		} catch (Exception e) {
			try {
				BeanUtils.copyProperties(model, srcMap);
			} catch (Exception e1) {

			}
		} finally {
			long endTime = System.currentTimeMillis();
			log.debug("cost time " + (endTime - startTime));
		}
	}

	/**
	 * jsonObject的相关属性复制到POJO
	 * 
	 * @param model
	 * @param jsonObject
	 */
	public static void copyProperties(Object model, JSONObject jsonObject) {
		Map<String, Object> srcMap = MapUtil.getMapFromJSONObject(jsonObject);
		BeanUtil.copyProperties(model, srcMap);
	}

	/**
	 * jsonString的相关属性复制到POJO
	 * 
	 * @param model
	 * @param jsonObject
	 */
	public static void copyProperties(Object model, String jsonString) {
		Map<String, Object> srcMap = MapUtil.getMapFromJSONString(jsonString);
		BeanUtil.copyProperties(model, srcMap);
	}
	
	/**
	 * 将 XML字符串  转化为 POJO
	 * @param pojo
	 * @return
	 */
	public static Object getPojoFromXml(String xmlStr) {
		XStream xstream = new XStream(new DomDriver());
		return xstream.fromXML(xmlStr);
	}
	

}
