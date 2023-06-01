package com.frame.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.frame.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Description: 用户mapper
 *
 * @author wenbin
 * @date 2019/10/8 下午9:23
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}