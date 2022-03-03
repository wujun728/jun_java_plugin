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
import com.erp.jee.entity.JeecgOrderCustomEntity;
import com.erp.jee.page2.JeecgOrderCustomPage;
import com.erp.jee.pageModel.DataGrid;
import com.erp.jee.service.JeecgOrderCustomServiceI;
import com.erp.service.impl.BaseServiceImpl;
import com.jun.plugin.utils.BeanUtil;
import com.jun.plugin.utils.SQLUtil;

/**   
 * @Title: ServiceImpl
 * @Description: 订单客户明细
 * @author Wujun
 * @date 2011-12-31 16:22:42
 * @version V1.0   
 *
 */
@Service("jeecgOrderCustomService")
public class JeecgOrderCustomServiceImpl extends BaseServiceImpl implements JeecgOrderCustomServiceI {

	@Autowired
	//SQL 使用JdbcDao
	private JdbcDao jdbcDao;
	private IBaseDao<JeecgOrderCustomEntity> jeecgOrderCustomEntityDao;

	public IBaseDao<JeecgOrderCustomEntity> getJeecgOrderCustomEntityDao() {
		return jeecgOrderCustomEntityDao;
	}
	@Autowired
	public void setJeecgOrderCustomEntityDao(IBaseDao<JeecgOrderCustomEntity> jeecgOrderCustomEntityDao) {
		this.jeecgOrderCustomEntityDao = jeecgOrderCustomEntityDao;
	}

	@Transactional(propagation = Propagation.SUPPORTS)
	public DataGrid datagrid(JeecgOrderCustomPage jeecgOrderCustomPage) {
		DataGrid j = new DataGrid();
		j.setRows(getPagesFromEntitys(find(jeecgOrderCustomPage)));
		j.setTotal(total(jeecgOrderCustomPage));
		return j;
	}

	private List<JeecgOrderCustomPage> getPagesFromEntitys(List<JeecgOrderCustomEntity> jeecgOrderCustomEntitys) {
		List<JeecgOrderCustomPage> jeecgOrderCustomPages = new ArrayList<JeecgOrderCustomPage>();
		if (jeecgOrderCustomEntitys != null && jeecgOrderCustomEntitys.size() > 0) {
			for (JeecgOrderCustomEntity tb : jeecgOrderCustomEntitys) {
				JeecgOrderCustomPage b = new JeecgOrderCustomPage();
				BeanUtils.copyProperties(tb, b);
				jeecgOrderCustomPages.add(b);
			}
		}
		return jeecgOrderCustomPages;
	}

	private List<JeecgOrderCustomEntity> find(JeecgOrderCustomPage jeecgOrderCustomPage) {
		String hql = "from JeecgOrderCustomEntity t where 1=1 ";

		List<Object> values = new ArrayList<Object>();
		hql = addWhere(jeecgOrderCustomPage, hql, values);

		if (jeecgOrderCustomPage.getSort() != null && jeecgOrderCustomPage.getOrder() != null) {
			hql += " order by " + jeecgOrderCustomPage.getSort() + " " + jeecgOrderCustomPage.getOrder();
		}
		return jeecgOrderCustomEntityDao.find(hql, jeecgOrderCustomPage.getPage(), jeecgOrderCustomPage.getRows(), values);
	}

	private Long total(JeecgOrderCustomPage jeecgOrderCustomPage) {
		String hql = "select count(*) from JeecgOrderCustomEntity t where 1=1 ";
		List<Object> values = new ArrayList<Object>();
		hql = addWhere(jeecgOrderCustomPage, hql, values);
		return jeecgOrderCustomEntityDao.count(hql, values);
	}

	private String addWhere(JeecgOrderCustomPage jeecgOrderCustomPage, String hql, List<Object> values) {
	    //循环查询条件Page的所有[Integer][String]类型的字段，如果字段不为空则动态加上查询条件
		//-----------------------------------------------------
		StringBuffer hqlbf = new StringBuffer(hql);
		
		JeecgOrderCustomEntity jeecgOrderCustomEntity = new JeecgOrderCustomEntity();
		BeanUtils.copyProperties(jeecgOrderCustomPage, jeecgOrderCustomEntity);
		SQLUtil.createSearchParamsHql(hqlbf, values, jeecgOrderCustomEntity);
		hql = hqlbf.toString();
		//-----------------------------------------------------
		if (jeecgOrderCustomPage.getCcreatedatetimeStart() != null) {
			hql += " and createDt>=? ";
			values.add(jeecgOrderCustomPage.getCcreatedatetimeStart());
		}
		if (jeecgOrderCustomPage.getCcreatedatetimeEnd() != null) {
			hql += " and createDt<=? ";
			values.add(jeecgOrderCustomPage.getCcreatedatetimeEnd());
		}
		if (jeecgOrderCustomPage.getCmodifydatetimeStart() != null) {
			hql += " and modifyDt>=? ";
			values.add(jeecgOrderCustomPage.getCmodifydatetimeStart());
		}
		if (jeecgOrderCustomPage.getCmodifydatetimeEnd() != null) {
			hql += " and modifyDt<=? ";
			values.add(jeecgOrderCustomPage.getCmodifydatetimeEnd());
		}
		return hql;
	}

	public void add(JeecgOrderCustomPage jeecgOrderCustomPage) {
		if (jeecgOrderCustomPage.getObid() == null || jeecgOrderCustomPage.getObid().trim().equals("")) {
			jeecgOrderCustomPage.setObid(UUID.randomUUID().toString());
		}
		JeecgOrderCustomEntity t = new JeecgOrderCustomEntity();
		BeanUtils.copyProperties(jeecgOrderCustomPage, t);
		jeecgOrderCustomEntityDao.save(t);
	}

	public void update(JeecgOrderCustomPage jeecgOrderCustomPage) throws Exception {
		JeecgOrderCustomEntity t = jeecgOrderCustomEntityDao.get(JeecgOrderCustomEntity.class, jeecgOrderCustomPage.getObid());
	    if(t != null) {
			BeanUtil.copyBeanNotNull2Bean(jeecgOrderCustomPage, t);
		}
	}

	public void delete(String ids) {
		if (ids != null) {
			for (String id : ids.split(",")) {
				JeecgOrderCustomEntity t = jeecgOrderCustomEntityDao.get(JeecgOrderCustomEntity.class, id);
				if (t != null) {
					jeecgOrderCustomEntityDao.delete(t);
				}
			}
		}
	}

	public JeecgOrderCustomEntity get(JeecgOrderCustomPage jeecgOrderCustomPage) {
		return jeecgOrderCustomEntityDao.get(JeecgOrderCustomEntity.class, jeecgOrderCustomPage.getObid());
	}

	public JeecgOrderCustomEntity get(String obid) {
		return jeecgOrderCustomEntityDao.get(JeecgOrderCustomEntity.class, obid);
	}
	public List<JeecgOrderCustomEntity> listAll(JeecgOrderCustomPage jeecgOrderCustomPage) {
		String hql = "from JeecgOrderCustomEntity where 1 = 1 ";
		List<Object> values = new ArrayList<Object>();
		hql = addWhere(jeecgOrderCustomPage, hql, values);
		List<JeecgOrderCustomEntity> list = jeecgOrderCustomEntityDao.find(hql,values);
		return list;
	}
}
