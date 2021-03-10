package cn.classg.service;

import cn.classg.entity.User;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface UserService {

    // 查询所有用户
    List<User> queryAllUser();

    // 查询所有用户（分页）
    PageInfo<User> queryAllUserPage(Integer page, Integer limit);

    // 根据id删除用户
    Integer deleteUserById(String id);

    // 批量删除用户
    Integer deleteUserByIds(List<User> userList);

    // 添加用户
    Integer insertUser(User user);

    // 根据id查询用户
    User queryUserById(String id);

    // 更新用户
    Integer updateUser(User user);
}
