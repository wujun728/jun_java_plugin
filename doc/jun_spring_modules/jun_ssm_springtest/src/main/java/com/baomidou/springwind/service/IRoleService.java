package com.baomidou.springwind.service;

import com.baomidou.mybatisplus.service.IService;
import com.baomidou.springwind.entity.Role;

/**
 *
 * Role 表数据服务层接口
 *
 */
public interface IRoleService extends IService<Role> {

	void deleteByUserId(Long userId);

}
