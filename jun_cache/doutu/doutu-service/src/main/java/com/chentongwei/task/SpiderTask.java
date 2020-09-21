package com.chentongwei.task;

import com.alibaba.fastjson.JSON;
import com.chentongwei.entity.doutu.io.PictureIO;
import com.chentongwei.entity.doutu.vo.CatalogVO;
import com.chentongwei.service.doutu.ICatalogService;
import com.chentongwei.service.doutu.IPictureService;
import com.chentongwei.util.HttpClientHelper;
import com.chentongwei.util.SpiderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.util.CollectionUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 爬虫工具类
 *
 * @author TongWei.Chen 2017-05-17 10:20:53
 */
@org.springframework.stereotype.Component
@Configurable
@EnableScheduling
public class SpiderTask {

    @Autowired
    private ICatalogService catalogService;
    @Autowired
    private IPictureService pictureService;

    // 每天11：30执行一次
//    @Scheduled(cron = "0 35 16 * * ?")
//    @Scheduled(cron = "0/5 * * * * ?")
    public void run() {
        // 查询所有目录
//        List<CatalogVO> list = catalogService.list();
        List<CatalogVO> list = new ArrayList<>();
        CatalogVO catalogVO = new CatalogVO();
        catalogVO.setId(1);
        catalogVO.setName("小岳岳");
        list.add(catalogVO);
        if (CollectionUtils.isEmpty(list)) {
            return;
        }
        ExecutorService cachedThreadPool = Executors.newFixedThreadPool(list.size());
        for (final CatalogVO catalog : list) {
            cachedThreadPool.execute(new SpiderThread(catalog, pictureService));
        }
        cachedThreadPool.shutdown();
    }
}

class SpiderThread implements Runnable {
    private static final Logger THREAD_LOG = LoggerFactory.getLogger(SpiderThread.class);

    private CatalogVO catalog;
    private IPictureService pictureService;

    public SpiderThread(CatalogVO catalog, IPictureService pictureService) {
        this.catalog = catalog;
        this.pictureService = pictureService;
    }

    @Override
    public void run() {
        int pages = 50;
        String image = "";
        String text = "";
        for (int i = pages; i > 0; i--) {
            String url = String.format("https://www.doutula.com/search?type=photo&more=1&keyword=%s&page=%s", catalog.getName(), i);
            String result = HttpClientHelper.get(url);
            if (result != null) {
                int from = 0;
                int pFrom = 0;
                while (true) {
                    from = result.indexOf("data-backup=");
                    int end = result.indexOf("!dtb", from);
                    if (-1 == from) {
                        break;
                    }
                    image = result.substring(from + 13, end);

                    pFrom = result.indexOf("<p style=\"height:10px; text-indent: -9999px;\">");
                    int pEnd = result.indexOf("</p>", pFrom);
                    if (-1 == pFrom) {
                        break;
                    }
                    text = result.substring(pFrom + 46, pEnd);
                    result = result.substring(pEnd);
                    deal(image, text);
                }
            }
        }
    }

    private void deal(String imgUrl, String tagName) {
        THREAD_LOG.info(JSON.toJSONString(catalog));
        byte[] bytes = SpiderUtil.getImageFromNetByUrl(imgUrl);
        if (bytes != null) {
            try (InputStream is = new ByteArrayInputStream(bytes)) {
                //判断是不是图片
                Image image = ImageIO.read(is);
                if (image == null) {
                    System.out.println("不是图片文件");
                    return;
                }
                THREAD_LOG.info(imgUrl);
                PictureIO pictureIO = new PictureIO();
                pictureIO.setCatalogId(catalog.getId());
                pictureIO.setBytes(bytes);
                pictureIO.setStatus(1);  //爬虫获取的
                pictureIO.setSuffix(imgUrl.substring(imgUrl.lastIndexOf(".")));
                pictureIO.setTagList(Arrays.asList(tagName));
                pictureService.saveForSpider(pictureIO);
            } catch (Exception e) {
                e.printStackTrace();
                THREAD_LOG.info("爬虫的deal方法报错：", e.getMessage());
            }
        }
    }
}