package com.wf.ew.system.service;

import com.baomidou.mybatisplus.service.IService;
import com.wf.ew.system.model.User;

public interface UserService extends IService<User> {

    User getByUsername(String username);

}
