package com.cailei.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cailei.demo.entity.Users;
import com.cailei.demo.vo.ResultVo;
/**
 * @author 蔡磊
 * @description 针对表【users(用户 )】的数据库操作Service
 * @createDate 2022-10-20 21:10:05
 */
public interface UsersService extends IService<Users> {
    ResultVo queryUsers(Integer userId, String username);
}
