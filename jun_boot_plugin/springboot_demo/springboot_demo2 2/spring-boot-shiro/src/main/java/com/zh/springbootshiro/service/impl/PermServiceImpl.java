package com.zh.springbootshiro.service.impl;

import com.zh.springbootshiro.service.PermService;
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
public class PermServiceImpl implements PermService {

    private static Map<Integer, Set<String>> permMap = new HashMap<>(16);

    static {
        Set<String> set = new HashSet<>(16);
        set.add("增");
        set.add("删");
        set.add("改");
        set.add("查");
        permMap.put(1,set);
        set = new HashSet<>(16);
        set.add("查");
        permMap.put(2,set);
    }

    @Override
    public Set<String> findByUserId(Integer userId) {
        return permMap.get(userId);
    }
}
