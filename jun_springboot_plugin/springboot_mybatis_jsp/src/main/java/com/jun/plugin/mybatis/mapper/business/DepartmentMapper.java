package com.jun.plugin.mybatis.mapper.business;



import java.math.BigDecimal;

import com.jun.plugin.mybatis.model.business.Department;

/**
 * Create Date: 2017/10/31
 * Description: 部门操作接口
 *
 * @author Wujun
 */
public interface DepartmentMapper {
    int deleteByPrimaryKey(BigDecimal deptno);

    int insert(Department record);

    int insertSelective(Department record);

    Department selectByPrimaryKey(BigDecimal deptno);

    int updateByPrimaryKeySelective(Department record);

    int updateByPrimaryKey(Department record);
}