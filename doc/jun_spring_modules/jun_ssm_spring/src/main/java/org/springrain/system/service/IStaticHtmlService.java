package org.springrain.system.service;

/**
 * TODO 在此加入类描述
 * @copyright {@link springrain}
 * @author weicms.net<Auto generate>
 * @version  2013-07-29 11:36:44
 * @see org.springrain.springrain.service.Fwlog
 */
public interface IStaticHtmlService extends IBaseSpringrainService {
	

     /**
      * 根据Uri 查询对应的静态文件路径地址, URI-->cms_link-->是否静态-->返回静态文件路径
      * @param uri
      * @return
      * @throws Exception
      */
	String findHtmlPathByURI(String siteId,String uri) throws Exception;
	
	
	
	
}
