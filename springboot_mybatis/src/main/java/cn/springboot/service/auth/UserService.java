package cn.springboot.service.auth;

import cn.springboot.model.auth.Role;
import cn.springboot.model.auth.User;

import java.util.List;

public interface UserService {

    /**
     * 新增用户
     *
     * @param user
     *            用户
     * @param organize
     *            组织
     * @param role
     *            角色
     */
    public void addUser(User user, Role role);

    /**
     * 修改密码
     *
     * @param userCommand
     *            临时用户对象
     * @param usr
     *            当前用户
     * @author Wujun
     */
    public void updatePassword(User user);

    /**
     * 根据用户名查询用户
     *
     * @param username
     *            用户名
     * @return user 用户
     */
    public User findUserByName(String username);

    /**
     * 更新用户登录时间
     *
     * @param user
     *            用户
     */
    public void updateUserLastLoginTime(User user);

    /**
     * 查询组织下所有客服员工
     *
     * @return
     */
    public List<User> findUsers();

    /**
     * 根据条件（店铺、名称）查询客服人员
     *
     * @param shopId
     *            店铺ID
     * @param empName
     *            客服人员名称
     * @return
     */
    public List<User> findEmp(String shopId, String empName);

}
