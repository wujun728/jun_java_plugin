package com.wf.ew.system.service;

import com.baomidou.mybatisplus.service.IService;
import com.wf.ew.system.model.User;
import com.wf.ew.system.model.UserRole;

/**
 * Created by Administrator on 2018-12-19 下午 4:09.
 */
public interface UserRoleService extends IService<UserRole> {

    Integer[] getRoleIds(Integer userId);
}
