package com.xxl.apm.admin.dao;

import com.xxl.apm.admin.core.model.XxlApmEventReport;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface IXxlApmEventReportDao {

    public int add(@Param("xxlApmEventReport") XxlApmEventReport xxlApmEventReport);

    public int update(@Param("xxlApmEventReport") XxlApmEventReport xxlApmEventReport);

    public int addOrUpdate(@Param("xxlApmEventReport") XxlApmEventReport xxlApmEventReport);

    public int clean(@Param("timeoutTime") long timeoutTime);

    public List<XxlApmEventReport> find(@Param("appname") String appname,
                                            @Param("addtime_from") long addtime_from,
                                            @Param("addtime_to") long addtime_to,
                                            @Param("address") String address,
                                            @Param("type") String type);

    public List<String> findTypeList(@Param("appname") String appname,
                                                  @Param("addtime_from") long addtime_from,
                                                  @Param("addtime_to") long addtime_to);

    public List<XxlApmEventReport> findFailReport(@Param("addtime_from") long addtime_from,
                                                 @Param("addtime_to") long addtime_to);

}
