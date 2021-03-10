package com.roncoo.jui.common.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.roncoo.jui.common.dao.RcDataDictionaryDao;
import com.roncoo.jui.common.entity.RcDataDictionary;
import com.roncoo.jui.common.entity.RcDataDictionaryExample;
import com.roncoo.jui.common.mapper.RcDataDictionaryMapper;
import com.roncoo.jui.common.util.PageUtil;
import com.roncoo.jui.common.util.jui.Page;

@Repository
public class RcDataDictionaryDaoImpl implements RcDataDictionaryDao {
    @Autowired
    private RcDataDictionaryMapper rcDataDictionaryMapper;

    public int save(RcDataDictionary record) {
        return this.rcDataDictionaryMapper.insertSelective(record);
    }

    public int deleteById(Long id) {
        return this.rcDataDictionaryMapper.deleteByPrimaryKey(id);
    }

    public int updateById(RcDataDictionary record) {
        return this.rcDataDictionaryMapper.updateByPrimaryKeySelective(record);
    }

    public RcDataDictionary getById(Long id) {
        return this.rcDataDictionaryMapper.selectByPrimaryKey(id);
    }

    public Page<RcDataDictionary> listForPage(int pageCurrent, int pageSize, RcDataDictionaryExample example) {
        int count = this.rcDataDictionaryMapper.countByExample(example);
        pageSize = PageUtil.checkPageSize(pageSize);
        pageCurrent = PageUtil.checkPageCurrent(count, pageSize, pageCurrent);
        int totalPage = PageUtil.countTotalPage(count, pageSize);
        example.setLimitStart(PageUtil.countOffset(pageCurrent, pageSize));
        example.setPageSize(pageSize);
        return new Page<RcDataDictionary>(count, totalPage, pageCurrent, pageSize, this.rcDataDictionaryMapper.selectByExample(example));
    }
}