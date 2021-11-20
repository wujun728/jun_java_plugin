package com.snakerflow.demo.api.service.impl;

import com.snakerflow.demo.api.entity.SecUser;
import com.snakerflow.demo.api.mapper.SecUserMapper;
import com.snakerflow.demo.api.service.ISecUserService;
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
public class SecUserServiceImpl extends ServiceImpl<SecUserMapper, SecUser> implements ISecUserService {

}
