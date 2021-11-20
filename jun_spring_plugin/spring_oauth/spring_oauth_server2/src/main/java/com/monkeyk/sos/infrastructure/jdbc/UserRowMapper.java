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

import com.monkeyk.sos.domain.user.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 2015/11/16
 *
 * @author Shengzhao Li
 */
public class UserRowMapper implements RowMapper<User> {


    public UserRowMapper() {
    }

    @Override
    public User mapRow(ResultSet rs, int i) throws SQLException {
        User user = new User();

        user.id(rs.getInt("id"));
        user.guid(rs.getString("guid"));

        user.archived(rs.getBoolean("archived"));
        user.createTime(rs.getTimestamp("create_time").toLocalDateTime());

        user.email(rs.getString("email"));
        user.phone(rs.getString("phone"));

        user.password(rs.getString("password"));
        user.username(rs.getString("username"));

        user.lastLoginTime(rs.getTimestamp("last_login_time"));

        return user;
    }
}
