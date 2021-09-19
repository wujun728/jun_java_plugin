package com.jun.mvc.dao;

import java.io.Serializable;
import java.util.List;

import com.jun.mvc.entity.User;

/*********
 * 用户DAO接口
 */
public interface IUserDao {

    /*********
     * 插入一个帐户对象到数据库中
     * @param user
     */
    void save(User user);

    /***************
     * 根据帐户ID来查询帐户信息
     * @param id
     * @return
     */
    User findById(Serializable id);

    /****************
     * 查询所有帐户信息
     * @return
     */
    List<User> findAll();

    /*****************
     * 更新帐户
     * @param user
     */
    void update(User user);

    /***************
     * 删除帐户
     * @param id
     */
    void delete(Serializable id);

    /**********
     * 根据帐户ID来查询它所属的用户信息
     * @param accountId
     * @return
     */
    User queryByAccountId(Serializable accountId);

    /***********
     * 根据用户名来查询用户信息
     * @param userName
     * @return
     */
    User queryByUserName(String userName);
}
