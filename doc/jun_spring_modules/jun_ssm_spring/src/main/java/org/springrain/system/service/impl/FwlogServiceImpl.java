package org.springrain.system.service.impl;

import java.io.File;
import java.util.List;

import org.springframework.stereotype.Service;

import org.springrain.frame.entity.IBaseEntity;
import org.springrain.frame.util.Finder;
import org.springrain.frame.util.Page;
import org.springrain.system.entity.Fwlog;
import org.springrain.system.service.BaseSpringrainServiceImpl;
import org.springrain.system.service.IFwlogService;


/**
 * TODO 在此加入类描述
 * @copyright {@link springrain}
 * @author weicms.net<Auto generate>
 * @version  2013-07-29 11:36:44
 * @see org.springrain.springrain.service.impl.Fwlog
 */
@Service("fwlogService")
public class FwlogServiceImpl extends BaseSpringrainServiceImpl implements IFwlogService {

   
    @Override
	public String  save(Object entity ) throws Exception{
	      Fwlog fwlog=(Fwlog) entity;
	       return (String) super.save(fwlog);
	}

    @Override
	public String  saveorupdate(Object entity ) throws Exception{
	      Fwlog fwlog=(Fwlog) entity;
		 return super.saveorupdate(fwlog).toString();
	}
	
	@Override
    public Integer update(IBaseEntity entity ) throws Exception{
	 Fwlog fwlog=(Fwlog) entity;
	return super.update(fwlog);
    }
    @Override
	public Fwlog findFwlogById(Object id) throws Exception{

    	if(id==null){
    		return null;
    	}
    	Fwlog fwLog=new Fwlog();
    	fwLog.setId(id.toString());
    	/*
     	//使用finder 构建查询语句
        Finder finder=new Finder("SELECT * FROM ");
        //确定年度分表,实际可以根据ID的前四位确定年份,例如,我的Id前四位是2013 就是2013年的数据
        finder.append("t_fwlog").append(fwLog.getExt());
        //添加where 条件
        finder.append(" WHERE id=:id");
        //设置参数值
        finder.setParam("id", id.toString());
	    return super.queryForObject(finder, Fwlog.class);
	 */
    	
    	return super.queryForObject(fwLog);
	 
	}
	
/**
 * 列表查询,每个service都会重载,要把sql语句封装到service中,Finder只是最后的方案
 * @param finder
 * @param page
 * @param clazz
 * @param o
 * @return
 * @throws Exception
 */
        @Override
    public <T> List<T> findListDataByFinder(Finder finder, Page page, Class<T> clazz,
			Object o) throws Exception{
			 return super.findListDataByFinder(finder,page,clazz,o);
			}
	/**
	 * 根据查询列表的宏,导出Excel
	 * @param finder 为空则只查询 clazz表
	 * @param ftlurl 类表的模版宏
	 * @param page 分页对象
	 * @param clazz 要查询的对象
	 * @param o  querybean
	 * @return
	 * @throws Exception
	 */
		@Override
	public <T> File findDataExportExcel(Finder finder,String ftlurl, Page page,
			Class<T> clazz, Object o)
			throws Exception {
			 return super.findDataExportExcel(finder,ftlurl,page,clazz,o);
		}

}
