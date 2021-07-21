package com.wf.ew.system.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.wf.ew.system.model.User;

public interface UserMapper extends BaseMapper<User> {

    User getByUsername(String username);
}
