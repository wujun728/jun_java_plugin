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
import com.erp.jee.entity.JeecgOrderProductEntity;
import com.erp.jee.page2.JeecgOrderProductPage;
import com.erp.jee.pageModel.DataGrid;
import com.erp.jee.service.JeecgOrderProductServiceI;
import com.erp.service.impl.BaseServiceImpl;
import com.jun.plugin.utils.BeanUtil;
import com.jun.plugin.utils.SQLUtil;

/**   
 * @Title: ServiceImpl
 * @Description: 订单产品明细
 * @author Wujun
 * @date 2011-12-31 16:22:59
 * @version V1.0   
 *
 */
@Service("jeecgOrderProductService")
public class JeecgOrderProductServiceImpl extends BaseServiceImpl implements JeecgOrderProductServiceI {

	@Autowired
	//SQL 使用JdbcDao
	private JdbcDao jdbcDao;
	private IBaseDao<JeecgOrderProductEntity> jeecgOrderProductEntityDao;

	public IBaseDao<JeecgOrderProductEntity> getJeecgOrderProductEntityDao() {
		return jeecgOrderProductEntityDao;
	}
	@Autowired
	public void setJeecgOrderProductEntityDao(IBaseDao<JeecgOrderProductEntity> jeecgOrderProductEntityDao) {
		this.jeecgOrderProductEntityDao = jeecgOrderProductEntityDao;
	}

	@Transactional(propagation = Propagation.SUPPORTS)
	public DataGrid datagrid(JeecgOrderProductPage jeecgOrderProductPage) {
		DataGrid j = new DataGrid();
		j.setRows(getPagesFromEntitys(find(jeecgOrderProductPage)));
		j.setTotal(total(jeecgOrderProductPage));
		return j;
	}

	private List<JeecgOrderProductPage> getPagesFromEntitys(List<JeecgOrderProductEntity> jeecgOrderProductEntitys) {
		List<JeecgOrderProductPage> jeecgOrderProductPages = new ArrayList<JeecgOrderProductPage>();
		if (jeecgOrderProductEntitys != null && jeecgOrderProductEntitys.size() > 0) {
			for (JeecgOrderProductEntity tb : jeecgOrderProductEntitys) {
				JeecgOrderProductPage b = new JeecgOrderProductPage();
				BeanUtils.copyProperties(tb, b);
				jeecgOrderProductPages.add(b);
			}
		}
		return jeecgOrderProductPages;
	}

	private List<JeecgOrderProductEntity> find(JeecgOrderProductPage jeecgOrderProductPage) {
		String hql = "from JeecgOrderProductEntity t where 1=1 ";

		List<Object> values = new ArrayList<Object>();
		hql = addWhere(jeecgOrderProductPage, hql, values);

		if (jeecgOrderProductPage.getSort() != null && jeecgOrderProductPage.getOrder() != null) {
			hql += " order by " + jeecgOrderProductPage.getSort() + " " + jeecgOrderProductPage.getOrder();
		}
		return jeecgOrderProductEntityDao.find(hql, jeecgOrderProductPage.getPage(), jeecgOrderProductPage.getRows(), values);
	}

	private Long total(JeecgOrderProductPage jeecgOrderProductPage) {
		String hql = "select count(*) from JeecgOrderProductEntity t where 1=1 ";
		List<Object> values = new ArrayList<Object>();
		hql = addWhere(jeecgOrderProductPage, hql, values);
		return jeecgOrderProductEntityDao.count(hql, values);
	}

	private String addWhere(JeecgOrderProductPage jeecgOrderProductPage, String hql, List<Object> values) {
	    //循环查询条件Page的所有[Integer][String]类型的字段，如果字段不为空则动态加上查询条件
		//-----------------------------------------------------
		StringBuffer hqlbf = new StringBuffer(hql);
		
		JeecgOrderProductEntity jeecgOrderProductEntity = new JeecgOrderProductEntity();
		BeanUtils.copyProperties(jeecgOrderProductPage, jeecgOrderProductEntity);
		SQLUtil.createSearchParamsHql(hqlbf, values, jeecgOrderProductEntity);
		hql = hqlbf.toString();
		//-----------------------------------------------------
		if (jeecgOrderProductPage.getCcreatedatetimeStart() != null) {
			hql += " and createDt>=? ";
			values.add(jeecgOrderProductPage.getCcreatedatetimeStart());
		}
		if (jeecgOrderProductPage.getCcreatedatetimeEnd() != null) {
			hql += " and createDt<=? ";
			values.add(jeecgOrderProductPage.getCcreatedatetimeEnd());
		}
		if (jeecgOrderProductPage.getCmodifydatetimeStart() != null) {
			hql += " and modifyDt>=? ";
			values.add(jeecgOrderProductPage.getCmodifydatetimeStart());
		}
		if (jeecgOrderProductPage.getCmodifydatetimeEnd() != null) {
			hql += " and modifyDt<=? ";
			values.add(jeecgOrderProductPage.getCmodifydatetimeEnd());
		}
		return hql;
	}

	public void add(JeecgOrderProductPage jeecgOrderProductPage) {
		if (jeecgOrderProductPage.getObid() == null || jeecgOrderProductPage.getObid().trim().equals("")) {
			jeecgOrderProductPage.setObid(UUID.randomUUID().toString());
		}
		JeecgOrderProductEntity t = new JeecgOrderProductEntity();
		BeanUtils.copyProperties(jeecgOrderProductPage, t);
		jeecgOrderProductEntityDao.save(t);
	}

	public void update(JeecgOrderProductPage jeecgOrderProductPage) throws Exception {
		JeecgOrderProductEntity t = jeecgOrderProductEntityDao.get(JeecgOrderProductEntity.class, jeecgOrderProductPage.getObid());
	    if(t != null) {
			BeanUtil.copyBeanNotNull2Bean(jeecgOrderProductPage, t);
		}
	}

	public void delete(String ids) {
		if (ids != null) {
			for (String id : ids.split(",")) {
				JeecgOrderProductEntity t = jeecgOrderProductEntityDao.get(JeecgOrderProductEntity.class, id);
				if (t != null) {
					jeecgOrderProductEntityDao.delete(t);
				}
			}
		}
	}

	public JeecgOrderProductEntity get(JeecgOrderProductPage jeecgOrderProductPage) {
		return jeecgOrderProductEntityDao.get(JeecgOrderProductEntity.class, jeecgOrderProductPage.getObid());
	}

	public JeecgOrderProductEntity get(String obid) {
		return jeecgOrderProductEntityDao.get(JeecgOrderProductEntity.class, obid);
	}
	public List<JeecgOrderProductEntity> listAll(JeecgOrderProductPage jeecgOrderProductPage) {
		String hql = "from JeecgOrderProductEntity where 1 = 1 ";
		List<Object> values = new ArrayList<Object>();
		hql = addWhere(jeecgOrderProductPage, hql, values);
		List<JeecgOrderProductEntity> list = jeecgOrderProductEntityDao.find(hql,values);
		return list;
	}
}
