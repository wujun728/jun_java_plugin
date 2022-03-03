package org.springrain.cms.directive;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfig;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.springrain.cms.util.DirectiveUtils;
import org.springrain.frame.util.SpringUtils;

import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

/**
 * 标签基类
 */
public abstract class AbstractCMSDirective implements
		TemplateDirectiveModel {
	
	/**
	 * 输入参数，内容ID
	 */
	public static final String PARAM_ID = "id";
	
	/**
	 * 输入参数，下一个
	 */
	public static final String PARAM_NEXT = "next";
	
	/**
	 * 主参数，排斥其他所有筛选参数
	 */
	public static final String PARAM_IDS = "ids";
	
	/**
	 * 默认的参数分隔符
	 */
	public static final String PARAM_SPLIT = ",";
	
	/**
	 * 输入参数，排序方式。
	 */
	public static final String PARAM_ORDER_BY = "orderBy";
	
	
	public Logger logger = LoggerFactory.getLogger(getClass());
	public String getReuestSiteId(){
		return getRequestAttributeString("siteId");
	}
	
	public String getReuestBusinessId(){
		return getRequestAttributeString("businessId");
	}
	/**
	 * 优先从 标签参数取值
	 * @param params
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public String getSiteId(Map params) throws TemplateException{
		return getParameter("siteId", params);
		}
	/**
	 * 优先从 标签参数取值
	 * @param params
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public String getBusinessId(Map params) throws TemplateException{
		return getParameter("businessId", params);
	}
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private String getParameter(String key,Map params) throws TemplateException{
			if(params==null){
				return getRequestAttributeString(key);
			}
			String value = DirectiveUtils.getString(key, params);
			
			if(value==null){
				return getRequestAttributeString(key);
			}
			return value;
		
	}
	
	public String getRequestAttributeString(String key){
		 Object attribute = getRequest().getAttribute(key);
		 if(attribute==null){
			 return null;
		 }
		 return attribute.toString();
		 
	}
	
	
	public HttpServletRequest getRequest(){
		return ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
	}
	
	
	public FreeMarkerConfig getFreeMarkerConfig(){
		return (FreeMarkerConfigurer) SpringUtils.getBean("freeMarkerConfigurer");
	}
	
	public void  setFreeMarkerSharedVariable(String key,TemplateModel model){
		FreeMarkerConfigurer freeMarkerConfigurer=(FreeMarkerConfigurer) SpringUtils.getBean("freeMarkerConfigurer");
		freeMarkerConfigurer.getConfiguration().setSharedVariable(key, model);
	}
	
	
	public Object getDirectiveData(String key){
		return getRequest().getAttribute(key);
	}
	
	public void setDirectiveData(String key,Object obj){
		 getRequest().setAttribute(key,obj);
	}
	
	/**
	 * 获取排序规则，以数字标识
	 * @param params
	 * @return
	 * @throws TemplateException
	 */
	public static int getOrderBy(Map<String, TemplateModel> params)
			throws TemplateException {
		Integer orderBy = DirectiveUtils.getInt(PARAM_ORDER_BY, params);
		if (orderBy == null) {
			return 0;
		} else {
			return orderBy;
		}
	}
	
	
}
