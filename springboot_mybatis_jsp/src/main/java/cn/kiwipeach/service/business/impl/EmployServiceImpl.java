package cn.kiwipeach.service.business.impl;

import cn.kiwipeach.enums.ResponseCodeEnum;
import cn.kiwipeach.exeception.BusinessException;
import cn.kiwipeach.mapper.business.EmployMapper;
import cn.kiwipeach.model.business.Employ;
import cn.kiwipeach.service.business.EmployService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
