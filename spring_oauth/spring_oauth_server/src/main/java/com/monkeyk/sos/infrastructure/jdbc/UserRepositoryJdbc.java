/*
 * Copyright (c) 2015 MONKEYK Information Technology Co. Ltd
 * www.monkeyk.com
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of
 * MONKEYK Information Technology Co. Ltd ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you
 * entered into with MONKEYK Information Technology Co. Ltd.
 */
package com.monkeyk.sos.infrastructure.jdbc;

import com.monkeyk.sos.domain.user.Privilege;
import com.monkeyk.sos.domain.user.User;
import com.monkeyk.sos.domain.user.UserRepository;

import static com.monkeyk.sos.infrastructure.CacheConstants.*;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 2015/11/16
 *
 * @author Shengzhao Li
 */
@Repository("userRepositoryJdbc")
public class UserRepositoryJdbc implements UserRepository {


    private static UserRowMapper userRowMapper = new UserRowMapper();

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public User findByGuid(String guid) {
        final String sql = " select * from user_ where  guid = ? ";
        final List<User> list = this.jdbcTemplate.query(sql, new Object[]{guid}, userRowMapper);

        User user = null;
        if (!list.isEmpty()) {
            user = list.get(0);
            user.privileges().addAll(findPrivileges(user.id()));
        }

        return user;
    }

    private Collection<Privilege> findPrivileges(int userId) {
        final String sql = " select privilege from user_privilege where user_id = ? ";
        final List<String> strings = this.jdbcTemplate.queryForList(sql, new Object[]{userId}, String.class);

        List<Privilege> privileges = new ArrayList<>(strings.size());
        privileges.addAll(strings.stream().map(Privilege::valueOf).collect(Collectors.toList()));
        return privileges;
    }

    @Override
    public void saveUser(final User user) {
        final String sql = " insert into user_(guid,archived,create_time,email,password,username,phone) " +
                " values (?,?,?,?,?,?,?) ";
        this.jdbcTemplate.update(sql, ps -> {
            ps.setString(1, user.guid());
            ps.setBoolean(2, user.archived());

            ps.setTimestamp(3, Timestamp.valueOf(user.createTime()));
            ps.setString(4, user.email());

            ps.setString(5, user.password());
            ps.setString(6, user.username());

            ps.setString(7, user.phone());
        });

        //get user id
        final Integer id = this.jdbcTemplate.queryForObject("select id from user_ where guid = ?", new Object[]{user.guid()}, Integer.class);

        //insert privileges
        for (final Privilege privilege : user.privileges()) {
            this.jdbcTemplate.update("insert into user_privilege(user_id, privilege) values (?,?)", ps -> {
                ps.setInt(1, id);
                ps.setString(2, privilege.name());
            });
        }

    }

    @Override
    @CacheEvict(value = USER_CACHE, key = "#user.username()")
    public void updateUser(final User user) {
        final String sql = " update user_ set username = ?, password = ?, phone = ?,email = ? where guid = ? ";
        this.jdbcTemplate.update(sql, ps -> {
            ps.setString(1, user.username());
            ps.setString(2, user.password());

            ps.setString(3, user.phone());
            ps.setString(4, user.email());

            ps.setString(5, user.guid());
        });
    }

    @Override
    @Cacheable(value = USER_CACHE, key = "#username")
    public User findByUsername(String username) {
        final String sql = " select * from user_ where username = ? and archived = 0 ";
        final List<User> list = this.jdbcTemplate.query(sql, new Object[]{username}, userRowMapper);

        User user = null;
        if (!list.isEmpty()) {
            user = list.get(0);
            user.privileges().addAll(findPrivileges(user.id()));
        }

        return user;
    }

    @Override
    public List<User> findUsersByUsername(String username) {
        String sql = " select * from user_ where archived = 0 ";
        Object[] params = new Object[]{};
        if (StringUtils.isNotEmpty(username)) {
            sql += " and username like ?";
            params = new Object[]{"%" + username + "%"};
        }
        sql += " order by create_time desc ";

        final List<User> list = this.jdbcTemplate.query(sql, params, userRowMapper);
        for (User user : list) {
            user.privileges().addAll(findPrivileges(user.id()));
        }
        return list;
    }
}
