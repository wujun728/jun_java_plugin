package com.erp.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.erp.dao.PublicDao;
import com.erp.model.SystemCode;
import com.erp.service.ISystemCodeService;
import com.erp.viewModel.TreeModel;
import com.jun.plugin.utils.Constants;
@Service("systemCodeService")
public class SystemCodeServiceImpl implements ISystemCodeService
{
	private PublicDao<SystemCode> publicDao;
	@Autowired
	public void setPublicDao(PublicDao<SystemCode> publicDao )
	{
		this.publicDao = publicDao;
	}
	
	/* (非 Javadoc) 
	* <p>Title: findSystemCodeList</p> 
	* <p>Description: </p> 
	* @param id
	* @return 
	* @see com.erp.service.SystemCodeService#findSystemCodeList(java.lang.Integer) 
	*/
	public List<SystemCode> findSystemCodeList(Integer id)
	{
		String hql="from SystemCode t where t.status='A'";
		if(null==id||"".equals(id)){
			hql+=" and t.parentId is null";
		}else {
			hql+=" and t.parentId="+id;
		}
		return publicDao.find(hql);
	}
	public List<SystemCode> findSystemCodeList(String id)
	{
		String hql="from SystemCode t where t.status='A'";
		if(null==id||"".equals(id)){
			hql+=" and t.parentId is null";
		}else {
			hql+=" and t.parentId="+id;
		}
		return publicDao.find(hql);
	}
	
	/* (非 Javadoc) 
	* <p>Title: findSystemCodeList</p> 
	* <p>Description: </p> 
	* @return 
	* @see com.erp.service.SystemCodeService#findSystemCodeList() 
	*/
	public List<TreeModel> findSystemCodeList()
	{
		String hql="from SystemCode t where t.status='A'";
		List<SystemCode> list = publicDao.find(hql);
		List<TreeModel> tempList=new ArrayList<TreeModel>();
		for (SystemCode s : list)
		{
			TreeModel treeModel=new TreeModel();
			treeModel.setId(s.getCodeId().toString());
			treeModel.setPid(s.getParentId()==null?"":s.getParentId().toString());
			treeModel.setName(s.getName());
			treeModel.setIconCls(s.getIconCls());
			treeModel.setState(s.getState());
			treeModel.setState("open");
			treeModel.setPermissionId(s.getPermissionId());
			tempList.add(treeModel);
		}
		return tempList;
	}
	
	
	/* (非 Javadoc) 
	* <p>Title: persistenceSystemCodeDig</p> 
	* <p>Description: </p> 
	* @param systemCode
	* @return 
	* @see com.erp.service.SystemCodeService#persistenceSystemCodeDig(com.erp.model.SystemCode) 
	*/
	public boolean persistenceSystemCodeDig(SystemCode systemCode,String permissionName,Integer codePid)
	{
		Integer userid = Constants.getCurrendUser().getUserId();
		Integer pid = systemCode.getParentId();
		Integer codeId = systemCode.getCodeId();
		if (null==codeId||"".equals(codeId)||codeId==0)
		{
			systemCode.setCreated(new Date());
			systemCode.setLastmod(new Date());
			systemCode.setCreater(userid);
			systemCode.setModifyer(userid);
			systemCode.setStatus(Constants.PERSISTENCE_STATUS);
			systemCode.setType("D");
			if (null==pid||"".equals(pid)||pid==0)
			{
				systemCode.setState(Constants.TREE_STATUS_OPEN);
				//systemCode.setParentId(codePid);
			}else {
				SystemCode pCode = publicDao.get(SystemCode.class, pid);
				if (!Constants.TREE_STATUS_CLOSED.equals(pCode.getState()))
				{
					pCode.setState(Constants.TREE_STATUS_CLOSED);
					publicDao.update(pCode);
				}
				systemCode.setState(Constants.TREE_STATUS_OPEN);
			}
			List<SystemCode> list = isExtPermissionId(systemCode.getPermissionId());
			if (list.size()!=0)
			{
				if (pid==null||"".equals(pid))
				{
					SystemCode sysc = list.get(0);
					systemCode.setParentId(sysc.getCodeId());
				}
			}else {
				SystemCode ss=new SystemCode();
				ss.setCreated(new Date());
				ss.setLastmod(new Date());
				ss.setCreater(userid);
				ss.setModifyer(userid);
				ss.setStatus(Constants.PERSISTENCE_STATUS);
				ss.setPermissionId(systemCode.getPermissionId());
				String[] temp=permissionName.split(",");
				ss.setName(temp[0]);
				ss.setState(Constants.TREE_STATUS_CLOSED);
				ss.setIconCls(temp[1]);
				ss.setType("M");
				publicDao.save(ss);
				systemCode.setParentId(ss.getCodeId());
			}
			publicDao.save(systemCode);
		}else {
			systemCode.setLastmod(new Date());
			systemCode.setModifyer(userid);
			publicDao.update(systemCode);
		}
		return true;
	}
	
	/**  
	* 函数功能说明
	* Administrator修改者名字
	* 2013-6-20修改日期
	* 修改内容
	* @Title: isExtPermissionId 
	* @Description:  TODO:检查模块是否存在 
	* @param @param permissionId
	* @param @return    设定文件 
	* @return boolean    返回类型 
	* @throws 
	*/
	public List<SystemCode> isExtPermissionId(Integer permissionId)
	{
		String hql=" from SystemCode t where t.status='A' and t.type='M' and t.permissionId="+permissionId;
		return publicDao.find(hql);
	}
	
	/* (非 Javadoc) 
	* <p>Title: findSystemCodeByType</p> 
	* <p>Description: </p> 
	* @param codeMyid
	* @return 
	* @see com.erp.service.SystemCodeService#findSystemCodeByType(java.lang.String) 
	*/
	public List<SystemCode> findSystemCodeByType(String codeMyid)
	{
		String hql="from SystemCode t where t.status='A' and t.type='D' and t.codeMyid='"+codeMyid+"'";
		List<SystemCode> list = publicDao.find(hql);
		if (list.size()==1)
		{
			SystemCode ss = list.get(0);
			String hql2="from SystemCode t where t.status='A' and t.parentId="+ss.getCodeId();
			return publicDao.find(hql2);
		}
		return null;
	}
	
	/* (非 Javadoc) 
	* <p>Title: delSystemCode</p> 
	* <p>Description: </p> 
	* @param codeId
	* @return 
	* @see com.erp.service.SystemCodeService#delSystemCode(java.lang.Integer) 
	*/
	public boolean delSystemCode(Integer codeId)
	{
		String hql=" from SystemCode t where t.status='A' and t.parentId="+codeId;
		List<SystemCode> list = publicDao.find(hql);
		if (list.size()!=0)
		{
			return false;
		}else {
			Integer userid = Constants.getCurrendUser().getUserId();
			SystemCode s = publicDao.get(SystemCode.class,codeId);
			s.setLastmod(new Date());
			s.setModifyer(userid);
			s.setStatus(Constants.PERSISTENCE_DELETE_STATUS);
			publicDao.deleteToUpdate(s);
			return true;
		}
	}
}
