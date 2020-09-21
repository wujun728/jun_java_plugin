package cn.skynethome.redisx.common;

/**
  * 项目名称:[redisx]
  * 包:[cn.skynethome.redisx.common]    
  * 文件名称:[ExpirationDate]  
  * 描述:[一句话描述该文件的作用]
  * 创建人:[陆文斌]
  * 创建时间:[2017年1月4日 下午3:45:00]   
  * 修改人:[陆文斌]   
  * 修改时间:[2017年1月4日 下午3:45:00]   
  * 修改备注:[说明本次修改内容]  
  * 版权所有:luwenbin006@163.com
  * 版本:[v1.0]
 */
public class ExpirationDate 
{
	 /** 
     * redis过期时间,以秒为单位 
     */
    public final  int EXRP_HOUR = 60 * 60; //一小时  
    public final  int EXRP_DAY = 60 * 60 * 24; //一天  
    public final  int EXRP_WEEK = 60 * 60 * 24 * 7; //周 
    public final  int EXRP_MONTH = 60 * 60 * 24 * 30; //一个月  
    public final  int EXRP_YEAR = 60 * 60 * 24 * 365; //一年

}
