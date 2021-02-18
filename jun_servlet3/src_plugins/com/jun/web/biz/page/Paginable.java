/**   
  * @Title: Paginable.java 
  * @package com.sysmanage.common.tools.page 
  * @Description: 
  * @author zhangfeng 13940488705@163.com 
  * @date 2011-8-1 下午03:37:12 
  * @version V1.0   
  */

package com.jun.web.biz.page;

/** 
 * @ClassName: Paginable 
 * @Description: 分页接口
 * @author zhangfeng 13940488705@163.com
 * @date 2011-8-1 下午03:37:12 
 *  
 */
public interface Paginable {

	/** 
	  * @Title: getTotalCount 
	  * @Description: 总记录数 
	  * @param @return
	  * @return int
	  * @throws 
	  */
	public int getTotalCount();

	/** 
	  * @Title: getTotalPage 
	  * @Description: 总页数
	  * @param @return
	  * @return int
	  * @throws 
	  */
	public int getTotalPage();

	/** 
	  * @Title: getPageSize 
	  * @Description: 每页记录数 
	  * @param @return
	  * @return int
	  * @throws 
	  */
	public int getPageSize();

	/** 
	  * @Title: getPageNo 
	  * @Description: 当前页号
	  * @param @return
	  * @return int
	  * @throws 
	  */
	public int getPageNo();

	/** 
	  * @Title: isFirstPage 
	  * @Description: 是否第一页
	  * @param @return
	  * @return boolean
	  * @throws 
	  */
	public boolean isFirstPage();

	/** 
	  * @Title: isLastPage 
	  * @Description: 是否最后一页
	  * @param @return
	  * @return boolean
	  * @throws 
	  */
	public boolean isLastPage();

	/** 
	  * @Title: getNextPage 
	  * @Description: 返回下页的页号 
	  * @param @return
	  * @return int
	  * @throws 
	  */
	public int getNextPage();

	/** 
	  * @Title: getPrePage 
	  * @Description: 返回上页的页号 
	  * @param @return
	  * @return int
	  * @throws 
	  */
	public int getPrePage();
}
