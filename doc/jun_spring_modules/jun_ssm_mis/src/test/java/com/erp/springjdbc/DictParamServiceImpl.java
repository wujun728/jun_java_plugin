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
import com.erp.jee.entity.DictParamEntity;
import com.erp.jee.page.base.DictParamPage;
import com.erp.jee.pageModel.DataGrid;
import com.erp.jee.service.DictParamServiceI;
import com.erp.service.impl.BaseServiceImpl;
import com.jun.plugin.utils.BeanUtil;
import com.jun.plugin.utils.SQLUtil;

/**   
 * @Title: ServiceImpl
 * @Description: 数据字典
 * @author Wujun
 * @date 2011-11-26 10:46:04
 * @version V1.0   
 *
 */
@Service("dictParamService")
public class DictParamServiceImpl extends BaseServiceImpl implements DictParamServiceI {

	@Autowired
	//SQL 使用JdbcDao
	private JdbcDao jdbcDao;
	private IBaseDao<DictParamEntity> dictParamEntityDao;

	public IBaseDao<DictParamEntity> getDictParamEntityDao() {
		return dictParamEntityDao;
	}
	@Autowired
	public void setDictParamEntityDao(IBaseDao<DictParamEntity> dictParamEntityDao) {
		this.dictParamEntityDao = dictParamEntityDao;
	}

	@Transactional(propagation = Propagation.SUPPORTS)
	public DataGrid datagrid(DictParamPage dictParamPage) {
		DataGrid j = new DataGrid();
		j.setRows(getPagesFromEntitys(find(dictParamPage)));
		j.setTotal(total(dictParamPage));
		return j;
	}

	private List<DictParamPage> getPagesFromEntitys(List<DictParamEntity> dictParamEntitys) {
		List<DictParamPage> dictParamPages = new ArrayList<DictParamPage>();
		if (dictParamEntitys != null && dictParamEntitys.size() > 0) {
			for (DictParamEntity tb : dictParamEntitys) {
				DictParamPage b = new DictParamPage();
				BeanUtils.copyProperties(tb, b);
				dictParamPages.add(b);
			}
		}
		return dictParamPages;
	}

	private List<DictParamEntity> find(DictParamPage dictParamPage) {
		String hql = "from DictParamEntity t where 1=1 ";

		List<Object> values = new ArrayList<Object>();
		hql = addWhere(dictParamPage, hql, values);

		if (dictParamPage.getSort() != null && dictParamPage.getOrder() != null) {
			hql += " order by " + dictParamPage.getSort() + " " + dictParamPage.getOrder();
		}
		return dictParamEntityDao.find(hql, dictParamPage.getPage(), dictParamPage.getRows(), values);
	}

	private Long total(DictParamPage dictParamPage) {
		String hql = "select count(*) from DictParamEntity t where 1=1 ";
		List<Object> values = new ArrayList<Object>();
		hql = addWhere(dictParamPage, hql, values);
		return dictParamEntityDao.count(hql, values);
	}

	private String addWhere(DictParamPage dictParamPage, String hql, List<Object> values) {
	    //循环查询条件Page的所有[Integer][String]类型的字段，如果字段不为空则动态加上查询条件
		//-----------------------------------------------------
		StringBuffer hqlbf = new StringBuffer(hql);
		SQLUtil.createSearchParamsHql(hqlbf, values, dictParamPage);
		hql = hqlbf.toString();
		//-----------------------------------------------------
		if (dictParamPage.getCcreatedatetimeStart() != null) {
			hql += " and createDt>=? ";
			values.add(dictParamPage.getCcreatedatetimeStart());
		}
		if (dictParamPage.getCcreatedatetimeEnd() != null) {
			hql += " and createDt<=? ";
			values.add(dictParamPage.getCcreatedatetimeEnd());
		}
		if (dictParamPage.getCmodifydatetimeStart() != null) {
			hql += " and modifyDt>=? ";
			values.add(dictParamPage.getCmodifydatetimeStart());
		}
		if (dictParamPage.getCmodifydatetimeEnd() != null) {
			hql += " and modifyDt<=? ";
			values.add(dictParamPage.getCmodifydatetimeEnd());
		}
		return hql;
	}

	public void add(DictParamPage dictParamPage) {
		if (dictParamPage.getObid() == null || dictParamPage.getObid().trim().equals("")) {
			dictParamPage.setObid(UUID.randomUUID().toString());
		}
		DictParamEntity t = new DictParamEntity();
		BeanUtils.copyProperties(dictParamPage, t);
		dictParamEntityDao.save(t);
	}

	public void update(DictParamPage dictParamPage) throws Exception {
		DictParamEntity t = dictParamEntityDao.get(DictParamEntity.class, dictParamPage.getObid());
	    if(t != null) {
			BeanUtil.copyBeanNotNull2Bean(dictParamPage, t);
		}
	}

	public void delete(String ids) {
		if (ids != null) {
			for (String id : ids.split(",")) {
				DictParamEntity t = dictParamEntityDao.get(DictParamEntity.class, id);
				if (t != null) {
					dictParamEntityDao.delete(t);
				}
			}
		}
	}

	public DictParamEntity get(DictParamPage dictParamPage) {
		return dictParamEntityDao.get(DictParamEntity.class, dictParamPage.getObid());
	}

	public DictParamEntity get(String obid) {
		return dictParamEntityDao.get(DictParamEntity.class, obid);
	}
	
	public List<DictParamEntity> listAll(DictParamPage dictParamPage) {
		String hql = "from DictParamEntity where 1 = 1 ";
		List<Object> values = new ArrayList<Object>();
		hql = addWhere(dictParamPage, hql, values);
		List<DictParamEntity> list = dictParamEntityDao.find(hql,values);
		return list;
	}
}
