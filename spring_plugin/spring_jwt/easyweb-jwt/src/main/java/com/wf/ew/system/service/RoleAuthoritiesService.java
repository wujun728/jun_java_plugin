package com.wf.ew.system.service;

import com.baomidou.mybatisplus.service.IService;
import com.wf.ew.system.model.RoleAuthorities;
import com.wf.ew.system.model.User;

/**
 * Created by Administrator on 2018-12-19 下午 4:38.
 */
public interface RoleAuthoritiesService extends IService<RoleAuthorities> {

    void deleteTrash();
}
