package com.osmp.web.system.dict.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.osmp.web.core.SystemFrameWork;
import com.osmp.web.system.dict.entity.Dict;
import com.osmp.web.system.dict.entity.DictItem;
import com.osmp.web.system.dict.service.DictItemService;
import com.osmp.web.system.dict.service.DictService;
import com.osmp.web.utils.DataGrid;
import com.osmp.web.utils.Pagination;

/**
 * Description:数据字典控制器
 * 
 * @author: wangkaiping
 * @date: 2014年11月14日 下午2:33:21
 */
@Controller
@RequestMapping("/dict")
public class DictController {

	@Autowired
	private DictService dictService;

	@Autowired
	private DictItemService dictItemService;

	@RequestMapping("/toList")
	public String list() {
		return "system/dict/dictlist";
	}

	/**
	 * 编辑字典
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/toEdit")
	public ModelAndView toEdit(@RequestParam(value = "id", required = false, defaultValue = "") String id) {
		ModelAndView mv = new ModelAndView("system/dict/dictEdit");
		if (!"".equals(id)) {
			Dict dict = new Dict();
			dict.setId(id);
			mv.addObject("dict", dictService.getDict(dict));
		}
		return mv;
	}

	/**
	 * 字典项管理列表界面
	 * 
	 * @param dict
	 * @return
	 */
	@RequestMapping("/toDictItem")
	public ModelAndView toDictItem(Dict dict) {
		ModelAndView mv = new ModelAndView("system/dict/dictitemlist");
		mv.addObject("code", dict.getCode());
		return mv;
	}

	/**
	 * 跳转到字典表新增界面
	 * 
	 * @param code
	 * @return
	 */
	@RequestMapping("/toDictItemEdit")
	public ModelAndView toDictItemEdit(String code) {
		ModelAndView mv = new ModelAndView("system/dict/dictItemEdit");
		mv.addObject("code", code);
		return mv;
	}

	/**
	 * 获取字典项列表数据
	 * 
	 * @param code
	 * @return
	 */
	@RequestMapping("/dictitemList")
	@ResponseBody
	public List<DictItem> dictitemList(String code) {
		List<DictItem> list = dictItemService.getAll("parentCode= '" + code + "'");
		return list;
	}

	/**
	 * 字典列表数据
	 * 
	 * @param dg
	 * @return
	 */
	@RequestMapping("/dictList")
	@ResponseBody
	public Map<String, Object> dictList(DataGrid dg) {
		Pagination<Dict> dict = new Pagination<Dict>(dg.getPage(), dg.getRows());
		List<Dict> list = dictService.getDictByPage(dict);
		dg.setTotal(dict.getTotal());
		dg.setResult(list);
		return dg.getMap();
	}

	/**
	 * 编辑字典
	 * 
	 * @param dict
	 * @return
	 */
	@RequestMapping("/saveOrUpdate")
	@ResponseBody
	public Map<String, Object> saveOrUpdate(Dict dict) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if (null != dict && dict.getId().equals("")) {
				dict.setId(UUID.randomUUID().toString());
				dictService.insert(dict);
			} else {
				dictService.update(dict);
			}
		} catch (Exception e) {
			e.printStackTrace();
			map.put("success", false);
			map.put("msg", "操作失败");
			return map;
		}
		map.put("success", true);
		map.put("msg", "操作成功");
		return map;
	}

	@RequestMapping("/dictItemsave")
	@ResponseBody
	public Map<String, Object> dictItemsave(DictItem dictItem) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if (null != dictItem && null == dictItem.getId()) {
				dictItem.setId(UUID.randomUUID().toString());
				dictItemService.insert(dictItem);
			} else {
				dictItemService.update(dictItem);
			}
		} catch (Exception e) {
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
	@RequestMapping("/deleteDict")
	@ResponseBody
	public Map<String, Object> deleteDict(Dict dict) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			dictService.deleteDict(dict);
			map.put("success", true);
			map.put("msg", "添加成功");
		} catch (Exception e) {
			e.printStackTrace();
			map.put("success", false);
			map.put("msg", "添加失败");
		}

		return map;
	}

	@RequestMapping("/deleteDictItem")
	@ResponseBody
	public Map<String, Object> deleteDictItem(DictItem dict) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			dictItemService.deleteDictIteme(dict);
			map.put("success", true);
			map.put("msg", "添加成功");
		} catch (Exception e) {
			e.printStackTrace();
			map.put("success", false);
			map.put("msg", "添加失败");
		}

		return map;
	}

	/**
	 * 刷新字典数据到内存
	 * 
	 * @return
	 */
	@RequestMapping("/refresh")
	@ResponseBody
	public Map<String, Object> deleteDict() {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			dictService.refresh();
			map.put("success", true);
			map.put("msg", "操作成功");
		} catch (Exception e) {
			e.printStackTrace();
			map.put("success", false);
			map.put("msg", "操作失败");
		}

		return map;
	}

	/**
	 * 根据字典CODE获取字典项JSON
	 * 
	 * @return
	 */
	@RequestMapping("/dictJson")
	@ResponseBody
	public List<DictItem> dictJson(String code) {
		List<DictItem> list = SystemFrameWork.dictMap.get(code);
		return list;
	}

}
