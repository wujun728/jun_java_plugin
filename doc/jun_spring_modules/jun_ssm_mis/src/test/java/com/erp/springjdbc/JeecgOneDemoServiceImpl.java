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
import com.erp.jee.entity.JeecgOneDemoEntity;
import com.erp.jee.page2.JeecgOneDemoPage;
import com.erp.jee.pageModel.DataGrid;
import com.erp.jee.service.JeecgOneDemoServiceI;
import com.erp.service.impl.BaseServiceImpl;
import com.jun.plugin.utils.BeanUtil;
import com.jun.plugin.utils.SQLUtil;

/**   
 * @Title: ServiceImpl
 * @Description: 单表模型Demo
 * @author Wujun
 * @date 2011-12-31 14:02:58
 * @version V1.0   
 *
 */
@Service("jeecgOneDemoService")
public class JeecgOneDemoServiceImpl extends BaseServiceImpl implements JeecgOneDemoServiceI {

	@Autowired
	//SQL 使用JdbcDao
	private JdbcDao jdbcDao;
	private IBaseDao<JeecgOneDemoEntity> jeecgOneDemoEntityDao;

	public IBaseDao<JeecgOneDemoEntity> getJeecgOneDemoEntityDao() {
		return jeecgOneDemoEntityDao;
	}
	@Autowired
	public void setJeecgOneDemoEntityDao(IBaseDao<JeecgOneDemoEntity> jeecgOneDemoEntityDao) {
		this.jeecgOneDemoEntityDao = jeecgOneDemoEntityDao;
	}

	@Transactional(propagation = Propagation.SUPPORTS)
	public DataGrid datagrid(JeecgOneDemoPage jeecgOneDemoPage) {
		DataGrid j = new DataGrid();
		j.setRows(getPagesFromEntitys(find(jeecgOneDemoPage)));
		j.setTotal(total(jeecgOneDemoPage));
		return j;
	}

	private List<JeecgOneDemoPage> getPagesFromEntitys(List<JeecgOneDemoEntity> jeecgOneDemoEntitys) {
		List<JeecgOneDemoPage> jeecgOneDemoPages = new ArrayList<JeecgOneDemoPage>();
		if (jeecgOneDemoEntitys != null && jeecgOneDemoEntitys.size() > 0) {
			for (JeecgOneDemoEntity tb : jeecgOneDemoEntitys) {
				JeecgOneDemoPage b = new JeecgOneDemoPage();
				BeanUtils.copyProperties(tb, b);
				jeecgOneDemoPages.add(b);
			}
		}
		return jeecgOneDemoPages;
	}

	private List<JeecgOneDemoEntity> find(JeecgOneDemoPage jeecgOneDemoPage) {
		String hql = "from JeecgOneDemoEntity t where 1=1 ";

		List<Object> values = new ArrayList<Object>();
		hql = addWhere(jeecgOneDemoPage, hql, values);

		if (jeecgOneDemoPage.getSort() != null && jeecgOneDemoPage.getOrder() != null) {
			hql += " order by " + jeecgOneDemoPage.getSort() + " " + jeecgOneDemoPage.getOrder();
		}
		return jeecgOneDemoEntityDao.find(hql, jeecgOneDemoPage.getPage(), jeecgOneDemoPage.getRows(), values);
	}

	private Long total(JeecgOneDemoPage jeecgOneDemoPage) {
		String hql = "select count(*) from JeecgOneDemoEntity t where 1=1 ";
		List<Object> values = new ArrayList<Object>();
		hql = addWhere(jeecgOneDemoPage, hql, values);
		return jeecgOneDemoEntityDao.count(hql, values);
	}

	private String addWhere(JeecgOneDemoPage jeecgOneDemoPage, String hql, List<Object> values) {
	    //循环查询条件Page的所有[Integer][String]类型的字段，如果字段不为空则动态加上查询条件
		//-----------------------------------------------------
		StringBuffer hqlbf = new StringBuffer(hql);
		
		JeecgOneDemoEntity jeecgOneDemoEntity = new JeecgOneDemoEntity();
		BeanUtils.copyProperties(jeecgOneDemoPage, jeecgOneDemoEntity);
		SQLUtil.createSearchParamsHql(hqlbf, values, jeecgOneDemoEntity);
		hql = hqlbf.toString();
		//-----------------------------------------------------
		if (jeecgOneDemoPage.getCcreatedatetimeStart() != null) {
			hql += " and createDt>=? ";
			values.add(jeecgOneDemoPage.getCcreatedatetimeStart());
		}
		if (jeecgOneDemoPage.getCcreatedatetimeEnd() != null) {
			hql += " and createDt<=? ";
			values.add(jeecgOneDemoPage.getCcreatedatetimeEnd());
		}
		if (jeecgOneDemoPage.getCmodifydatetimeStart() != null) {
			hql += " and modifyDt>=? ";
			values.add(jeecgOneDemoPage.getCmodifydatetimeStart());
		}
		if (jeecgOneDemoPage.getCmodifydatetimeEnd() != null) {
			hql += " and modifyDt<=? ";
			values.add(jeecgOneDemoPage.getCmodifydatetimeEnd());
		}
		return hql;
	}

	public void add(JeecgOneDemoPage jeecgOneDemoPage) {
		if (jeecgOneDemoPage.getObid() == null || jeecgOneDemoPage.getObid().trim().equals("")) {
			jeecgOneDemoPage.setObid(UUID.randomUUID().toString());
		}
		JeecgOneDemoEntity t = new JeecgOneDemoEntity();
		BeanUtils.copyProperties(jeecgOneDemoPage, t);
		jeecgOneDemoEntityDao.save(t);
	}

	public void update(JeecgOneDemoPage jeecgOneDemoPage) throws Exception {
		JeecgOneDemoEntity t = jeecgOneDemoEntityDao.get(JeecgOneDemoEntity.class, jeecgOneDemoPage.getObid());
	    if(t != null) {
			BeanUtil.copyBeanNotNull2Bean(jeecgOneDemoPage, t);
		}
	}

	public void delete(String ids) {
		if (ids != null) {
			for (String id : ids.split(",")) {
				JeecgOneDemoEntity t = jeecgOneDemoEntityDao.get(JeecgOneDemoEntity.class, id);
				if (t != null) {
					jeecgOneDemoEntityDao.delete(t);
				}
			}
		}
	}

	public JeecgOneDemoEntity get(JeecgOneDemoPage jeecgOneDemoPage) {
		return jeecgOneDemoEntityDao.get(JeecgOneDemoEntity.class, jeecgOneDemoPage.getObid());
	}

	public JeecgOneDemoEntity get(String obid) {
		return jeecgOneDemoEntityDao.get(JeecgOneDemoEntity.class, obid);
	}
	public List<JeecgOneDemoEntity> listAll(JeecgOneDemoPage jeecgOneDemoPage) {
		String hql = "from JeecgOneDemoEntity where 1 = 1 ";
		List<Object> values = new ArrayList<Object>();
		hql = addWhere(jeecgOneDemoPage, hql, values);
		List<JeecgOneDemoEntity> list = jeecgOneDemoEntityDao.find(hql,values);
		return list;
	}
}
