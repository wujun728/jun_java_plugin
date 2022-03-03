package com.erp.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.erp.model.OrderSaleLine;
import com.erp.model.Project;
import com.erp.model.ProjectFollow;
import com.erp.service.IProjectService;
import com.erp.viewModel.GridModel;
//@Namespace("/project")
//@Action(value = "projectAction")
import com.jun.plugin.utils.Constants;
import com.jun.plugin.utils.WebUtil;
import com.jun.plugin.utils.WebUtil;
import com.jun.plugin.utils.biz.PageUtil;

@Controller
@RequestMapping("/projectController.do")
public class ProjectController extends BaseController {
	private static final long serialVersionUID = -8785609987685604362L;
	private IProjectService projectService;

	// private Project project;
	// public Project getProject()
	// {
	// return project;
	// }
	// public void setProject(Project project )
	// {
	// this.project = project;
	// }

	@Autowired
	public void setProjectService(IProjectService projectService) {
		this.projectService = projectService;
	}

	/**
	 * 函数功能说明 Administrator修改者名字 2013-6-27修改日期 修改内容
	 * 
	 * @Title: findCustomers
	 * @Description: TODO:查询所有客户
	 * @param @return
	 * @param @throws Exception 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	@RequestMapping(params = "method=findCustomers")
	public String findCustomers(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		OutputJson(projectService.findCustomers(), response);
		return null;
	}

	/**
	 * 函数功能说明 Administrator修改者名字 2013-6-27修改日期 修改内容
	 * 
	 * @Title: findProjectFollowsList
	 * @Description: TODO:查询项目记录
	 * @param @return
	 * @param @throws Exception 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	@RequestMapping(params = "method=findProjectFollowsList")
	public String findProjectFollowsList(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map param = WebUtil.getAllParameters(request);
		Project project = new Project();
		WebUtil.MapToBean(param, project);
		OutputJson(projectService.findProjectFollowsList(project.getProjectId()), response);
		return null;
	}

	/**
	 * 函数功能说明 Administrator修改者名字 2013-6-27修改日期 修改内容
	 * 
	 * @Title: findCustomerList
	 * @Description: TODO:查询所有项目
	 * @param @return
	 * @param @throws Exception 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	@RequestMapping(params = "method=findProjectList")
	public String findProjectList(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		Map param = WebUtil.getAllParameters(request);
		if (null != param.get("searchValue") && !"".equals(String.valueOf(param.get("searchValue")))) {
			map.put(String.valueOf(param.get("searchName")), Constants.GET_SQL_LIKE + String.valueOf(param.get("searchValue")) + Constants.GET_SQL_LIKE);
		}
		PageUtil pageUtil = new PageUtil();
		WebUtil.MapToBean(param, pageUtil);
		GridModel gridModel = new GridModel();
		gridModel.setRows(projectService.findProjectList(map, pageUtil));
		gridModel.setTotal(projectService.getCount(map, pageUtil));
		OutputJson(gridModel, response);
		return null;
	}

	/**
	 * 函数功能说明 TODO: Administrator修改者名字 2013-7-1修改日期 修改内容
	 * 
	 * @Title: findProjectListCombobox
	 * @Description: TODO:查询所有项目下拉框格式
	 * @param @return
	 * @param @throws Exception 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	@RequestMapping(params = "method=findProjectListCombobox")
	public String findProjectListCombobox(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		OutputJson(projectService.findProjectListCombobox(),response);
		return null;
	}

	/**
	 * 函数功能说明 Administrator修改者名字 2013-6-27修改日期 修改内容
	 * 
	 * @Title: persistenceProject
	 * @Description: TODO:持久化persistenceProject
	 * @param @return
	 * @param @throws Exception 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	@RequestMapping(params = "method=persistenceProject")
	public String persistenceProject(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, List<ProjectFollow>> map = new HashMap<String, List<ProjectFollow>>();
		Map param = WebUtil.getAllParameters(request);
		Project project = new Project();
		WebUtil.MapToBean(param, project);

		if (param.get("inserted") != null && !"".equals(String.valueOf(param.get("inserted")))) {
			map.put("addList", JSON.parseArray(String.valueOf(param.get("inserted")), ProjectFollow.class));
		}
		if (param.get("updated") != null && !"".equals(String.valueOf(param.get("updated")))) {
			map.put("updList", JSON.parseArray(String.valueOf(param.get("updated")), ProjectFollow.class));
		}
		if (param.get("deleted") != null && !"".equals(String.valueOf(param.get("deleted")))) {
			map.put("delList", JSON.parseArray(String.valueOf(param.get("deleted")), ProjectFollow.class));
		}
		OutputJson(getMessage(projectService.persistenceProject(project, map)), Constants.TEXT_TYPE_PLAIN, response);
		return null;
	}

	/**
	 * 函数功能说明 Administrator修改者名字 2013-6-27修改日期 修改内容
	 * 
	 * @Title: delProject
	 * @Description: TODO:删除Project
	 * @param @return
	 * @param @throws Exception 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	@RequestMapping(params = "method=delProject")
	public String delProject(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, List<OrderSaleLine>> map = new HashMap<String, List<OrderSaleLine>>();
		Map param = WebUtil.getAllParameters(request);
		Project project = new Project();
		WebUtil.MapToBean(param, project);
		OutputJson(getMessage(projectService.delProject(project.getProjectId())), response);
		return null;
	}

	// public Project getModel()
	// {
	// if (null==project)
	// {
	// project=new Project();
	// }
	// return project;
	// }
}
