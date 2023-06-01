package com.jun.plugin.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.jun.plugin.entity.Dept;
import com.jun.plugin.mapper.DeptMapper;
import com.jun.plugin.service.IDeptService;

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
public class DeptServiceImpl extends ServiceImpl<DeptMapper, Dept> implements IDeptService {

}
