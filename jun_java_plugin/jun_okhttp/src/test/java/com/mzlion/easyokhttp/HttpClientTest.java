package com.mzlion.easyokhttp;

import com.mzlion.easyokhttp.response.HttpResponse;
import com.mzlion.easyokhttp.response.handle.StringDataHandler;
import org.junit.Test;

/**
 * @author kudo on 2017-10-31 13:52:57
 */
public class HttpClientTest {

    @Test
    public void post() throws Exception {
//        HttpResponse httpResponse = HttpClient
//                .post("https://project.mzlion.com/easy-okhttp/api/post/simple")
//                .param("blog", "https://www.mzlion.com")
//                .param("author", "mzlion")
//                .param("location", "上海")
//                .param("projectName", "easy-okhttp")
//                .param("projectUrl", "https://git.oschina.net/mzllon/easy-okhttp")
//                .header("customHeader1", "customValue1")//自定义header
//                .execute();
        HttpResponse httpResponse = HttpClient.post("http://localhost:8060/c1-svc-server/lan/svc/yjlmk/hb_activity/test2")
                .param("flag", "flag")
                .execute();
        System.out.println("http code = " + httpResponse.getHttpCode());
        System.out.println("http message = " + httpResponse.getErrorMessage());

        String asString = httpResponse.custom(StringDataHandler.create(), false);
        System.out.println("asString = " + asString);
    }

    @Test
    public void post2() throws Exception {
        HttpResponse httpResponse = HttpClient.post("http://localhost:8060/c1-svc-server/lan/svc/yjlmk/hb_activity/stop")
                .param("flag", "flag")
                .execute();
        System.out.println("http code = " + httpResponse.getHttpCode());
        System.out.println("http message = " + httpResponse.getErrorMessage());

        String asString = httpResponse.custom(StringDataHandler.create(), false);
        System.out.println("asString = " + asString);


    }

}