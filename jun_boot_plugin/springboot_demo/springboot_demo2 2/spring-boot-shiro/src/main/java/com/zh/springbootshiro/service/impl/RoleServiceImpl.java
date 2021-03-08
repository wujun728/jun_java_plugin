package com.zh.springbootshiro.service.impl;

import com.zh.springbootshiro.model.User;
import com.zh.springbootshiro.service.RoleService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author Wujun
 * @date 2019/6/26
 */
@Service
public class RoleServiceImpl implements RoleService {

    private static Map<Integer, Set<String>> roleMap = new HashMap<>(16);

    static {
        Set<String> set = new HashSet<>(16);
        set.add("超级管理员");
        roleMap.put(1,set);
        set = new HashSet<>(16);
        set.add("运营经理");
        roleMap.put(2,set);
    }

    @Override
    public Set<String> findByUserId(Integer userId) {
        return roleMap.get(userId);
    }
}
