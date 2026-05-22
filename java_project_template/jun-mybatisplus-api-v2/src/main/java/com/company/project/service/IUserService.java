package com.company.project.service;

import com.company.project.core.Result;
import com.company.project.model.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author project
 * @since 2020-01-08
 */
public interface IUserService extends IService<User> {

    Result checkPassword(User userParam);
}
