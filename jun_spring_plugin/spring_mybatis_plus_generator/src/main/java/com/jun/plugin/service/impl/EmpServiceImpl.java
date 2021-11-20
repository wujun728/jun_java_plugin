package com.jun.plugin.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.jun.plugin.entity.Emp;
import com.jun.plugin.mapper.EmpMapper;
import com.jun.plugin.service.IEmpService;

import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Wujun
 * @since 2018-04-06
 */
@Service
public class EmpServiceImpl extends ServiceImpl<EmpMapper, Emp> implements IEmpService {

}
