package com.jun.plugin.ldap.service;

import com.jun.plugin.ldap.api.Result;
import com.jun.plugin.ldap.entity.Person;
import com.jun.plugin.ldap.request.LoginRequest;

/**
 * PersonService
 *
 * @author Wujun
 * @version v1.0
 * @since 2019/8/26 1:05
 */
public interface PersonService {

    /**
     * 登录
     *
     * @param request {@link LoginRequest}
     * @return {@link Result}
     */
    Result login(LoginRequest request);

    /**
     * 查询全部
     *
     * @return {@link Result}
     */
    Result listAllPerson();

    /**
     * 保存
     *
     * @param person {@link Person}
     */
    void save(Person person);

    /**
     * 删除
     *
     * @param person {@link Person}
     */
    void delete(Person person);

}
