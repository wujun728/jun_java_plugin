package com.jun.plugin.elasticsearch;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.entity.ContentType;
import org.apache.http.message.BasicHeader;
import org.apache.http.nio.entity.NStringEntity;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.client.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ESRestClientTest {


    @Test
    public void initClient() {
        RestClientBuilder clientBuilder = RestClient.builder(new HttpHost("localhost", 9200, "http"));

        Header[] defaultHeaders = new Header[]{
                //new BasicHeader(" Content-Type", "application/json"),
                new BasicHeader("Accept", "*/*"),
                new BasicHeader("Charset", "UTF-8"),
                new BasicHeader("E_TOKEN", "esestokentoken")
        };
        clientBuilder.setDefaultHeaders(defaultHeaders);

        //设置超时返回时间和建立连接超时时间
        clientBuilder.setRequestConfigCallback(builder ->
                builder.setConnectTimeout(5000).setConnectTimeout(15000));

        //builder 的失败监听
        clientBuilder.setFailureListener(new RestClient.FailureListener(){
            @Override
            public void onFailure(Node node) {
                System.out.println("node:" + node.getName()+ "失败了");
            }
        });



        RestClient client = clientBuilder.build();

        String es_query = "{" +
                "  \"query\": {" +
                "    \"bool\": {" +
                "      \"must\": [" +
                "        {\"match\":{" +
                "          \"title\" : \"apple\"" +
                "        } }," +
                "        {\"range\": {" +
                "          \"price\": {" +
                "            \"gte\": 0.4," +
                "            \"lte\": 1000" +
                "          }" +
                "        }}" +
                "      ]" +
                "    }" +
                "  }" +
                "}";

        Request request = new Request("GET", "/items/_search");
        request.addParameter("pretty", "true");


        HttpEntity entity = new NStringEntity(es_query, ContentType.APPLICATION_JSON);
        request.setEntity(entity);

        try {
            Response response = client.performRequest(request);
            String token = response.getHeader("E-TOKEN");
            System.out.println("header: " + token);

            HttpEntity responseEntity = response.getEntity();
            String res = EntityUtils.toString(responseEntity);

            JSONObject js = JSON.parseObject(res);
            JSONArray data = js.getJSONObject("hits").getJSONArray("hits");

            System.out.println(data);

            for (int i = 0; i < data.size() ; i++) {
                JSONObject source = data.getJSONObject(i).getJSONObject("_source");
                System.out.println(source.toString());
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                client.close();
            } catch(IOException e) {
                e.printStackTrace();
            }
        }

    }
}
