package com.chentongwei.core.system.dao;

import com.chentongwei.core.system.entity.vo.report.ReportTypeListVO;

import java.util.List;

/**
 * @author TongWei.Chen 2017-12-16 18:15:33
 * @Project tucaole
 * @Description: 吐槽了举报类型DAO
 */
public interface IReportTypeDAO {

    /**
     * 举报类型列表
     *
     * @return
     */
    List<ReportTypeListVO> list();
}
