<#import "base/date.ftl" as dt>
package ${conf.servicePackage}.impl<#if table.prefix!="">.${table.prefix}</#if>;

<#assign beanName = table.beanName/>
<#assign beanNameUncap_first = beanName?uncap_first/>
import com.github.wujun728.common.db.SqlPageDoing;
import com.github.wujun728.common.enums.Whether;
import com.github.wujun728.common.web.PageResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;
import ${conf.entityPackage}<#if table.prefix!="">.${table.prefix}</#if>.${beanName};
import ${conf.servicePackage}<#if table.prefix!="">.${table.prefix}</#if>.${beanName}Service;
import ${conf.daoPackage}<#if table.prefix!="">.${table.prefix}</#if>.${beanName}Mapper;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;


/**
 * Copyright (C), 2017-2020, cn.zlinks
 * FileName: ${beanName}ServiceImpl
 * Author:   Wujun
 * Date:     ${.now}
 * Description:${beanName}Service接口
 */
@Service
public class ${beanName}ServiceImpl implements ${beanName}Service {

	private static Logger logger = LoggerFactory.getLogger(${beanName}ServiceImpl.class);


	@Autowired
	private ${beanName}Mapper ${beanNameUncap_first}Mapper;

	@Override
	public ${beanName} queryInfoById(Long id) {
		return ${beanNameUncap_first}Mapper.queryInfoById(id);
	}

	@Override
	public int getListCount(${beanName} entity) {
		return ${beanNameUncap_first}Mapper.selectCountByCondition(entity);
	}

	@Override
	public List<${beanName}> selectByCondition(${beanName} entity) {
		List<${beanName}> resut = null;
		resut = ${beanNameUncap_first}Mapper.selectByCondition(entity);
		return resut;
	}

	@Override
	public List<${beanName}> getList() {
		List<${beanName}> resut = null;
		resut = ${beanNameUncap_first}Mapper.getList();
		return resut;
	}

	@Override
    @Transactional(rollbackFor = Exception.class)
	public int update(${beanName} entity) {
		return ${beanNameUncap_first}Mapper.updateById(entity);
	}

	@Override
    @Transactional(rollbackFor = Exception.class)
	public int deleteById(int id) {
		return ${beanNameUncap_first}Mapper.deleteById(id);
	}

	@Override
    @Transactional(rollbackFor = Exception.class)
	public int add(${beanName} entity) {
		return ${beanNameUncap_first}Mapper.insert(entity);
	}

	@Override
    @Transactional(rollbackFor = Exception.class)
	public int addList(List<${beanName}> entityList) {
		return ${beanNameUncap_first}Mapper.insertList(entityList);
	}

	@SuppressWarnings("unchecked")
	@Override
    public PageResult<${beanName}> findPage(${beanName} pageInfo){
  		SqlPageDoing doing = pageResult -> {
            //查记录数
            Integer totalCount = ${beanNameUncap_first}Mapper.selectCountByCondition(pageInfo);

            List<${beanName}> list = ${beanNameUncap_first}Mapper.selectByCondition(pageInfo);

   		 	pageResult.list = list;
    		pageResult.totalCount = totalCount;
    	};
    	return doing.go(pageInfo, logger);
	}

	@Override
	public List<${beanName}> getActivedList() {
		List<${beanName}> resut = null;
		${beanName} entity = new ${beanName}();
        entity.setDeleteFlag(Whether.NO);
		resut = ${beanNameUncap_first}Mapper.selectByCondition(entity);
		return resut;
	}
}