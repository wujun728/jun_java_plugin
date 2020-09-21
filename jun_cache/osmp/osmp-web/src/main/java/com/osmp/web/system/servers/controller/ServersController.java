package com.osmp.web.system.servers.controller;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.osmp.web.base.core.SystemGlobal;
import com.osmp.web.core.SystemFrameWork;
import com.osmp.web.system.servers.entity.Servers;
import com.osmp.web.system.servers.entity.VmData;
import com.osmp.web.system.servers.service.ServersService;
import com.osmp.web.utils.DataGrid;
import com.osmp.web.utils.Pagination;
import com.osmp.web.utils.Utils;

/**
 * Description:服务器列表
 *
 * @author: wangqiang
 * @date: 2014年11月27日 上午10:04:25
 */

@Controller
@RequestMapping("servers")
public class ServersController {

	@Autowired
	private ServersService serversService;

	@RequestMapping("/toList")
	public String toList() {
		return "system/servers/serverslist";
	}

	/**
	 * 获取服务器列表
	 *
	 * @param dg
	 * @return
	 */
	@RequestMapping("/serversList")
	@ResponseBody
	public Map<String, Object> serversList(DataGrid dg) {
		final Pagination<Servers> servers = new Pagination<Servers>(dg.getPage(), dg.getRows());
		final List<Servers> list = serversService.getServersByPage(servers);
		for (Servers i : list) {
			if (SystemFrameWork.checkServerState(i.getServerIp())) {
				i.setState(Servers.STATE_UP);
			} else {
				i.setState(Servers.STATE_DOWN);
			}
		}
		dg.setTotal(servers.getTotal());
		dg.setResult(list);
		return dg.getMap();
	}

	/**
	 * 执行服务器命令
	 *
	 * @param id
	 * @param flag
	 * @return
	 */
	@RequestMapping("/optionServers")
	@ResponseBody
	public Map<String, Object> optionServers(@RequestParam("id") String id, @RequestParam("flag") String flag) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if ("start".equals(flag)) {
				map = serversService.startUp(id);
			} else if ("stop".equals(flag)) {
				map = serversService.shutDown(id);
			}
		} catch (Exception e) {
			e.printStackTrace();
			map.put("success", false);
			map.put("msg", "命令执行失败,ERROR=[" + e.getMessage() + "]");
		}

		return map;
	}

	/**
	 * 编辑服务
	 *
	 * @param id
	 * @return
	 */
	@RequestMapping("/toEdit")
	public ModelAndView toEdit(@RequestParam(value = "id", required = false, defaultValue = "") String id) {
		final ModelAndView mv = new ModelAndView("system/servers/serversEdit");
		if (!"".equals(id)) {
			final Servers servers = new Servers();
			servers.setId(id);
			mv.addObject("servers", serversService.getServers(servers));
		}
		return mv;
	}

	/**
	 * 编辑服务
	 *
	 * @param servers
	 * @return
	 */
	@RequestMapping("/saveOrUpdate")
	@ResponseBody
	public Map<String, Object> saveOrUpdate(Servers servers) {
		final Map<String, Object> map = new HashMap<String, Object>();
		try {
			if (null != servers && servers.getId().equals("")) {
				servers.setId(UUID.randomUUID().toString());
				serversService.insert(servers);
			} else {
				serversService.update(servers);
			}
		} catch (final Exception e) {
			e.printStackTrace();
			map.put("success", false);
			map.put("msg", "操作失败");
			return map;
		}
		map.put("success", true);
		map.put("msg", "操作成功");
		return map;
	}

	/*******
	 * 删除字典
	 *
	 * @param area
	 * @return
	 */
	@RequestMapping("/deleteServers")
	@ResponseBody
	public Map<String, Object> deleteServers(Servers servers) {
		final Map<String, Object> map = new HashMap<String, Object>();
		try {
			serversService.delete(servers);
			map.put("success", true);
			map.put("msg", "添加成功");
		} catch (final Exception e) {
			e.printStackTrace();
			map.put("success", false);
			map.put("msg", "添加失败");
		}

		return map;
	}

	@RequestMapping("/xtmsg")
	@ResponseBody
	public String receivext(HttpServletRequest request, String name) {
		String ip = Utils.getRemoteHost(request);
		SystemFrameWork.refreshXtMsg(ip, System.currentTimeMillis());
		return "ok";
	}

	@RequestMapping("/jvmMomitor")
	@ResponseBody
	public String jvmMomitor(HttpServletRequest request, String memTotal, String memUse, String cpuUse, String time) {
		String ip = Utils.getRemoteHost(request);
		SystemFrameWork.refreshXtMsg(ip, System.currentTimeMillis());
		serversService.addVmdataMap(ip, memTotal, memUse, cpuUse, time);
		return "ok";
	}

	@RequestMapping("/getVmData")
	@ResponseBody
	public Map<String, Object> getVmData(String ip) {
		Map<String, Object> map = new HashMap<String, Object>();
		LinkedList<VmData> vmdataList = SystemGlobal.getVmData(ip);
		if (vmdataList == null) {
			map.put("xcategories", "");
			map.put("cpuUse", "");
			map.put("memUse", "");
			return map;
		}
		map.put("xcategories", this.getDateList(vmdataList));
		map.put("memUse", this.getMemUseList(vmdataList));
		map.put("cpuUse", this.getCpuUseList(vmdataList));
		return map;
	}

	/**
	 * 得到时间list
	 * 
	 * @param vmdataList
	 * @return
	 */
	private LinkedList<String> getDateList(LinkedList<VmData> vmdataList) {
		LinkedList<String> dates = new LinkedList<String>();
		for (int i = 0; i < vmdataList.size(); i++) {
			VmData vmData = vmdataList.get(i);
			dates.addLast(new SimpleDateFormat("HH:mm:ss").format(vmData.getTime()));
		}
		return dates;
	}

	/**
	 * 得到内存使用率list
	 * 
	 * @param vmdataList
	 * @return
	 */
	private LinkedList<Double> getMemUseList(LinkedList<VmData> vmdataList) {
		LinkedList<Double> dates = new LinkedList<Double>();
		for (int i = 0; i < vmdataList.size(); i++) {
			VmData vmData = vmdataList.get(i);
			dates.addLast(vmData.getMemUse());
		}
		return dates;
	}

	/**
	 * 得到cpu占用率list
	 * 
	 * @param vmdataList
	 * @return
	 */
	private LinkedList<Double> getCpuUseList(LinkedList<VmData> vmdataList) {
		LinkedList<Double> dates = new LinkedList<Double>();
		for (int i = 0; i < vmdataList.size(); i++) {
			VmData vmData = vmdataList.get(i);
			dates.addLast(vmData.getCpuUse());
		}
		return dates;
	}

}
