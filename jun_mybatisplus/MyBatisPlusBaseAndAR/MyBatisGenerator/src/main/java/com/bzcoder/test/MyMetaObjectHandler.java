package com.bzcoder.test;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 自定义公共字段处理器
 * @author: BaoZhou
 * @date : 2018/10/8 22:41
 */
public class MyMetaObjectHandler implements MetaObjectHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(MyMetaObjectHandler.class);

    /**
     * 插入操作自动填充
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        LOGGER.info("start insert fill ....");
        this.setFieldValByName("age", 99, metaObject);
    }

    /**
     * 更新操作自动填充
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        LOGGER.info("start update fill ....");
        this.setFieldValByName("age", 98, metaObject);
    }
}