package com.roncoo.jui.web.service;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.roncoo.jui.common.dao.SysMenuRoleDao;
import com.roncoo.jui.common.entity.SysMenuRole;
import com.roncoo.jui.common.entity.SysMenuRoleExample;
import com.roncoo.jui.common.entity.SysMenuRoleExample.Criteria;
import com.roncoo.jui.common.util.ArrayListUtil;
import com.roncoo.jui.common.util.PageUtil;
import com.roncoo.jui.common.util.jui.Page;
import com.roncoo.jui.web.bean.qo.SysMenuRoleQO;
import com.roncoo.jui.web.bean.vo.SysMenuRoleVO;
import com.xiaoleilu.hutool.util.CollectionUtil;

/**
 * 菜单角色关联表
 *
 * @author Wujun
 * @since 2017-10-20
 */
@Component
public class SysMenuRoleService {

	@Autowired
	private SysMenuRoleDao dao;

	public Page<SysMenuRoleVO> listForPage(int pageCurrent, int pageSize, SysMenuRoleQO qo) {
		SysMenuRoleExample example = new SysMenuRoleExample();
		Criteria c = example.createCriteria();
		example.setOrderByClause(" sort desc, id desc ");
		Page<SysMenuRole> page = dao.listForPage(pageCurrent, pageSize, example);
		return PageUtil.transform(page, SysMenuRoleVO.class);
	}

	public int save(SysMenuRoleQO qo) {
		SysMenuRole record = new SysMenuRole();
		BeanUtils.copyProperties(qo, record);
		return dao.save(record);
	}

	public int deleteById(Long id) {
		return dao.deleteById(id);
	}

	public SysMenuRoleVO getById(Long id) {
		SysMenuRoleVO vo = new SysMenuRoleVO();
		SysMenuRole record = dao.getById(id);
		BeanUtils.copyProperties(record, vo);
		return vo;
	}

	public int updateById(SysMenuRoleQO qo) {
		SysMenuRole record = new SysMenuRole();
		BeanUtils.copyProperties(qo, record);
		return dao.updateById(record);
	}

	public List<SysMenuRoleVO> listByRoleId(Long roleId) {
		List<SysMenuRole> list = dao.listByRoleId(roleId);
		return ArrayListUtil.copy(list, SysMenuRoleVO.class);

	}

	public String getIds(List<SysMenuRoleVO> list) {
		StringBuilder sb = new StringBuilder();
		if (CollectionUtil.isNotEmpty(list)) {
			for (SysMenuRoleVO p : list) {
				sb.append(p.getId()).append(",");
			}
			sb = sb.delete(sb.length() - 1, sb.length());
		}
		return sb.toString();
	}

	public int save(Long roleId, String ids) {
		if (StringUtils.hasText(ids)) {
			// 先删除旧的，再添加新的
			dao.deleteByRoleId(roleId);
			String[] idStrings = ids.split(",");
			for (String id : idStrings) {
				SysMenuRole entity = new SysMenuRole();
				entity.setMenuId(Long.valueOf(id));
				entity.setRoleId(roleId);
				dao.save(entity);
			}
		}
		return 1;
	}

}
