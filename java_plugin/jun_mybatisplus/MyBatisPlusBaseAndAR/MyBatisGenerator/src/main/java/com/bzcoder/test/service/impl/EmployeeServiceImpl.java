package com.bzcoder.test.service.impl;

import com.bzcoder.test.entity.Employee;
import com.bzcoder.test.mapper.EmployeeMapper;
import com.bzcoder.test.service.IEmployeeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author BaoZhou
 * @since 2018-10-03
 */
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements IEmployeeService {

}
