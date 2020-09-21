package com.osmp.web.system.bundle.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.methods.multipart.StringPart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.osmp.web.base.core.SystemConstant;
import com.osmp.web.core.SystemFrameWork;
import com.osmp.web.core.tools.HttpClientWrapper;
import com.osmp.web.system.bundle.entity.Bundle;
import com.osmp.web.system.bundle.entity.BundleInfo;
import com.osmp.web.system.bundle.entity.PropObj;
import com.osmp.web.system.bundle.service.BundleService;
import com.osmp.web.system.servers.entity.Servers;
import com.osmp.web.system.servers.service.ServersService;

/**
 * Description:
 *
 * @author: wangkaiping
 * @date: 2014年11月19日 下午2:54:34
 */
@Service
public class BundleServiceImpl implements BundleService {

	@Resource
	HttpClientWrapper clientWrapper;

	@Autowired
	private ServersService serversService;

	@Override
	public List<Bundle> getBundles(String serverIp) {

		String manageUrl = getManageUrl(serverIp);
		if (manageUrl == "" || SystemConstant.BUNDLES_MANAGER_URL.equals(manageUrl)) {
			return new ArrayList<Bundle>();
		}
		String json = clientWrapper.get(manageUrl + SystemConstant.BUNDLES_JSON);
		if (json.length() > 0) {
			return clientWrapper.parse(json, Bundle.class, "data");
		}
		return new ArrayList<Bundle>();
	}

	/**
	 * 根据服务器IP获得服务管理的url
	 * 
	 * @param serverIp
	 * @return 组件列表的url
	 */
	private String getManageUrl(String serverIp) {
		Servers server = new Servers();
		server.setServerIp(serverIp);
		server = serversService.getServers(server);
		String manageUrl = "";
		if (server != null) {
			manageUrl = server.getManageUrl();
		}
		return manageUrl;
	}

	@Override
	public Bundle getBundleById(String id) {
		return null;
	}

	@Override
	public void uninstall(String id, String serverIp) {
		Map<String, String> params = new HashMap<String, String>(1);
		params.put("action", "uninstall");
		String bundleInfoUrl = getManageUrl(serverIp);
		if (bundleInfoUrl != "" && !SystemConstant.BUNDLES_MANAGER_URL.equals(bundleInfoUrl)) {
			clientWrapper.post(bundleInfoUrl + SystemConstant.BUNDLES_SPLICE + id, params);
		}
	}

	@Override
	public void start(String id, String serverIp) {
		Map<String, String> params = new HashMap<String, String>(1);
		params.put("action", "start");
		String bundleInfoUrl = getManageUrl(serverIp);
		if (bundleInfoUrl != "" && !SystemConstant.BUNDLES_MANAGER_URL.equals(bundleInfoUrl)) {
			clientWrapper.post(bundleInfoUrl + SystemConstant.BUNDLES_SPLICE + id, params);
		}
	}

	@Override
	public void stop(String id, String serverIp) {
		Map<String, String> params = new HashMap<String, String>(1);
		params.put("action", "stop");
		String bundleInfoUrl = getManageUrl(serverIp);
		if (bundleInfoUrl != "" && !SystemConstant.BUNDLES_MANAGER_URL.equals(bundleInfoUrl)) {
			clientWrapper.post(bundleInfoUrl + SystemConstant.BUNDLES_SPLICE + id, params);
		}
	}

	@Override
	public void refresh(String id, String serverIp) {
		Map<String, String> params = new HashMap<String, String>(1);
		params.put("action", "refresh");
		String bundleInfoUrl = getManageUrl(serverIp);
		if (bundleInfoUrl != "" && !SystemConstant.BUNDLES_MANAGER_URL.equals(bundleInfoUrl)) {
			clientWrapper.post(bundleInfoUrl + SystemConstant.BUNDLES_SPLICE + id, params);
		}
	}

	@Override
	public BundleInfo getBundleInfoById(String id, String serverIp) {
		String manageUrl = getManageUrl(serverIp);
		if (manageUrl == "" || SystemConstant.BUNDLES_MANAGER_URL.equals(manageUrl)) {
			return new BundleInfo();
		}
		String json = clientWrapper.get(manageUrl + SystemConstant.BUNDLES_SPLICE + id + ".json");
		if (json.length() > 0) {
			try {
				JSONObject jsonObject = JSONObject.parseObject(json);
				if (jsonObject != null) {
					JSONArray bundleArr = jsonObject.getJSONArray("data");
					JSONObject bundleJson = new JSONObject();
					if (null != bundleArr) {
						bundleJson = bundleArr.getJSONObject(0);
					}
					Map<String, Class<?>> classMap = new HashMap<String, Class<?>>();
					classMap.put("props", PropObj.class);

					BundleInfo bundleInfo = (BundleInfo) JSONObject
							.parseObject(bundleJson.toString(), BundleInfo.class);
					return bundleInfo;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return new BundleInfo();
	}

	@Override
	public void installOrUpdate(String bundlestart, String refreshPackages, String bundlestartlevel, File bundlefile,
			String serverIp) {
		final HttpClient client = new HttpClient();
		client.getParams().setAuthenticationPreemptive(true);
		String user = SystemFrameWork.proMap.get("webuser");
		String password = SystemFrameWork.proMap.get("webpassword");
		client.getState().setCredentials(new AuthScope(AuthScope.ANY_HOST, AuthScope.ANY_PORT),
				new UsernamePasswordCredentials(null == user ? "smx" : user, null == password ? "smx" : password));

		// String bundleInfo = null == SystemFrameWork.proMap.get("bundleInfo")
		// ? "http://localhost:8181/system/console/bundles/"
		// : SystemFrameWork.proMap.get("bundleInfo");
		String manageUrl = getManageUrl(serverIp);
		if (manageUrl != "" && !SystemConstant.BUNDLES_MANAGER_URL.equals(manageUrl)) {
			final PostMethod method = new PostMethod(manageUrl + SystemConstant.BUNDLES_SPLICE);
			try {
				Part[] parts = { new FilePart("bundlefile", bundlefile), new StringPart("action", "install"),
						new StringPart("refreshPackages", refreshPackages), new StringPart("bundlestart", bundlestart),
						new StringPart("bundlestartlevel", bundlestartlevel) };
				method.setRequestEntity(new MultipartRequestEntity(parts, method.getParams()));

				client.executeMethod(method);
				System.out.println(method.getResponseBodyAsString());
			} catch (HttpException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				method.releaseConnection();
			}
		}
	}
}
