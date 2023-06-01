package com.baomidou.springmvc.common;

/**
 * <p>
 * 测试自定义 mapper 父类
 * </p>
 */
public interface SuperMapper<T> extends com.baomidou.mybatisplus.mapper.BaseMapper<T> {

    // 这里可以写自己的公共方法、注意不要让 mp 扫描到误以为是实体 Base 的操作
}
