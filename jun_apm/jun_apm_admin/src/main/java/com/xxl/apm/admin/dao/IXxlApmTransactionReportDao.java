package com.xxl.apm.admin.dao;

import com.xxl.apm.admin.core.model.XxlApmTransactionReport;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface IXxlApmTransactionReportDao {

    public int add(@Param("xxlApmTransactionReport") XxlApmTransactionReport xxlApmTransactionReport);

    public int update(@Param("xxlApmTransactionReport") XxlApmTransactionReport xxlApmTransactionReport);

    public int addOrUpdate(@Param("xxlApmTransactionReport") XxlApmTransactionReport xxlApmTransactionReport);

    public int clean(@Param("timeoutTime") long timeoutTime);

    public List<XxlApmTransactionReport> find(@Param("appname") String appname,
                                        @Param("addtime_from") long addtime_from,
                                        @Param("addtime_to") long addtime_to,
                                        @Param("address") String address,
                                        @Param("type") String type);

    public List<String> findTypeList(@Param("appname") String appname,
                                     @Param("addtime_from") long addtime_from,
                                     @Param("addtime_to") long addtime_to);

    public List<XxlApmTransactionReport> findFailReport(@Param("addtime_from") long addtime_from,
                                                        @Param("addtime_to") long addtime_to);

}
