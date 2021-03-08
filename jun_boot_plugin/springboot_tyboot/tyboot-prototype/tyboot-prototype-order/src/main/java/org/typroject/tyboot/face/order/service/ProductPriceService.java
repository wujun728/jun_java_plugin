package org.typroject.tyboot.face.order.service;


import org.springframework.stereotype.Component;
import org.typroject.tyboot.core.rdbms.service.BaseService;
import org.typroject.tyboot.face.order.model.ProductPriceModel;
import org.typroject.tyboot.face.order.orm.dao.ProductPriceMapper;
import org.typroject.tyboot.face.order.orm.entity.ProductPrice;

import java.util.List;


/**
 * <p>
 * 产品价格表,通用的价格表,包括了书籍和餐品的价格 服务类
 * </p>
 *
 * @author Wujun
 * @since 2018-02-01
 */
@Component
public class ProductPriceService extends BaseService<ProductPriceModel, ProductPrice, ProductPriceMapper> {

    public static final String OPTION_NORMAL = "NORMAL";


    public ProductPriceModel queryByOption(Long productSeq, String option) throws Exception {
        ProductPriceModel model = new ProductPriceModel();
        model.setOptions(option);
        model.setProductSeq(productSeq);
        return this.queryByModel(model);
    }


    public List<ProductPriceModel> queryByProductSeq(Long productSeq) throws Exception {
        return this.queryForList(null, false, productSeq);
    }

}
