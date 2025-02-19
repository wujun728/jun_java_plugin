package io.github.wujun728.db.orm.utils;

import java.util.Collection;

/**
 * 集合工具类
 */
public class CollectionUtils {
    public static boolean isEmpty(Collection collection) {
        if(null == collection || collection.isEmpty()) {
            return true;
        }else {
            return false;
        }
    }
}
