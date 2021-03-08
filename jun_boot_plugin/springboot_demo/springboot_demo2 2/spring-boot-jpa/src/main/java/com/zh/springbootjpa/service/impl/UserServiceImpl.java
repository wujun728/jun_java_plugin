package com.zh.springbootjpa.service.impl;

import com.zh.springbootjpa.dao.UserRepository;
import com.zh.springbootjpa.model.User;
import com.zh.springbootjpa.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Wujun
 * @date 2019/5/31
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User findById(Integer id) {
        return this.userRepository.findById(id).orElse(null);
    }

    @Override
    public User findByName(String name) {
        return this.userRepository.findByNameIs(name);
    }

    @Override
    public List<User> listByAge(Integer age) {
        return this.userRepository.findByAgeIs(age);
    }

    @Override
    public void save(User user) {
        this.userRepository.saveAndFlush(user);
    }

    @Override
    public void save(List<User> users) {
        this.userRepository.saveAll(users);
        this.userRepository.flush();
    }

    @Override
    public void updateAgeByName(String name, Integer age) {
        this.userRepository.updateAgeByName(name,age);
    }

    @Override
    public void deleteById(Integer id) {
        this.userRepository.deleteById(id);
    }
}
