package com.chentongwei.task;

import com.chentongwei.cache.redis.IBasicCache;
import com.chentongwei.cache.redis.ISetCache;
import com.chentongwei.common.constant.RedisEnum;
import com.chentongwei.dao.doutu.IPictureDAO;
import com.chentongwei.dao.doutu.IPictureTagDAO;
import com.chentongwei.entity.doutu.vo.PictureListVO;
import com.chentongwei.util.QiniuUtil;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.Set;

/**
 * 七牛小程序
 *
 * @author TongWei.Chen 2017-05-18 17:52:39
 */
@Component
@Configurable
@EnableScheduling
public class QiNiuTask {
    private static final Logger LOG = LoggerFactory.getLogger(QiNiuTask.class);

    @Autowired
    private QiniuUtil qiniuUtil;
    @Autowired
    private IBasicCache basicCache;
    @Autowired
    private ISetCache setCache;

    /**
     * 由于导出表情包后，zip会残留在空间上，
     * 所以写个小程序，每天凌晨三点来删除zip
     */
    @Scheduled(cron = "0 0 03 * * ?")
    public void delete() {
        //0.记录log
        LOG.info("开始删除残留在七牛的zip包");
        //1.读取redis的zipName，查询列表全部ZIP开头的，遍历删除
        Set<String> fileNames = setCache.getSet(RedisEnum.MKZIP.getKey());
        if(CollectionUtils.isNotEmpty(fileNames)) {
            fileNames.forEach(finalName -> {
                //2.进行删除
                qiniuUtil.delete("doutu-other", finalName);
            });
            //删除redis对应的key
            basicCache.deleteKey(RedisEnum.MKZIP.getKey());
        }
        LOG.info("完成reids中key为qiniu_zip的清理,共清理了{}个", fileNames.size());
    }
}