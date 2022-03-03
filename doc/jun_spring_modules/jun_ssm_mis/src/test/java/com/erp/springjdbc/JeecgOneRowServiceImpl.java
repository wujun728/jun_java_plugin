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
import com.erp.jee.entity.JeecgOneRowEntity;
import com.erp.jee.page2.JeecgOneRowPage;
import com.erp.jee.pageModel.DataGrid;
import com.erp.jee.service.JeecgOneRowServiceI;
import com.erp.service.impl.BaseServiceImpl;
import com.jun.plugin.utils.BeanUtil;
import com.jun.plugin.utils.SQLUtil;

/**   
 * @Title: ServiceImpl
 * @Description: 行编辑
 * @author Wujun
 * @date 2011-12-31 14:29:04
 * @version V1.0   
 *
 */
@Service("jeecgOneRowService")
public class JeecgOneRowServiceImpl extends BaseServiceImpl implements JeecgOneRowServiceI {

	@Autowired
	//SQL 使用JdbcDao
	private JdbcDao jdbcDao;
	private IBaseDao<JeecgOneRowEntity> jeecgOneRowEntityDao;

	public IBaseDao<JeecgOneRowEntity> getJeecgOneRowEntityDao() {
		return jeecgOneRowEntityDao;
	}
	@Autowired
	public void setJeecgOneRowEntityDao(IBaseDao<JeecgOneRowEntity> jeecgOneRowEntityDao) {
		this.jeecgOneRowEntityDao = jeecgOneRowEntityDao;
	}

	@Transactional(propagation = Propagation.SUPPORTS)
	public DataGrid datagrid(JeecgOneRowPage jeecgOneRowPage) {
		DataGrid j = new DataGrid();
		j.setRows(getPagesFromEntitys(find(jeecgOneRowPage)));
		j.setTotal(total(jeecgOneRowPage));
		return j;
	}

	private List<JeecgOneRowPage> getPagesFromEntitys(List<JeecgOneRowEntity> jeecgOneRowEntitys) {
		List<JeecgOneRowPage> jeecgOneRowPages = new ArrayList<JeecgOneRowPage>();
		if (jeecgOneRowEntitys != null && jeecgOneRowEntitys.size() > 0) {
			for (JeecgOneRowEntity tb : jeecgOneRowEntitys) {
				JeecgOneRowPage b = new JeecgOneRowPage();
				BeanUtils.copyProperties(tb, b);
				jeecgOneRowPages.add(b);
			}
		}
		return jeecgOneRowPages;
	}

	private List<JeecgOneRowEntity> find(JeecgOneRowPage jeecgOneRowPage) {
		String hql = "from JeecgOneRowEntity t where 1=1 ";

		List<Object> values = new ArrayList<Object>();
		hql = addWhere(jeecgOneRowPage, hql, values);

		if (jeecgOneRowPage.getSort() != null && jeecgOneRowPage.getOrder() != null) {
			hql += " order by " + jeecgOneRowPage.getSort() + " " + jeecgOneRowPage.getOrder();
		}
		return jeecgOneRowEntityDao.find(hql, jeecgOneRowPage.getPage(), jeecgOneRowPage.getRows(), values);
	}

	private Long total(JeecgOneRowPage jeecgOneRowPage) {
		String hql = "select count(*) from JeecgOneRowEntity t where 1=1 ";
		List<Object> values = new ArrayList<Object>();
		hql = addWhere(jeecgOneRowPage, hql, values);
		return jeecgOneRowEntityDao.count(hql, values);
	}

	private String addWhere(JeecgOneRowPage jeecgOneRowPage, String hql, List<Object> values) {
	    //循环查询条件Page的所有[Integer][String]类型的字段，如果字段不为空则动态加上查询条件
		//-----------------------------------------------------
		StringBuffer hqlbf = new StringBuffer(hql);
		
		JeecgOneRowEntity jeecgOneRowEntity = new JeecgOneRowEntity();
		BeanUtils.copyProperties(jeecgOneRowPage, jeecgOneRowEntity);
		SQLUtil.createSearchParamsHql(hqlbf, values, jeecgOneRowEntity);
		hql = hqlbf.toString();
		//-----------------------------------------------------
		if (jeecgOneRowPage.getCcreatedatetimeStart() != null) {
			hql += " and createDt>=? ";
			values.add(jeecgOneRowPage.getCcreatedatetimeStart());
		}
		if (jeecgOneRowPage.getCcreatedatetimeEnd() != null) {
			hql += " and createDt<=? ";
			values.add(jeecgOneRowPage.getCcreatedatetimeEnd());
		}
		if (jeecgOneRowPage.getCmodifydatetimeStart() != null) {
			hql += " and modifyDt>=? ";
			values.add(jeecgOneRowPage.getCmodifydatetimeStart());
		}
		if (jeecgOneRowPage.getCmodifydatetimeEnd() != null) {
			hql += " and modifyDt<=? ";
			values.add(jeecgOneRowPage.getCmodifydatetimeEnd());
		}
		return hql;
	}

	public void add(JeecgOneRowPage jeecgOneRowPage) {
		if (jeecgOneRowPage.getObid() == null || jeecgOneRowPage.getObid().trim().equals("")) {
			jeecgOneRowPage.setObid(UUID.randomUUID().toString());
		}
		JeecgOneRowEntity t = new JeecgOneRowEntity();
		BeanUtils.copyProperties(jeecgOneRowPage, t);
		jeecgOneRowEntityDao.save(t);
	}

	public void update(JeecgOneRowPage jeecgOneRowPage) throws Exception {
		JeecgOneRowEntity t = jeecgOneRowEntityDao.get(JeecgOneRowEntity.class, jeecgOneRowPage.getObid());
	    if(t != null) {
			BeanUtil.copyBeanNotNull2Bean(jeecgOneRowPage, t);
		}
	}

	public void delete(String ids) {
		if (ids != null) {
			for (String id : ids.split(",")) {
				JeecgOneRowEntity t = jeecgOneRowEntityDao.get(JeecgOneRowEntity.class, id);
				if (t != null) {
					jeecgOneRowEntityDao.delete(t);
				}
			}
		}
	}

	public JeecgOneRowEntity get(JeecgOneRowPage jeecgOneRowPage) {
		return jeecgOneRowEntityDao.get(JeecgOneRowEntity.class, jeecgOneRowPage.getObid());
	}

	public JeecgOneRowEntity get(String obid) {
		return jeecgOneRowEntityDao.get(JeecgOneRowEntity.class, obid);
	}
	public List<JeecgOneRowEntity> listAll(JeecgOneRowPage jeecgOneRowPage) {
		String hql = "from JeecgOneRowEntity where 1 = 1 ";
		List<Object> values = new ArrayList<Object>();
		hql = addWhere(jeecgOneRowPage, hql, values);
		List<JeecgOneRowEntity> list = jeecgOneRowEntityDao.find(hql,values);
		return list;
	}
}
