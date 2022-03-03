package com.erp.springjdbc;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.erp.dao.IBaseDao;
import com.erp.jee.entity.GroupEntity;
import com.erp.jee.page2.GroupPage;
import com.erp.jee.pageModel.DataGrid;
import com.erp.jee.service.GroupServiceI;
import com.erp.service.impl.BaseServiceImpl;
import com.jun.plugin.utils.BeanUtil;
import com.jun.plugin.utils.SQLUtil;

/**   
 * @Title: ServiceImpl
 * @Description: 组织机构
 * @author Wujun
 * @date 2013-01-18 12:18:08
 * @version V1.0   
 *
 */
@Service("groupService")
public class GroupServiceImpl extends BaseServiceImpl implements GroupServiceI {

	@Autowired
	//SQL 使用JdbcDao
	private JdbcDao jdbcDao;
	private IBaseDao<GroupEntity> groupEntityDao;

	public IBaseDao<GroupEntity> getGroupEntityDao() {
		return groupEntityDao;
	}
	@Autowired
	public void setGroupEntityDao(IBaseDao<GroupEntity> groupEntityDao) {
		this.groupEntityDao = groupEntityDao;
	}

	@Transactional(propagation = Propagation.SUPPORTS)
	public DataGrid datagrid(GroupPage groupPage) {
		DataGrid j = new DataGrid();
		j.setRows(getPagesFromEntitys(find(groupPage)));
		j.setTotal(total(groupPage));
		return j;
	}

	private List<GroupPage> getPagesFromEntitys(List<GroupEntity> groupEntitys) {
		List<GroupPage> groupPages = new ArrayList<GroupPage>();
		if (groupEntitys != null && groupEntitys.size() > 0) {
			for (GroupEntity tb : groupEntitys) {
				GroupPage b = new GroupPage();
				BeanUtils.copyProperties(tb, b);
				if(tb!=null && tb.getPobid()!=null && tb.getPobid().trim().length()>0){
					tb = this.groupEntityDao.get(GroupEntity.class, tb.getPobid());
					if(tb!=null) b.setPcname(tb.getCname());
				}
				groupPages.add(b);
			}
		}
		return groupPages;
	}

	private List<GroupEntity> find(GroupPage groupPage) {
		String hql = "from GroupEntity t where 1=1 ";

		List<Object> values = new ArrayList<Object>();
		hql = addWhere(groupPage, hql, values);

		if (groupPage.getSort() != null && groupPage.getOrder() != null) {
			hql += " order by " + groupPage.getSort() + " " + groupPage.getOrder();
		}
		return groupEntityDao.find(hql, groupPage.getPage(), groupPage.getRows(), values);
	}

	private Long total(GroupPage groupPage) {
		String hql = "select count(*) from GroupEntity t where 1=1 ";
		List<Object> values = new ArrayList<Object>();
		hql = addWhere(groupPage, hql, values);
		return groupEntityDao.count(hql, values);
	}

	private String addWhere(GroupPage groupPage, String hql, List<Object> values) {
	    //循环查询条件Page的所有[Integer][String]类型的字段，如果字段不为空则动态加上查询条件
		//-----------------------------------------------------
		StringBuffer hqlbf = new StringBuffer(hql);
		
		GroupEntity groupEntity = new GroupEntity();
		BeanUtils.copyProperties(groupPage, groupEntity);
		SQLUtil.createSearchParamsHql(hqlbf, values, groupEntity);
		hql = hqlbf.toString();
		//-----------------------------------------------------
		if (groupPage.getCcreatedatetimeStart() != null) {
			hql += " and createDt>=? ";
			values.add(groupPage.getCcreatedatetimeStart());
		}
		if (groupPage.getCcreatedatetimeEnd() != null) {
			hql += " and createDt<=? ";
			values.add(groupPage.getCcreatedatetimeEnd());
		}
		if (groupPage.getCmodifydatetimeStart() != null) {
			hql += " and modifyDt>=? ";
			values.add(groupPage.getCmodifydatetimeStart());
		}
		if (groupPage.getCmodifydatetimeEnd() != null) {
			hql += " and modifyDt<=? ";
			values.add(groupPage.getCmodifydatetimeEnd());
		}
		return hql;
	}

	public void add(GroupPage groupPage) {
		if (groupPage.getObid() == null || groupPage.getObid().trim().equals("")) {
			groupPage.setObid(UUID.randomUUID().toString());
		}
		GroupEntity t = new GroupEntity();
		BeanUtils.copyProperties(groupPage, t);
		if(t.getPobid()!=null && t.getPobid().trim().length()==0){
			t.setPobid(null);
		}else{
			if(t.getPobid()!=null && t.getPobid().trim().length()>0){
				GroupEntity _t = this.groupEntityDao.get(GroupEntity.class, t.getPobid());
				if(_t==null || _t.getPobid()==null || _t.getPobid().length()<1) t.setPobid(null);//(_t.getCname());
			}
		}
		groupEntityDao.save(t);
	}

	public void update(GroupPage groupPage) throws Exception {
		GroupEntity t = groupEntityDao.get(GroupEntity.class, groupPage.getObid());
	    if(t != null) {
	    	if(groupPage.getPobid()!=null && groupPage.getPobid().trim().length()==0){
	    		groupPage.setPobid(null);
			}else{
				if(groupPage.getPobid()!=null && groupPage.getPobid().trim().length()>0){
					GroupEntity _t = this.groupEntityDao.get(GroupEntity.class, groupPage.getPobid());
					if(_t==null || _t.getPobid()==null || _t.getPobid().length()<1) groupPage.setPobid(null);//(_t.getCname());
				}
			}
			BeanUtil.copyBeanNotNull2Bean(groupPage, t);
		}
	}

	public void delete(String ids) {
		if (ids != null) {
			for (String id : ids.split(",")) {
				GroupEntity t = groupEntityDao.get(GroupEntity.class, id);
				if (t != null) {
					groupEntityDao.delete(t);
				}
			}
		}
	}

	public GroupEntity get(GroupPage groupPage) {
		return groupEntityDao.get(GroupEntity.class, groupPage.getObid());
	}

	public GroupEntity get(String obid) {
		return groupEntityDao.get(GroupEntity.class, obid);
	}
	
	public List<GroupEntity> listAll(GroupPage groupPage) {
		String hql = "from GroupEntity where 1 = 1 ";
		List<Object> values = new ArrayList<Object>();
		hql = addWhere(groupPage, hql, values);
		List<GroupEntity> list = groupEntityDao.find(hql,values);
		return list;
	}
}
