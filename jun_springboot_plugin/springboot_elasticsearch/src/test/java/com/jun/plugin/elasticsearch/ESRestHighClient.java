package com.jun.plugin.elasticsearch;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.jun.plugin.elasticsearch.highclient.RestHighLevelClientService;

import java.io.IOException;
import java.util.Date;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ESRestHighClient {


    @Autowired
    RestHighLevelClientService service;

//    @Before
//    public void testRestHighClinet() {
//
//        RestClientBuilder restClientBuilder = RestClient.builder(
//                new HttpHost("localhost", 9200, "http")
//        );
//
//        Header[] defaultHeaders = new Header[]{
//                new BasicHeader("Accept", "*/*"),
//                new BasicHeader("Charset", "UTF-8"),
//                new BasicHeader("E_TOKEN", "esestokentoken")
//        };
//        restClientBuilder.setDefaultHeaders(defaultHeaders);
//
//        restClientBuilder.setFailureListener(new RestClient.FailureListener(){
//            @Override
//            public void onFailure(Node node) {
//                System.out.println("监听失败");
//            }
//        });
//
//        restClientBuilder.setRequestConfigCallback(builder ->
//                builder.setConnectTimeout(5000).setSocketTimeout(15000));
//
//        RestHighLevelClient highClient = new RestHighLevelClient(restClientBuilder);
//
//        restHighLevelClient = highClient;
//        service = new RestHighLevelClientService();
//    }

    @Test
    public void testAddIndex() {
        String settings = "" +
                "  {\n" +
                "      \"number_of_shards\" : \"2\",\n" +
                "      \"number_of_replicas\" : \"0\"\n" +
                "   }";

        String mappings = "" +
                "{\n" +
                "    \"properties\": {\n" +
                "      \"proId\" : {\n" +
                "        \"type\": \"keyword\",\n" +
                "        \"ignore_above\": 64\n" +
                "      },\n" +
                "      \"name\" : {\n" +
                "        \"type\": \"text\",\n" +
                "        \"analyzer\": \"ik_max_word\", \n" +
                "        \"search_analyzer\": \"ik_smart\",\n" +
                "        \"fields\": {\n" +
                "          \"keyword\" : {\"ignore_above\" : 256, \"type\" : \"keyword\"}\n" +
                "        }\n" +
                "      },\n" +
                "      \"mytimestamp\" : {\n" +
                "        \"type\": \"date\",\n" +
                "        \"format\": \"epoch_millis\"\n" +
                "      },\n" +
                "      \"createTime\" : {\n" +
                "        \"type\": \"date\",\n" +
                "        \"format\": \"yyyy-MM-dd HH:mm:ss\"\n" +
                "      }\n" +
                "    }\n" +
                "}";

        try {
            service.createIndex("idx_pro", settings, mappings);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("创建索引失败");
        }

    }

    @Test
    public void deleteIndex() throws IOException {
        service.deleteIndex("idx_pro");
    }

    @Test
    public void addDoc() {
    }

    @Test
    public void bulk() throws IOException {
        //不需要[]，奇不奇怪
        String bulkVal = "[" +
                "\n" +
                "  {\n" +
                "  \"proId\" : \"1\",\n" +
                "  \"name\" : \"冬日工装裤\",\n" +
                "  \"timestamp\" : 1576312053946,\n" +
                "  \"createTime\" : \"2019-12-12 12:56:56\"\n" +
                "  },\n" +
                "  {\n" +
                "  \"proId\" : \"2\",\n" +
                "  \"name\" : \"冬日羽绒服\",\n" +
                "  \"timestamp\" : 1576313210024,\n" +
                "  \"createTime\" : \"2019-12-10 10:50:50\"\n" +
                "  },\n" +
                "  {\n" +
                "  \"proId\" : \"3\",\n" +
                "  \"name\" : \"花花公子外套\",\n" +
                "  \"timestamp\" : 1576313239816,\n" +
                "  \"createTime\" : \"2019-12-19 12:50:50\"\n" +
                "  },\n" +
                "  {\n" +
                "  \"proId\" : \"4\",\n" +
                "  \"name\" : \"花花公子羽绒服\",\n" +
                "  \"timestamp\" : 1576313264391,\n" +
                "  \"createTime\" : \"2019-12-12 11:56:56\"\n" +
                "  },\n" +
                "  {\n" +
                "  \"proId\" : \"5\",\n" +
                "  \"name\" : \"花花公子暖心羽绒服\",\n" +
                "  \"timestamp\" : 1576313264491,\n" +
                "  \"createTime\" : \"2019-12-19 11:56:56\"\n" +
                "  },\n" +
                "  {\n" +
                "  \"proId\" : \"6\",\n" +
                "  \"name\" : \"花花公子帅气外套\",\n" +
                "  \"timestamp\" : 1576313264691,\n" +
                "  \"createTime\" : \"2019-12-19 15:56:56\"\n" +
                "  },\n" +
                "  {\n" +
                "  \"proId\" : \"7\",\n" +
                "  \"name\" : \"冬天暖心羽绒服\",\n" +
                "  \"timestamp\" : 1576313265491,\n" +
                "  \"createTime\" : \"2019-12-19 17:56:56\"\n" +
                "  },\n" +
                "  {\n" +
                "  \"proId\" : \"8\",\n" +
                "  \"name\" : \"冬天超级暖心羽绒服\",\n" +
                "  \"timestamp\" : 1576313275491,\n" +
                "  \"createTime\" : \"2019-12-20 17:56:56\"\n" +
                "  },\n" +
                "  {\n" +
                "  \"proId\" : \"9\",\n" +
                "  \"name\" : \"\",\n" +
                "  \"timestamp\" : 1576313275491,\n" +
                "  \"createTime\" : \"2019-12-20 17:56:56\"\n" +
                "  },\n" +
                "  {\n" +
                "  \"proId\" : \"9\",\n" +
                "  \"name\" : [],\n" +
                "  \"timestamp\" : 1576313275491,\n" +
                "  \"createTime\" : \"2019-12-20 17:56:56\"\n" +
                "  }\n" +
                "]";
        service.importAll("idx_pro", true, bulkVal);
    }

    @Test
    public void testSearch() {
    }

    @Test
    public void testMain() {
        Date date = new Date();
        System.out.println(date.getTime());
    }

}
