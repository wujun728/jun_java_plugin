package com.opensource.nredis.proxy.monitor.api;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.opensource.nredis.proxy.monitor.enums.StatusEnums;
import com.opensource.nredis.proxy.monitor.model.PsDict;
import com.opensource.nredis.proxy.monitor.model.pagination.PageAttribute;
import com.opensource.nredis.proxy.monitor.model.pagination.PageList;
import com.opensource.nredis.proxy.monitor.platform.DataGridObject;
import com.opensource.nredis.proxy.monitor.platform.ResponseObject;
import com.opensource.nredis.proxy.monitor.platform.RestStatus;
import com.opensource.nredis.proxy.monitor.platform.SelectLabel;
import com.opensource.nredis.proxy.monitor.service.IPsDictService;

/**
* controller
*
* @author liubing
* @date 2016/12/21 17:32
* @version v1.0.0
*/
@Controller
public class PsDictController {
	
	@Autowired
	private IPsDictService psDictService;
	
	/**
	 * 新增
	 * @param psDict
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/psDict", method = RequestMethod.POST)
	@ResponseBody
	public ResponseObject psDict(PsDict psDict) throws Exception{
		ResponseObject responseObject=new ResponseObject();
		psDictService.create(psDict);
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
	@RequestMapping(value = "/editPsDict", method = RequestMethod.POST)
	@ResponseBody
	public ResponseObject editPsDict(PsDict psDict) throws Exception{
		ResponseObject responseObject=new ResponseObject();
		
		
		psDictService.modifyEntityById(psDict);
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
	@RequestMapping(value = "/{id}/searchPsDict", method = RequestMethod.GET)
	@ResponseBody
	public ResponseObject searchPageElement(@PathVariable Integer id) throws Exception{
		ResponseObject responseObject=new ResponseObject();
		PsDict psDict= psDictService.getEntityById(id);
		responseObject.setStatus(RestStatus.SUCCESS.code);
		responseObject.setMessage(RestStatus.SUCCESS.message);
		responseObject.setData(psDict);
		return responseObject;
	}
	
	/**
	 * 删除
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/removePsDict", method = RequestMethod.POST)
	@ResponseBody
	public ResponseObject removePsDict(@Validated Integer id) throws Exception{
		ResponseObject responseObject=new ResponseObject();
		PsDict oldPsDict=psDictService.getEntityById(id);
		if(oldPsDict.getPsStatus()==2){//
			responseObject.setStatus(RestStatus.CAN_NOT_AUTH.code);
			responseObject.setMessage(RestStatus.CAN_NOT_AUTH.message);
			return responseObject;
		}
		psDictService.deleteEntityById(id);
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
	@RequestMapping(value = "/searchPsDicts", method = RequestMethod.GET)
	@ResponseBody
	public DataGridObject searchPsDicts(PsDict psDict,Integer page , Integer rows) throws Exception{
		DataGridObject responseObject=new DataGridObject();
		PageList<PsDict> pageList=null;
		if(page==null||page==0){
			page=1;
		}
		PageAttribute pageAttribute=new PageAttribute(page, rows);
		pageList=psDictService.queryEntityPageList(pageAttribute, psDict, null);
		List<PsDict> psDicts=pageList.getDatas();
		for(PsDict dict:psDicts){
			dict.setPsStatusName(StatusEnums.getMessage(dict.getPsStatus()));
		}
		responseObject.setTotal(pageList.getPage().getRowCount());
		responseObject.setRows(psDicts);
		return responseObject;
	}
	/**
	 * 查询下拉选项框
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/searchSelectPsDicts", method = RequestMethod.POST)
	@ResponseBody
	public List<SelectLabel> searchSelectPsDicts(@Validated String moudle) throws Exception{
		PsDict psDict =new PsDict();
		psDict.setPsMoudle(moudle);
		List<PsDict> psDicts=psDictService.queryEntityList(psDict);
		List<SelectLabel> selectLabels=new ArrayList<SelectLabel>();
		for(PsDict dict:psDicts){
			SelectLabel selectLabel=new SelectLabel(dict.getPsValue(), dict.getPsKey());
			selectLabels.add(selectLabel);
		}
		//selectObject.setData(selectLabels);
		return selectLabels;
	}
}
