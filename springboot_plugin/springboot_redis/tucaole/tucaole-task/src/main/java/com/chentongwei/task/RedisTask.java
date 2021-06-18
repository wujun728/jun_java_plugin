package com.chentongwei.task;

import com.chentongwei.cache.redis.IBasicCache;
import com.chentongwei.cache.redis.IHashCache;
import com.chentongwei.common.enums.constant.RedisEnum;
import com.chentongwei.core.tucao.dao.IArticleSupportDAO;
import com.chentongwei.core.tucao.entity.io.support.ArticleSupportSaveIO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @author Wujun
 * @Project tucaole
 * @Description: Redis的小任务
 */
@Component
public class RedisTask {
    private static final Logger LOG = LogManager.getLogger(UserTask.class);

    @Autowired
    private IBasicCache basicCache;
    @Autowired
    private IHashCache hashCache;
    @Autowired
    private IArticleSupportDAO articleSupportDAO;

    /**
     * 每天凌晨06:00定时清理一些每天最大的次数
     */
    @Scheduled(cron = "0 00 06 * * ?")
    public void deleteEmailCountExceed() {
        Set<String> forgetPasswordKeys = basicCache.keys(RedisEnum.getForgetPwdEmailCountKey("*"));
        basicCache.deleteKey(forgetPasswordKeys);
        LOG.info("------删除忘记密码--发送email的次数------");

        Set<String> sendSourceEmailKeys = basicCache.keys(RedisEnum.getSendSourceEmailCountKey("*"));
        basicCache.deleteKey(sendSourceEmailKeys);
        LOG.info("------删除更改邮箱--发送email的次数------");

        Set<String> reportCountKeys = basicCache.keys(RedisEnum.getReportCountKey("*"));
        basicCache.deleteKey(reportCountKeys);
        LOG.info("------删除举报功能--每个人最多举报次数------");
    }


    /**
     * 每天凌晨三点将点赞数刷到db
     */
    @Scheduled(cron = "0 00 03 * * ?")
    public void deleteSupportHash() {

        List<ArticleSupportSaveIO> list = new ArrayList<>();

        Set<String> keys = basicCache.keys(RedisEnum.getTucaoHashArticleSupportKey("*"));
        Iterator<String> iterator = keys.iterator();
        //计数器
        int count = 0;
        while (iterator.hasNext()) {
            String key = iterator.next();
            Map<Object, Object> map = hashCache.entries(key);
            //to_db是redis的key前缀最后几个字符
            int index = key.indexOf("to_db") + 5;
            for (Map.Entry<Object, Object> entry : map.entrySet()) {
                //每200个就save进去
                if (count == 200) {
                    articleSupportDAO.save(list);
                    list.clear();
                    //重新计数
                    count = 0;
                }
                ArticleSupportSaveIO article = new ArticleSupportSaveIO();
                article.setUserId(Long.parseLong(entry.getKey().toString()));
                article.setStatus(Integer.parseInt(entry.getValue().toString()));
                article.setArticleId(Long.parseLong(key.substring(index)));
                list.add(article);
                count ++;
            }
        }
        if (count > 0) {
            articleSupportDAO.save(list);
        }
        basicCache.deleteKey(keys);
        LOG.info("---------------将点赞数刷到db成功----------key的个数为：【{}】---------", keys.size());
    }
}
