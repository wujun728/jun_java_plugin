package cn.kiwipeach.mapper.business;



import cn.kiwipeach.model.business.Department;

import java.math.BigDecimal;

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