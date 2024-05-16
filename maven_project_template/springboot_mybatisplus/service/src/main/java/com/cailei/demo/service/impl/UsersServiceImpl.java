package com.cailei.demo.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cailei.demo.entity.Users;
import com.cailei.demo.enums.StatusEnum;
import com.cailei.demo.mapper.UsersMapper;
import com.cailei.demo.service.UsersService;
import com.cailei.demo.vo.ResultVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 蔡磊
 * @description 针对表【users(用户 )】的数据库操作Service实现
 * @createDate 2022-10-20 21:10:05
 */
@Service
public class UsersServiceImpl extends ServiceImpl<UsersMapper, Users>
        implements UsersService {

    @Resource
    private UsersMapper usersMapper;

    public ResultVo queryUsers(Integer userId, String username) {
        //查询用户名包含a，id大于5，并且实际名称不为null的用户信息
        QueryWrapper<Users> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("username", username)
                .ge("user_id", userId);
        List<Users> users = usersMapper.selectList(queryWrapper);

        ResultVo resultVo = new ResultVo();
        if (users.size() > 0) {
            resultVo.setCode(StatusEnum.RESULT_SUCCESS.getCode());
            resultVo.setMessage(StatusEnum.RESULT_SUCCESS.getMessage());
            resultVo.setData(users);
        } else {
            resultVo.setCode(StatusEnum.RESULT_NULL.getCode());
            resultVo.setMessage(StatusEnum.RESULT_NULL.getMessage());
        }
        return resultVo;
    }
}




