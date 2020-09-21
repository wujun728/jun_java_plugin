package com.osmp.web.system.httpproxy.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.osmp.web.base.core.SystemConstant;
import com.osmp.web.core.SystemFrameWork;
import com.osmp.web.system.dataService.entity.DataService;
import com.osmp.web.system.dataService.service.DataServiceService;
import com.osmp.web.system.httpproxy.entity.HttpContext;
import com.osmp.web.system.httpproxy.service.HttpContextService;
import com.osmp.web.system.servers.entity.Servers;
import com.osmp.web.system.servers.service.ServersService;
import com.osmp.web.system.strategy.entity.Strategy;
import com.osmp.web.system.strategy.entity.StrategyCondition;
import com.osmp.web.system.strategy.service.StrategyService;
import com.osmp.web.utils.Utils;

/**
 * Description:解析请求上下文
 * 
 * @author: wangkaiping
 * @date: 2015年4月20日 下午2:23:34
 */
@Service
public class HttpContextServiceImpl implements HttpContextService {
	private Logger logger = Logger.getLogger(HttpContextServiceImpl.class);

	@Autowired
	private ServersService serversService;

	@Autowired
	private StrategyService strategyService;

	@Autowired
	private DataServiceService dataServiceService;

	@Override
	public HttpContext parse(HttpServletRequest request, String url) {
		String source = request.getParameter("source");
		String para = request.getParameter("parameter");
		Map<String, Object> map = Utils.parseJSON2Map(para);
		String requestUrl = request.getRequestURI();
		if (null == source || "".equals(source.trim())) {
			source = "";
		} else {
			try {
				source = source.substring(source.indexOf(":") + 2, source.indexOf("}") - 1);
			} catch (Exception e) {
				logger.error("解析请求来源失败：source = " + source, e);
				source = "";
			}
		}
		HttpContext hc = new HttpContext();
		hc.setIp(Utils.getRemoteHost(request));
		hc.setUrl(url);
		hc.setSource(source);
		hc.setService(requestUrl.substring(requestUrl.lastIndexOf("/") + 1, requestUrl.length()));
		hc.setArgsMap(map);
		return hc;
	}

	@Override
	public String dispathByUrl(HttpServletRequest request, String url) {
		HttpContext httpContext = this.parse(request, url);
		Map<String, List<StrategyCondition>> map = SystemFrameWork.strategyMap;
		Set<String> s = map.keySet();
		Iterator<String> ite = s.iterator();
		while (ite.hasNext()) {
			String id = ite.next();
			List<StrategyCondition> conList = map.get(id);
			boolean flag = this.isStrategyAllConform(httpContext, conList);
			if (flag) {
				Servers servers = this.getStrategyServer(id);
				if (SystemFrameWork.checkServerState(servers.getServerIp())) {
					return servers.getManageUrl();
				} else {
					return "";
				}
			}
		}
		return "";
	}

	/**
	 * 得到当前策略所属服务器
	 * 
	 * @param StrategyId
	 *            策略id
	 * @return Servers
	 */
	private Servers getStrategyServer(String StrategyId) {
		Strategy strategy = new Strategy();
		strategy.setId(StrategyId);
		strategy = strategyService.getStrategy(strategy);
		Servers servers = new Servers();
		servers.setServerIp(strategy.getForwardIp());
		servers = serversService.getServers(servers);
		return servers;
	}

	/**
	 * 判断请求是否符合该服务器策略
	 * 
	 * @param hc
	 *            请求封装后的对象
	 * @param conList
	 *            该服务的策略
	 * @return 返回true：满足该策略
	 */
	private boolean isStrategyAllConform(HttpContext httpContext, List<StrategyCondition> conList) {
		if (conList == null || conList.size() == 0) {
			return false;
		}
		for (int i = 0; i < conList.size(); i++) {
			StrategyCondition sc = conList.get(i);
			String type = sc.getType();
			String key = sc.getKey();
			String judge = sc.getCondition();
			String value = sc.getValue();
			switch (type) {
			case SystemConstant.STRATEGY_SERVICE_TYPE:// 判断服务名称
				String service = httpContext.getService();
				boolean b = this.isJudgeOk(service, judge, value);
				if (!b) {
					return false;
				}
				break;
			case SystemConstant.STRATEGY_PROJECT_TYPE:// 判断项目名称
				String projectName = httpContext.getSource();
				boolean b1 = this.isJudgeOk(projectName, judge, value);
				if (!b1) {
					return false;
				}
				break;
			case SystemConstant.STRATEGY_IP_TYPE:// 判断客户端IP
				String ip = httpContext.getIp();
				boolean b2 = this.isJudgeOk(ip, judge, value);
				if (!b2) {
					return false;
				}
				break;
			case SystemConstant.STRATEGY_URL_TYPE:// 判断请求的URL
				String url = httpContext.getUrl();
				boolean b3 = this.isJudgeOk(url, judge, value);
				if (!b3) {
					return false;
				}
				break;
			case SystemConstant.STRATEGY_ARGS_TYPE:// 判断参数
				Map<String, Object> m = httpContext.getArgsMap();
				Object obj = m.get(key);
				if (obj != null) {
					if (!this.isJudgeOk(obj.toString(), judge, value)) {
						return false;
					}
				} else {
					return false;
				}
				break;
			default:
				break;
			}
		}

		return true;
	}

	/**
	 * 判断这个条件是否满足
	 * 
	 * @param key
	 * @param judge
	 * @param value
	 * @return
	 */
	private boolean isJudgeOk(String key, String judge, String value) {
		switch (judge) {
		case SystemConstant.JUDGE_EQ_TYPE:// 等于
			if (key.compareTo(value) == 0) {
				return true;
			}
			break;
		case SystemConstant.JUDGE_GREAT_TYPE:// 大于
			if (key.compareTo(value) > 0) {
				return true;
			}
			break;
		case SystemConstant.JUDGE_LESS_TYPE:// 小于
			if (key.compareTo(value) < 0) {
				return true;
			}
			break;
		case SystemConstant.JUDGE_NOTGREAT_TYPE:// 小于等于
			if (key.compareTo(value) < 0 || key.compareTo(value) == 0) {
				return true;
			}
			break;
		default:// 大于等于 SystemConstant.JUDGE_NOTLESS_TYPE
			if (key.compareTo(value) > 0 || key.compareTo(value) == 0) {
				return true;
			}
			break;
		}
		return false;
	}

	@Override
	public String getOtherUrl(HttpServletRequest request, String url) {
		HttpContext httpContext = this.parse(request, url);
		List<Servers> list = serversService.getAllRunServers();
		List<Servers> serverList = new ArrayList<Servers>(list.size());
		for (int i = 0; i < list.size(); i++) {
			Servers s = list.get(i);
			if (this.isRunService(httpContext, s)) {
				serverList.add(s);
			}
		}
		return this.getRandomManagerUrl(serverList);
	}

	/**
	 * 得到随机分配服务器的managerUrl
	 * 
	 * @param serverList
	 * @return
	 */
	private String getRandomManagerUrl(List<Servers> serverList) {
		if (serverList.size() == 0) {//
			return "";
		} else {
			Servers s = serverList.get((int) (Math.random() * serverList.size()));
			return s.getManageUrl();
		}
	}

	/**
	 * 判断服务器中是否有运行正常的"请求的服务id"
	 * 
	 * @param httpContext
	 * @param server
	 * @return true:该服务器可以分发
	 */
	private boolean isRunService(HttpContext httpContext, Servers server) {
		String service = httpContext.getService();
		List<DataService> ds = dataServiceService.getByWhere(" loadIp= '" + server.getServerIp() + "' and name='"
				+ service + "' and state = '" + SystemConstant.DATASERVICE_GREEN + "'");
		if (ds != null && ds.size() > 0) {
			return true;
		}
		return false;
	}

}
