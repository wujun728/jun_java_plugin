package com.erp.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.erp.model.${Table};
import com.erp.service.${Table}Service;
import com.erp.util.Constants;
import com.erp.util.PageUtil;
import com.erp.viewModel.GridModel;
import com.opensymphony.xwork2.ModelDriven;

/**
* 类功能说明 TODO:公司信息处理action
* 类修改者
* 修改日期
* 修改说明
* @author Wujun
* @date 2013-4-28 下午12:51:26
* @version V1.0
*/
@Namespace("/${table}")
@Action(value = "${table}Action")
public class ${Table}Action extends BaseAction implements ModelDriven<${Table}>{
	//private static final Logger logger = Logger.getLogger(LoginAction.class);
	private static final long serialVersionUID = -3276708781259160129L;
	private ${Table}Service ${table}Service;
	private ${Table} ${table};
	
	public ${Table} get${Table}()
	{
		return ${table};
	}

	public void set${Table}(${Table} ${table} )
	{
		this.${table} = ${table};
	}

	@Autowired
	public void set${Table}Service(${Table}Service ${table}Service) {
		this.${table}Service = ${table}Service;
	}
	
	/**  
	* 函数功能说明 
	* Administrator修改者名字
	* 2013-4-28修改日期
	* 修改内容
	* @Title: persistence${Table} 
	* @Description:TODO:持久化persistence${Table}
	* @param @return
	* @param @throws Exception    设定文件 
	* @return String    返回类型 
	* @throws 
	*/
	public String persistence${Table}() throws Exception {
		System.out.println(inserted);
		//String sdfString="[{"name":"123123","tel":"123","fax":"123","address":"123","zip":"123","email":"123123@qq.com","contact":"123","description":"123123"}]";
		Map<String, List<${Table}>> map=new HashMap<String, List<${Table}>>();
		map.put("addList", JSON.parseArray(inserted, ${Table}.class));
		map.put("updList", JSON.parseArray(updated, ${Table}.class));
		map.put("delList", JSON.parseArray(deleted, ${Table}.class));
		OutputJson(getMessage(${table}Service.persistence${Table}(map)));
		return null;
	}
	
	/**  
	* 函数功能说明 TODO:${Table}弹出框模式新增
	* Administrator修改者名字
	* 2013-6-9修改日期
	* 修改内容
	* @Title: add${Table} 
	* @Description: 
	* @param @return
	* @param @throws Exception    设定文件 
	* @return String    返回类型 
	* @throws 
	*/
	public String persistence${Table}Dlg() throws Exception
	{
		List<${Table}> list=new ArrayList<${Table}>();
		list.add(getModel());
		${keyType} companyId = getModel().${keyGetMethod}();
		if (null==companyId||"".equals(companyId))
		{
			OutputJson(getMessage(${table}Service.add${Table}(list)), Constants.TEXT_TYPE_PLAIN);
		}else {
			OutputJson(getMessage(${table}Service.upd${Table}(list)), Constants.TEXT_TYPE_PLAIN);
		}
		return null;
	}
	
	/**  
	* 函数功能说明
	* Administrator修改者名字
	* 2013-4-28修改日期
	* 修改内容
	* @Title: findAll${Table}List 
	* @Description: TODO:查询所有或符合条件的${Table}
	* @param @return
	* @param @throws Exception    设定文件 
	* @return String    返回类型 
	* @throws 
	*/
	public String findAll${Table}List() throws Exception {
		Map<String, Object> map=new HashMap<String, Object>();
		if (null!=searchValue&&!"".equals(searchValue))
		{
			map.put(searchName, Constants.GET_SQL_LIKE+searchValue+Constants.GET_SQL_LIKE);
		}
		PageUtil pageUtil=new PageUtil(page, rows, searchAnds, searchColumnNames, searchConditions, searchVals);
		GridModel gridModel=new GridModel();
		gridModel.setRows(${table}Service.findAll${Table}List(map, pageUtil));
		gridModel.setTotal(${table}Service.getCount(map,pageUtil));
		OutputJson(gridModel);
		return null;
	}
 
	/**  
	* 函数功能说明 TODO:删除${Table}
	* Administrator修改者名字
	* 2013-6-14修改日期
	* 修改内容
	* @Title: del${Table} 
	* @Description: 
	* @param @return
	* @param @throws Exception    设定文件 
	* @return String    返回类型 
	* @throws 
	*/
	public String del${Table}() throws Exception
	{
		OutputJson(getMessage(${table}Service.del${Table}(getModel().${keyGetMethod}())));
		return null;
	}
	public ${Table} getModel()
	{
		if(null==${table}){
			${table}=new ${Table}();
		}
		return ${table};
	}
}
