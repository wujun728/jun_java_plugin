package cn.classg.service.impl;

import cn.classg.common.utils.IDUtil;
import cn.classg.entity.User;
import cn.classg.entity.UserExample;
import cn.classg.mapper.UserMapper;
import cn.classg.service.UserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    // 查询所有用户
    @Override
    public List<User> queryAllUser() {
        UserExample example = new UserExample();
        UserExample.Criteria criteria = example.createCriteria();
        criteria.andIsDelEqualTo(0);
        return userMapper.selectByExample(example);
    }

    // 查询所有用户（分页）
    @Override
    public PageInfo<User> queryAllUserPage(Integer page, Integer limit) {
        // 开启分页
        PageHelper.startPage(page, limit);

        UserExample example = new UserExample();
        UserExample.Criteria criteria = example.createCriteria();
        criteria.andIsDelEqualTo(0);
        List<User> users = userMapper.selectByExample(example);

        PageInfo<User> pageInfo = new PageInfo<>(users);
        return pageInfo;
    }


    // 根据id查询用户
    @Override
    public User queryUserById(String id) {
        return userMapper.selectByPrimaryKey(id);
    }

    // 更新用户
    @Override
    public Integer updateUser(User user) {
        User oldUser = queryUserById(user.getId());
        user.setUpdateDate(new Date());
        user.setCreateDate(oldUser.getCreateDate());
        user.setIsDel(oldUser.getIsDel());
        int temp = userMapper.updateByPrimaryKey(user);
        return temp;
    }


    // 根据id删除用户
    @Override
    public Integer deleteUserById(String id) {
        Integer temp = changeIsDel(id);
        return temp;
    }


    // 批量删除用户
    @Override
    public Integer deleteUserByIds(List<User> userList) {
        userList.forEach(item -> {
            item.setIsDel(1);
            userMapper.updateByPrimaryKey(item);
        });
        return 1;
    }

    // 添加用户
    @Override
    public Integer insertUser(User user) {
        user.setId(IDUtil.getRandomIdByUUID());
        user.setCreateDate(new Date());
        user.setUpdateDate(new Date());
        user.setIsDel(0);
        int temp = userMapper.insert(user);
        return temp;
    }


    // 逻辑删除
    private Integer changeIsDel(String id) {
        User user = userMapper.selectByPrimaryKey(id);
        user.setIsDel(1);
        return userMapper.updateByPrimaryKey(user);
    }



}
