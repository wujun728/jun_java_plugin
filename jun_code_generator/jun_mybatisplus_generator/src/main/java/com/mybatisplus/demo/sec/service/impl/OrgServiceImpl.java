package com.mybatisplus.demo.sec.service.impl;

import com.mybatisplus.demo.sec.entity.Org;
import com.mybatisplus.demo.sec.mapper.OrgMapper;
import com.mybatisplus.demo.sec.service.IOrgService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author liuxzh
 * @since 2018-12-20
 */
@Service
public class OrgServiceImpl extends ServiceImpl<OrgMapper, Org> implements IOrgService {

}
