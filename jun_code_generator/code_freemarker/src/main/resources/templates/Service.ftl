package ${conf.servicePackage}<#if table.prefix!="">.${table.prefix}</#if>;

<#assign beanName = table.beanName/>
import java.util.List;
import java.util.Map;

import com.github.wujun728.common.web.PageResult;
import ${conf.entityPackage}<#if table.prefix!="">.${table.prefix}</#if>.${beanName};

/**
 * Copyright (C), 2017-2020, cn.zlinks
 * FileName: ${beanName}Service
 * Author:   Wujun
 * Date:     ${.now}
 * Description:${beanName}Service接口
 */
public interface ${beanName}Service {


	${beanName} queryInfoById(Long id);

	int getListCount(${beanName} entity);

	public List<${beanName}> selectByCondition(${beanName} entity) ;

	public List<${beanName}> getList();

	int update(${beanName} entity);

	int deleteById(int id);

	int add(${beanName} entity);

	int addList(List<${beanName}> entityList);

    PageResult<${beanName}> findPage(${beanName} pageInfo);

	List<${beanName}> getActivedList();
}