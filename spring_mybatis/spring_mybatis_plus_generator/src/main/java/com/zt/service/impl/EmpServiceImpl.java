package com.zt.service.impl;

import com.zt.entity.Emp;
import com.zt.mapper.EmpMapper;
import com.zt.service.IEmpService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
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
