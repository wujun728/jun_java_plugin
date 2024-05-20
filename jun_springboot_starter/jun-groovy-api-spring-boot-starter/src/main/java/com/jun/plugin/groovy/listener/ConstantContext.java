package com.jun.plugin.groovy.listener;

import cn.hutool.core.lang.Dict;
import cn.hutool.core.util.ObjectUtil;

/**
 * 系统参数配置容器
 *
 * @date 2019/6/20 13:37
 */
public class ConstantContext {

    /**
     * 所有的常量，可以增删改查
     */
    private static final Dict CONSTANTS_HOLDER = Dict.create();

    /**
     * 添加系统常量
     *
     * @date 2020/6/20 22:32
     */
    public static void putConstant(String code, Object value) {
        if (ObjectUtil.hasEmpty(code, value)) {
            return;
        }
        CONSTANTS_HOLDER.put(code, value);
    }

    /**
     * 删除常量，系统常量无法删除，在sysConfig已判断
     *
     * @date 2020/6/20 22:32
     */
    public static void deleteConstant(String code) {
        if (ObjectUtil.hasEmpty(code)) {
            return;
        }
        CONSTANTS_HOLDER.remove(code);
    }

    /**
     * 获取系统常量本身
     *
     * @date 2020/6/20 22:32
     */
    public static Dict me() {
        return CONSTANTS_HOLDER;
    }
}
