package com.xxl.apm.admin.dao;

import com.xxl.apm.admin.core.model.XxlApmHeartbeatReport;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author xuxueli 2019-01-18
 */
@Mapper
public interface IXxlApmHeartbeatReportDao {

    public int addMult(@Param("heartbeatReportList") List<XxlApmHeartbeatReport> heartbeatReportList);

    public int clean(@Param("timeoutTime") long timeoutTime);

    public List<XxlApmHeartbeatReport> find(@Param("appname") String appname,
                                            @Param("addtime_from") long addtime_from,
                                            @Param("addtime_to") long addtime_to,
                                            @Param("address") String address);

    public List<String> findAppNameList(@Param("appname") String appname);

    public List<XxlApmHeartbeatReport> findAddressList(@Param("appname") String appname,
                                                  @Param("addtime_from") long addtime_from,
                                                  @Param("addtime_to") long addtime_to);

    public int findAppNameCount();

    public int findAppNameAddressCount();

    public int findTotalMsgCount();

}
