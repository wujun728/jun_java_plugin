package ${conf.daoPackage}<#if table.prefix!="">.${table.prefix}</#if>;

<#--
import java.util.List;
-->
import ${conf.entityPackage}<#if table.prefix!="">.${table.prefix}</#if>.${table.beanName};
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
/**
 * Copyright (C), 2017-2018, cn.zlinks
 * FileName: AccountMapper
 * Author:   Wujun
 * Date:     ${.now}
 * Description: ${table.beanName}Mapper
 */
@Mapper
public interface ${table.beanName}Mapper{



${table.beanName} queryInfoById(Long id);

	int selectCount();

	int selectCountByCondition(${table.beanName} entity);

	List<${table.beanName}> selectByCondition(${table.beanName} entity);

	int updateById(${table.beanName} entity);

	int deleteById(int id);

	int insert(${table.beanName} entity);

	int insertList(List<${table.beanName}> entityList);

	List<${table.beanName}>  getList();




}