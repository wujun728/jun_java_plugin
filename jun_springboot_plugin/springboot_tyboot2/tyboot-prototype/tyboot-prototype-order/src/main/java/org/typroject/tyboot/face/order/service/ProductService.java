package org.typroject.tyboot.face.order.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.typroject.tyboot.core.rdbms.service.BaseService;
import org.typroject.tyboot.face.order.model.ProductModel;
import org.typroject.tyboot.face.order.orm.dao.ProductMapper;
import org.typroject.tyboot.face.order.orm.entity.Product;

import java.util.List;


/**
 * <p>
 * 商品表 服务类
 * </p>
 *
 * @author Wujun
 * @since 2018-01-14
 */
@Component
public class ProductService extends BaseService<ProductModel, Product, ProductMapper> {

    public static final String PRODUCT_TYPE_VIP = "VIP";
    public static final String PRODUCT_TYPE_RECHARGE = "RECHARGE";

    @Autowired
    private ProductPriceService productPriceService;


    public List<ProductModel> queryByType(String productType) throws Exception {
        return this.setDetailForList(queryForList(null, false, productType));
    }


    private List<ProductModel> setDetailForList(List<ProductModel> models) throws Exception {
        for (ProductModel model : models) {
            model = this.setDetail(model);
        }
        return models;
    }

    private ProductModel setDetail(ProductModel model) throws Exception {
        //设置价格信息
        model.setProductPrice(this.productPriceService.queryByProductSeq(model.getSequenceNbr()));

        return model;
    }

}
