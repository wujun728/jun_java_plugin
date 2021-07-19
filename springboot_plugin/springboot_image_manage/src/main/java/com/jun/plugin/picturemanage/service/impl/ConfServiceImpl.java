package com.jun.plugin.picturemanage.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jun.plugin.picturemanage.dao.ConfMapper;
import com.jun.plugin.picturemanage.entity.ConfEntity;
import com.jun.plugin.picturemanage.service.ConfService;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @Author YuChen
 * @Email 835033913@qq.com
 * @Create 2019/10/31 15:11
 */
@Service
@Log4j2
@Transactional(rollbackFor = Exception.class)
public class ConfServiceImpl extends ServiceImpl<ConfMapper, ConfEntity> implements ConfService {

    /**
     * Set 存入一个键值对
     *
     * @param key
     * @param value
     * @return
     */
    @Override
    public boolean set(String key, String value) {
        ConfEntity entity = new ConfEntity();
        entity.setKey(key);
        entity.setValue(value);
        entity.setCreateTime(new Date());
        return this.saveOrUpdate(entity);
    }

    @Override
    public String get(String key, String defaultValue) {
        String value = this.get(key);
        return StringUtils.isBlank(value) ? defaultValue : value;
    }


    @Override
    public String get(String key) {
        ConfEntity entity = this.getById(key);
        if (entity == null) {
            return null;
        }
        return entity.getValue();
    }

    @Override
    public boolean del(String key) {
        return this.removeById(key);
    }

}
