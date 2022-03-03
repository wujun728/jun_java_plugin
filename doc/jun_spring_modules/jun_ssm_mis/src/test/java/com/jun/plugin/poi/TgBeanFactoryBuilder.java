/**   
  * @Title: E3BeanFactoryBuilder.java 
  * @package com.sysmanage.common.tools.core 
  * @Description: 
  * @author Wujun
  * @date 2011-8-9 上午11:01:42 
  * @version V1.0   
  */

package com.jun.plugin.poi;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;

/** 
 * @ClassName: BeanFactoryBuilder 
 * @Description: 设置beanfactory的初始化 
 * @author Wujun
 * @date 2011-8-9 上午11:01:42 
 *  
 */
public class TgBeanFactoryBuilder {
	
	 private TgBeanFactoryBuilder(){
   	  
     }
     
	  public static BeanFactory build(String[] pBeansConfigFiles)throws BeansException{
		  return new TgClassPathXmlApplicationContext(pBeansConfigFiles);
	  }
	   
	  public static BeanFactory build(String pBeansConfigFile)throws BeansException{
		    return new TgClassPathXmlApplicationContext(new String[]{pBeansConfigFile});
	  }
}
