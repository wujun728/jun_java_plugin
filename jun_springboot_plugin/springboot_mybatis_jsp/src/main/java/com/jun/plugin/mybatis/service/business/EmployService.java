package com.jun.plugin.mybatis.service.business;

import java.math.BigDecimal;

import com.jun.plugin.mybatis.model.business.Employ;

/**
 * Create Date: 2017/11/06
 * Description: 员工Service服务接口
 *
 * @author Wujun
 */
public interface EmployService {
    /**
     * 查询员工信息
     * @param empno 员工编号
     * @return 员工信息
     */
    Employ queryEmploy(String empno);
}
