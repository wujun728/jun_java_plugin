package com.jun.plugin.oauth.server.domain.user;

import java.util.List;

import com.jun.plugin.oauth.server.domain.shared.Repository;

/**
 * @author Wujun
 */

public interface UserRepository extends Repository {

    User findByGuid(String guid);

    void saveUser(User user);

    void updateUser(User user);

    User findByUsername(String username);

    List<User> findUsersByUsername(String username);
}