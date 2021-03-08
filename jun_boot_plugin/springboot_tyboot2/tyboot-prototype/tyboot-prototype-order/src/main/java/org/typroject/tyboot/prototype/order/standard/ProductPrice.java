package org.typroject.tyboot.prototype.order.standard;

import java.math.BigDecimal;
import java.util.List;

/**
 * 价格相关处理顶级接口
 * @author Wujun
 *
 */
public interface ProductPrice {

	/**
	 * 计算商品价格
	 * @param products
	 * @return
	 */
	BigDecimal getProductsPrice(List<StandardProduct> products);
	
	
	
}
