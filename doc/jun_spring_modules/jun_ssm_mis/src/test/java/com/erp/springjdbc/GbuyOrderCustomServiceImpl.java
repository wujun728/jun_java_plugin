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
import com.erp.jee.entity.GbuyOrderCustomEntity;
import com.erp.jee.page2.GbuyOrderCustomPage;
import com.erp.jee.pageModel.DataGrid;
import com.erp.jee.service.GbuyOrderCustomServiceI;
import com.erp.service.impl.BaseServiceImpl;
import com.jun.plugin.utils.BeanUtil;
import com.jun.plugin.utils.SQLUtil;

/**   
 * @Title: ServiceImpl
 * @Description: 订单客户明细
 * @author Wujun
 * @date 2011-12-19 13:09:28
 * @version V1.0   
 *
 */
@Service("gbuyOrderCustomService")
public class GbuyOrderCustomServiceImpl extends BaseServiceImpl implements GbuyOrderCustomServiceI {

	@Autowired
	//SQL 使用JdbcDao
	private JdbcDao jdbcDao;
	private IBaseDao<GbuyOrderCustomEntity> gbuyOrderCustomEntityDao;

	public IBaseDao<GbuyOrderCustomEntity> getGbuyOrderCustomEntityDao() {
		return gbuyOrderCustomEntityDao;
	}
	@Autowired
	public void setGbuyOrderCustomEntityDao(IBaseDao<GbuyOrderCustomEntity> gbuyOrderCustomEntityDao) {
		this.gbuyOrderCustomEntityDao = gbuyOrderCustomEntityDao;
	}

	@Transactional(propagation = Propagation.SUPPORTS)
	public DataGrid datagrid(GbuyOrderCustomPage gbuyOrderCustomPage) {
		DataGrid j = new DataGrid();
		j.setRows(getPagesFromEntitys(find(gbuyOrderCustomPage)));
		j.setTotal(total(gbuyOrderCustomPage));
		return j;
	}

	private List<GbuyOrderCustomPage> getPagesFromEntitys(List<GbuyOrderCustomEntity> gbuyOrderCustomEntitys) {
		List<GbuyOrderCustomPage> gbuyOrderCustomPages = new ArrayList<GbuyOrderCustomPage>();
		if (gbuyOrderCustomEntitys != null && gbuyOrderCustomEntitys.size() > 0) {
			for (GbuyOrderCustomEntity tb : gbuyOrderCustomEntitys) {
				GbuyOrderCustomPage b = new GbuyOrderCustomPage();
				BeanUtils.copyProperties(tb, b);
				gbuyOrderCustomPages.add(b);
			}
		}
		return gbuyOrderCustomPages;
	}

	private List<GbuyOrderCustomEntity> find(GbuyOrderCustomPage gbuyOrderCustomPage) {
		String hql = "from GbuyOrderCustomEntity t where 1=1 ";

		List<Object> values = new ArrayList<Object>();
		hql = addWhere(gbuyOrderCustomPage, hql, values);

		if (gbuyOrderCustomPage.getSort() != null && gbuyOrderCustomPage.getOrder() != null) {
			hql += " order by " + gbuyOrderCustomPage.getSort() + " " + gbuyOrderCustomPage.getOrder();
		}
		return gbuyOrderCustomEntityDao.find(hql, gbuyOrderCustomPage.getPage(), gbuyOrderCustomPage.getRows(), values);
	}

	private Long total(GbuyOrderCustomPage gbuyOrderCustomPage) {
		String hql = "select count(*) from GbuyOrderCustomEntity t where 1=1 ";
		List<Object> values = new ArrayList<Object>();
		hql = addWhere(gbuyOrderCustomPage, hql, values);
		return gbuyOrderCustomEntityDao.count(hql, values);
	}

	private String addWhere(GbuyOrderCustomPage gbuyOrderCustomPage, String hql, List<Object> values) {
	    //循环查询条件Page的所有[Integer][String]类型的字段，如果字段不为空则动态加上查询条件
		//-----------------------------------------------------
		StringBuffer hqlbf = new StringBuffer(hql);
		
		GbuyOrderCustomEntity gbuyOrderCustomEntity = new GbuyOrderCustomEntity();
		BeanUtils.copyProperties(gbuyOrderCustomPage, gbuyOrderCustomEntity);
		SQLUtil.createSearchParamsHql(hqlbf, values, gbuyOrderCustomEntity);
		hql = hqlbf.toString();
		//-----------------------------------------------------
		if (gbuyOrderCustomPage.getCcreatedatetimeStart() != null) {
			hql += " and createDt>=? ";
			values.add(gbuyOrderCustomPage.getCcreatedatetimeStart());
		}
		if (gbuyOrderCustomPage.getCcreatedatetimeEnd() != null) {
			hql += " and createDt<=? ";
			values.add(gbuyOrderCustomPage.getCcreatedatetimeEnd());
		}
		if (gbuyOrderCustomPage.getCmodifydatetimeStart() != null) {
			hql += " and modifyDt>=? ";
			values.add(gbuyOrderCustomPage.getCmodifydatetimeStart());
		}
		if (gbuyOrderCustomPage.getCmodifydatetimeEnd() != null) {
			hql += " and modifyDt<=? ";
			values.add(gbuyOrderCustomPage.getCmodifydatetimeEnd());
		}
		return hql;
	}

	public void add(GbuyOrderCustomPage gbuyOrderCustomPage) {
		if (gbuyOrderCustomPage.getObid() == null || gbuyOrderCustomPage.getObid().trim().equals("")) {
			gbuyOrderCustomPage.setObid(UUID.randomUUID().toString());
		}
		GbuyOrderCustomEntity t = new GbuyOrderCustomEntity();
		BeanUtils.copyProperties(gbuyOrderCustomPage, t);
		gbuyOrderCustomEntityDao.save(t);
	}

	public void update(GbuyOrderCustomPage gbuyOrderCustomPage) throws Exception {
		GbuyOrderCustomEntity t = gbuyOrderCustomEntityDao.get(GbuyOrderCustomEntity.class, gbuyOrderCustomPage.getObid());
	    if(t != null) {
			BeanUtil.copyBeanNotNull2Bean(gbuyOrderCustomPage, t);
		}
	}

	public void delete(String ids) {
		if (ids != null) {
			for (String id : ids.split(",")) {
				GbuyOrderCustomEntity t = gbuyOrderCustomEntityDao.get(GbuyOrderCustomEntity.class, id);
				if (t != null) {
					gbuyOrderCustomEntityDao.delete(t);
				}
			}
		}
	}

	public GbuyOrderCustomEntity get(GbuyOrderCustomPage gbuyOrderCustomPage) {
		return gbuyOrderCustomEntityDao.get(GbuyOrderCustomEntity.class, gbuyOrderCustomPage.getObid());
	}

	public GbuyOrderCustomEntity get(String obid) {
		return gbuyOrderCustomEntityDao.get(GbuyOrderCustomEntity.class, obid);
	}
	public List<GbuyOrderCustomEntity> listAll(GbuyOrderCustomPage gbuyOrderCustomPage) {
		String hql = "from GbuyOrderCustomEntity where 1 = 1 ";
		List<Object> values = new ArrayList<Object>();
		hql = addWhere(gbuyOrderCustomPage, hql, values);
		List<GbuyOrderCustomEntity> list = gbuyOrderCustomEntityDao.find(hql,values);
		return list;
	}
}
