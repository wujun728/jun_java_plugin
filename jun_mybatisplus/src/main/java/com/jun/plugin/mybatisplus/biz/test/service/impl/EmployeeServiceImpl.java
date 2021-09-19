package com.jun.plugin.mybatisplus.biz.test.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jun.plugin.mybatisplus.biz.test.entity.Employee;
import com.jun.plugin.mybatisplus.biz.test.mapper.EmployeeMapper;
import com.jun.plugin.mybatisplus.biz.test.service.IEmployeeService;

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
