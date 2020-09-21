/*
 * Project: OSMP 
 * FileName: BundleController.java 
 * V1.0
 */
package com.osmp.web.system.bundle.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.osmp.web.system.bundle.entity.Bundle;
import com.osmp.web.system.bundle.entity.BundleInfo;
import com.osmp.web.system.bundle.service.BundleService;
import com.osmp.web.system.servers.entity.Servers;
import com.osmp.web.system.servers.service.ServersService;

/**
 * Description: BUNDLE管理
 *
 * @author: wangkaiping
 * @date: 2014年8月22日 上午10:51:30
 */
@Controller
@RequestMapping("/bundle")
public class BundleController {

	@Autowired
	private BundleService bundleService;
	@Autowired
	private ServersService serversService;

	@RequestMapping("/toBundlelistOfServer")
	public ModelAndView toserverBundlelist(@RequestParam("serverIp") String serverIp) {// 车龙泉
		ModelAndView mav = new ModelAndView("system/bundle/bundlelistOfServer");
		mav.addObject("serverIp", serverIp);
		return mav;
	}

	@RequestMapping("/toList")
	public ModelAndView toList() {
		ModelAndView mav = new ModelAndView("system/bundle/bundlelist");
		List<Servers> list = serversService.getAllServers("");
		mav.addObject("serverList", list);
		return mav;
	}

	@RequestMapping("/toInstall")
	public String toInstall() {
		return "system/bundle/bundleInstall";
	}

	@RequestMapping("/bundleList")
	@ResponseBody
	public List<Bundle> bundleList(
			@RequestParam(value = "displayAll", required = false, defaultValue = "N") String displayAll,
			@RequestParam("serverIp") String serverIp) {
		List<Bundle> list = bundleService.getBundles(serverIp);
		if ("N".equals(displayAll)) {// 默认不显示全部
			List<Bundle> blist = new ArrayList<Bundle>();
			for (Bundle b : list) {
				if (b.getName().indexOf("osmp") > -1 || b.getName().indexOf("osmp") > -1) {
					blist.add(b);
				}
			}
			return blist;
		}
		return list;
	}

	@RequestMapping("/optionBundle")
	@ResponseBody
	public Map<String, Object> optionBundle(@RequestParam("id") String id, @RequestParam("flag") String flag
			,@RequestParam("serverIp") String serverIp) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if ("start".equals(flag)) {
				bundleService.start(id, serverIp);
			} else if ("stop".equals(flag)) {
				bundleService.stop(id, serverIp);
			} else if ("refresh".equals(flag)) {
				bundleService.refresh(id, serverIp);
			} else if ("uninstall".equals(flag)) {
				bundleService.uninstall(id, serverIp);
			}
			map.put("success", true);
			map.put("msg", "添加成功");
		} catch (Exception e) {
			e.printStackTrace();
			map.put("success", false);
			map.put("msg", "添加失败");
		}

		return map;
	}

	@RequestMapping("/toDetail")
	public ModelAndView toDetail(@RequestParam(value = "id", required = false, defaultValue = "-1") String bundleId,
			@RequestParam("serverIp") String serverIp) {
		BundleInfo bundleInfo = bundleService.getBundleInfoById(bundleId, serverIp);
		return new ModelAndView("system/bundle/bundledetail", "bundleInfo", bundleInfo);
	}

	@RequestMapping("/installOrUpdate")
	@ResponseBody
	public Map<String, Object> installOrUpdate(HttpServletRequest request,
			@RequestParam(value = "bundlefile", required = true) MultipartFile file,
			@RequestParam("serverIp") String serverIp) {

		String bundlestart = request.getParameter("bundlestart");
		String refreshPackages = request.getParameter("refreshPackages");
		String bundlestartlevel = request.getParameter("bundlestartlevel");

		Map<String, Object> map = new HashMap<String, Object>();
		try {
			String name = file.getOriginalFilename();
			if (StringUtils.isBlank(name)) {
				map.put("success", false);
				map.put("msg", "请选择Bundle");
				return map;
			}

			boolean isRightExt = StringUtils.endsWithAny(name, new String[] { ".jar", ".war" });
			String ext = StringUtils.substring(name, StringUtils.lastIndexOf(name, "."));

			if (isRightExt) {
				File bundlefile = new File(name);
				try {
					file.transferTo(bundlefile);
					bundleService.installOrUpdate(bundlestart, refreshPackages, bundlestartlevel, bundlefile, serverIp);
					map.put("success", true);
					map.put("msg", "安装/升级成功");
					return map;
				} catch (Exception e) {
					e.printStackTrace();
					map.put("success", false);
					map.put("msg", e.getMessage());
				}
			} else {
				map.put("success", false);
				map.put("msg", "Bundle不能为" + ext + "后缀文件");
			}
		} catch (Exception e) {
			e.printStackTrace();
			map.put("success", false);
			map.put("msg", "安装/升级失败");
		}
		return map;
	}
}
