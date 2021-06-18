package cn.kiwipeach.mapper.business;



import cn.kiwipeach.model.business.Employ;

import java.math.BigDecimal;

/**
 * Create Date: 2017/10/31
 * Description: 员工操作接口
 *
 * @author Wujun
 */
public interface EmployMapper {
    int deleteByPrimaryKey(BigDecimal empno);

    int insert(Employ record);

    int insertSelective(Employ record);

    Employ selectByPrimaryKey(BigDecimal empno);

    int updateByPrimaryKeySelective(Employ record);

    int updateByPrimaryKey(Employ record);
}