package io.github.wujun728.db.dao.util;

import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 */
public class CollectionUtil {


    /**
     * 将集合切片，切片后的每个集合大小为size
     * @param size 切片大小
     * @param c    待切片集合
     * @param <T>  集合存放元素类型
     * @return List[] 切片后的集合数组
     */
    public static <T> List<T>[] slice(List<T> c, int size) {
        if (size < 1) {
            throw new IllegalArgumentException("无效的size:" + size);
        }
        if (CollectionUtils.isEmpty(c)) {
            return new List[0];
        }
        int cSize = c.size();
        int sliceNum = cSize % size == 0 ? cSize / size : cSize / size + 1;
        List<T>[] result = new ArrayList[sliceNum];
        for (int i = 0; i < sliceNum; i++) {
            int startIdx = size * i;
            int endIdx = size * (i + 1) > cSize ? cSize : size * (i + 1);
            List<T> l = new ArrayList<>();
            for (int j = startIdx; j < endIdx; j++) {
                l.add(c.get(j));
            }
            result[i] = l;
        }
        return result;
    }
}
