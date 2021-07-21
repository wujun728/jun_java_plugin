package com.wf.ew.system.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.wf.ew.system.model.RoleAuthorities;

public interface RoleAuthoritiesMapper extends BaseMapper<RoleAuthorities> {

    int deleteTrash();
}
