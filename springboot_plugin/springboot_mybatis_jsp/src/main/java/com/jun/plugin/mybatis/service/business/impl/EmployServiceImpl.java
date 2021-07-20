package com.jun.plugin.mybatis.service.business.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jun.plugin.mybatis.enums.ResponseCodeEnum;
import com.jun.plugin.mybatis.exeception.BusinessException;
import com.jun.plugin.mybatis.mapper.business.EmployMapper;
import com.jun.plugin.mybatis.model.business.Employ;
import com.jun.plugin.mybatis.service.business.EmployService;

import java.math.BigDecimal;

/**
 * Create Date: 2017/11/06
 * Description: 员工服务接口实现类
 *
 * @author Wujun
 */
@Service
public class EmployServiceImpl implements EmployService {

    @Autowired
    private EmployMapper employMapper;

    @Override
    public Employ queryEmploy(String empno) {
        //FIXME 此处有可能会抛出转换异常
        Integer intEmpno = null;
        try {
            intEmpno = Integer.parseInt(empno);
        } catch (NumberFormatException e) {
            throw new BusinessException(ResponseCodeEnum.EMP_NUMBER_FORMAT);
        }
        BigDecimal bigDecimal = new BigDecimal(intEmpno);
        Employ employ = employMapper.selectByPrimaryKey(bigDecimal);
        if (employ==null){
            throw new BusinessException(ResponseCodeEnum.EMP_NULL_POINTER);
        }
        return employ;
    }
}
