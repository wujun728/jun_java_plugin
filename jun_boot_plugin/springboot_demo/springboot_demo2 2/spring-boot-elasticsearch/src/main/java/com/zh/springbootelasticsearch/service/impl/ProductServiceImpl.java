package com.zh.springbootelasticsearch.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.JSONObject;
import com.zh.springbootelasticsearch.model.Product;
import com.zh.springbootelasticsearch.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.sum.InternalSum;
import org.elasticsearch.search.aggregations.metrics.sum.SumAggregationBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.SearchResultMapper;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.aggregation.impl.AggregatedPageImpl;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.*;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * 利用ElasticsearchTemplate操作ES
 * @author Wujun
 * @date 2019/6/18
 */
@Slf4j
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ElasticsearchTemplate esTemplate;

    private static List<String> categoryList = Arrays.asList("衣裤","鞋子","箱包","手机","电脑","乐器","眼镜","生鲜","游戏","汽车");
    //分类->品牌
    private static List<String> clothesBrandList = Arrays.asList("JEANS","花花公子","Lee","唐狮","南极人","阿迪达斯","耐克","美特斯邦威","李宁","以纯");
    private static List<String> shoesBrandList = Arrays.asList("回力","匡威","万斯","特步","乔丹","阿迪达斯","耐克","匹克","李宁","安踏");
    private static List<String> bagBrandList = Arrays.asList("迪卡侬","木村耀司","稻草人","彪马","七匹狼","阿迪达斯","耐克","瑞戈","李宁","北极狐");
    private static List<String> phoneBrandList = Arrays.asList("苹果","华为","小米","三星","锤子","一加","vivo","oppo","诺基亚","魅族");
    private static List<String> computerBrandList = Arrays.asList("苹果","华为","小米","三星","联想","外星人","戴尔","华硕","惠普","神舟");
    private static List<String> musicalInstrumentsBrandList = Arrays.asList("奇美","天鹅","伶吟","敦煌","润扬","卢森","仙声","六美","俏娃宝贝","海之韵");
    private static List<String> glassesBrandList = Arrays.asList("灵视","暴龙","美特龙","智焦","曼特斯","COOLBAR","TROPhee","帕森","Maekin","曼特斯");
    private static List<String> foodBrandList = Arrays.asList("双汇","易果生鲜","鲜有汇聚","盒马","味央","云厨一站","豚之杰","王明公","卖鱼郎","闲田牧");
    private static List<String> gameBrandList = Arrays.asList("腾讯","网易","九成","世纪天城","暴雪","任天堂","西山居","索尼","育碧","柯乐美");
    private static List<String> carBrandList = Arrays.asList("奔驰","宝马","大众","特斯拉","马自达","丰田","本田","别克","奥迪","比亚迪");
    //分类->商品
    private static List<String> clothesProductList = Arrays.asList("连衣裙","牛仔裤","热裤","甩帽衫","卫衣","羽绒服","长裤","T恤","睡衣","连体服");
    private static List<String> shoesProductList = Arrays.asList("篮球鞋","跑步鞋","运动鞋","棉鞋","洞洞鞋","旅游鞋","凉鞋","帆布鞋","高跟鞋","小皮鞋");
    private static List<String> bagProductList = Arrays.asList("双肩包","单肩包","行李箱","腰包","钱包","卡包","布袋包","书包","木箱","皮箱");
    private static List<String> phoneProductList = Arrays.asList("青春版","plus","旗舰版","s","1","2","3","4","5","6");
    private static List<String> computerProductList = Arrays.asList("灵越","睿智","y","pro","air","小新","锐龙","极速","飞行堡垒","毁灭者");
    private static List<String> musicalInstrumentsProductList = Arrays.asList("古筝","二胡","琵琶","长笛","箫","吉他","钢琴","电子琴","手风琴","笛子");
    private static List<String> glassesProductList = Arrays.asList("近视镜","平镜","老花镜","太阳镜","变色镜","游泳眼镜","墨镜","3D眼镜","夜视镜","蛤蟆镜");
    private static List<String> foodProductList = Arrays.asList("鸡肉","猪肉","牛肉","苹果","草莓","樱桃","鲜奶","炼乳","白菜","土豆");
    private static List<String> gameProductList = Arrays.asList("竞技游戏","策略游戏","战争游戏","恐怖游戏","换装游戏","智力游戏","射击游戏","小游戏","格斗游戏","冒险游戏");
    private static List<String> carProductList = Arrays.asList("X1","X2","X3","X4","Q1","Q2","Q3","Q4","R1","R2");

    private static Map<String,List<String>> category2Brand = new HashMap<>(16);
    private static Map<String,List<String>> category2Product = new HashMap<>(16);
    static {
        //分类->品牌
        category2Brand.put("衣裤",clothesBrandList);
        category2Brand.put("鞋子",shoesBrandList);
        category2Brand.put("箱包",bagBrandList);
        category2Brand.put("手机",phoneBrandList);
        category2Brand.put("电脑",computerBrandList);
        category2Brand.put("乐器",musicalInstrumentsBrandList);
        category2Brand.put("眼镜",glassesBrandList);
        category2Brand.put("生鲜",foodBrandList);
        category2Brand.put("游戏",gameBrandList);
        category2Brand.put("汽车",carBrandList);
        //分类->商品
        category2Product.put("衣裤",clothesProductList);
        category2Product.put("鞋子",shoesProductList);
        category2Product.put("箱包",bagProductList);
        category2Product.put("手机",phoneProductList);
        category2Product.put("电脑",computerProductList);
        category2Product.put("乐器",musicalInstrumentsProductList);
        category2Product.put("眼镜",glassesProductList);
        category2Product.put("生鲜",foodProductList);
        category2Product.put("游戏",gameProductList);
        category2Product.put("汽车",carProductList);
    }

    @Override
    public Product getProduct() {
        Product product = new Product();
        product.setId(IdUtil.simpleUUID());
        product.setCategory(categoryList.get(RandomUtil.randomInt(10)));
        product.setBrand(category2Brand.get(product.getCategory()).get(RandomUtil.randomInt(10)));
        String name;
        if ("手机".equals(product.getCategory())) {
            name = product.getBrand() + category2Product.get(product.getCategory()).get(RandomUtil.randomInt(10));
        } else {
            name = category2Product.get(product.getCategory()).get(RandomUtil.randomInt(10));
        }
        product.setName(name);
        //价格均保留小数点后2位小数，但是由于es存储为double类型，对价格聚合时候会精度失真，因此此处价格乘以100，后续手动除以100处理[后续会对销售价聚合，因此销售价*100]
        product.setCostPrice(RandomUtil.randomBigDecimal(new BigDecimal("100"),new BigDecimal("10000")).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
        product.setSalePrice(new BigDecimal(String.valueOf(product.getCostPrice())).multiply(new BigDecimal("2")).multiply(new BigDecimal("100")).doubleValue());
        product.setStockCount(RandomUtil.randomInt(10,100));
        product.setCreateTime(new Date(RandomUtil.randomLong(1400000000000L,1560000000000L)));
        return product;
    }

    @Override
    public List<Product> listProduct() {
        int count = 100;
        List<Product> list = new ArrayList<>(count * 2);
        for (int i=0;i<count;i++) {
            list.add(this.getProduct());
        }
        return list;
    }

    /**
     * 单字段模糊查询
     * 会被分词
     * @param filed
     * @param value
     * @return
     */
    @Override
    public List<Product> findByFieldMatch(String filed, String value) {
        return this.esTemplate.queryForList(new NativeSearchQueryBuilder().withQuery(matchQuery(filed,value)).build(),Product.class);
    }

    @Override
    public Page<Product> findByFieldMatch(String filed, String value, Pageable pageable) {
        return this.esTemplate.queryForPage(new NativeSearchQueryBuilder().withQuery(matchQuery(filed,value)).withPageable(pageable).build(),Product.class);
    }

    /**
     * 多字段模糊查询
     * 会被分词
     * @param fileds
     * @param value
     * @return
     */
    @Override
    public List<Product> findByMultiFieldMatch(List<String> fileds, String value) {
        BoolQueryBuilder bqb = boolQuery();
        fileds.forEach(e -> bqb.should(QueryBuilders.matchQuery(e,value)));
        return this.esTemplate.queryForList(new NativeSearchQueryBuilder().withQuery(bqb).build(),Product.class);
    }

    @Override
    public Page<Product> findByMultiFieldMatch(List<String> fileds, String value, Pageable pageable) {
        BoolQueryBuilder bqb = boolQuery();
        fileds.forEach(e -> bqb.should(QueryBuilders.matchQuery(e,value)));
        return this.esTemplate.queryForPage(new NativeSearchQueryBuilder().withQuery(bqb).withPageable(pageable).build(),Product.class);
    }

    /**
     * 全文模糊查询
     * 会被分词
     * @param value
     * @return
     */
    @Override
    public List<Product> findByValue(String value) {
        return this.esTemplate.queryForList(new NativeSearchQueryBuilder().withQuery(queryStringQuery(value)).build(),Product.class);
    }

    @Override
    public Page<Product> findByValue(String value, Pageable pageable) {
        return this.esTemplate.queryForPage(new NativeSearchQueryBuilder().withQuery(queryStringQuery(value)).withPageable(pageable).build(),Product.class);
    }

    @Override
    public Page<Product> findByValue(String value,List<String> hightLightField, Pageable pageable) {
        HighlightBuilder.Field[] hfields = new HighlightBuilder.Field[0];
        if (!CollectionUtils.isEmpty(hightLightField)){
            hfields = new HighlightBuilder.Field[hightLightField.size()];
            for (int i = 0; i < hightLightField.size(); i++) {
                hfields[i] = new HighlightBuilder.Field(hightLightField.get(i)).preTags("<em style='color:red'>").postTags("</em>").fragmentSize(250);
            }
        }
        return this.esTemplate.queryForPage(
                new NativeSearchQueryBuilder()
                        .withQuery(queryStringQuery(value))
                        .withHighlightFields(hfields)
                        .withPageable(pageable)
                        .build(),
                Product.class,
                new SearchResultMapper(){
                    @Override
                    public <T> AggregatedPage<T> mapResults(SearchResponse searchResponse, Class<T> aClass, Pageable pageable) {
                        SearchHits hits = searchResponse.getHits();
                        return  Optional.ofNullable(searchResponse.getHits())
                                    .map(e -> {
                                        int total = (int) hits.totalHits;
                                        List<Product> list = new ArrayList<>(total);
                                        Arrays.stream(hits.getHits()).forEach(e1 -> {
                                            Map<String, Object> hitMap = e1.getSourceAsMap();
                                            Map<String, HighlightField> highlightFieldMap = e1.getHighlightFields();
                                            if (MapUtil.isNotEmpty(highlightFieldMap)) {
                                                highlightFieldMap.entrySet().forEach(e2 -> hitMap.put(e2.getKey(), e2.getValue().getFragments()[0].toString()));
                                            }
                                            Product product = BeanUtil.mapToBean(hitMap,Product.class,false);
                                            list.add(product);
                                        });
                                        return new AggregatedPageImpl(list,pageable,total);
                                    })
                                    .orElse(null);
//                        if (hits != null){
//                            int total = (int) hits.totalHits;
//                            List<Product> list = new ArrayList<>(total);
//                            Product product;
//                            for (SearchHit hit : hits){
//                                Map<String, Object> hitMap = hit.getSourceAsMap();
//                                Map<String, HighlightField> highlightFieldMap = hit.getHighlightFields();
//                                if (MapUtil.isNotEmpty(highlightFieldMap)) {
//                                    for (Map.Entry field : highlightFieldMap.entrySet()) {
//                                        hitMap.put(field.getKey().toString(), ((HighlightField) field.getValue()).getFragments()[0].toString());
//                                    }
//                                }
//                                product = BeanUtil.mapToBean(hitMap,Product.class,false);
//                                list.add(product);
//                            }
//                            return new AggregatedPageImpl(list,pageable,total);
//                        }else {
//                            return null;
//                        }
                    }
                }
               );
    }

    /**
     * 统计聚合
     * 汇总所有分类商品的总金额
     */
    @Override
    public List<JSONObject> findAllCategorySumPrice() {
        //聚合默认是10条,若想聚合全部：size(0)
        TermsAggregationBuilder tab = AggregationBuilders.terms("category").field("category");
        SumAggregationBuilder sab = AggregationBuilders.sum("salePriceSum").field("salePrice");
        tab.subAggregation(sab);
        Aggregations agg = this.esTemplate.query(
                new NativeSearchQueryBuilder().addAggregation(tab).build(),
                searchResponse -> searchResponse.getAggregations()
        );
        StringTerms terms = agg.get("category");
        List<StringTerms.Bucket> buckets = terms.getBuckets();
        if (!CollectionUtils.isEmpty(buckets)){
            List<JSONObject> list = new ArrayList<>(16);
            JSONObject json;
            for (StringTerms.Bucket bk : buckets){
                json = new JSONObject();
                json.put("key",bk.getKeyAsString());
                json.put("count",bk.getDocCount());
                double costPriceSum = ((InternalSum) bk.getAggregations().get("salePriceSum")).getValue();
                json.put("salePriceSum",new BigDecimal(String.valueOf(costPriceSum)).divide(new BigDecimal("100")));
                list.add(json);
            }
            return list;
        }else {
            return null;
        }
    }

}
