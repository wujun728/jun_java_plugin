package com.chentongwei.core.system.dao;

import com.chentongwei.core.system.entity.io.report.ReportIO;
import org.apache.ibatis.annotations.Param;

/**
 * @author Wujun
 * @Project tucaole
 * @Description: 吐槽了举报DAO
 */
public interface IReportDAO {

    /**
     * 保存举报
     *
     * @param reportIO：参数
     * @return
     */
    void save(ReportIO reportIO);

    /**
     * 检查每个用户今天是否举报了某资源
     * null：没举报，否则举报了
     *
     * @param resourceId：资源id
     * @param type： 1：举报文章；2：举报文章的评论
     * @param userId：用户id
     * @return
     */
    Integer checkReportCurrentDate(@Param("resourceId") Long resourceId, @Param("type") Integer type, @Param("userId") Long userId);
}
