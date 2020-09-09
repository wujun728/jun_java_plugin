package com.fyfeng.netty.upload.example;

import com.squareup.okhttp.*;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

/**
 * Created by yuzw on 2015/10/13.
 */
public class TestUploadClient2 {

    private static final String URL = "http://127.0.0.1:8080/hsserv/services";

    private static final String IMGUR_CLIENT_ID = "...";

    public static final MediaType MEDIA_TYPE_MARKDOWN
            = MediaType.parse("text/x-markdown; charset=utf-8");

    private final OkHttpClient client = new OkHttpClient();

    public static void main(String[] args) {

        TestUploadClient2 test = new TestUploadClient2();
        try {
            test.request();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void request() throws IOException {
        File file = new File("D:\\Test\\device-2015-10-10-150104.png");
        String postBody = FileUtils.readFileToString(file);// "Hello, Server!";

        Request request = new Request.Builder()
                .header("Authorization", "Client-ID " + IMGUR_CLIENT_ID)
                .header("Connection", "Close")
                .url(URL)
                .post(RequestBody.create(MEDIA_TYPE_MARKDOWN, postBody))
                .build();

        Response response = client.newCall(request).execute();
        if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

        System.out.println(response.body().string());

    }



}
