package com.test.client.choice.choice02;

import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author CYX
 * @create 2018-08-04-11:01
 */
public class TestClient {

    public static void main(String[] args) {

        URL url = null;
        HttpURLConnection http = null;

        try {
            url = new URL("http://0.0.0.0:8282/doHelloWorld");
            singleCall(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 单次调用
     */
    private static void singleCall(URL url) throws Exception {

        HttpURLConnection http = null;
        System.out.println("http post start !!!");
        Long startTime = System.currentTimeMillis();
        http = (HttpURLConnection) url.openConnection();

        // ************************************************************
        JSONObject authorityJson = new JSONObject();
        authorityJson.put("orgId", "yuanbao");

        JSONObject requestJson = new JSONObject();
        requestJson.put("data", authorityJson);
        requestJson.put("token", "asdaopsd89as0d8as7dasdas-=8a90sd7as6dasd");
        requestJson.put("desc", "123456");
        // ************************************************************

        StringBuffer sb = new StringBuffer();
        sb.append(requestJson.toString());
        System.out.println("发送消息 : " + sb.toString());
        String result = HttpClient.doPost(sb.toString(), 30000000, http);
        System.out.println("调用时间 : " + (System.currentTimeMillis() - startTime) + "ms");
        System.out.println("返回数据 : " + result);
        Thread.sleep(500);


    }


    /**
     * 循环调用
     */
    private static void loopCall(URL url) throws Exception {
        HttpURLConnection http = null;
        for (int i = 0; i < 1; i++) {
            System.out.println("http post start !!!");
            Long startTime = System.currentTimeMillis();
            http = (HttpURLConnection) url.openConnection();

            // ************************************************************
            JSONObject authorityJson = new JSONObject();
            authorityJson.put("orgId", "yuanbao");

            JSONObject requestJson = new JSONObject();
            requestJson.put("data", authorityJson);
            requestJson.put("token", "asdaopsd89as0d8as7dasdas-=8a90sd7as6dasd");
            requestJson.put("desc", "");
            // ************************************************************

            StringBuffer sb = new StringBuffer();
            sb.append(requestJson.toString());
            System.out.println("发送消息 : " + sb.toString());
            String result = HttpClient.doPost(sb.toString(), 30000000, http);
            System.out.println("调用时间 : " + (System.currentTimeMillis() - startTime) + "ms");
            System.out.println("返回数据 : " + result);
            Thread.sleep(500);
        }

    }

}
