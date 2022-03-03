package com.jun.common.page;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;

import org.apache.commons.beanutils.PropertyUtils;

/**
 * 自定义标签工具类
 * @author Wujun
 * @createTime   2011-11-29 上午11:55:39
 */
public class TagUtils {
	private static final TagUtils instance = new TagUtils();
    private static final Map scopes;
    
	protected TagUtils(){}
	
	public static TagUtils getInstance(){
        return instance;
    }
	static{
        scopes = new HashMap();
        scopes.put("page", new Integer(1));
        scopes.put("request", new Integer(2));
        scopes.put("session", new Integer(3));
        scopes.put("application", new Integer(4));
    }
	
	/**
	 * 获取作用域对象的代号
	 */
	public int getScope(String scopeName) throws JspException{
		Integer scope = (Integer)scopes.get(scopeName.toLowerCase());
		if(scope == null){
			throw new JspException("lookup scopeName exception");
		}
		else{
			return scope.intValue();
		}
	}
	
	/**
	 * 查找指定作用域内的对象
	 * @param pageContext   页面上下文
	 * @param name          对象名
	 * @param scopeName     作用域名称
	 */
	public Object lookup(PageContext pageContext, String name, String scopeName){
	    if(scopeName == null){
	        return pageContext.findAttribute(name);
	    }
	    try{
	        return pageContext.getAttribute(name, instance.getScope(scopeName));
	    }
	    catch(JspException e){
	        throw new RuntimeException("Don't find "+ name + "in the" + scopeName);
	    }
	}
	
	/**
	 * 查找指定作用域内bean的属性值
	 * @param pageContext   页面上下文
	 * @param name          对象名
	 * @param name          对象属性名
	 * @param scope         作用域名称
	 */
	public Object lookup(PageContext pageContext, String name, String property, String scope){
		Object bean = lookup(pageContext, name, scope);
		if(bean == null){
	        if(scope == null){
	        	throw new RuntimeException("Scope not found!");
	        }
	        else{
	        	throw new RuntimeException(name + "bean not found " + "in the " + scope);
	        }
		}
	    if(property == null){
	        return bean;
	    }
	    try
	    {
	        return PropertyUtils.getProperty(bean, property);
	    } catch(IllegalAccessException e)
	    {
	    	throw new RuntimeException("IllegalAccessException when find the property of bean!");
	    } catch(IllegalArgumentException e)
	    {
	    	throw new RuntimeException("IllegalArgumentException when find the property of bean!");
	    } catch(InvocationTargetException e)
	    {
	    	throw new RuntimeException("InvocationTargetException when find the property of bean!");
	    } catch(NoSuchMethodException e)
	    {
	    	throw new RuntimeException("NoSuchMethodException when find the property of bean!");
	    }
	}
	/**
	 * 获取用户本地语言环境
	 * @param  pageContext   页面上下文
	 * @param  locale        本地语言环境key
	 * 
	 */
	public Locale getUserLocale(PageContext pageContext, String locale){
		Locale userLocale = null;
		HttpServletRequest request = (HttpServletRequest)pageContext.getRequest();
        HttpSession session = request.getSession(false);
        if(locale == null || locale.length() == 0){
        	throw new RuntimeException("The param of local can't be null!");
        }
        if(session != null){
            userLocale = (Locale)session.getAttribute(locale);
        }
        if(userLocale == null){
            userLocale = request.getLocale();
        }
        return userLocale;
    }
	
	/**
	 * 输出内容到页面
	 * @param pageContext  页面上下文
	 * @param text         输出内容
	 */
	public void write(PageContext pageContext, String text){
	    JspWriter writer = pageContext.getOut();
	    try{
	        writer.print(text);
	    } catch(IOException e){
	    	throw new RuntimeException("Write IO Exception");
	    } 
	}
}
