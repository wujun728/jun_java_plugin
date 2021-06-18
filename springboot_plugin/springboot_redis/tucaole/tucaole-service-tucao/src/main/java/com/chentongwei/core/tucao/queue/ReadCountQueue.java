package com.chentongwei.core.tucao.queue;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.PriorityBlockingQueue;

/**
 * @author Wujun
 * @Project tucaole
 * @Description: 分类记录浏览次数标识队列，只记录浏览的数据标识 主键ID
 */
public class ReadCountQueue {

    /**
     * 吐槽文章队列，初始化10长度
     */
    private static PriorityBlockingQueue<Long> tucaoArticleQueue = new PriorityBlockingQueue<>(10, new ReadCountComparator<>());

    /**
     * 新增吐槽文章阅读数
     *
     * @param articleId：文章id
     * @return
     */
    public static int addTucaoArticleReadCountQueue(Long articleId) {
        if (null != articleId) {
            tucaoArticleQueue.add(articleId);
        }
        return tucaoArticleQueue.size();
    }

    /**
     * 获取吐槽文章阅读列表
     * @return
     */
    public static Map<Long, Integer> getArticleReadCount() {
        Map<Long, Integer> map = new HashMap<>();
        while (true) {
            //弹出文章id
            Long articleId = tucaoArticleQueue.poll();
            if (null != articleId) {
                if (map.containsKey(articleId)) {
                    map.put(articleId, map.get(articleId) + 1);
                } else {
                    //阅读量为1
                    map.put(articleId, 1);
                }
            } else {
                break;
            }
        }
        return map;
    }

}

class ReadCountComparator<T> implements Comparator<T> {

    /**
     * 比较器，浏览次数队列
     *
     * @param o1：参数1
     * @param o2：参数2
     * @return
     */
    @Override
    public int compare(T o1, T o2) {
        if (o1 == null || o2 == null) {
            throw new RuntimeException("比较器参数不能为空！");
        }
        return (int)((Long) o1 - (Long) o2);
    }
}