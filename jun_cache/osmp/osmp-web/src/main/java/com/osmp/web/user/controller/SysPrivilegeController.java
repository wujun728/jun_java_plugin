package com.osmp.web.user.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.osmp.web.user.entity.PrivilegeTreeGrid;
import com.osmp.web.user.entity.SysPrivilege;
import com.osmp.web.user.entity.SysPrivilegeTreeGrid;
import com.osmp.web.user.service.SysPrivilegeService;

/**
 * Description:
 * 
 * @author: wangkaiping
 * @date: 2014年10月13日 上午11:22:39
 */
@Controller
@RequestMapping("/privilege")
public class SysPrivilegeController {

	@Autowired
	private SysPrivilegeService services;

	@RequestMapping("/toList")
	public String list() {
		return "user/privilege/list";
	}

	@RequestMapping("/toAdd")
	public String toAdd() {
		return "user/privilege/privilegeAdd";
	}

	@RequestMapping("/toEdit")
	public String toEdit(SysPrivilege p, HttpServletRequest req) {
		SysPrivilege sysPrivilege = services.get(p);
		req.setAttribute("privilege", sysPrivilege);
		return "user/privilege/privilegeEdit";
	}

	@RequestMapping("/list")
	@ResponseBody
	public List<SysPrivilegeTreeGrid> dataGride() {
		List<SysPrivilege> list = services.getList();
		List<SysPrivilegeTreeGrid> sptList = this.changeTreeGrid(list);
		return sptList;
	}

	@RequestMapping("/addPrivilege")
	@ResponseBody
	public Map<String, Object> addPrivilege(SysPrivilege p) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if (null != p && p.getId() != 0) {
				services.update(p);
			} else {
				services.insert(p);
			}
		} catch (Exception e) {
			e.printStackTrace();
			map.put("success", false);
			map.put("msg", "操作失败");
			return map;
		}
		map.put("success", true);
		map.put("msg", "操作成功");
		map.put("obj", p);
		return map;
	}

	@RequestMapping("/deletePrivilege")
	@ResponseBody
	public Map<String, Object> deletePrivilege(SysPrivilege p) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			services.delete(p);
		} catch (Exception e) {
			map.put("success", false);
			map.put("msg", "操作失败");
			return map;
		}
		map.put("success", true);
		map.put("msg", "操作成功");
		return map;
	}

	public List<SysPrivilegeTreeGrid> changeTreeGrid(List<SysPrivilege> plist) {
		List<SysPrivilegeTreeGrid> list = new ArrayList<SysPrivilegeTreeGrid>();
		for (SysPrivilege i : plist) {
			if (i.getPid() == -1) {
				SysPrivilegeTreeGrid stg = new SysPrivilegeTreeGrid(i);
				list.add(stg);
				this.fuck(stg, plist);
			}
		}
		return list;
	}

	public void fuck(SysPrivilegeTreeGrid stg, List<SysPrivilege> plist) {
		for (SysPrivilege i : plist) {
			if (i.getPid() == stg.getId()) {
				SysPrivilegeTreeGrid child = new SysPrivilegeTreeGrid(i);
				stg.getChildren().add(child);
				stg.setState("closed");
				this.fuck(child, plist);
			}
		}
	}

	@RequestMapping("/getPidList")
	@ResponseBody
	public List<PrivilegeTreeGrid> getPidList(@RequestParam(value = "pid", required = true) int pid) {
		List<PrivilegeTreeGrid> atgList = new ArrayList<PrivilegeTreeGrid>();
		List<SysPrivilege> ss = services.getList();
		PrivilegeTreeGrid atg = null;
		for (SysPrivilege s : ss) {
			atg = new PrivilegeTreeGrid();
			atg.setId(s.getId());
			atg.setText(s.getName());
			atg.setPid(s.getPid());
			atgList.add(atg);
		}
		// 判断是否是第一级，如果是就加入默认-1值
		if (pid == -1) {
			atg = new PrivilegeTreeGrid();
			atg.setChecked(true);
			atg.setText("-1");
			atg.setPid(-1);
			atgList.add(atg);
		}

		// 判断选择项
		for (int i = 0; i < atgList.size(); i++) {
			PrivilegeTreeGrid pt = atgList.get(i);
			if (pt.getId() == pid) {
				atgList.get(i).setChecked(true);
			}
		}
		// 折叠
		return getCheprivilegeTreeGrid(atgList);
	}

	public List<PrivilegeTreeGrid> getCheprivilegeTreeGrid(List<PrivilegeTreeGrid> atgList) {
		List<PrivilegeTreeGrid> list = new ArrayList<PrivilegeTreeGrid>();
		for (int i = 0; i < atgList.size(); i++) {
			PrivilegeTreeGrid pt = atgList.get(i);
			if (pt.getPid() == -1) {
				for (int j = 0; j < atgList.size(); j++) {
					if (pt.getId() == atgList.get(j).getPid()) {
						pt.getChildren().add(atgList.get(j));
					}
				}
				list.add(pt);
			}
		}
		return list;
	}

	@RequestMapping("/getPidAddList")
	@ResponseBody
	public List<PrivilegeTreeGrid> getPidAddList() {
		List<PrivilegeTreeGrid> atgList = new ArrayList<PrivilegeTreeGrid>();
		List<SysPrivilege> ss = services.getList();
		PrivilegeTreeGrid atg = null;
		for (SysPrivilege s : ss) {
			atg = new PrivilegeTreeGrid();
			atg.setId(s.getId());
			atg.setText(s.getName());
			atg.setPid(s.getPid());
			atgList.add(atg);
		}
		return getCheprivilegeTreeGrid(atgList);
	}

}
