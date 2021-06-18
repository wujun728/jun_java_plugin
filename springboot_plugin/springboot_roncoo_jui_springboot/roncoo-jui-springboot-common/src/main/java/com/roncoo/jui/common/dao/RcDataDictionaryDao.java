package com.roncoo.jui.common.dao;

import com.roncoo.jui.common.entity.RcDataDictionary;
import com.roncoo.jui.common.entity.RcDataDictionaryExample;
import com.roncoo.jui.common.util.jui.Page;

public interface RcDataDictionaryDao {
    int save(RcDataDictionary record);

    int deleteById(Long id);

    int updateById(RcDataDictionary record);

    RcDataDictionary getById(Long id);

    Page<RcDataDictionary> listForPage(int pageCurrent, int pageSize, RcDataDictionaryExample example);
}