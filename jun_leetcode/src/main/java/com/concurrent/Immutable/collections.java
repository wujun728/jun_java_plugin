package com.concurrent.Immutable;

import com.concurrent.juc.annotation.ThreadSafe;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author BaoZhou
 * @date 2019/1/6
 */
@ThreadSafe
public class collections {
    public static void main(String[] args) {
        HashMap<Integer, Integer> objectObjectHashMap = Maps.newHashMap();
        Map<Integer, Integer> unmodifiableMap = Collections.unmodifiableMap(objectObjectHashMap);

        ImmutableList<Integer> immutableList = ImmutableList.of(1, 2, 3);


        ImmutableMap<String, Integer> map = ImmutableMap.<String, Integer>builder()
                .put("1", 1)
                .put("2", 3)
                .build();


    }
}
