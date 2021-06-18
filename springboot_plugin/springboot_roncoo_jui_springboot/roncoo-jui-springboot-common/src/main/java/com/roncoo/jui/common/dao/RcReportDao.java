package com.roncoo.jui.common.dao;

import com.roncoo.jui.common.entity.RcReport;
import com.roncoo.jui.common.entity.RcReportExample;
import com.roncoo.jui.common.util.jui.Page;

public interface RcReportDao {
    int save(RcReport record);

    int deleteById(Long id);

    int updateById(RcReport record);

    RcReport getById(Long id);

    Page<RcReport> listForPage(int pageCurrent, int pageSize, RcReportExample example);
}