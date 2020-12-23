package com.thinkingcao.silence.ssm.service;

import com.thinkingcao.silence.ssm.entity.User;

import java.util.List;

public interface IUserService {
    void save(User u);

    void update(User u);

    void delete(Long id);

    User get(Long id);

    List<User> listAll();
}