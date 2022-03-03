package com.erp.jee.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.erp.dao.IBaseDao;
import com.erp.jee.model.Tauth;
import com.erp.jee.model.Trole;
import com.erp.jee.model.Troletauth;
import com.erp.jee.model.Tusertrole;
import com.erp.jee.pageModel.DataGrid;
import com.erp.jee.pageModel.Role;
import com.erp.jee.service.RoleServiceI;
import com.erp.service.impl.BaseServiceImpl;

/**
 * 角色Service
 * 
 * @author Wujun
 * 
 */
@Service("roleService")
public class RoleServiceImpl extends BaseServiceImpl implements RoleServiceI {

	private IBaseDao<Trole> roleDao;
	private IBaseDao<Tauth> authDao;
	private IBaseDao<Troletauth> roleauthDao;
	private IBaseDao<Tusertrole> userroleDao;

	public IBaseDao<Tusertrole> getUserroleDao() {
		return userroleDao;
	}

	@Autowired
	public void setUserroleDao(IBaseDao<Tusertrole> userroleDao) {
		this.userroleDao = userroleDao;
	}

	public IBaseDao<Troletauth> getRoleauthDao() {
		return roleauthDao;
	}

	@Autowired
	public void setRoleauthDao(IBaseDao<Troletauth> roleauthDao) {
		this.roleauthDao = roleauthDao;
	}

	public IBaseDao<Tauth> getAuthDao() {
		return authDao;
	}

	@Autowired
	public void setAuthDao(IBaseDao<Tauth> authDao) {
		this.authDao = authDao;
	}

	public IBaseDao<Trole> getRoleDao() {
		return roleDao;
	}

	@Autowired
	public void setRoleDao(IBaseDao<Trole> roleDao) {
		this.roleDao = roleDao;
	}

	@Transactional(propagation = Propagation.SUPPORTS)
	public DataGrid datagrid(Role role) {
		DataGrid j = new DataGrid();
		j.setRows(getRolesFromTroles(find(role)));
		j.setTotal(total(role));
		return j;
	}

	private List<Role> getRolesFromTroles(List<Trole> troles) {
		List<Role> roles = new ArrayList<Role>();
		if (troles != null && troles.size() > 0) {
			for (Trole tu : troles) {
				Role u = new Role();
				BeanUtils.copyProperties(tu, u);

				Set<Troletauth> troletauths = tu.getTroletauths();
				String authIds = "";
				String authNames = "";
				if (troletauths != null && troletauths.size() > 0) {
					for (Troletauth troletauth : troletauths) {
						if (troletauth.getTauth() != null) {
							authIds += "," + troletauth.getTauth().getCid();
							authNames += "," + troletauth.getTauth().getCname();
						}
					}
				}
				if (authIds.equals("")) {
					u.setAuthIds("");
					u.setAuthNames("");
				} else {
					u.setAuthIds(authIds.substring(1));
					u.setAuthNames(authNames.substring(1));
				}

				roles.add(u);
			}
		}
		return roles;
	}

	private List<Trole> find(Role role) {
		String hql = "from Trole t where 1=1 ";

		List<Object> values = new ArrayList<Object>();
		hql = addWhere(role, hql, values);

		if (role.getSort() != null && role.getOrder() != null) {
			hql += " order by " + role.getSort() + " " + role.getOrder();
		}
		return roleDao.find(hql, role.getPage(), role.getRows(), values);
	}

	private Long total(Role role) {
		String hql = "select count(*) from Trole t where 1=1 ";
		List<Object> values = new ArrayList<Object>();
		hql = addWhere(role, hql, values);
		return roleDao.count(hql, values);
	}

	private String addWhere(Role role, String hql, List<Object> values) {
		return hql;
	}

	public void add(Role role) {
		Trole r = new Trole();
		BeanUtils.copyProperties(role, r);
		roleDao.save(r);

		saveRoleAuth(role, r);

	}

	/**
	 * 保存Trole和Tauth的关系
	 * 
	 * @param role
	 * @param r
	 */
	private void saveRoleAuth(Role role, Trole r) {
		if (role.getAuthIds() != null) {
			for (String id : role.getAuthIds().split(",")) {
				Troletauth troletauth = new Troletauth();
				troletauth.setCid(UUID.randomUUID().toString());
				troletauth.setTauth(authDao.get(Tauth.class, id.trim()));
				troletauth.setTrole(r);
				roleauthDao.save(troletauth);
			}
		}
	}

	public void update(Role role) {
		Trole r = roleDao.get(Trole.class, role.getCid());
		BeanUtils.copyProperties(role, r, new String[] { "cid" });

		roleauthDao.executeHql("delete Troletauth t where t.trole = ?", r);
		saveRoleAuth(role, r);

	}

	public void delete(String ids) {
		if (ids != null) {
			for (String id : ids.split(",")) {
				Trole r = roleDao.get(Trole.class, id.trim());
				if (r != null) {
					roleauthDao.executeHql("delete Troletauth t where t.trole = ?", r);
					userroleDao.executeHql("delete Tusertrole t where t.trole = ?", r);
					roleDao.delete(r);
				}
			}
		}
	}

	@Transactional(propagation = Propagation.SUPPORTS)
	public List<Role> combobox() {
		return getRolesFromTroles(findAllTrole());
	}

	private List<Trole> findAllTrole() {
		String hql = "from Trole ";
		return roleDao.find(hql);
	}

}
