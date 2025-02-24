package com.company.project.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.company.project.core.Result;
import com.company.project.core.ResultGenerator;
import com.company.project.model.User;
import com.company.project.dao.UserMapper;
import com.company.project.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.company.project.service.RedisService;
import com.company.project.utils.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author project
 * @since 2020-01-08
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    private UserMapper userMapper;


    @Autowired
    private RedisService redis;
    @Value("${redis.key.prefix.passwordError}")
    private String PASSWORD_ERROR_PREFIX;
    @Value("${redis.key.expire.passwordError}")
    private int PASSWORD_ERROR_EXPIRE;

    @Override
    public Result checkPassword(User userParam) {

        String username = userParam.getUsername();
        String password = userParam.getPassword();
        //redis key
        String redisPasswordErrorKey = PASSWORD_ERROR_PREFIX + username;
        //判断账户是否锁定
        String errorCount = redis.get(redisPasswordErrorKey);
        if (errorCount != null && Integer.parseInt(errorCount) == 5) {
            return ResultGenerator.genFailResult("密码连续错误5次，请1小时后重试");
        }
        //根据用户名获取用户信息
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        User user = userMapper.selectOne(queryWrapper);
        if (user == null) {
            return ResultGenerator.genFailResult("账号未找到");
        }

        if (!MD5Utils.Encrypt(password, true).equals(user.getPassword())) {
            if (errorCount == null) {
                errorCount = "1";
            } else {
                errorCount = String.valueOf(Integer.parseInt(errorCount) + 1);
            }
            redis.setAndExpire(redisPasswordErrorKey, errorCount, PASSWORD_ERROR_EXPIRE);
            if ("5".equals(errorCount)) {
                return ResultGenerator.genFailResult("密码连续错误5次，请1小时后重试");
            } else {
                return ResultGenerator.genFailResult("密码错误，剩余次数" + (5 - Integer.parseInt(errorCount)));
            }
        } else {
            //登录成功删除错误登录次数
            redis.del(redisPasswordErrorKey);
            return ResultGenerator.genSuccessResult(user);
        }
    }
}
