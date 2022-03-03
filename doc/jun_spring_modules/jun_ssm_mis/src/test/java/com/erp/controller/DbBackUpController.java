package com.erp.controller;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.erp.model.BackupScheduleConfig;
import com.erp.service.IDbBackUpService;
import com.erp.viewModel.GridModel;
import com.erp.viewModel.Json;
import com.jun.plugin.utils.Constants;
import com.jun.plugin.utils.FileUtil;
import com.jun.plugin.utils.WebUtil;
import com.jun.plugin.utils.WebUtil;
import com.jun.plugin.utils.biz.PageUtil;

//@Namespace("/dbBackUp")
//@Action(value = "dbBackUpAction")
@Controller
@RequestMapping("/dbBackUpController.do")
public class DbBackUpController extends BaseController {
	private static final long serialVersionUID = -1946182036619744959L;
	private BackupScheduleConfig backupScheduleConfig;
	private IDbBackUpService dbBackUpService;

	// private String fileName;

	// public String getFileName()
	// {
	// return fileName;
	// }
	//
	// public void setFileName(String fileName )
	// {
	// this.fileName = fileName;
	// }

	@Autowired
	public void setDbBackUpService(IDbBackUpService dbBackUpService) {
		this.dbBackUpService = dbBackUpService;
	}

	public BackupScheduleConfig getBackupScheduleConfig() {
		return backupScheduleConfig;
	}

	public void setBackupScheduleConfig(BackupScheduleConfig backupScheduleConfig) {
		this.backupScheduleConfig = backupScheduleConfig;
	}

	/**
	 * 函数功能说明 Administrator修改者名字 2013-6-28修改日期 修改内容
	 * 
	 * @Title: findDbBackUpAllList
	 * @Description: TODO:数据库备份
	 * @param @return
	 * @param @throws Exception 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	@RequestMapping(params = "method=findDbBackUpAllList")
	public String findDbBackUpAllList(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		Map param = WebUtil.getAllParameters(request);
		if (null != param.get("searchValue") && !"".equals(String.valueOf(param.get("searchValue")))) {
			map.put(String.valueOf(param.get("searchName")), Constants.GET_SQL_LIKE + String.valueOf(param.get("searchValue")) + Constants.GET_SQL_LIKE);
		}
		PageUtil pageUtil = new PageUtil();
		WebUtil.MapToBean(param, pageUtil);
		GridModel gridModel = new GridModel();
		gridModel.setRows(dbBackUpService.findLogsAllList(map, pageUtil));
		gridModel.setTotal(dbBackUpService.getCount(map, pageUtil));
		OutputJson(gridModel, response);
		return null;
	}

	/**
	 * 函数功能说明 Administrator修改者名字 2013-7-11修改日期 修改内容
	 * 
	 * @Title: getScheduleConfig
	 * @Description: TODO:获取调度配置
	 * @param @return
	 * @param @throws Exception 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	@RequestMapping(params = "method=getScheduleConfig")
	public String getScheduleConfig(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		OutputJson(dbBackUpService.getBackupScheduleConfig(), response);
		return null;
	}

	/**
	 * 函数功能说明 Administrator修改者名字 2013-7-11修改日期 修改内容
	 * 
	 * @Title: handSchedule
	 * @Description: TODO:手动备份方法
	 * @param @return
	 * @param @throws Exception 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	@RequestMapping(params = "method=handSchedule")
	public String handSchedule(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Json json = new Json();
		json.setTitle("提示");
		if (dbBackUpService.handSchedule(request)) {
			json.setStatus(true);
			json.setMessage("备份完成!");
		} else {
			json.setStatus(false);
			json.setMessage("备份失败!");
		}
		OutputJson(json, Constants.TEXT_TYPE_PLAIN, response);
		return null;
	}

	/**
	 * 函数功能说明 Administrator修改者名字 2013-7-11修改日期 修改内容
	 * 
	 * @Title: checkBackUp
	 * @Description: TODO:检查备份文件是否存在 ，并进行压缩
	 * @param @return
	 * @param @throws Exception 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	@RequestMapping(params = "method=checkBackUp")
	public String checkBackUp(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		BackupScheduleConfig backupScheduleConfig = new BackupScheduleConfig();
		Map param = WebUtil.getAllParameters(request);
		WebUtil.MapToBean(param, backupScheduleConfig);
		String fileName=String.valueOf(param.get("fileName"));
		
		Json json = new Json();
		json.setTitle("提示");
		if ("".equals(fileName) || null == fileName) {
			json.setStatus(false);
			json.setMessage("文件不存在!");
		} else {
			String sqlName = Constants.BASE_PATH + "attachment" + File.separator + "dbBackUp" + File.separator + fileName;
			String zipDir = Constants.BASE_PATH + "attachment" + File.separator + "dbBackUpZip";
			File file = new File(sqlName);
			if (file.exists()) {
				File zipDirPath = new File(zipDir);
				if (!zipDirPath.exists()) {
					zipDirPath.mkdir();
				}
				String zipNameString = fileName.substring(0, fileName.lastIndexOf("."));
				String zipPath = zipDir + File.separator + zipNameString + Constants.FILE_SUFFIX_ZIP;
				File fileZip = new File(zipPath);
				if (!fileZip.exists()) {
					FileUtil.createZip(sqlName, zipPath);
				}
				json.setStatus(true);
			} else {
				json.setStatus(false);
				json.setMessage("文件不存在!");
			}
		}
		OutputJson(json, response);
		return null;
	}

	/**
	 * 函数功能说明 Administrator修改者名字 2013-7-11修改日期 修改内容
	 * 
	 * @Title: downBackUpFile
	 * @Description: TODO:下载备份文件
	 * @param @return
	 * @param @throws Exception 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	@RequestMapping(params = "method=downBackUpFile")
	public String downBackUpFile(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		BackupScheduleConfig backupScheduleConfig = new BackupScheduleConfig();
		Map param = WebUtil.getAllParameters(request);
		WebUtil.MapToBean(param, backupScheduleConfig);
		String fileName=String.valueOf(param.get("fileName"));
		String zipName = fileName.substring(0, fileName.lastIndexOf(".")) + Constants.FILE_SUFFIX_ZIP;
		String sqlName = Constants.BASE_PATH + "attachment" + File.separator + "dbBackUpZip" + File.separator + zipName;
		FileUtil.downFile(zipName, response, sqlName);
		return null;
	}

	/**
	 * 函数功能说明 Administrator修改者名字 2013-7-10修改日期 修改内容
	 * 
	 * @Title: schedule
	 * @Description: TODO:定时任务启动
	 * @param @return
	 * @param @throws Exception 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	@RequestMapping(params = "method=schedule")
	public String schedule(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		BackupScheduleConfig backupScheduleConfig = new BackupScheduleConfig();
		Map param = WebUtil.getAllParameters(request);
		WebUtil.MapToBean(param, backupScheduleConfig);
		dbBackUpService.unSchedule();
		String msg = dbBackUpService.schedule(backupScheduleConfig.getScheduleHour(), backupScheduleConfig.getScheduleMinute(), "Y");
		Json json = new Json();
		json.setTitle("提示");
		json.setStatus(true);
		json.setMessage(msg);
		OutputJson(json, Constants.TEXT_TYPE_PLAIN, response);
		return null;
	}

	// public BackupScheduleConfig getModel()
	// {
	// if (null==backupScheduleConfig)
	// {
	// backupScheduleConfig=new BackupScheduleConfig();
	// }
	// return backupScheduleConfig;
	// }

}
