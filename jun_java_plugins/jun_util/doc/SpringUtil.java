package org.myframework.util;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.util.ClassUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * 完成SPRING 的BEAN获取和 SPRING 容器的刷新
 * @author Administrator
 *
 */
public class SpringUtil {
	private static Log log = LogFactory.getLog(SpringUtil.class);
	
    /**
     * 取得SpringBean实例
     * @param request
     * @param beanId
     * @return
     */
    public static Object getBean(HttpServletRequest request, String beanId) {
        return getWebApplicationContext(request).getBean(beanId);
    }

	/**
	 * 刷新WEB中SPRING的信息
	 * @param request
	 */
	public static void refreshSpring(HttpServletRequest request) {
		WebApplicationContext wac = getWebApplicationContext(request);
		refreshSpring(wac);
	}
	
	/**
	 * 刷新SPRING的信息
	 * @param aContext
	 */
	public static void refreshSpring(ApplicationContext aContext) {
		Class[] cls = ClassUtils.getAllInterfaces(aContext);
		for (Class item : cls) {
			if (ConfigurableApplicationContext.class.getName().equals(
					item.getName())) {
				log.debug("refresh   Spring start ");
				long start = System.currentTimeMillis();
				((ConfigurableApplicationContext) aContext).refresh();
				log.debug("refresh   Spring end ; cost time (ms) :"
						+ (System.currentTimeMillis() - start));
			}
		}
	}

	/**
	 * 取得WebApplicationContext
	 * @param sc
	 * @return
	 */
	public static WebApplicationContext getWebApplicationContext(
			ServletContext sc) {
		// 通过web.xml 配置 org.springframework.web.context.ContextLoaderListener 加载。
		WebApplicationContext ctx = WebApplicationContextUtils
				.getRequiredWebApplicationContext(sc);
		if (ctx == null) {
				throw new RuntimeException(
						"WebApplicationContext error ........  Can't find [WebApplicationContext] in any scope !");
		}
		return ctx;
	}

	/**
	 * 取得WebApplicationContext
	 * @param request
	 * @return
	 */
	public static WebApplicationContext getWebApplicationContext(
			HttpServletRequest request) {
		ServletContext sc = request.getSession().getServletContext();
		return getWebApplicationContext(sc);
	}

}
