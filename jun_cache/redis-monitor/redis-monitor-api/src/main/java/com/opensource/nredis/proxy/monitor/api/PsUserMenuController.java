package com.opensource.nredis.proxy.monitor.api;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.opensource.nredis.proxy.monitor.model.PsUserMenu;
import com.opensource.nredis.proxy.monitor.model.pagination.PageAttribute;
import com.opensource.nredis.proxy.monitor.model.pagination.PageList;
import com.opensource.nredis.proxy.monitor.platform.DataGridObject;

import com.opensource.nredis.proxy.monitor.platform.ResponseObject;
import com.opensource.nredis.proxy.monitor.platform.RestStatus;
import com.opensource.nredis.proxy.monitor.service.IPsUserMenuService;

/**
* service impl
*
* @author liubing
* @date 2016/12/20 18:45
* @version v1.0.0
*/
@Controller
public class PsUserMenuController {
	
	@Autowired
	private IPsUserMenuService psUserMenuService;
	
	/**
	 * 新增
	 * @param psUserMenu
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/psUserMenu", method = RequestMethod.POST)
	@ResponseBody
	public ResponseObject psUserMenu(PsUserMenu psUserMenu) throws Exception{
		ResponseObject responseObject=new ResponseObject();
		psUserMenuService.create(psUserMenu);
		responseObject.setStatus(RestStatus.SUCCESS.code);
		responseObject.setMessage(RestStatus.SUCCESS.message);
		return responseObject;
	}
	
	/**
	 * 修改
	 * @param pageElement
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/editPsUserMenu", method = RequestMethod.POST)
	@ResponseBody
	public ResponseObject editPsUserMenu(PsUserMenu psUserMenu) throws Exception{
		ResponseObject responseObject=new ResponseObject();
		psUserMenuService.modifyEntityById(psUserMenu);
		responseObject.setStatus(RestStatus.SUCCESS.code);
		responseObject.setMessage(RestStatus.SUCCESS.message);
		return responseObject;
	}
	
	/**
	 * 查询详情
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/{id}/searchPsUserMenu", method = RequestMethod.GET)
	@ResponseBody
	public ResponseObject searchPageElement(@PathVariable Integer id) throws Exception{
		ResponseObject responseObject=new ResponseObject();
		PsUserMenu psUserMenu= psUserMenuService.getEntityById(id);
		responseObject.setStatus(RestStatus.SUCCESS.code);
		responseObject.setMessage(RestStatus.SUCCESS.message);
		responseObject.setData(psUserMenu);
		return responseObject;
	}
	
	/**
	 * 删除
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/removePsUserMenu", method = RequestMethod.POST)
	@ResponseBody
	public ResponseObject removePsUserMenu(@Validated Integer id) throws Exception{
		ResponseObject responseObject=new ResponseObject();
		psUserMenuService.deleteEntityById(id);
		responseObject.setStatus(RestStatus.SUCCESS.code);
		responseObject.setMessage(RestStatus.SUCCESS.message);
		return responseObject;
	}
	
	/**
	 * 查询列表
	 * @param menuId
	 * @param page
	 * @param rows
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/searchPsUserMenus", method = RequestMethod.GET)
	@ResponseBody
	public DataGridObject searchPsUserMenus(PsUserMenu psUserMenu,Integer page , Integer rows) throws Exception{
		DataGridObject responseObject=new DataGridObject();
		PageList<PsUserMenu> pageList=null;
		if(page==null||page==0){
			page=1;
		}
		PageAttribute pageAttribute=new PageAttribute(page, rows);
		pageList=psUserMenuService.queryEntityPageList(pageAttribute, psUserMenu, null);
		responseObject.setTotal(pageList.getPage().getRowCount());
		responseObject.setRows(pageList.getDatas());
		return responseObject;
	}
	
	
}
