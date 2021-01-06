package com.xxl.apm.admin.controller;

import com.xxl.apm.admin.core.model.XxlApmEventReport;
import com.xxl.apm.admin.core.model.XxlApmHeartbeatReport;
import com.xxl.apm.admin.core.util.CookieUtil;
import com.xxl.apm.admin.core.util.DateUtil;
import com.xxl.apm.admin.core.util.JacksonUtil;
import com.xxl.apm.admin.dao.IXxlApmEventReportDao;
import com.xxl.apm.admin.dao.IXxlApmHeartbeatReportDao;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * @author xuxueli 2019-01-28
 */
@Controller
@RequestMapping("/event")
public class EventController {


    @Resource
    private IXxlApmEventReportDao xxlApmEventReportDao;
    @Resource
    private IXxlApmHeartbeatReportDao xxlApmHeartbeatReportDao;


    @RequestMapping("")
    public String index(Model model, HttpServletRequest request, HttpServletResponse response,
                        String querytime, String appname, String address,
                        String type){

        // get cookie
        if (querytime == null) {
            String xxlapm_querytime = CookieUtil.getValue(request, "xxlapm_querytime");
            if (xxlapm_querytime != null) {
                querytime = xxlapm_querytime;
            }
        }
        if (appname == null) {
            String xxlapm_appname = CookieUtil.getValue(request, "xxlapm_appname");
            if (xxlapm_appname != null) {
                appname = xxlapm_appname;
            }
        }

        // parse querytime
        Date querytime_date = null;
        if (querytime!=null && querytime.trim().length()>0) {
            querytime_date = DateUtil.parse(querytime, "yyyyMMddHH");
        }
        if (querytime_date == null) {
            querytime_date = DateUtil.parse(DateUtil.format(new Date(), "yyyyMMddHH"), "yyyyMMddHH");
        }
        long addtime_from = querytime_date.getTime();
        long addtime_to = addtime_from + 59*60*1000;    // an hour

        // addressInfo
        Map<String, String> addressInfo = new TreeMap<>();
        if (appname!=null && appname.trim().length()>0) {
            List<XxlApmHeartbeatReport> addressList = xxlApmHeartbeatReportDao.findAddressList(appname, addtime_from, addtime_to);
            if (addressList!=null && addressList.size()>0) {
                for (XxlApmHeartbeatReport item: addressList) {
                    addressInfo.put(item.getAddress(), item.getAddress().concat("(").concat(item.getHostname()).concat(")") );
                }
            }
        }
        model.addAttribute("addressInfo", addressInfo);

        // address
        address = (address!=null&&addressInfo.containsKey(address))
                ? address
                : null;

        // filter data
        model.addAttribute("querytime", querytime_date);
        model.addAttribute("appname", appname);
        model.addAttribute("address", address);

        // set cookie
        CookieUtil.set(response, "xxlapm_querytime", querytime, false);
        CookieUtil.set(response, "xxlapm_appname", appname, false);

        // typeList
        List<String> typeList = null;
        if (appname!=null && appname.trim().length()>0) {
            typeList = xxlApmEventReportDao.findTypeList(appname, addtime_from, addtime_to);
        }
        model.addAttribute("typeList", typeList);

        // type
        type = (type!=null&&typeList!=null&&typeList.contains(type))
                ? type
                : (typeList!=null && typeList.size()>0)
                ?typeList.get(0)
                :null;
        model.addAttribute("type", type);


        // periodSecond
        long periodSecond = (addtime_to<=System.currentTimeMillis())
                ?3600:     // an hour -> second
                (System.currentTimeMillis()-addtime_from)/1000;     // -> second
        model.addAttribute("periodSecond", periodSecond);


        // load data
        List<XxlApmEventReport> reportList = xxlApmEventReportDao.find(appname, addtime_from, addtime_to, address, type);
        if (reportList!=null && reportList.size()>0) {
            model.addAttribute("reportList", JacksonUtil.writeValueAsString(reportList));
        }

        return "event/event.index";
    }

}
