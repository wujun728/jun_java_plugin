package com.zhaodui.springboot.system.mapper;

import com.zhaodui.springboot.common.mapper.BaseMapper;
import com.zhaodui.springboot.system.model.SysLog;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface SysLogMapper extends BaseMapper<SysLog> {

    /**
     * @功能：清空所有日志记录
     */
    public void removeAll();

    /**
     * 获取系统总访问次数
     *
     * @return Long
     */
    long findTotalVisitCount();

    //传入开始时间，结束时间参数
    /**
     * 获取系统今日访问次数
     *
     * @return Long
     */
    long findTodayVisitCount(@Param("dayStart") Date dayStart, @Param("dayEnd") Date dayEnd);

    /**
     * 获取系统今日访问 IP数
     *
     * @return Long
     */
    long findTodayIp(@Param("dayStart") Date dayStart, @Param("dayEnd") Date dayEnd);
    //传入开始时间，结束时间参数

    /**
     *   首页：根据时间统计访问数量/ip数量
     * @param dayStart
     * @param dayEnd
     * @return
     */
    List<Map<String,Object>> findVisitCount(@Param("dayStart") Date dayStart, @Param("dayEnd") Date dayEnd, @Param("dbType") String dbType);

}