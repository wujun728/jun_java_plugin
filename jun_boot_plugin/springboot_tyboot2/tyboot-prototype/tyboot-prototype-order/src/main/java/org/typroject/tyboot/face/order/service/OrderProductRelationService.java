package org.typroject.tyboot.face.order.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Component;
import org.typroject.tyboot.core.rdbms.service.BaseService;
import org.typroject.tyboot.face.order.model.OrderProductRelationModel;
import org.typroject.tyboot.face.order.orm.dao.OrderProductRelationMapper;
import org.typroject.tyboot.face.order.orm.entity.OrderProductRelation;

import java.util.List;


/**
 * <p>
 * 订单产品关系表 服务类
 * </p>
 *
 * @author Wujun
 * @since 2018-01-14
 */
@Component
public class OrderProductRelationService extends BaseService<OrderProductRelationModel, OrderProductRelation, OrderProductRelationMapper> {


    /**
     * 分页查询
     */
    public Page<OrderProductRelationModel> queryForPage(Page page, String agencyCode) throws Exception {
      return queryForPage(page,agencyCode);
    }


    /**
     * 列表查询 示例
     */
    public List<OrderProductRelationModel> selectForList(String agencyCode) throws Exception {
      return queryForList("",false,agencyCode);
    }


}
