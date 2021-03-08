package top.linxz.sell.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import top.linxz.sell.dataobject.ProductInfo;
import top.linxz.sell.dto.CartDTO;

import java.util.List;

public interface ProductService {

    ProductInfo findOne(String productId);

    List<ProductInfo> findUpAll();

    Page<ProductInfo> findAll(Pageable pageable);

    ProductInfo save(ProductInfo productInfo);

    //加库存
    void increaseStock(List<CartDTO> cartDTOList);

    //减库存
    void decreaseStock(List<CartDTO> cartDTOList);

    ProductInfo onSale(String productId);

    ProductInfo offSale(String productId);
}
