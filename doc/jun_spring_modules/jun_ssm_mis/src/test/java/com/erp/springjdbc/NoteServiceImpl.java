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
import com.erp.jee.entity.NoteEntity;
import com.erp.jee.page.base.NotePage;
import com.erp.jee.pageModel.DataGrid;
import com.erp.jee.service.NoteServiceI;
import com.erp.service.impl.BaseServiceImpl;
import com.jun.plugin.utils.BeanUtil;
import com.jun.plugin.utils.SQLUtil;

/**   
 * @Title: ServiceImpl
 * @Description: 公告
 * @author Wujun
 * @date 2013-01-18 14:55:33
 * @version V1.0   
 *
 */
@Service("noteService")
public class NoteServiceImpl extends BaseServiceImpl implements NoteServiceI {

	@Autowired
	//SQL 使用JdbcDao
	private JdbcDao jdbcDao;
	private IBaseDao<NoteEntity> noteEntityDao;

	public IBaseDao<NoteEntity> getNoteEntityDao() {
		return noteEntityDao;
	}
	@Autowired
	public void setNoteEntityDao(IBaseDao<NoteEntity> noteEntityDao) {
		this.noteEntityDao = noteEntityDao;
	}

	@Transactional(propagation = Propagation.SUPPORTS)
	public DataGrid datagrid(NotePage notePage) {
		DataGrid j = new DataGrid();
		j.setRows(getPagesFromEntitys(find(notePage)));
		j.setTotal(total(notePage));
		return j;
	}

	private List<NotePage> getPagesFromEntitys(List<NoteEntity> noteEntitys) {
		List<NotePage> notePages = new ArrayList<NotePage>();
		if (noteEntitys != null && noteEntitys.size() > 0) {
			for (NoteEntity tb : noteEntitys) {
				NotePage b = new NotePage();
				BeanUtils.copyProperties(tb, b);
				notePages.add(b);
			}
		}
		return notePages;
	}

	private List<NoteEntity> find(NotePage notePage) {
		String hql = "from NoteEntity t where 1=1 ";

		List<Object> values = new ArrayList<Object>();
		hql = addWhere(notePage, hql, values);

		if (notePage.getSort() != null && notePage.getOrder() != null) {
			hql += " order by " + notePage.getSort() + " " + notePage.getOrder();
		}
		return noteEntityDao.find(hql, notePage.getPage(), notePage.getRows(), values);
	}

	private Long total(NotePage notePage) {
		String hql = "select count(*) from NoteEntity t where 1=1 ";
		List<Object> values = new ArrayList<Object>();
		hql = addWhere(notePage, hql, values);
		return noteEntityDao.count(hql, values);
	}

	private String addWhere(NotePage notePage, String hql, List<Object> values) {
	    //循环查询条件Page的所有[Integer][String]类型的字段，如果字段不为空则动态加上查询条件
		//-----------------------------------------------------
		StringBuffer hqlbf = new StringBuffer(hql);
		
		NoteEntity noteEntity = new NoteEntity();
		BeanUtils.copyProperties(notePage, noteEntity);
		SQLUtil.createSearchParamsHql(hqlbf, values, noteEntity);
		hql = hqlbf.toString();
		//-----------------------------------------------------
		if (notePage.getCcreatedatetimeStart() != null) {
			hql += " and createDt>=? ";
			values.add(notePage.getCcreatedatetimeStart());
		}
		if (notePage.getCcreatedatetimeEnd() != null) {
			hql += " and createDt<=? ";
			values.add(notePage.getCcreatedatetimeEnd());
		}
		if (notePage.getCmodifydatetimeStart() != null) {
			hql += " and modifyDt>=? ";
			values.add(notePage.getCmodifydatetimeStart());
		}
		if (notePage.getCmodifydatetimeEnd() != null) {
			hql += " and modifyDt<=? ";
			values.add(notePage.getCmodifydatetimeEnd());
		}
		return hql;
	}

	public void add(NotePage notePage) {
		if (notePage.getObid() == null || notePage.getObid().trim().equals("")) {
			notePage.setObid(UUID.randomUUID().toString());
		}
		NoteEntity t = new NoteEntity();
		BeanUtils.copyProperties(notePage, t);
		noteEntityDao.save(t);
	}

	public void update(NotePage notePage) throws Exception {
		NoteEntity t = noteEntityDao.get(NoteEntity.class, notePage.getObid());
	    if(t != null) {
			BeanUtil.copyBeanNotNull2Bean(notePage, t);
		}
	}

	public void delete(String ids) {
		if (ids != null) {
			for (String id : ids.split(",")) {
				NoteEntity t = noteEntityDao.get(NoteEntity.class, id);
				if (t != null) {
					noteEntityDao.delete(t);
				}
			}
		}
	}

	public NoteEntity get(NotePage notePage) {
		return noteEntityDao.get(NoteEntity.class, notePage.getObid());
	}

	public NoteEntity get(String obid) {
		return noteEntityDao.get(NoteEntity.class, obid);
	}
	public List<NoteEntity> listAll(NotePage notePage) {
		String hql = "from NoteEntity where 1 = 1 ";
		List<Object> values = new ArrayList<Object>();
		hql = addWhere(notePage, hql, values);
		List<NoteEntity> list = noteEntityDao.find(hql,values);
		return list;
	}
}
