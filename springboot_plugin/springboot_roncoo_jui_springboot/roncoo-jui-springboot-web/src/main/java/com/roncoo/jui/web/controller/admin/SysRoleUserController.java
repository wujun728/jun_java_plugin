package com.roncoo.jui.web.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.roncoo.jui.common.enums.StatusIdEnum;
import com.roncoo.jui.common.util.base.BaseController;
import com.roncoo.jui.web.bean.qo.SysRoleUserQO;
import com.roncoo.jui.web.bean.vo.SysRoleUserVO;
import com.roncoo.jui.web.service.SysRoleService;
import com.roncoo.jui.web.service.SysRoleUserService;

/**
 * 角色用户关联表
 *
 * @author Wujun
 * @since 2017-10-20
 */
@Controller
@RequestMapping(value = "/admin/sysRoleUser", method = RequestMethod.POST)
public class SysRoleUserController extends BaseController {

	private final static String TARGETID = "admin-sysRoleUser";

	@Autowired
	private SysRoleUserService service;
	@Autowired
	private SysRoleService sysRoleService;

	/**
	 * 设置
	 * 
	 * @param id
	 * @param modelMap
	 */
	@RequestMapping(value = "/set", method = RequestMethod.GET)
	public void set(@RequestParam(value = "pageNum", defaultValue = "1") int pageCurrent, @RequestParam(value = "numPerPage", defaultValue = "20") int pageSize, SysRoleUserQO qo, ModelMap modelMap) {
		List<SysRoleUserVO> list = service.listByUserId(qo.getUserId());
		modelMap.put("bean", qo);
		modelMap.put("roleUserList", list);
		modelMap.put("page", sysRoleService.checkUserByRole(pageCurrent, pageSize, qo, list));
		modelMap.put("statusIdEnums", StatusIdEnum.values());
	}

	/**
	 * 设置
	 * 
	 * @param id
	 * @param modelMap
	 */
	@RequestMapping(value = "/setRole", method = RequestMethod.GET)
	@ResponseBody
	public String setPost(Long userId, String ids, ModelMap modelMap) {
		service.save(userId, ids);
		return success(TARGETID);
	}

}
