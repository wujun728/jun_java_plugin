package com.jun.plugin.elasticsearch;
//package com.anqi.es;
//
//import com.anqi.es.DemoEsApplication;
//import com.anqi.es.item.Product;
//import com.anqi.es.service.ProductService;
//import com.anqi.es.util.SnowflakeIdWorker;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest(classes = DemoEsApplication.class)
//public class DemoEsApplicationTests {
//
//
//    private SnowflakeIdWorker idWorker;
//
//    @Autowired
//    ProductService productService;
//
//    private int page = 1;
//
//    @Before
//    public void initIdWorker() {
//        idWorker = new SnowflakeIdWorker(1, 0);
//    }
//
//    @Autowired
//    private ElasticsearchTemplate elasticsearchTemplate;
//
//    @Test
//    public void createIndex() {
//        elasticsearchTemplate.createIndex(Product.class);
//    }
//
//    @Test
//    public void deleteIndex() {
//        elasticsearchTemplate.deleteIndex(Product.class);
//    }
//
//
//    @Test
//    public void contextLoads() {
//        elasticsearchTemplate.putMapping(Product.class);
//
//    }
//
//    @Test
//    public void save() {
//        List<Product> products = new ArrayList<>();
//        products.add(new Product(idWorker.nextId(), "清扬男士洗发水", "洗漱用品", 100, 35.00, "清凉去屑，自信男人"));
//        products.add(new Product(idWorker.nextId(), "海飞丝洗发水", "洗漱用品", 100, 25.00, "飞一般的感觉"));
//        products.add(new Product(idWorker.nextId(), "滋源生姜洗发露", "洗漱用品", 100, 95.00, "强健发根"));
//        products.add(new Product(idWorker.nextId(), "滋源无硅油洗发水", "洗漱用品", 100, 75.00, "无硅油，滋润头皮"));
//        products.add(new Product(idWorker.nextId(), "飘柔洗发露", "洗漱用品", 100, 18.00, "头发飘起来"));
//
//        products.forEach(product -> productService.save(product));
//    }
//
//    @Test
//    public void testFindNameOrDescByKey() {
//        //es分页首页为第0页
////        Pageable pageable = PageRequest.of(page - 1, 9);
////        Page<Product> result = productRepository.findNameOrDescByKey("滋源", "滋源", pageable);
////        System.out.println("pages: "+ result.getTotalPages() +" elements: " + result.getTotalElements());
////        System.out.println(result.getContent());
//    }
//
//    @Test
//    public void testFindNameByKey() {
//        List<Product> res = productService.getByName("洗发");
//        System.out.println(res);
//    }
//
//    @Test
//    public void testId() {
//        for (int i = 0; i < 20 ; i++) {
//            new Thread(() -> System.out.println(System.currentTimeMillis() + ":" +idWorker.nextId())).start();
//        }
//
//    }
//
//}
