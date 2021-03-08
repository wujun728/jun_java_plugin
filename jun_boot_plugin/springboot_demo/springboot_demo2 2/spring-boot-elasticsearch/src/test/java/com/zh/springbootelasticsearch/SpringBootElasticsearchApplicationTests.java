package com.zh.springbootelasticsearch;

import com.alibaba.fastjson.JSONObject;
import com.zh.springbootelasticsearch.model.Product;
import com.zh.springbootelasticsearch.repository.ProductRepository;
import com.zh.springbootelasticsearch.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBootElasticsearchApplicationTests {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    /**
     * 单条保存
     */
    @Test
    public void saveTest() {
        Product product = this.productService.getProduct();
        this.productRepository.save(product);
    }

    /**
     * 批量保存
     */
    @Test
    public void saveListTest() {
        List<Product> list = this.productService.listProduct();
        this.productRepository.saveAll(list);
    }

    /**
     * 根据id单条删除文档
     */
    @Test
    public void deleteDocumentByIdTest() {
        this.productRepository.deleteById("2581fef991a04f8bb4ad71f68dcf7a0f");
    }

    /**
     * 根据id批量删除文档
     */
    @Test
    public void deleteDocumentByIdsTest() {
        List<String> ids = Arrays.asList("80a22255742c4a38a1b5e64230566942","6a4c474e98f24c85a19f1044432ea1e0");
        this.productRepository.deleteByIdIn(ids);
    }

    /**
     * 单条更新，需要先查
     * 像JPA一样，否则其他为null字段也给更没了
     */
    @Test
    public void updateTest() {
        Product product = this.productRepository.findById("eae1916e6d0a493e86ca662b1d4052ee").get();
        product.setName("X30");
        this.productRepository.save(product);
    }

    /**
     * 批量更新，需要先查
     */
    @Test
    public void updateListTest() {
        List<Product> list = this.productRepository.findByBrand("华为");
        list.forEach(System.out :: println);
        list = list.stream()
                   .filter(e -> e.getCategory().equals("手机"))
                   .peek(e -> e.setCategory("电脑"))
                   .collect(Collectors.toList());
        this.productRepository.saveAll(list);
        log.info("====================更新后====================");
        list = this.productRepository.findByBrand("华为");
        list.forEach(System.out :: println);
    }

    /**
     * 带分页排序的查询全部
     */
    @Test
    public void searchAllByPageTest() {
        Page<Product> page = this.productRepository.findAll(PageRequest.of(0,10,Sort.Direction.ASC,"createTime"));
        page.get().forEach(System.out :: println);
    }

    /**
     * 带分页的根据条件查询排序
     */
    @Test
    public void searchByFieldOrderByFieldTest() {
        Page<Product> page = this.productRepository.findByCategoryOrderByCreateTimeDesc("电脑",PageRequest.of(0,10));
        page.get().forEach(System.out :: println);
    }

    /**
     * 条件查询
     */
    @Test
    public void searchByFieldBetweenTest() {
        List<Product> list = this.productRepository.findByCreateTimeBetween("2018-01-01 00:00:00","2019-12-31 00:00:00");
        list.forEach(System.out :: println);
    }

    /**
     * 单字段条件模糊查询
     * 包含了：prefix,fuzzy,wildcard
     * 会被分词
     */
    @Test
    public void searchByFieldLikeTest() {
        List<Product> list = this.productRepository.findByNameLike("耐克鞋");
        list.forEach(System.out :: println);
    }

    /**
     * 多字段条件模糊查询
     * 包含了：prefix,fuzzy,wildcard
     * 会被分词
     */
    @Test
    public void searchByFieldLikeOrTest() {
        List<Product> list = this.productRepository.findByNameLikeOrBrandLike("三星","三星");
        list.forEach(System.out :: println);
    }

    /**
     * 全字段条件模糊查询
     * 会被分词
     */
    @Test
    public void searchByAllFiledValueTest() {
        List<Product> list = this.productService.findByValue("华为游戏");
        list.forEach(System.out :: println);
        log.info("=========================================================");
        Page<Product> pageList = this.productService.findByValue("耐克鞋",PageRequest.of(0,5,Sort.Direction.DESC,"createTime"));
        log.info("共有{}条,共有{}页",pageList.getTotalElements(),pageList.getTotalPages());
        pageList.getContent().forEach(System.out :: println);
    }

    /**
     * 全字段条件模糊查询
     * 会被分词
     * 指定字段高亮
     */
    @Test
    public void searchByAllFiledValueWithHightLightTest() {
        Page<Product> list = this.productService.findByValue("苹果",Arrays.asList("name"),PageRequest.of(0,5));
        list.getContent().forEach(System.out :: println);
    }

    /**
     * 单字段条件模糊查询
     * 会被分词
     */
    @Test
    public void searchByFieldMatchTest() {
        List<Product> list = this.productService.findByFieldMatch("name","苹果琵琶");
        list.forEach(System.out :: println);
        log.info("=========================================================");
        Page<Product> pageList = this.productService.findByFieldMatch("name","耐克鞋",PageRequest.of(0,5,Sort.Direction.DESC,"createTime"));
        log.info("共有{}条,共有{}页",pageList.getTotalElements(),pageList.getTotalPages());
        pageList.getContent().forEach(System.out :: println);
    }

    /**
     * 多字段条件模糊查询
     * 会被分词
     */
    @Test
    public void searchByMultiFieldMatchTest() {
        List<String> fileds = Arrays.asList("brand","name");
        List<Product> list = this.productService.findByMultiFieldMatch(fileds,"耐克牛肉");
        list.forEach(System.out :: println);
        log.info("=========================================================");
        Page<Product> pageList = this.productService.findByMultiFieldMatch(fileds,"耐克牛肉",PageRequest.of(0,5,Sort.Direction.DESC,"createTime"));
        log.info("共有{}条,共有{}页",pageList.getTotalElements(),pageList.getTotalPages());
        pageList.getContent().forEach(System.out :: println);
    }

    /**
     * 统计聚合
     * 汇总所有分类商品的总金额
     */
    @Test
    public void searchAllCategorySumPriceTest() {
        List<JSONObject> list = this.productService.findAllCategorySumPrice();
        list.forEach(System.out :: println);
    }

}

