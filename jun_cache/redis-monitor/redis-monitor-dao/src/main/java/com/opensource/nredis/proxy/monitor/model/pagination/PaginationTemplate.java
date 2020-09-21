package com.opensource.nredis.proxy.monitor.model.pagination;


import java.util.List;
import com.opensource.nredis.proxy.monitor.dao.IPaginationDao;
/**
* 分页模板，用于Service进行分页查询
*
* @author liubing
* @date 2016/12/22 20:05
* @version v1.0.0
*/
public class PaginationTemplate<T> {

    /** MyBatisDao */
    private IPaginationDao<T> paginationDao;

    /**
    * 封闭无参构造器，必须持有Dao
    */
    public PaginationTemplate(IPaginationDao<T> paginationDao) {
        this.paginationDao = paginationDao;
    }

    /**
    * 分页查询数据方法
    * @param pageAttr 分页参数对象
    * @param queryObject 过滤条件对象
    * @param otherParam 其它过滤条件
    */
    public PageList<T> queryPageList(PageAttribute pageAttr, T queryObject) {

        if(null == pageAttr)
           pageAttr = PageAttribute.getInstance();

        //count记录数
        int count = paginationDao.count(queryObject);
        if (0 == count)
            return PageList.<T>getEmptyInstance();
         List<T> datas = paginationDao.queryPageList(pageAttr, queryObject);
          return PageList.getInstance(datas, Page.getInstance(pageAttr, count));
      }

     
}
