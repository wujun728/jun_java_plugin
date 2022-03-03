/**   
  * @Title: E3ClassPathXmlApplicationContext.java 
  * @package com.sysmanage.common.tools.core 
  * @Description: 
  * @author Wujun
  * @date 2011-8-9 上午11:02:30 
  * @version V1.0   
  */

package com.jun.plugin.poi;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.util.Assert;

/** 
 * @ClassName: ClassPathXmlApplicationContext 
 * @Description: xml应用加载
 * @author Wujun
 * @date 2011-8-9 上午11:02:30 
 *  
 */
public class TgClassPathXmlApplicationContext extends ClassPathXmlApplicationContext{

	public TgClassPathXmlApplicationContext(String arg0) throws BeansException {
		super(arg0);
	}

	public TgClassPathXmlApplicationContext(String[] arg0, ApplicationContext arg1) throws BeansException {
		super(arg0, arg1);
	}

	public TgClassPathXmlApplicationContext(String[] arg0, boolean arg1, ApplicationContext arg2) throws BeansException {
		super(arg0, arg1, arg2);
	}

	public TgClassPathXmlApplicationContext(String[] arg0, boolean arg1) throws BeansException {
		super(arg0, arg1);
	}

	public TgClassPathXmlApplicationContext(String[] arg0) throws BeansException {
		super(arg0);
	}
	
	public Resource getResource(String location) {
		Assert.notNull(location, "location is required");
		TgResourceLoader resourceLoader = new TgResourceLoader();
		return resourceLoader.getResource(location);
   }
}

