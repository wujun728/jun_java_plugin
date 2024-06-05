package com.jun.plugin.app1.service.impl;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jun.plugin.app1.entity.BizTestEntity;
import com.jun.plugin.app1.mapper.BizTestMapper;
import com.jun.plugin.app1.service.BizTestService;

/**
 * @description 客户信息服务层实现
 * @author Wujun
 * @date 2024-03-08
 */
@Service
public class BizTestServiceImpl extends ServiceImpl<BizTestMapper, BizTestEntity> implements BizTestService {

	@Autowired
    private BizTestMapper bizTestMapper;

}



