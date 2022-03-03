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
import com.erp.jee.entity.JeecgOrderCustomSingleEntity;
import com.erp.jee.page2.JeecgOrderCustomSinglePage;
import com.erp.jee.pageModel.DataGrid;
import com.erp.jee.service.JeecgOrderCustomSingleServiceI;
import com.erp.service.impl.BaseServiceImpl;
import com.jun.plugin.utils.BeanUtil;
import com.jun.plugin.utils.SQLUtil;

/**   
 * @Title: ServiceImpl
 * @Description: 订单客户明细
 * @author Wujun
 * @date 2013-01-18 15:44:08
 * @version V1.0   
 *
 */
@Service("jeecgOrderCustomSingleService")
public class JeecgOrderCustomSingleServiceImpl extends BaseServiceImpl implements JeecgOrderCustomSingleServiceI {

	@Autowired
	//SQL 使用JdbcDao
	private JdbcDao jdbcDao;
	private IBaseDao<JeecgOrderCustomSingleEntity> jeecgOrderCustomSingleEntityDao;

	public IBaseDao<JeecgOrderCustomSingleEntity> getJeecgOrderCustomSingleEntityDao() {
		return jeecgOrderCustomSingleEntityDao;
	}
	@Autowired
	public void setJeecgOrderCustomSingleEntityDao(IBaseDao<JeecgOrderCustomSingleEntity> jeecgOrderCustomSingleEntityDao) {
		this.jeecgOrderCustomSingleEntityDao = jeecgOrderCustomSingleEntityDao;
	}

	@Transactional(propagation = Propagation.SUPPORTS)
	public DataGrid datagrid(JeecgOrderCustomSinglePage jeecgOrderCustomSinglePage) {
		DataGrid j = new DataGrid();
		j.setRows(getPagesFromEntitys(find(jeecgOrderCustomSinglePage)));
		j.setTotal(total(jeecgOrderCustomSinglePage));
		return j;
	}

	private List<JeecgOrderCustomSinglePage> getPagesFromEntitys(List<JeecgOrderCustomSingleEntity> jeecgOrderCustomSingleEntitys) {
		List<JeecgOrderCustomSinglePage> jeecgOrderCustomSinglePages = new ArrayList<JeecgOrderCustomSinglePage>();
		if (jeecgOrderCustomSingleEntitys != null && jeecgOrderCustomSingleEntitys.size() > 0) {
			for (JeecgOrderCustomSingleEntity tb : jeecgOrderCustomSingleEntitys) {
				JeecgOrderCustomSinglePage b = new JeecgOrderCustomSinglePage();
				BeanUtils.copyProperties(tb, b);
				jeecgOrderCustomSinglePages.add(b);
			}
		}
		return jeecgOrderCustomSinglePages;
	}

	private List<JeecgOrderCustomSingleEntity> find(JeecgOrderCustomSinglePage jeecgOrderCustomSinglePage) {
		String hql = "from JeecgOrderCustomSingleEntity t where 1=1 ";

		List<Object> values = new ArrayList<Object>();
		hql = addWhere(jeecgOrderCustomSinglePage, hql, values);

		if (jeecgOrderCustomSinglePage.getSort() != null && jeecgOrderCustomSinglePage.getOrder() != null) {
			hql += " order by " + jeecgOrderCustomSinglePage.getSort() + " " + jeecgOrderCustomSinglePage.getOrder();
		}
		return jeecgOrderCustomSingleEntityDao.find(hql, jeecgOrderCustomSinglePage.getPage(), jeecgOrderCustomSinglePage.getRows(), values);
	}

	private Long total(JeecgOrderCustomSinglePage jeecgOrderCustomSinglePage) {
		String hql = "select count(*) from JeecgOrderCustomSingleEntity t where 1=1 ";
		List<Object> values = new ArrayList<Object>();
		hql = addWhere(jeecgOrderCustomSinglePage, hql, values);
		return jeecgOrderCustomSingleEntityDao.count(hql, values);
	}

	private String addWhere(JeecgOrderCustomSinglePage jeecgOrderCustomSinglePage, String hql, List<Object> values) {
	    //循环查询条件Page的所有[Integer][String]类型的字段，如果字段不为空则动态加上查询条件
		//-----------------------------------------------------
		StringBuffer hqlbf = new StringBuffer(hql);
		
		JeecgOrderCustomSingleEntity jeecgOrderCustomSingleEntity = new JeecgOrderCustomSingleEntity();
		BeanUtils.copyProperties(jeecgOrderCustomSinglePage, jeecgOrderCustomSingleEntity);
		SQLUtil.createSearchParamsHql(hqlbf, values, jeecgOrderCustomSingleEntity);
		hql = hqlbf.toString();
		//-----------------------------------------------------
		if (jeecgOrderCustomSinglePage.getCcreatedatetimeStart() != null) {
			hql += " and createDt>=? ";
			values.add(jeecgOrderCustomSinglePage.getCcreatedatetimeStart());
		}
		if (jeecgOrderCustomSinglePage.getCcreatedatetimeEnd() != null) {
			hql += " and createDt<=? ";
			values.add(jeecgOrderCustomSinglePage.getCcreatedatetimeEnd());
		}
		if (jeecgOrderCustomSinglePage.getCmodifydatetimeStart() != null) {
			hql += " and modifyDt>=? ";
			values.add(jeecgOrderCustomSinglePage.getCmodifydatetimeStart());
		}
		if (jeecgOrderCustomSinglePage.getCmodifydatetimeEnd() != null) {
			hql += " and modifyDt<=? ";
			values.add(jeecgOrderCustomSinglePage.getCmodifydatetimeEnd());
		}
		return hql;
	}

	public void add(JeecgOrderCustomSinglePage jeecgOrderCustomSinglePage) {
		if (jeecgOrderCustomSinglePage.getObid() == null || jeecgOrderCustomSinglePage.getObid().trim().equals("")) {
			jeecgOrderCustomSinglePage.setObid(UUID.randomUUID().toString());
		}
		JeecgOrderCustomSingleEntity t = new JeecgOrderCustomSingleEntity();
		BeanUtils.copyProperties(jeecgOrderCustomSinglePage, t);
		jeecgOrderCustomSingleEntityDao.save(t);
	}

	public void update(JeecgOrderCustomSinglePage jeecgOrderCustomSinglePage) throws Exception {
		JeecgOrderCustomSingleEntity t = jeecgOrderCustomSingleEntityDao.get(JeecgOrderCustomSingleEntity.class, jeecgOrderCustomSinglePage.getObid());
	    if(t != null) {
			BeanUtil.copyBeanNotNull2Bean(jeecgOrderCustomSinglePage, t);
		}
	}

	public void delete(String ids) {
		if (ids != null) {
			for (String id : ids.split(",")) {
				JeecgOrderCustomSingleEntity t = jeecgOrderCustomSingleEntityDao.get(JeecgOrderCustomSingleEntity.class, id);
				if (t != null) {
					jeecgOrderCustomSingleEntityDao.delete(t);
				}
			}
		}
	}

	public JeecgOrderCustomSingleEntity get(JeecgOrderCustomSinglePage jeecgOrderCustomSinglePage) {
		return jeecgOrderCustomSingleEntityDao.get(JeecgOrderCustomSingleEntity.class, jeecgOrderCustomSinglePage.getObid());
	}

	public JeecgOrderCustomSingleEntity get(String obid) {
		return jeecgOrderCustomSingleEntityDao.get(JeecgOrderCustomSingleEntity.class, obid);
	}
	public List<JeecgOrderCustomSingleEntity> listAll(JeecgOrderCustomSinglePage jeecgOrderCustomSinglePage) {
		String hql = "from JeecgOrderCustomSingleEntity where 1 = 1 ";
		List<Object> values = new ArrayList<Object>();
		hql = addWhere(jeecgOrderCustomSinglePage, hql, values);
		List<JeecgOrderCustomSingleEntity> list = jeecgOrderCustomSingleEntityDao.find(hql,values);
		return list;
	}
}
