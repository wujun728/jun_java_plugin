package com.jun.plugin.elasticsearch.highclient;

import com.alibaba.fastjson.JSON;
import com.jun.plugin.elasticsearch.DemoEsApplication;
import com.jun.plugin.elasticsearch.entity.Cloth;
import com.jun.plugin.elasticsearch.highclient.RestHighLevelClientService;
import com.jun.plugin.elasticsearch.util.SnowflakeIdWorker;

import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;

//有RunWith才会有ioc容器
@SpringBootTest(classes = DemoEsApplication.class)
@RunWith(SpringRunner.class)
@ComponentScan("com.anqi.es")
public class RestHighLevelClientServiceTest {

    @Autowired
    RestHighLevelClientService service;

    @Test
    public void createIndex() throws IOException{
        String settings =
                "{\n" +
                        " \"number_of_shards\" : 1,\n" +
                        " \"number_of_replicas\" : 0\n" +
                        " }\n" ;


        //设置 id 为 keyword 不分词，用来精准匹配，存放主键 可以设置 index : true 来让属性只存储，不会被查到
        String mappings =
                "{\n" +
                        "  \"properties\": {\n" +
                        "   \"id\": {\n" +
                        "    \"type\": \"keyword\"\n" +
                        "   },\n" +
                        "   \"name\": {\n" +
                        "    \"type\": \"text\",\n" +
                        "     \"analyzer\": \"ik_max_word\"\n" +
                        "   },\n" +
                        "   \"desc\": {\n" +
                        "    \"type\": \"text\",\n" +
                        "    \"analyzer\": \"ik_max_word\"\n" +
                        "   },\n" +
                        "    \"price\": {\n" +
                        "     \"type\": \"double\"\n" +
                        "   },\n" +
                        "   \"date\": {\n" +
                        "    \"format\": \"yyyy-MM-dd HH:mm:ss||yyyy-MM-dd||epoch_millis\",\n" +
                        "     \"type\": \"date\"\n" +
                        "   },\n" +
                        "   \"num\": {\n" +
                        "    \"type\": \"integer\"\n" +
                        "     }\n" +
                        "  }\n" +
                        "}";
        CreateIndexResponse response = service.createIndex("idx_cloth", settings, mappings);
        if (response.isAcknowledged()) {
            System.out.println("创建成功");
        }
    }

    @Test
    public void deleteIndex() throws IOException{
        AcknowledgedResponse response = service.deleteIndex("idx_cloth");
        if (response.isAcknowledged()) {
            System.out.println("删除成功");
        }
    }

    @Test
    public void indexExists() throws IOException{
        System.out.println(service.indexExists("idx_tt"));
    }


    @Test
    public void addDoc() throws IOException {
        SnowflakeIdWorker idWorker = new SnowflakeIdWorker(0, 0);
        Cloth cloth = new Cloth(idWorker.nextId()+"","新版日系毛衣", "潮流前线等你来Pick!", 50, 199.99, new Date());
        String source = JSON.toJSONString(cloth);
        IndexResponse response = service.addDoc("idx_cloth", source);
        System.out.println(response.status());
    }

    @Test
    public void search() throws IOException{
        SearchResponse response = service.search("name", "毛衣", 0, 30, "idx_cloth");
        Arrays.asList(response.getHits().getHits())
                .forEach(e -> System.out.println(e.getSourceAsString()));
    }

    @Test
    public void termSearch() throws IOException{
        SearchResponse response = service.termSearch("name", "nike潮流毛衣", 0, 50);
        SearchHits hits = response.getHits();
        for (SearchHit hit : hits) {
            System.out.println(hit.getSourceAsString());
        }
    }

    @Test
    public void deleteDoc() throws IOException{
        String id = "yvJ_Q24BymdyZW22Os2D";
        SearchResponse search = service.search("id", "2", 0, 5, "idx_cloth");

        for (SearchHit hit : search.getHits().getHits()) {
            id = hit.getId();
        }
        DeleteResponse response = service.deleteDoc("idx_cloth", id);
        if (response.status().equals(RestStatus.OK)) {
            System.out.println("删除成功");
        }
    }

//    @Test
//    public void importAll() throws IOException{
//        List<Cloth> list = buildJson();
//
//        String[] cloths = new String[list.size()];
//
//        for (int i = 0; i < list.size(); i++) {
//            cloths[i] = JSON.toJSONString(list.get(i));
//        }
//
//        for (String cloth : cloths) {
//            System.out.println(cloth);
//        }
//
//        BulkResponse bulk = service.importAll("idx_cloth", true, cloths);
//
//        if (bulk.hasFailures()) {
//            System.out.println("批量失败");
//            System.out.println(bulk.buildFailureMessage());
//        }
//
//    }
//
//    private List<Cloth> buildJson(){
//        SnowflakeIdWorker idWorker = new SnowflakeIdWorker(0, 0);
//
//        List<Cloth> cloths = new ArrayList<>();
//
//        String[] tags = new String[]{"nike","阿迪达斯","阿迪达斯三叶草","鸿星尔克",
//                "乔丹","飞人乔丹","哈雷乔丹","cba","法国老人头","特步","花花公子","海澜之家"};
//
//        String[] adj = new String[]{"性感","宽松","潮流","fashion","nice","热卖","新版"};
//        String[] cls = new String[]{"半袖","衬衫","外套","跑鞋","运动衣","毛衣","长裤","棉裤","背心"};
//
//
//        String[] descPre = new String[]{"双十一来袭,","618活动大促销,","店面到期亏本清仓,","换季大甩卖,","打造潮流攻势,"};
//
//        String[] descAft = new String[]{"商品满100减五十!","买一送一，买不了吃亏买不了上当!","关注店铺收藏商品即可立减五十!","包邮到家包邮到家!"};
//
//        Random random = new Random();
//        DecimalFormat df = new DecimalFormat( "0.00");
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//
//        for (int i = 0; i < 100; i++) {
//            cloths.add(new Cloth(idWorker.nextId()+"",
//                    tags[random.nextInt(tags.length)] + adj[random.nextInt(adj.length)] + cls[random.nextInt(cls.length)],
//                    descPre[random.nextInt(descPre.length)] + descAft[random.nextInt(descAft.length)],
//                    random.nextInt(200),Double.valueOf(df.format(random.nextDouble()*300)), new Date()
//            ));
//        }
//
//        return cloths;
//    }
}