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
import com.erp.jee.entity.GbuyOrderProductEntity;
import com.erp.jee.page2.GbuyOrderProductPage;
import com.erp.jee.pageModel.DataGrid;
import com.erp.jee.service.GbuyOrderProductServiceI;
import com.erp.service.impl.BaseServiceImpl;
import com.jun.plugin.utils.BeanUtil;
import com.jun.plugin.utils.SQLUtil;

/**   
 * @Title: ServiceImpl
 * @Description: 订单产品明细
 * @author Wujun
 * @date 2011-12-19 13:09:30
 * @version V1.0   
 *
 */
@Service("gbuyOrderProductService")
public class GbuyOrderProductServiceImpl extends BaseServiceImpl implements GbuyOrderProductServiceI {

	@Autowired
	//SQL 使用JdbcDao
	private JdbcDao jdbcDao;
	private IBaseDao<GbuyOrderProductEntity> gbuyOrderProductEntityDao;

	public IBaseDao<GbuyOrderProductEntity> getGbuyOrderProductEntityDao() {
		return gbuyOrderProductEntityDao;
	}
	@Autowired
	public void setGbuyOrderProductEntityDao(IBaseDao<GbuyOrderProductEntity> gbuyOrderProductEntityDao) {
		this.gbuyOrderProductEntityDao = gbuyOrderProductEntityDao;
	}

	@Transactional(propagation = Propagation.SUPPORTS)
	public DataGrid datagrid(GbuyOrderProductPage gbuyOrderProductPage) {
		DataGrid j = new DataGrid();
		j.setRows(getPagesFromEntitys(find(gbuyOrderProductPage)));
		j.setTotal(total(gbuyOrderProductPage));
		return j;
	}

	private List<GbuyOrderProductPage> getPagesFromEntitys(List<GbuyOrderProductEntity> gbuyOrderProductEntitys) {
		List<GbuyOrderProductPage> gbuyOrderProductPages = new ArrayList<GbuyOrderProductPage>();
		if (gbuyOrderProductEntitys != null && gbuyOrderProductEntitys.size() > 0) {
			for (GbuyOrderProductEntity tb : gbuyOrderProductEntitys) {
				GbuyOrderProductPage b = new GbuyOrderProductPage();
				BeanUtils.copyProperties(tb, b);
				gbuyOrderProductPages.add(b);
			}
		}
		return gbuyOrderProductPages;
	}

	private List<GbuyOrderProductEntity> find(GbuyOrderProductPage gbuyOrderProductPage) {
		String hql = "from GbuyOrderProductEntity t where 1=1 ";

		List<Object> values = new ArrayList<Object>();
		hql = addWhere(gbuyOrderProductPage, hql, values);

		if (gbuyOrderProductPage.getSort() != null && gbuyOrderProductPage.getOrder() != null) {
			hql += " order by " + gbuyOrderProductPage.getSort() + " " + gbuyOrderProductPage.getOrder();
		}
		return gbuyOrderProductEntityDao.find(hql, gbuyOrderProductPage.getPage(), gbuyOrderProductPage.getRows(), values);
	}

	private Long total(GbuyOrderProductPage gbuyOrderProductPage) {
		String hql = "select count(*) from GbuyOrderProductEntity t where 1=1 ";
		List<Object> values = new ArrayList<Object>();
		hql = addWhere(gbuyOrderProductPage, hql, values);
		return gbuyOrderProductEntityDao.count(hql, values);
	}

	private String addWhere(GbuyOrderProductPage gbuyOrderProductPage, String hql, List<Object> values) {
	    //循环查询条件Page的所有[Integer][String]类型的字段，如果字段不为空则动态加上查询条件
		//-----------------------------------------------------
		StringBuffer hqlbf = new StringBuffer(hql);
		
		GbuyOrderProductEntity gbuyOrderProductEntity = new GbuyOrderProductEntity();
		BeanUtils.copyProperties(gbuyOrderProductPage, gbuyOrderProductEntity);
		SQLUtil.createSearchParamsHql(hqlbf, values, gbuyOrderProductEntity);
		hql = hqlbf.toString();
		//-----------------------------------------------------
		if (gbuyOrderProductPage.getCcreatedatetimeStart() != null) {
			hql += " and createDt>=? ";
			values.add(gbuyOrderProductPage.getCcreatedatetimeStart());
		}
		if (gbuyOrderProductPage.getCcreatedatetimeEnd() != null) {
			hql += " and createDt<=? ";
			values.add(gbuyOrderProductPage.getCcreatedatetimeEnd());
		}
		if (gbuyOrderProductPage.getCmodifydatetimeStart() != null) {
			hql += " and modifyDt>=? ";
			values.add(gbuyOrderProductPage.getCmodifydatetimeStart());
		}
		if (gbuyOrderProductPage.getCmodifydatetimeEnd() != null) {
			hql += " and modifyDt<=? ";
			values.add(gbuyOrderProductPage.getCmodifydatetimeEnd());
		}
		return hql;
	}

	public void add(GbuyOrderProductPage gbuyOrderProductPage) {
		if (gbuyOrderProductPage.getObid() == null || gbuyOrderProductPage.getObid().trim().equals("")) {
			gbuyOrderProductPage.setObid(UUID.randomUUID().toString());
		}
		GbuyOrderProductEntity t = new GbuyOrderProductEntity();
		BeanUtils.copyProperties(gbuyOrderProductPage, t);
		gbuyOrderProductEntityDao.save(t);
	}

	public void update(GbuyOrderProductPage gbuyOrderProductPage) throws Exception {
		GbuyOrderProductEntity t = gbuyOrderProductEntityDao.get(GbuyOrderProductEntity.class, gbuyOrderProductPage.getObid());
	    if(t != null) {
			BeanUtil.copyBeanNotNull2Bean(gbuyOrderProductPage, t);
		}
	}

	public void delete(String ids) {
		if (ids != null) {
			for (String id : ids.split(",")) {
				GbuyOrderProductEntity t = gbuyOrderProductEntityDao.get(GbuyOrderProductEntity.class, id);
				if (t != null) {
					gbuyOrderProductEntityDao.delete(t);
				}
			}
		}
	}

	public GbuyOrderProductEntity get(GbuyOrderProductPage gbuyOrderProductPage) {
		return gbuyOrderProductEntityDao.get(GbuyOrderProductEntity.class, gbuyOrderProductPage.getObid());
	}

	public GbuyOrderProductEntity get(String obid) {
		return gbuyOrderProductEntityDao.get(GbuyOrderProductEntity.class, obid);
	}
	public List<GbuyOrderProductEntity> listAll(GbuyOrderProductPage gbuyOrderProductPage) {
		String hql = "from GbuyOrderProductEntity where 1 = 1 ";
		List<Object> values = new ArrayList<Object>();
		hql = addWhere(gbuyOrderProductPage, hql, values);
		List<GbuyOrderProductEntity> list = gbuyOrderProductEntityDao.find(hql,values);
		return list;
	}
}
