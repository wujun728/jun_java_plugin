package com.monkeyk.sos.domain.user;

import com.monkeyk.sos.domain.shared.Repository;

import java.util.List;

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