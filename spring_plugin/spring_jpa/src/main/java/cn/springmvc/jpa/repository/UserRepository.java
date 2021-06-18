package cn.springmvc.jpa.repository;

import java.util.List;

import cn.springmvc.jpa.entity.User;

/**
 * 用户Dao
 * 
 * @author Vincent.wang
 *
 */
public interface UserRepository {

    /**
     * 根据用户名查询用户
     *
     * @param username
     *            用户名
     * @return user 用户
     */
    public User findLocalUserByName(String username);

    /**
     * 查询店铺所有用户
     *
     * @param id
     *            店铺ID
     * @return
     * @author wangxin
     */
    public List<User> findUserByShop(String id);

    /**
     * 查询组织下所有客服员工
     *
     * @return
     */
    public List<User> findUsers();

    /**
     * 更新用户最后登录时间
     *
     * @param user
     *            用户
     */
    public void updateUserLastLoginTime(User user);

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

    public void add(User user);

    public User findUserById(String id);

    public void updatePassword(User u);

}
