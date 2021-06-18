package org.supercall.common;

import org.supercall.mybatis.Pagination;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by kira on 16/7/26.
 */
public interface IListPlugin {
    List<LinkedHashMap<String, Object>> all(Pagination pagination,
                                            HashMap<String, Object> map);
}

