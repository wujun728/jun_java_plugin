package com.fyfeng.netty.upload.example;

import com.squareup.okhttp.*;

import javax.net.ssl.*;
import java.io.File;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * Created by yuzw on 2015/10/13.
 */
public class TestUploadClient {

    private static final String IMGUR_CLIENT_ID = "...";


    private static final OkHttpClient client = new OkHttpClient();

    public static void main(String[] args) {


        try {
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, new TrustManager[]{new MyX509TrustManager()}, new java.security.SecureRandom());
            client.setSslSocketFactory(sslContext.getSocketFactory());
        } catch (NoSuchAlgorithmException | KeyManagementException e) {
            e.printStackTrace();
        }

        client.setHostnameVerifier(new MyHostnameVerifier());

        TestUploadClient test = new TestUploadClient();
        try {
            test.upload();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void upload() throws IOException {

        //https://127.0.0.1:8080/hsserv/upload
        //https://123.57.9.70/hsserv/upload
        final String FORM_ACTION = "http://127.0.0.1:8080/hsserv/upload";//文件上传路径
        //【批量下载】C++Primer.Plus（第6版）中文版等.zip
        //ideaIC-14.1.5.exe
        //jdk-8u60-windows-x64.exe
        final File file = new File("D:\\Pictures\\搞笑图片\\9b17754bjw1dz2ybsr9coj.jpg");//要上传的文件

        String fileName = file.getName();
        MediaType mediaType = MediaType.parse(contentType(file.getAbsolutePath()));


        // Use the imgur image upload API as documented at https://api.imgur.com/endpoints/image
        RequestBody requestBody = new MultipartBuilder()
                .type(MultipartBuilder.FORM)
                .addPart(
                        Headers.of("Content-Disposition", "form-data; name=\"title\""),
                        RequestBody.create(null, "Square Logo"))
                .addPart(
                        Headers.of("Content-Disposition", "form-data; name=\"image\";filename=\""+fileName+"\""),
                        RequestBody.create(mediaType, file))
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

    static class MyX509TrustManager implements X509TrustManager {

        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            // Log.d(tag, "check client trusted. authType=" + authType);
        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            // Log.d(tag, "check servlet trusted. authType=" + authType);
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            // Log.d(tag, "get acceptedissuer");
            return null;
        }

    }

    static class MyHostnameVerifier implements HostnameVerifier {

        @Override
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }

    }
}
