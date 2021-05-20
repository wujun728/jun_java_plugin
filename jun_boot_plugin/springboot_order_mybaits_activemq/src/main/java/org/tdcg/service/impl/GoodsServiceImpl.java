package org.tdcg.service.impl;

import com.baomidou.framework.service.impl.CommonServiceImpl;
import org.springframework.stereotype.Service;
import org.tdcg.entity.Goods;
import org.tdcg.mapper.GoodsMapper;
import org.tdcg.service.GoodsService;

/**
 *
 * Goods 表数据服务层接口实现类
 *
 */
@Service
public class GoodsServiceImpl extends CommonServiceImpl<GoodsMapper, Goods> implements GoodsService {


}