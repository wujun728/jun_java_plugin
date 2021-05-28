package com.roncoo.jui.common.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.roncoo.jui.common.dao.RcReportDao;
import com.roncoo.jui.common.entity.RcReport;
import com.roncoo.jui.common.entity.RcReportExample;
import com.roncoo.jui.common.mapper.RcReportMapper;
import com.roncoo.jui.common.util.PageUtil;
import com.roncoo.jui.common.util.jui.Page;

@Repository
public class RcReportDaoImpl implements RcReportDao {
    @Autowired
    private RcReportMapper rcReportMapper;

    public int save(RcReport record) {
        return this.rcReportMapper.insertSelective(record);
    }

    public int deleteById(Long id) {
        return this.rcReportMapper.deleteByPrimaryKey(id);
    }

    public int updateById(RcReport record) {
        return this.rcReportMapper.updateByPrimaryKeySelective(record);
    }

    public RcReport getById(Long id) {
        return this.rcReportMapper.selectByPrimaryKey(id);
    }

    public Page<RcReport> listForPage(int pageCurrent, int pageSize, RcReportExample example) {
        int count = this.rcReportMapper.countByExample(example);
        pageSize = PageUtil.checkPageSize(pageSize);
        pageCurrent = PageUtil.checkPageCurrent(count, pageSize, pageCurrent);
        int totalPage = PageUtil.countTotalPage(count, pageSize);
        example.setLimitStart(PageUtil.countOffset(pageCurrent, pageSize));
        example.setPageSize(pageSize);
        return new Page<RcReport>(count, totalPage, pageCurrent, pageSize, this.rcReportMapper.selectByExample(example));
    }
}