package com.erp.springjdbc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.erp.controller.BaseController;
import com.erp.jee.entity.GbuyOrderEntity;
import com.erp.jee.page2.GbuyOrderCustomPage;
import com.erp.jee.page2.GbuyOrderPage;
import com.erp.jee.page2.GbuyOrderProductPage;
import com.erp.jee.pageModel.Json;
import com.erp.jee.service.GbuyOrderServiceI;
import com.jun.plugin.utils.BeanUtil;
import com.jun.plugin.utils.ExceptionUtil;

/**   
 * @Title: Action
 * @Description: 订单抬头
 * @author Wujun
 * @date 2011-12-19 13:09:35
 * @version V1.0   
 *
 */
//@Action(value = "gbuyOrderAction", results = { @Result(name = "gbuyOrder", location = "/sun/order/gbuyOrder.jsp"),
//													   @Result(name = "gbuyOrder-main-add", location = "/sun/order/gbuyOrder-main-add.jsp"),
//													   @Result(name = "gbuyOrder-main-edit", location = "/sun/order/gbuyOrder-main-edit.jsp")})
@Controller
@RequestMapping("/GbuyOrderAction.do")
public class GbuyOrderAction extends BaseController{

	private static final Logger logger = Logger.getLogger(GbuyOrderAction.class);

	private GbuyOrderServiceI gbuyOrderService;

	private GbuyOrderPage gbuyOrderPage = new GbuyOrderPage();

	/**订单客户明细*/
	private List<GbuyOrderCustomPage> gbuyOrderCustomList = new ArrayList<GbuyOrderCustomPage>();
	public List<GbuyOrderCustomPage> getGbuyOrderCustomList(Model model, HttpServletRequest request, HttpServletResponse response){
		return gbuyOrderCustomList;
	}
	public void setGbuyOrderCustomList(List<GbuyOrderCustomPage> gbuyOrderCustomList) {
		this.gbuyOrderCustomList = gbuyOrderCustomList;
	}
	/**订单产品明细*/
	private List<GbuyOrderProductPage> gbuyOrderProductList = new ArrayList<GbuyOrderProductPage>();
	public List<GbuyOrderProductPage> getGbuyOrderProductList(Model model, HttpServletRequest request, HttpServletResponse response){
		return gbuyOrderProductList;
	}
	public void setGbuyOrderProductList(List<GbuyOrderProductPage> gbuyOrderProductList) {
		this.gbuyOrderProductList = gbuyOrderProductList;
	}
	
	public GbuyOrderPage getModel(Model model, HttpServletRequest request, HttpServletResponse response){
		return gbuyOrderPage;
	}


	public GbuyOrderServiceI getGbuyOrderService(Model model, HttpServletRequest request, HttpServletResponse response){
		return gbuyOrderService;
	}

	@Autowired
	public void setGbuyOrderService(GbuyOrderServiceI gbuyOrderService) {
		this.gbuyOrderService = gbuyOrderService;
	}


	/**
	 * 跳转到订单抬头管理页面
	 * 
	 * @return
	 */
	public String goGbuyOrder(Model model, HttpServletRequest request, HttpServletResponse response){
		return "gbuyOrder";
	}

	
	/**
	 * 跳转一对多添加页面
	 * 
	 * @return
	 */
	public String toGbuyOrderMainAdd(Model model, HttpServletRequest request, HttpServletResponse response){
		return "gbuyOrder-main-add";
	}
	
	/**
	 * 跳转一对多编辑页面
	 * 
	 * @return
	 */
	public String toGbuyOrderMainEdit(Model model, HttpServletRequest request, HttpServletResponse response){
		//主表KEY参数
		String obid = this.gbuyOrderPage.getObid();
		//[1].根据订单ID，查询订单抬头信息
		GbuyOrderEntity gbuyOrderEntity  = this.gbuyOrderService.get(obid);
		try {
			BeanUtil.copyBean2Bean(this.gbuyOrderPage, gbuyOrderEntity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		//[2].根据主表ID,查询子表明细
		//订单客户明细
		gbuyOrderCustomList = gbuyOrderService.getGbuyOrderCustomListByFkey(gbuyOrderEntity.getObid(),gbuyOrderEntity.getGoOrderCode());
		//订单产品明细
		gbuyOrderProductList = gbuyOrderService.getGbuyOrderProductListByFkey(gbuyOrderEntity.getObid(),gbuyOrderEntity.getGoOrderCode());
		return "gbuyOrder-main-edit";
	}
	
	
	/**
	 * 跳转到查看desc页面
	 * 
	 * @return
	 */
	public void showDesc(Model model, HttpServletRequest request, HttpServletResponse response){
		writeJson(gbuyOrderService.get(gbuyOrderPage), response);
	}

	/**
	 * 获得pageHotel数据表格
	 */
	public void datagrid(Model model, HttpServletRequest request, HttpServletResponse response){
		writeJson(gbuyOrderService.datagrid(gbuyOrderPage), response);
	}
	
	
	/**
	 * 获得无分页的所有数据
	 */
	public void  combox(){
		writeJson(gbuyOrderService.listAll(gbuyOrderPage), null);
	}

	/**
	 * 添加一对多
	 */
	public void addMain(Model model, HttpServletRequest request, HttpServletResponse response){
		Json j = new Json();
		try {
			gbuyOrderService.addMain(gbuyOrderPage, gbuyOrderCustomList,gbuyOrderProductList);
			j.setSuccess(true);
			j.setMsg("添加成功！");
		} catch (Exception e) {
			j.setMsg("添加失败！");
			logger.error(ExceptionUtil.getExceptionMessage(e));
		}
		writeJson(j, response);
	}
	
	/**
	 * 编辑订单抬头
	 */
	public void editMain(Model model, HttpServletRequest request, HttpServletResponse response){
		Json j = new Json();
		try {
			gbuyOrderService.editMain(gbuyOrderPage, gbuyOrderCustomList,gbuyOrderProductList);
			j.setSuccess(true);
			j.setMsg("修改订单抬头成功！");
		} catch (Exception e) {
			j.setMsg("修改失败！");
			logger.error(ExceptionUtil.getExceptionMessage(e));
		}
		writeJson(j, response);
	}

	/**
	 * 删除订单抬头
	 */
	public void delete(Model model, HttpServletRequest request, HttpServletResponse response){
		Json j = new Json();
		gbuyOrderService.delete(gbuyOrderPage.getIds());
		j.setSuccess(true);
		writeJson(j, response);
	}

	/**
	 * 文件上传
	 */
	/*public void upload(Model model, HttpServletRequest request, HttpServletResponse response){
		String savePath = ServletActionContext.getServletContext().getRealPath("/") + ResourceUtil.getUploadDirectory() + "/";// 文件保存目录路径
		String saveUrl = "/" + ResourceUtil.getUploadDirectory() + "/";// 文件保存目录URL

		String contentDisposition = ServletActionContext.getRequest().getHeader("Content-Disposition");// 如果是HTML5上传文件，那么这里有相应头的

		if (contentDisposition != null) {// HTML5拖拽上传文件
			Long fileSize = Long.valueOf(ServletActionContext.getRequest().getHeader("Content-Length"));// 上传的文件大小
			String fileName = contentDisposition.substring(contentDisposition.lastIndexOf("filename=\""));// 文件名称
			fileName = fileName.substring(fileName.indexOf("\"") + 1);
			fileName = fileName.substring(0, fileName.indexOf("\""));

			ServletInputStream inputStream;
			try {
				inputStream = ServletActionContext.getRequest().getInputStream();
			} catch (IOException e) {
				uploadError("上传文件出错！");
				ExceptionUtil.getExceptionMessage(e);
				return;
			}

			if (inputStream == null) {
				uploadError("您没有上传任何文件！");
				return;
			}

			if (fileSize > ResourceUtil.getUploadFileMaxSize()) {
				uploadError("上传文件超出限制大小！", fileName);
				return;
			}

			// 检查文件扩展名
			String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
			if (!Arrays.<String> asList(ResourceUtil.getUploadFileExts().split(",")).contains(fileExt)) {
				uploadError("上传文件扩展名是不允许的扩展名。\n只允许" + ResourceUtil.getUploadFileExts() + "格式！");
				return;
			}

			savePath += fileExt + "/";
			saveUrl += fileExt + "/";

			SimpleDateFormat yearDf = new SimpleDateFormat("yyyy");
			SimpleDateFormat monthDf = new SimpleDateFormat("MM");
			SimpleDateFormat dateDf = new SimpleDateFormat("dd");
			Date date = new Date();
			String ymd = yearDf.format(date) + "/" + monthDf.format(date) + "/" + dateDf.format(date) + "/";
			savePath += ymd;
			saveUrl += ymd;

			File uploadDir = new File(savePath);// 创建要上传文件到指定的目录
			if (!uploadDir.exists()) {
				uploadDir.mkdirs();
			}

			String newFileName = UUID.randomUUID().toString().replaceAll("-", "") + "." + fileExt;// 新的文件名称
			File uploadedFile = new File(savePath, newFileName);

			try {
				FileCopyUtils.copy(inputStream, new FileOutputStream(uploadedFile));
			} catch (FileNotFoundException e) {
				uploadError("上传文件出错！");
				ExceptionUtil.getExceptionMessage(e);
				return;
			} catch (IOException e) {
				uploadError("上传文件出错！");
				ExceptionUtil.getExceptionMessage(e);
				return;
			}

			uploadSuccess(ServletActionContext.getRequest().getContextPath() + saveUrl + newFileName, fileName, 0);// 文件上传成功

			return;
		}

		MultiPartRequestWrapper multiPartRequest = (MultiPartRequestWrapper) ServletActionContext.getRequest();// 由于struts2上传文件时自动使用了request封装
		File[] files = multiPartRequest.getFiles(ResourceUtil.getUploadFieldName());// 上传的文件集合
		String[] fileNames = multiPartRequest.getFileNames(ResourceUtil.getUploadFieldName());// 上传文件名称集合

		if (files == null || files.length < 1) {
			uploadError("您没有上传任何文件！");
			return;
		}

		for (int i = 0; i < files.length; i++) {// 循环所有文件
			File file = files[i];// 上传的文件(临时文件)
			String fileName = fileNames[i];// 上传文件名

			if (file.length() > ResourceUtil.getUploadFileMaxSize()) {
				uploadError("上传文件超出限制大小！", fileName);
				return;
			}

			// 检查文件扩展名
			String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
			if (!Arrays.<String> asList(ResourceUtil.getUploadFileExts().split(",")).contains(fileExt)) {
				uploadError("上传文件扩展名是不允许的扩展名。\n只允许" + ResourceUtil.getUploadFileExts() + "格式！");
				return;
			}

			savePath += fileExt + "/";
			saveUrl += fileExt + "/";

			SimpleDateFormat yearDf = new SimpleDateFormat("yyyy");
			SimpleDateFormat monthDf = new SimpleDateFormat("MM");
			SimpleDateFormat dateDf = new SimpleDateFormat("dd");
			Date date = new Date();
			String ymd = yearDf.format(date) + "/" + monthDf.format(date) + "/" + dateDf.format(date) + "/";
			savePath += ymd;
			saveUrl += ymd;

			File uploadDir = new File(savePath);// 创建要上传文件到指定的目录
			if (!uploadDir.exists()) {
				uploadDir.mkdirs();
			}

			String newFileName = UUID.randomUUID().toString().replaceAll("-", "") + "." + fileExt;// 新的文件名称
			File uploadedFile = new File(savePath, newFileName);

			try {
				FileCopyUtils.copy(file, uploadedFile);// 利用spring的文件工具上传
			} catch (Exception e) {
				uploadError("上传文件失败！", fileName);
				return;
			}

			uploadSuccess(ServletActionContext.getRequest().getContextPath() + saveUrl + newFileName, fileName, i);// 文件上传成功

		}

	}*/

	private void uploadError(String err, String msg) {
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("err", err);
		m.put("msg", msg);
		writeJson(null, m);
	}

	private void uploadError(String err) {
		uploadError(err, "");
	}

	private void uploadSuccess(String newFileName, String fileName, int id) {
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("err", "");
		Map<String, Object> nm = new HashMap<String, Object>();
		nm.put("url", newFileName);
		nm.put("localfile", fileName);
		nm.put("id", id);
		m.put("msg", nm);
		writeJson(m, null);
	}
	
	public GbuyOrderPage getGbuyOrderPage(Model model, HttpServletRequest request, HttpServletResponse response){
		return gbuyOrderPage;
	}


	public void setGbuyOrderPage(GbuyOrderPage gbuyOrderPage) {
		this.gbuyOrderPage = gbuyOrderPage;
	}
}
