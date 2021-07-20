package com.jun.plugin.mq.activemq.service.impl;

import com.baomidou.framework.service.impl.CommonServiceImpl;
import com.jun.plugin.mq.activemq.entity.Goods;
import com.jun.plugin.mq.activemq.mapper.GoodsMapper;
import com.jun.plugin.mq.activemq.service.GoodsService;

import org.springframework.stereotype.Service;

/**
 *
 * Goods 表数据服务层接口实现类
 *
 */
@Service
public class GoodsServiceImpl extends CommonServiceImpl<GoodsMapper, Goods> implements GoodsService {


}