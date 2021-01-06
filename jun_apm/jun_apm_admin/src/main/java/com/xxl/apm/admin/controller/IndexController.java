package com.xxl.apm.admin.controller;

import com.xxl.apm.admin.controller.annotation.PermessionLimit;
import com.xxl.apm.admin.controller.interceptor.PermissionInterceptor;
import com.xxl.apm.admin.core.model.XxlApmEventReport;
import com.xxl.apm.admin.core.model.XxlApmTransactionReport;
import com.xxl.apm.admin.core.result.ReturnT;
import com.xxl.apm.admin.core.util.DateUtil;
import com.xxl.apm.admin.dao.IXxlApmEventReportDao;
import com.xxl.apm.admin.dao.IXxlApmHeartbeatReportDao;
import com.xxl.apm.admin.dao.IXxlApmTransactionReportDao;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * index controller
 * @author xuxueli 2015-12-19 16:13:16
 */
@Controller
public class IndexController {


    @Resource
    private IXxlApmHeartbeatReportDao xxlApmHeartbeatReportDao;
	@Resource
	private IXxlApmEventReportDao xxlApmEventReportDao;
    @Resource
    private IXxlApmTransactionReportDao xxlApmTransactionReportDao;


	@RequestMapping("/")
	public String index(Model model, String querytime, @RequestParam(required = false, defaultValue = "-1") int min) {

		int appNameCount = xxlApmHeartbeatReportDao.findAppNameCount();
		model.addAttribute("appNameCount", appNameCount);

		int appNameAddressCount = xxlApmHeartbeatReportDao.findAppNameAddressCount();
		model.addAttribute("appNameAddressCount", appNameAddressCount);

		int totalMsgCount = xxlApmHeartbeatReportDao.findTotalMsgCount();
		model.addAttribute("totalMsgCount", totalMsgCount);


		// parse querytime
		Date querytime_date = null;
		if (querytime!=null && querytime.trim().length()>0) {
			querytime_date = DateUtil.parse(querytime, "yyyyMMddHH");
		}
		if (querytime_date == null) {
			querytime_date = DateUtil.parse(DateUtil.format(new Date(), "yyyyMMddHH"), "yyyyMMddHH");
		}
		model.addAttribute("querytime", querytime_date);

		// min
		min = (min>=0 && min<=59)?min:Calendar.getInstance().get(Calendar.MINUTE)-1;
		min = (min>=0 && min<=59)?min:0;
        model.addAttribute("min", min);

		// time peroid
		long addtime_from = querytime_date.getTime() + min*1000*60;
		long addtime_to = addtime_from + 1*1000*60;    // an min

        List<XxlApmEventReport> eventReportList = xxlApmEventReportDao.findFailReport(addtime_from, addtime_to);
        List<XxlApmTransactionReport> transactionReportList = xxlApmTransactionReportDao.findFailReport(addtime_from, addtime_to);

		// fault list
		List<Map<String, Object>> faultList = new ArrayList<>();
		if (eventReportList!=null && eventReportList.size()>0) {
            for (final XxlApmEventReport eventReport: eventReportList) {
                final Date querytime_date_tmp = querytime_date;
                faultList.add(new HashMap<String, Object>(){{
                    put("Category", "事件");
                    put("AppName", eventReport.getAppname());
					put("Type", eventReport.getType());
                    put("Count", eventReport.getFail_count());
                    put("Link", MessageFormat.format("/event?querytime={0}&appname={1}&address=&type={2}",
                            DateUtil.format(querytime_date_tmp, "yyyyMMddHH"), eventReport.getAppname(), eventReport.getType())
                    );
                }});
            }
        }
        if (transactionReportList!=null && transactionReportList.size()>0) {
            for (final XxlApmTransactionReport transactionReport: transactionReportList) {
                final Date querytime_date_tmp = querytime_date;
                faultList.add(new HashMap<String, Object>(){{
                    put("Category", "事务");
					put("AppName", transactionReport.getAppname());
					put("Type", transactionReport.getType());
                    put("Count", transactionReport.getFail_count());
                    put("Link", MessageFormat.format("/transaction?querytime={0}&appname={1}&address=&type={2}",
                            DateUtil.format(querytime_date_tmp, "yyyyMMddHH"), transactionReport.getAppname(), transactionReport.getType())
                    );
                }});
            }
        }
		model.addAttribute("faultList", faultList);

		return "index";
	}


	@RequestMapping("/toLogin")
	@PermessionLimit(limit=false)
	public String toLogin(Model model, HttpServletRequest request) {
		if (PermissionInterceptor.ifLogin(request)) {
			return "redirect:/";
		}
		return "login";
	}

	@RequestMapping(value="login", method=RequestMethod.POST)
	@ResponseBody
	@PermessionLimit(limit=false)
	public ReturnT<String> loginDo(HttpServletRequest request, HttpServletResponse response, String userName, String password, String ifRemember){
		// valid
		if (PermissionInterceptor.ifLogin(request)) {
			return ReturnT.SUCCESS;
		}

		// param
		if (userName==null || userName.trim().length()==0 || password==null || password.trim().length()==0){
			return new ReturnT<String>(500, "请输入账号密码");
		}
		boolean ifRem = (ifRemember!=null && "on".equals(ifRemember))?true:false;

		// do login
		boolean loginRet = PermissionInterceptor.login(response, userName, password, ifRem);
		if (!loginRet) {
			return new ReturnT<String>(500, "账号密码错误");
		}
		return ReturnT.SUCCESS;
	}

	@RequestMapping(value="logout", method=RequestMethod.POST)
	@ResponseBody
	@PermessionLimit(limit=false)
	public ReturnT<String> logout(HttpServletRequest request, HttpServletResponse response){
		if (PermissionInterceptor.ifLogin(request)) {
			PermissionInterceptor.logout(request, response);
		}
		return ReturnT.SUCCESS;
	}
	
	@RequestMapping("/help")
	public String help() {
		return "help";
	}


	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}

}
