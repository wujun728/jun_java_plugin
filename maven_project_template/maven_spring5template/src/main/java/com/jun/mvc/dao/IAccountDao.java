package com.jun.mvc.dao;

import java.io.Serializable;
import java.util.List;

import com.jun.mvc.entity.Account;

/****************************
 * 帐户持久层
 * CRUD操作
 */
public interface IAccountDao {

    /*********
     * 插入一个帐户对象到数据库中
     * @param account
     */
    void save(Account account);

    /***************
     * 根据帐户ID来查询帐户信息
     * @param id
     * @return
     */
    Account findById(Serializable id);

    /****************
     * 查询所有帐户信息
     * @return
     */
    List<Account> findAll();

    /*****************
     * 更新帐户
     * @param account
     */
    void update(Account account);

    /***************
     * 删除帐户
     * @param id
     */
    void delete(Serializable id);

    /*****************
     * 根据用户ID来查询出此用户下的所有帐户信息
     * @param userId
     * @return
     */
    List<Account> queryUserAccounts(Serializable userId);
}
