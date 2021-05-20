package com.roncoo.jui.common.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.roncoo.jui.common.dao.RcDataDictionaryListDao;
import com.roncoo.jui.common.entity.RcDataDictionaryList;
import com.roncoo.jui.common.entity.RcDataDictionaryListExample;
import com.roncoo.jui.common.mapper.RcDataDictionaryListMapper;
import com.roncoo.jui.common.util.PageUtil;
import com.roncoo.jui.common.util.jui.Page;

@Repository
public class RcDataDictionaryListDaoImpl implements RcDataDictionaryListDao {
    @Autowired
    private RcDataDictionaryListMapper rcDataDictionaryListMapper;

    public int save(RcDataDictionaryList record) {
        return this.rcDataDictionaryListMapper.insertSelective(record);
    }

    public int deleteById(Long id) {
        return this.rcDataDictionaryListMapper.deleteByPrimaryKey(id);
    }

    public int updateById(RcDataDictionaryList record) {
        return this.rcDataDictionaryListMapper.updateByPrimaryKeySelective(record);
    }

    public RcDataDictionaryList getById(Long id) {
        return this.rcDataDictionaryListMapper.selectByPrimaryKey(id);
    }

    public Page<RcDataDictionaryList> listForPage(int pageCurrent, int pageSize, RcDataDictionaryListExample example) {
        int count = this.rcDataDictionaryListMapper.countByExample(example);
        pageSize = PageUtil.checkPageSize(pageSize);
        pageCurrent = PageUtil.checkPageCurrent(count, pageSize, pageCurrent);
        int totalPage = PageUtil.countTotalPage(count, pageSize);
        example.setLimitStart(PageUtil.countOffset(pageCurrent, pageSize));
        example.setPageSize(pageSize);
        return new Page<RcDataDictionaryList>(count, totalPage, pageCurrent, pageSize, this.rcDataDictionaryListMapper.selectByExample(example));
    }
}