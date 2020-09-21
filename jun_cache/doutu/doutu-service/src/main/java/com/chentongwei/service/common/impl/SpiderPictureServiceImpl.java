package com.chentongwei.service.common.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chentongwei.entity.common.io.SpiderPictureIO;
import com.chentongwei.entity.doutu.io.PictureIO;
import com.chentongwei.service.common.ISpiderPictureService;
import com.chentongwei.service.doutu.IPictureService;
import com.chentongwei.util.HttpClientHelper;
import com.chentongwei.util.SpiderUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;

/**
 * 爬虫--图片
 *
 * @author TongWei.Chen 2017-06-21 11:30:47
 */
@Service
public class SpiderPictureServiceImpl implements ISpiderPictureService {

    @Autowired
    private IPictureService pictureService;

    @Override
    public void xuanBiaoQing(SpiderPictureIO spiderPictureIO) {
        String url = String.format("http://www.xuanbiaoqing.com/search/listajax/?page=%s&keyword=%s", spiderPictureIO.getPage(), spiderPictureIO.getCatalogName());
        String result = HttpClientHelper.get(url);
        JSONObject jsonObject = JSON.parseObject(result);
        JSONObject dataObj = jsonObject.getJSONObject("data");
        JSONArray list = dataObj.getJSONArray("list");
        for(Object obj : list) {
            JSONObject json = (JSONObject)obj;
            String pictureTag = json.get("title").toString();
            String pictureUrl = json.get("pic").toString();
            if(StringUtils.isEmpty(pictureTag)) {
                pictureTag = spiderPictureIO.getCatalogName();
            }
            byte[] bytes = SpiderUtil.getImageFromNetByUrl(pictureUrl);
            PictureIO pictureIO = new PictureIO();
            pictureIO.setCatalogId(spiderPictureIO.getCatalogId());
            pictureIO.setBytes(bytes);
            pictureIO.setStatus(1);  //爬虫获取的
            pictureIO.setSuffix(pictureUrl.substring(pictureUrl.lastIndexOf(".")));
            pictureIO.setTagList(Arrays.asList(pictureTag));

            pictureService.saveForSpider(pictureIO);
        }
    }
}
