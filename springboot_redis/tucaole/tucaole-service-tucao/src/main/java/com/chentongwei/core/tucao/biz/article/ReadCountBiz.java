package com.chentongwei.core.tucao.biz.article;

import com.chentongwei.common.enums.constant.QueueEnum;
import com.chentongwei.core.tucao.dao.IArticleDAO;
import com.chentongwei.core.tucao.queue.ReadCountQueue;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author Wujun
 * @Project tucaole
 * @Description: 阅读数+1
 */
@Service
@Async
public class ReadCountBiz {
    private static final Logger LOG = LogManager.getLogger("bizLog");

    @Autowired
    private IArticleDAO tucaoArticleDAO;

    /**
     * 向队列中添加文章
     *
     * @param articleId：文章id
     */
    public void addArticleReadCount(Long articleId) {
        //add
        int readCount = ReadCountQueue.addTucaoArticleReadCountQueue(articleId);
        //若大于队列长度，则直接update阅读数，无需等待task
        if (readCount >= QueueEnum.MAX_LENGTH.value()) {
            triggerUpdateReadCount();
        }
    }

    /**
     * 触发获取队列数据，修改数据库操作。
     */
    public void triggerUpdateReadCount(){
        updateArticleReadCount();
    }

    /**
     * 更新吐槽文章阅读数
     */
    private void updateArticleReadCount() {
        Map<Long, Integer> articleMap = ReadCountQueue.getArticleReadCount();
        for (Map.Entry<Long, Integer> entries : articleMap.entrySet()) {
            tucaoArticleDAO.updateReadCount(entries.getKey(), entries.getValue());
        }
    }

}
