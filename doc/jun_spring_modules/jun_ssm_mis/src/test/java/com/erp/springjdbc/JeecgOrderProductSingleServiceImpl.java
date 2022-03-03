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
import com.erp.jee.entity.JeecgOrderProductSingleEntity;
import com.erp.jee.page2.JeecgOrderProductSinglePage;
import com.erp.jee.pageModel.DataGrid;
import com.erp.jee.service.JeecgOrderProductSingleServiceI;
import com.erp.service.impl.BaseServiceImpl;
import com.jun.plugin.utils.BeanUtil;
import com.jun.plugin.utils.SQLUtil;

/**   
 * @Title: ServiceImpl
 * @Description: 订单产品明细
 * @author Wujun
 * @date 2013-01-18 15:44:09
 * @version V1.0   
 *
 */
@Service("jeecgOrderProductSingleService")
public class JeecgOrderProductSingleServiceImpl extends BaseServiceImpl implements JeecgOrderProductSingleServiceI {

	@Autowired
	//SQL 使用JdbcDao
	private JdbcDao jdbcDao;
	private IBaseDao<JeecgOrderProductSingleEntity> jeecgOrderProductSingleEntityDao;

	public IBaseDao<JeecgOrderProductSingleEntity> getJeecgOrderProductSingleEntityDao() {
		return jeecgOrderProductSingleEntityDao;
	}
	@Autowired
	public void setJeecgOrderProductSingleEntityDao(IBaseDao<JeecgOrderProductSingleEntity> jeecgOrderProductSingleEntityDao) {
		this.jeecgOrderProductSingleEntityDao = jeecgOrderProductSingleEntityDao;
	}

	@Transactional(propagation = Propagation.SUPPORTS)
	public DataGrid datagrid(JeecgOrderProductSinglePage jeecgOrderProductSinglePage) {
		DataGrid j = new DataGrid();
		j.setRows(getPagesFromEntitys(find(jeecgOrderProductSinglePage)));
		j.setTotal(total(jeecgOrderProductSinglePage));
		return j;
	}

	private List<JeecgOrderProductSinglePage> getPagesFromEntitys(List<JeecgOrderProductSingleEntity> jeecgOrderProductSingleEntitys) {
		List<JeecgOrderProductSinglePage> jeecgOrderProductSinglePages = new ArrayList<JeecgOrderProductSinglePage>();
		if (jeecgOrderProductSingleEntitys != null && jeecgOrderProductSingleEntitys.size() > 0) {
			for (JeecgOrderProductSingleEntity tb : jeecgOrderProductSingleEntitys) {
				JeecgOrderProductSinglePage b = new JeecgOrderProductSinglePage();
				BeanUtils.copyProperties(tb, b);
				jeecgOrderProductSinglePages.add(b);
			}
		}
		return jeecgOrderProductSinglePages;
	}

	private List<JeecgOrderProductSingleEntity> find(JeecgOrderProductSinglePage jeecgOrderProductSinglePage) {
		String hql = "from JeecgOrderProductSingleEntity t where 1=1 ";

		List<Object> values = new ArrayList<Object>();
		hql = addWhere(jeecgOrderProductSinglePage, hql, values);

		if (jeecgOrderProductSinglePage.getSort() != null && jeecgOrderProductSinglePage.getOrder() != null) {
			hql += " order by " + jeecgOrderProductSinglePage.getSort() + " " + jeecgOrderProductSinglePage.getOrder();
		}
		return jeecgOrderProductSingleEntityDao.find(hql, jeecgOrderProductSinglePage.getPage(), jeecgOrderProductSinglePage.getRows(), values);
	}

	private Long total(JeecgOrderProductSinglePage jeecgOrderProductSinglePage) {
		String hql = "select count(*) from JeecgOrderProductSingleEntity t where 1=1 ";
		List<Object> values = new ArrayList<Object>();
		hql = addWhere(jeecgOrderProductSinglePage, hql, values);
		return jeecgOrderProductSingleEntityDao.count(hql, values);
	}

	private String addWhere(JeecgOrderProductSinglePage jeecgOrderProductSinglePage, String hql, List<Object> values) {
	    //循环查询条件Page的所有[Integer][String]类型的字段，如果字段不为空则动态加上查询条件
		//-----------------------------------------------------
		StringBuffer hqlbf = new StringBuffer(hql);
		
		JeecgOrderProductSingleEntity jeecgOrderProductSingleEntity = new JeecgOrderProductSingleEntity();
		BeanUtils.copyProperties(jeecgOrderProductSinglePage, jeecgOrderProductSingleEntity);
		SQLUtil.createSearchParamsHql(hqlbf, values, jeecgOrderProductSingleEntity);
		hql = hqlbf.toString();
		//-----------------------------------------------------
		if (jeecgOrderProductSinglePage.getCcreatedatetimeStart() != null) {
			hql += " and createDt>=? ";
			values.add(jeecgOrderProductSinglePage.getCcreatedatetimeStart());
		}
		if (jeecgOrderProductSinglePage.getCcreatedatetimeEnd() != null) {
			hql += " and createDt<=? ";
			values.add(jeecgOrderProductSinglePage.getCcreatedatetimeEnd());
		}
		if (jeecgOrderProductSinglePage.getCmodifydatetimeStart() != null) {
			hql += " and modifyDt>=? ";
			values.add(jeecgOrderProductSinglePage.getCmodifydatetimeStart());
		}
		if (jeecgOrderProductSinglePage.getCmodifydatetimeEnd() != null) {
			hql += " and modifyDt<=? ";
			values.add(jeecgOrderProductSinglePage.getCmodifydatetimeEnd());
		}
		return hql;
	}

	public void add(JeecgOrderProductSinglePage jeecgOrderProductSinglePage) {
		if (jeecgOrderProductSinglePage.getObid() == null || jeecgOrderProductSinglePage.getObid().trim().equals("")) {
			jeecgOrderProductSinglePage.setObid(UUID.randomUUID().toString());
		}
		JeecgOrderProductSingleEntity t = new JeecgOrderProductSingleEntity();
		BeanUtils.copyProperties(jeecgOrderProductSinglePage, t);
		jeecgOrderProductSingleEntityDao.save(t);
	}

	public void update(JeecgOrderProductSinglePage jeecgOrderProductSinglePage) throws Exception {
		JeecgOrderProductSingleEntity t = jeecgOrderProductSingleEntityDao.get(JeecgOrderProductSingleEntity.class, jeecgOrderProductSinglePage.getObid());
	    if(t != null) {
			BeanUtil.copyBeanNotNull2Bean(jeecgOrderProductSinglePage, t);
		}
	}

	public void delete(String ids) {
		if (ids != null) {
			for (String id : ids.split(",")) {
				JeecgOrderProductSingleEntity t = jeecgOrderProductSingleEntityDao.get(JeecgOrderProductSingleEntity.class, id);
				if (t != null) {
					jeecgOrderProductSingleEntityDao.delete(t);
				}
			}
		}
	}

	public JeecgOrderProductSingleEntity get(JeecgOrderProductSinglePage jeecgOrderProductSinglePage) {
		return jeecgOrderProductSingleEntityDao.get(JeecgOrderProductSingleEntity.class, jeecgOrderProductSinglePage.getObid());
	}

	public JeecgOrderProductSingleEntity get(String obid) {
		return jeecgOrderProductSingleEntityDao.get(JeecgOrderProductSingleEntity.class, obid);
	}
	public List<JeecgOrderProductSingleEntity> listAll(JeecgOrderProductSinglePage jeecgOrderProductSinglePage) {
		String hql = "from JeecgOrderProductSingleEntity where 1 = 1 ";
		List<Object> values = new ArrayList<Object>();
		hql = addWhere(jeecgOrderProductSinglePage, hql, values);
		List<JeecgOrderProductSingleEntity> list = jeecgOrderProductSingleEntityDao.find(hql,values);
		return list;
	}
}
