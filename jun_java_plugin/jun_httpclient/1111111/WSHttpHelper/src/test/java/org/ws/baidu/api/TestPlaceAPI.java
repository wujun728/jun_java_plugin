package org.ws.baidu.api;

import junit.framework.TestCase;
import org.ws.httphelper.WSHttpHelper;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 15-12-11.
 */
public class TestPlaceAPI extends TestCase {
    public void testRequest()throws Exception{
        PlaceAPI api = new PlaceAPI();
        api.addParameter("q","饭店");
        api.addParameter("region","北京");
        System.out.println(api.execute().getBody().toString());
        System.out.println(api.getContext().getUrl());
    }

    public void testDoGet()throws Exception{
        Map<String,Object> param = new HashMap<String,Object>();
        param.put("uid","5a8fb739999a70a54207c130");
        param.put("ak","E4805d16520de693a3fe707cdc962045");
        param.put("output","json");
        param.put("scope","2");
        Object obj=WSHttpHelper.doGetHtml("http://api.map.baidu.com/place/v2/detail", param);
        System.out.print(obj.toString());
    }
}
