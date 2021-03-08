package com.roncoo.jui.web.service;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.roncoo.jui.common.dao.SysRoleDao;
import com.roncoo.jui.common.entity.SysRole;
import com.roncoo.jui.common.entity.SysRoleExample;
import com.roncoo.jui.common.entity.SysRoleExample.Criteria;
import com.roncoo.jui.common.util.PageUtil;
import com.roncoo.jui.common.util.jui.Page;
import com.roncoo.jui.web.bean.qo.SysRoleQO;
import com.roncoo.jui.web.bean.qo.SysRoleUserQO;
import com.roncoo.jui.web.bean.vo.SysRoleUserVO;
import com.roncoo.jui.web.bean.vo.SysRoleVO;

/**
 * 角色信息
 *
 * @author Wujun
 * @since 2017-10-20
 */
@Component
public class SysRoleService {

	@Autowired
	private SysRoleDao dao;

	public Page<SysRoleVO> listForPage(int pageCurrent, int pageSize, SysRoleQO qo) {
		SysRoleExample example = new SysRoleExample();
		Criteria c = example.createCriteria();
		if (StringUtils.hasText(qo.getRoleName())) {
			c.andRoleNameEqualTo(qo.getRoleName());
		}
		example.setOrderByClause("status_id desc, sort desc, id desc ");
		Page<SysRole> page = dao.listForPage(pageCurrent, pageSize, example);
		return PageUtil.transform(page, SysRoleVO.class);
	}

	public int save(SysRoleQO qo) {
		SysRole record = new SysRole();
		BeanUtils.copyProperties(qo, record);
		return dao.save(record);
	}

	public int deleteById(Long id) {
		return dao.deleteById(id);
	}

	public SysRoleVO getById(Long id) {
		SysRoleVO vo = new SysRoleVO();
		SysRole record = dao.getById(id);
		BeanUtils.copyProperties(record, vo);
		return vo;
	}

	public int updateById(SysRoleQO qo) {
		SysRole record = new SysRole();
		BeanUtils.copyProperties(qo, record);
		return dao.updateById(record);
	}

	public Page<SysRoleVO> checkUserByRole(int pageCurrent, int pageSize, SysRoleUserQO qo, List<SysRoleUserVO> list) {
		SysRoleExample example = new SysRoleExample();
		Criteria c = example.createCriteria();
		example.setOrderByClause(" id desc ");
		Page<SysRoleVO> page = PageUtil.transform(dao.listForPage(pageCurrent, pageSize, example), SysRoleVO.class);
		for (SysRoleVO roleVo : page.getList()) {
			Integer isShow = 0;
			for (SysRoleUserVO roleUserVo : list) {
				if (roleVo.getId().equals(roleUserVo.getRoleId())) {
					isShow = 1;
					break;
				}
			}
			roleVo.setIsShow(isShow);
		}
		return page;

	}

}
