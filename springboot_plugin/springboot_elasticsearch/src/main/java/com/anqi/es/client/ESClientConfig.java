//package com.anqi.es.client;
//
//import org.apache.http.Header;
//import org.apache.http.HttpHost;
//import org.apache.http.message.BasicHeader;
//import org.elasticsearch.client.Node;
//import org.elasticsearch.client.RestClient;
//import org.elasticsearch.client.RestClientBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
///**
// *  将 RestClinet 封装为单例交由 Spring 容易管理，不用关闭连接 建立长连接使用
// *
// *  如果不是单例的话 每次调用都会创建连接 不关闭会将导致资源被耗尽
// */
//
//@Configuration
//public class ESClientConfig {
//
//    /**
//     * 配置 RestClientBuilder
//     * @return
//     */
//    @Bean
//    public RestClientBuilder restClientBuilder() {
//        RestClientBuilder clientBuilder =
//                RestClient.builder(new HttpHost("localhost", 9200, "http"));
//
//        Header[] defaultHeaders = new Header[]{
//                //new BasicHeader(" Content-Type", "application/json"),
//                new BasicHeader("Accept", "*/*"),
//                new BasicHeader("Charset", "UTF-8"),
//                new BasicHeader("E_TOKEN", "esestokentoken")
//        };
//        clientBuilder.setDefaultHeaders(defaultHeaders);
//
//        //设置超时返回时间和建立连接超时时间
//        clientBuilder.setRequestConfigCallback(builder ->
//                builder.setConnectTimeout(5000).setConnectTimeout(15000));
//
//        //builder 的失败监听
//        clientBuilder.setFailureListener(new RestClient.FailureListener(){
//            @Override
//            public void onFailure(Node node) {
//                System.out.println("node:" + node.getName()+ "失败了");
//            }
//        });
//
//        return clientBuilder;
//    }
//
//    /**
//     * 创建 RestClient
//     * @param restClientBuilder
//     * @return
//     */
//    @Bean
//    public RestClient restClient(RestClientBuilder restClientBuilder) {
//        return restClientBuilder.build();
//    }
//
//}
