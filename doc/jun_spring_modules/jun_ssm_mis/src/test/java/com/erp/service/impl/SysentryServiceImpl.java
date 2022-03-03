package com.erp.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.erp.dao.PublicDao;
import com.erp.model.Permission;
import com.erp.model.Role;
import com.erp.model.SysEntity;
import com.erp.model.SysField;
import com.erp.service.ISysentryService;
import com.jun.plugin.utils.Constants;

@SuppressWarnings("unchecked")
@Service("sysentryServiceImpl")
public class SysentryServiceImpl implements ISysentryService {
	@SuppressWarnings("rawtypes")
	public PublicDao publicDao;

	@SuppressWarnings("rawtypes")
	@Autowired
	public void setPublicDao(PublicDao publicDao) {
		this.publicDao = publicDao;
	}
	
	@Autowired(required = false)
	@Qualifier("jdbcTemplate")
	protected JdbcTemplate jdbcTemplate;

	@Override
	public List<SysEntity> findAllSysEntryList(Map<String, Object> param, Integer page, Integer rows, boolean isPage) {
		String hql = "from SysEntity t   ";
		hql += Constants.getSearchConditionsHQL("t", param);
		List<SysEntity> tempList = null;
		if (isPage) {
			tempList = publicDao.find(hql, param, page, rows);
		} else {
			tempList = publicDao.find(hql, param);
		}
		// for (SysEntity SysEntity : tempList) {
		// SysEntity.setRolePermissions(null);
		// SysEntity.setUserRoles(null);
		// }
		return tempList;
	}

	@Override
	public Long getCount(Map<String, Object> param) {
		String hql = "select count(*) from SysEntity t  ";
		hql += Constants.getSearchConditionsHQL("t", param);
		return publicDao.count(hql, param);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List<SysField> getEntryFields(String ENTITY_ID) {
		String sql = " SELECT T2.ENTITY_CODE, T2.ENTITY_TABLENAME, T2.ENTITY_CHINANAME,T2.ENTITY_ID ,T1.* "
				+ "FROM SYS_FIELD T1 JOIN SYS_ENTITY T2 ON T1.FIELD_ENTITY_ID = T2.ENTITY_ID "
				+ "WHERE T2.DELETE_MARK = 'N' and T2.ENTITY_ID= "
				+ ENTITY_ID;
//		List list = publicDao.findBySQL(sql);
		List list=this.jdbcTemplate.queryForList(sql);
		List<SysField> list2 = new ArrayList<SysField>();
		if (list.size() != 0) {
			/*for (Object object : list) {
				SysField p = new SysField();
				p.setPermissionId(Integer.valueOf(object.toString()));
				list2.add(p);
			}*/
		}
		return list;
	}

}
