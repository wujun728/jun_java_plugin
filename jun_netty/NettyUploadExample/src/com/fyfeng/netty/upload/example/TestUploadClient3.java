package com.fyfeng.netty.upload.example;

import com.squareup.okhttp.*;

import java.io.File;
import java.io.IOException;

/**
 * Created by yuzw on 2015/10/13.
 */
public class TestUploadClient3 {

    private static final String IMGUR_CLIENT_ID = "...";

    private final OkHttpClient client = new OkHttpClient();

    public static void main(String[] args) {

        TestUploadClient3 test = new TestUploadClient3();
        try {
            test.upload();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void upload() throws IOException {

        final String FORM_ACTION = "http://127.0.0.1:8080/form/upload";//文件上传路径
        final File file = new File("D:\\Test\\device-2015-10-10-150104.png");//要上传的文件

        MediaType mediaType = MediaType.parse(contentType(file.getAbsolutePath()));

        // Use the imgur image upload API as documented at https://api.imgur.com/endpoints/image
        RequestBody requestBody = new MultipartBuilder()
                .type(MultipartBuilder.FORM)
                .addPart(
                        Headers.of("Content-Disposition", "form-data; name=\"title\""),
                        RequestBody.create(null, "Square Logo"))
               /* .addPart(
                        Headers.of("Content-Disposition", "form-data; name=\"image\";filename=\"image.jpg\""),
                        RequestBody.create(mediaType, file))*/
                .build();

        Request request = new Request.Builder()
                .header("Authorization", "Client-ID " + IMGUR_CLIENT_ID)
                .header("Connection", "Close")
                .url(FORM_ACTION)
                .post(requestBody)
                .build();

        Response response = client.newCall(request).execute();
        if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

        System.out.println(response.body().string());
    }

    private String contentType(String path) {
        if (path.endsWith(".png")) return "image/png";
        if (path.endsWith(".jpg")) return "image/jpeg";
        if (path.endsWith(".jpeg")) return "image/jpeg";
        if (path.endsWith(".gif")) return "image/gif";
        if (path.endsWith(".html")) return "text/html; charset=utf-8";
        if (path.endsWith(".txt")) return "text/plain; charset=utf-8";
        return "application/octet-stream";
    }
}
