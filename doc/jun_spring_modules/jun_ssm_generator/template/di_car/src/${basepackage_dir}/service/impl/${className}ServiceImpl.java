<#assign myParentDir="service.impl">
<#assign className = table.className>   
<#assign classNameLower = className?uncap_first>  
package ${basepackage}.service.impl;

import java.io.File;
import java.util.List;
import org.springframework.stereotype.Service;
import ${basepackage}.entity.${className};
import ${basepackage}.service.I${className}Service;
import org.springrain.frame.entity.IBaseEntity;
import org.springrain.frame.util.Finder;
import org.springrain.frame.util.Page;
import org.springrain.system.service.BaseSpringrainServiceImpl;


<#include "/copyright_class.include" >
@Service("${classNameLower}Service")
public class ${className}ServiceImpl extends BaseSpringrainServiceImpl implements I${className}Service {

   
    @Override
	public String  save(Object entity ) throws Exception{
	      ${className} ${classNameLower}=(${className}) entity;
	       return super.save(${classNameLower}).toString();
	}

    @Override
	public String  saveorupdate(Object entity ) throws Exception{
	      ${className} ${classNameLower}=(${className}) entity;
		 return super.saveorupdate(${classNameLower}).toString();
	}
	
	@Override
    public Integer update(IBaseEntity entity ) throws Exception{
	 ${className} ${classNameLower}=(${className}) entity;
	return super.update(${classNameLower});
    }
    @Override
	public ${className} find${className}ById(Object id) throws Exception{
	 return super.findById(id,${className}.class);
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
