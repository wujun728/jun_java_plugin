package com.erp.jee.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.erp.dao.IBaseDao;
import com.erp.jee.model.Tauth;
import com.erp.jee.pageModel.Auth;
import com.erp.jee.pageModel.TreeNode;
import com.erp.jee.service.AuthServiceI;
import com.erp.service.impl.BaseServiceImpl;
import com.jun.plugin.utils.comparator.AuthComparator;
//import com.jun.plugin.comparator.AuthComparator;

/**
 * 权限Service
 * 
 * @author Wujun
 * 
 */
@Service("authService")
public class AuthServiceImpl extends BaseServiceImpl implements AuthServiceI {

	private IBaseDao<Tauth> authDao;

	public IBaseDao<Tauth> getAuthDao() {
		return authDao;
	}

	@Autowired
	public void setAuthDao(IBaseDao<Tauth> authDao) {
		this.authDao = authDao;
	}

	/**
	 * 获得权限treegrid
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<Auth> treegrid(Auth auth) {
		List<Tauth> tauths;
		if (auth != null && auth.getId() != null) {
			tauths = authDao.find("from Tauth t where t.ctype='02' and t.tauth.cid = ? order by t.cseq", auth.getId());
		} else {
			tauths = authDao.find("from Tauth t where t.ctype='02' and  t.tauth is null order by t.cseq");
		}
		return getAuthsFromTauths(tauths);
	}

	private List<Auth> getAuthsFromTauths(List<Tauth> tauths) {
		List<Auth> auths = new ArrayList<Auth>();
		if (tauths != null && tauths.size() > 0) {
			for (Tauth t : tauths) {
				Auth a = new Auth();
				BeanUtils.copyProperties(t, a);
				if (t.getTauth() != null) {
					a.setCpid(t.getTauth().getCid());
					a.setCpname(t.getTauth().getCname());
				}
				if (countChildren(t.getCid()) > 0) {
					a.setState("closed");
				}
				auths.add(a);
			}
		}
		return auths;
	}

	private Long countChildren(String pid) {
		return authDao.count("select count(*) from Tauth t where t.ctype='02' and  t.tauth.cid = ?", pid);
	}

	public void delete(Auth auth) {
		del(auth.getCid());
	}

	private void del(String cid) {
		Tauth t = authDao.get(Tauth.class, cid);
		if (t != null) {
			authDao.executeHql("delete Troletauth t where t.tauth = ?", t);
			Set<Tauth> auths = t.getTauths();
			if (auths != null && !auths.isEmpty()) {
				// 说明当前权限下面还有子权限
				for (Tauth tauth : auths) {
					del(tauth.getCid());
				}
			}
			authDao.delete(t);
		}
	}

	@Transactional(propagation = Propagation.SUPPORTS)
	public List<TreeNode> tree(Auth auth, boolean b) {
		List<Object> param = new ArrayList<Object>();
		String hql = "from Tauth t where t.tauth is null order by t.cseq";
		if (auth != null && auth.getId() != null && !auth.getId().trim().equals("")) {
			hql = "from Tauth t where t.tauth.cid = ? order by t.cseq";
			param.add(auth.getId());
		}
		List<Tauth> l = authDao.find(hql, param);
		List<TreeNode> tree = new ArrayList<TreeNode>();
		for (Tauth t : l) {
			tree.add(tree(t, b));
		}
		return tree;
	}

	private TreeNode tree(Tauth t, boolean recursive) {
		TreeNode node = new TreeNode();
		node.setId(t.getCid());
		node.setText(t.getCname());
		Map<String, Object> attributes = new HashMap<String, Object>();
		node.setAttributes(attributes);
		if (t.getTauths() != null && t.getTauths().size() > 0) {
			node.setState("closed");
			if (recursive) {// 递归查询子节点
				List<Tauth> l = new ArrayList<Tauth>(t.getTauths());
				Collections.sort(l, new AuthComparator());// 排序
				List<TreeNode> children = new ArrayList<TreeNode>();
				for (Tauth r : l) {
					TreeNode tn = tree(r, true);
					children.add(tn);
				}
				node.setChildren(children);
			}
		}
		return node;
	}

	public void add(Auth auth) {
		Tauth t = new Tauth();
		BeanUtils.copyProperties(auth, t);
		if (auth.getCpid() != null && !auth.getCpid().equals(auth.getCid())) {
			t.setTauth(authDao.get(Tauth.class, auth.getCpid()));
		}
		authDao.save(t);
	}

	public void edit(Auth auth) {
		Tauth t = authDao.get(Tauth.class, auth.getCid());// 要修改的权限
		BeanUtils.copyProperties(auth, t);
		if (auth.getCpid() != null && !auth.getCpid().equals(auth.getCid())) {
			Tauth pAuth = authDao.get(Tauth.class, auth.getCpid());// 要设置的上级权限
			if (pAuth != null) {
				if (isDown(t, pAuth)) {// 要将当前节点修改到当前节点的子节点中
					Set<Tauth> tauths = t.getTauths();// 当前要修改的权限的所有下级权限
					if (tauths != null && tauths.size() > 0) {
						for (Tauth tauth : tauths) {
							if (tauth != null) {
								tauth.setTauth(null);
							}
						}
					}
				}
				t.setTauth(pAuth);
			}
		}
	}

	/**
	 * 判断是否是将当前节点修改到当前节点的子节点
	 * 
	 * @param t
	 * @param pt
	 * @return
	 */
	private boolean isDown(Tauth t, Tauth pt) {
		if (pt.getTauth() != null) {
			if (pt.getTauth().getCid().equals(t.getCid())) {
				return true;
			} else {
				return isDown(t, pt.getTauth());
			}
		}
		return false;
	}

	
	/**
	 * 根据请求参数,去权限表查询看是否存在配置
	 * @param auth
	 * @return
	 */
	public boolean findAuthExist(String curl) {
		String hql ="select count(*) from Tauth t where t.curl = ?";
		Long l = authDao.count(hql, curl);
		if(l==null||l.intValue()==0){
			return false;
		}
		return true;
	}

	/**
	 * 追加参数：权限类型
	 */
	public List<TreeNode> treeByCtype(Auth auth, boolean b) {
		List<Object> param = new ArrayList<Object>();
		String hql = "from Tauth t where t.ctype = ? and t.tauth is null order by t.cseq";
		param.add(auth.getCtype());
		if (auth != null && auth.getId() != null && !auth.getId().trim().equals("")) {
			hql = "from Tauth t where t.ctype = ? and  t.tauth.cid = ? order by t.cseq";
			param.add(auth.getId());
		}
		List<Tauth> l = authDao.find(hql, param);
		List<TreeNode> tree = new ArrayList<TreeNode>();
		for (Tauth t : l) {
			tree.add(tree(t, b));
		}
		return tree;
	}

}
