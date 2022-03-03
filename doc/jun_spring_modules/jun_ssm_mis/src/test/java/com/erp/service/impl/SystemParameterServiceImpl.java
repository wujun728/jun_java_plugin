package com.erp.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.erp.dao.PublicDao;
import com.erp.model.Parameter;
import com.erp.service.ISystemParameterService;
import com.erp.shiro.ShiroUser;
import com.erp.viewModel.CheckBoxModel;
import com.erp.viewModel.Options;
import com.erp.viewModel.ParameterModel;
import com.jun.plugin.utils.Constants;
import com.jun.plugin.utils.WebUtil;

@Service("systemParameterService")
public class SystemParameterServiceImpl implements ISystemParameterService
{
	private PublicDao<Parameter> publicDao;
	@Autowired
	public void setPublicDao(PublicDao<Parameter> publicDao )
	{
		this.publicDao = publicDao;
	}
	
	public  List<ParameterModel> findParameterList(String type)
	{
		String hql="from Parameter t where t.status='A'";
		 List<Parameter> temp = publicDao.find(hql);
		 List<ParameterModel> list2=new ArrayList<ParameterModel>();
		 for (Parameter p : temp){
			ParameterModel pm=new ParameterModel();
//			BeanUtils.copy(pm, p);
//			ConvertUtils.register(new DateLocaleConverter(), java.util.Date.class);
//			BeanUtils.populate(pm, p);
			WebUtil.copyBean3(p, pm);
			if ("checkbox".equals(p.getEditorType())){
				CheckBoxModel cm=new CheckBoxModel();
				cm.setType("checkbox");
				cm.setOptions(new Options());
				pm.setEditor(cm);
			}else if (p.getEditorType()==null){
				p.setEditorType("text");
//				p.setEditor("");
			}{
				pm.setEditor(p.getEditorType());
			}
			list2.add(pm);
		}
		return list2;
	}
	public boolean persistenceParameter(Map<String, List<Parameter>> map) 
	{
		this.addParameter(map.get("addList"));
		this.updParameter(map.get("updList"));
		this.delParameter(map.get("delList"));
		return true;
	}
	
	public boolean addParameter(List<Parameter> addlist) 
	{
		
		if (addlist!=null&&addlist.size()!=0) {
			ShiroUser users = Constants.getCurrendUser();
			for (Parameter companyInfo : addlist) {
				companyInfo.setCreated(new Date());
				companyInfo.setLastmod(new Date());
				companyInfo.setStatus("A");
				if (users!=null)
				{
					companyInfo.setCreater(users.getUserId());
					companyInfo.setModifyer(users.getUserId());
				}
				publicDao.save(companyInfo);
			}
		}
		return true;
	}
	
	public boolean updParameter(List<Parameter>  updlist) 
	{
		if (updlist!=null&&updlist.size()!=0) {
			ShiroUser users = Constants.getCurrendUser();
			for (Parameter companyInfo : updlist) {
				
				companyInfo.setLastmod(new Date());
				companyInfo.setModifyer(users.getUserId());
				publicDao.update(companyInfo);
			}
		}
		return true;
	}
	
	public boolean delParameter(List<Parameter>  dellist)
	{
		if (dellist!=null&&dellist.size()!=0) {
			for (Parameter companyInfo : dellist) {
				companyInfo.setStatus("I");
				companyInfo.setLastmod(new Date());
				publicDao.deleteToUpdate(companyInfo);
			}
		}
		return true;
	}
}
