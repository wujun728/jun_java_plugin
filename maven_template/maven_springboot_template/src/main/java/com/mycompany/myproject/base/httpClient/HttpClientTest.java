package com.mycompany.myproject.base.httpClient;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.util.ArrayList;
import java.util.List;

public class HttpClientTest {

    public static void main(String[] args) throws Exception{

        CloseableHttpClient httpClient = HttpClients.createDefault();

        HttpPost httpPost = new HttpPost("https://api.hero-cloud.com:8243/token");

        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(35000)
                .setConnectionRequestTimeout(35000)
                .setSocketTimeout(6000)
                .build();
        httpPost.setConfig(requestConfig);

        httpPost.addHeader("Authorization","Basic RHNCMThXZWpRNTBfU1Z6WXZxc1RhSWExS1pRYTpoYWFqbFRFQWdmY3p5Nk1uaHNRQUZWS2JCSEVh");

        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("grant_type","password"));
        params.add(new BasicNameValuePair("username","jansie-hyatt-dev"));
        params.add(new BasicNameValuePair("password","Hyatt123*"));

        httpPost.setEntity(new UrlEncodedFormEntity(params));

        CloseableHttpResponse httpResponse = httpClient.execute(httpPost);

        HttpEntity entity = httpResponse.getEntity();
        String result = EntityUtils.toString(entity);

        // {"access_token":"dd88e2ee-f9fc-3798-9c12-6404d17320ef","refresh_token":"05b95a38-7ce3-3a6e-8a59-46968d3d8366","scope":"default","token_type":"Bearer","expires_in":2832}
        System.out.println(result);
    }


}
