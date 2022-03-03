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
import com.erp.jee.entity.PersonEntity;
import com.erp.jee.page2.PersonPage;
import com.erp.jee.pageModel.DataGrid;
import com.erp.jee.service.PersonServiceI;
import com.erp.service.impl.BaseServiceImpl;
import com.jun.plugin.utils.BeanUtil;
import com.jun.plugin.utils.SQLUtil;

/**   
 * @Title: ServiceImpl
 * @Description: 用户
 * @author Wujun
 * @date 2013-01-17 11:41:25
 * @version V1.0   
 *
 */
@Service("personService")
public class PersonServiceImpl extends BaseServiceImpl implements PersonServiceI {

	@Autowired
	//SQL 使用JdbcDao
	private JdbcDao jdbcDao;
	private IBaseDao<PersonEntity> personEntityDao;

	public IBaseDao<PersonEntity> getPersonEntityDao() {
		return personEntityDao;
	}
	@Autowired
	public void setPersonEntityDao(IBaseDao<PersonEntity> personEntityDao) {
		this.personEntityDao = personEntityDao;
	}

	@Transactional(propagation = Propagation.SUPPORTS)
	public DataGrid datagrid(PersonPage personPage) {
		DataGrid j = new DataGrid();
		j.setRows(getPagesFromEntitys(find(personPage)));
		j.setTotal(total(personPage));
		return j;
	}

	private List<PersonPage> getPagesFromEntitys(List<PersonEntity> personEntitys) {
		List<PersonPage> personPages = new ArrayList<PersonPage>();
		if (personEntitys != null && personEntitys.size() > 0) {
			for (PersonEntity tb : personEntitys) {
				PersonPage b = new PersonPage();
				BeanUtils.copyProperties(tb, b);
				personPages.add(b);
			}
		}
		return personPages;
	}

	private List<PersonEntity> find(PersonPage personPage) {
		String hql = "from PersonEntity t where 1=1 ";

		List<Object> values = new ArrayList<Object>();
		hql = addWhere(personPage, hql, values);

		if (personPage.getSort() != null && personPage.getOrder() != null) {
			hql += " order by " + personPage.getSort() + " " + personPage.getOrder();
		}
		return personEntityDao.find(hql, personPage.getPage(), personPage.getRows(), values);
	}

	private Long total(PersonPage personPage) {
		String hql = "select count(*) from PersonEntity t where 1=1 ";
		List<Object> values = new ArrayList<Object>();
		hql = addWhere(personPage, hql, values);
		return personEntityDao.count(hql, values);
	}

	private String addWhere(PersonPage personPage, String hql, List<Object> values) {
	    //循环查询条件Page的所有[Integer][String]类型的字段，如果字段不为空则动态加上查询条件
		//-----------------------------------------------------
		StringBuffer hqlbf = new StringBuffer(hql);
		
		PersonEntity personEntity = new PersonEntity();
		BeanUtils.copyProperties(personPage, personEntity);
		SQLUtil.createSearchParamsHql(hqlbf, values, personEntity);
		hql = hqlbf.toString();
		//-----------------------------------------------------
		if (personPage.getCcreatedatetimeStart() != null) {
			hql += " and createDt>=? ";
			values.add(personPage.getCcreatedatetimeStart());
		}
		if (personPage.getCcreatedatetimeEnd() != null) {
			hql += " and createDt<=? ";
			values.add(personPage.getCcreatedatetimeEnd());
		}
		if (personPage.getCmodifydatetimeStart() != null) {
			hql += " and modifyDt>=? ";
			values.add(personPage.getCmodifydatetimeStart());
		}
		if (personPage.getCmodifydatetimeEnd() != null) {
			hql += " and modifyDt<=? ";
			values.add(personPage.getCmodifydatetimeEnd());
		}
		return hql;
	}

	public void add(PersonPage personPage) {
		if (personPage.getObid() == null || personPage.getObid().trim().equals("")) {
			personPage.setObid(UUID.randomUUID().toString());
		}
		PersonEntity t = new PersonEntity();
		BeanUtils.copyProperties(personPage, t);
		personEntityDao.save(t);
	}

	public void update(PersonPage personPage) throws Exception {
		PersonEntity t = personEntityDao.get(PersonEntity.class, personPage.getObid());
	    if(t != null) {
			BeanUtil.copyBeanNotNull2Bean(personPage, t);
		}
	}

	public void delete(String ids) {
		if (ids != null) {
			for (String id : ids.split(",")) {
				PersonEntity t = personEntityDao.get(PersonEntity.class, id);
				if (t != null) {
					personEntityDao.delete(t);
				}
			}
		}
	}

	public PersonEntity get(PersonPage personPage) {
		return personEntityDao.get(PersonEntity.class, personPage.getObid());
	}

	public PersonEntity get(String obid) {
		return personEntityDao.get(PersonEntity.class, obid);
	}
	public List<PersonEntity> listAll(PersonPage personPage) {
		String hql = "from PersonEntity where 1 = 1 ";
		List<Object> values = new ArrayList<Object>();
		hql = addWhere(personPage, hql, values);
		List<PersonEntity> list = personEntityDao.find(hql,values);
		return list;
	}
}
