package com.zh.springbootelasticsearch.repository;

import com.zh.springbootelasticsearch.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

/**
 * @author Wujun
 * @date 2019/6/19
 */
public interface ProductRepository extends ElasticsearchRepository<Product,String> {

    List<Product> findByBrand(String brand);

    Page<Product> findByCategoryOrderByCreateTimeDesc(String category, PageRequest page);

    List<Product> findByCreateTimeBetween(String from, String to);

    List<Product> findByNameLike(String name);

    List<Product> findByNameLikeOrBrandLike(String name,String brand);

    void deleteByIdIn(List<String> ids);
}
