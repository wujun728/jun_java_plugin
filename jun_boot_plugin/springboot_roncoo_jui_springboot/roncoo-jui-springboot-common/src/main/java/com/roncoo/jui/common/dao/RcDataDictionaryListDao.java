package com.roncoo.jui.common.dao;

import com.roncoo.jui.common.entity.RcDataDictionaryList;
import com.roncoo.jui.common.entity.RcDataDictionaryListExample;
import com.roncoo.jui.common.util.jui.Page;

public interface RcDataDictionaryListDao {
    int save(RcDataDictionaryList record);

    int deleteById(Long id);

    int updateById(RcDataDictionaryList record);

    RcDataDictionaryList getById(Long id);

    Page<RcDataDictionaryList> listForPage(int pageCurrent, int pageSize, RcDataDictionaryListExample example);
}