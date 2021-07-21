package com.wf.ew.system.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.wf.ew.system.dao.RoleAuthoritiesMapper;
import com.wf.ew.system.model.RoleAuthorities;
import com.wf.ew.system.service.RoleAuthoritiesService;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2018-12-19 下午 4:39.
 */
@Service
public class RoleAuthoritiesServiceImpl extends ServiceImpl<RoleAuthoritiesMapper, RoleAuthorities> implements RoleAuthoritiesService {

    @Override
    public void deleteTrash() {
        baseMapper.deleteTrash();
    }

}
