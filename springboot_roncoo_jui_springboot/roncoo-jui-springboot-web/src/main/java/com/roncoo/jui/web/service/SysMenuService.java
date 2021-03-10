package com.roncoo.jui.web.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.roncoo.jui.common.dao.SysMenuDao;
import com.roncoo.jui.common.entity.SysMenu;
import com.roncoo.jui.common.entity.SysMenuExample;
import com.roncoo.jui.common.entity.SysMenuExample.Criteria;
import com.roncoo.jui.common.util.PageUtil;
import com.roncoo.jui.common.util.jui.Page;
import com.roncoo.jui.web.bean.qo.SysMenuQO;
import com.roncoo.jui.web.bean.vo.SysMenuRoleVO;
import com.roncoo.jui.web.bean.vo.SysMenuVO;
import com.xiaoleilu.hutool.util.CollectionUtil;

/**
 * 菜单信息
 *
 * @author Wujun
 * @since 2017-10-25
 */
@Component
public class SysMenuService {

	@Autowired
	private SysMenuDao dao;

	public Page<SysMenuVO> listForPage(int pageCurrent, int pageSize, SysMenuQO qo) {
		SysMenuExample example = new SysMenuExample();
		Criteria c = example.createCriteria();
		if (StringUtils.hasText(qo.getMenuName())) {
			c.andMenuNameEqualTo(qo.getMenuName());
		} else {
			c.andParentIdEqualTo(0L);
		}
		example.setOrderByClause(" sort desc, id desc ");
		Page<SysMenu> page = dao.listForPage(pageCurrent, pageSize, example);
		Page<SysMenuVO> p = PageUtil.transform(page, SysMenuVO.class);
		if (!StringUtils.hasText(qo.getMenuName())) {
			if (CollectionUtil.isNotEmpty(p.getList())) {
				for (SysMenuVO sm : p.getList()) {
					sm.setList(recursionList(sm.getId()));
				}
			}
		}
		return p;
	}

	/**
	 * 递归展示菜单
	 * 
	 * @param parentId
	 * @return
	 */
	private List<SysMenuVO> recursionList(Long parentId) {
		List<SysMenuVO> lists = new ArrayList<>();
		List<SysMenu> list = dao.listByParentId(parentId);
		if (CollectionUtil.isNotEmpty(list)) {
			for (SysMenu m : list) {
				SysMenuVO vo = new SysMenuVO();
				BeanUtils.copyProperties(m, vo);
				vo.setList(recursionList(m.getId()));
				lists.add(vo);
			}
		}
		return lists;
	}

	public int save(SysMenuQO qo) {
		SysMenu record = new SysMenu();
		BeanUtils.copyProperties(qo, record);
		return dao.save(record);
	}

	public int deleteById(Long id) {
		List<SysMenu> list = dao.listByParentId(id);
		if (CollectionUtil.isNotEmpty(list)) {
			return -1;
		}
		return dao.deleteById(id);
	}

	public SysMenuVO getById(Long id) {
		SysMenuVO vo = new SysMenuVO();
		SysMenu record = dao.getById(id);
		BeanUtils.copyProperties(record, vo);
		return vo;
	}

	public int updateById(SysMenuQO qo) {
		SysMenu record = new SysMenu();
		BeanUtils.copyProperties(qo, record);
		return dao.updateById(record);
	}

	public List<SysMenuVO> listMenucByRole(List<SysMenuRoleVO> sysMenuRoleVOList) {
		List<SysMenuVO> list = recursionList(0L);
		List<SysMenuVO> sysMenuVOList = new ArrayList<>();
		listMenu(sysMenuVOList, sysMenuRoleVOList, list);
		return sysMenuVOList;
	}

	private List<SysMenuVO> listMenu(List<SysMenuVO> sysMenuVOList, List<SysMenuRoleVO> sysMenuRoleVOList, List<SysMenuVO> list) {
		for (SysMenuVO mv : list) {
			SysMenuVO v = null;
			for (SysMenuRoleVO vo : sysMenuRoleVOList) {
				if (mv.getId().equals(vo.getMenuId())) {
					v = new SysMenuVO();
					BeanUtils.copyProperties(mv, v);
					break;
				}
			}
			if (!StringUtils.isEmpty(v)) {
				sysMenuVOList.add(v);
				List<SysMenuVO> l = new ArrayList<>();
				if (v != null) {
					v.setList(l);
				}
				listMenu(l, sysMenuRoleVOList, mv.getList());
			}
		}
		return sysMenuVOList;
	}

	public List<SysMenuVO> checkMenucByRole(List<SysMenuRoleVO> sysMenuRoleVOList) {
		List<SysMenuVO> sysMenuVOList = recursionList(0L);
		checkMenu(sysMenuVOList, sysMenuRoleVOList);
		return sysMenuVOList;
	}

	private List<SysMenuVO> checkMenu(List<SysMenuVO> sysMenuVOList, List<SysMenuRoleVO> sysMenuRoleVOList) {
		for (SysMenuVO mv : sysMenuVOList) {
			Integer isShow = 0;
			for (SysMenuRoleVO vo : sysMenuRoleVOList) {
				if (mv.getId().equals(vo.getMenuId())) {
					isShow = 1;
					break;
				}
			}
			mv.setIsShow(isShow);
			checkMenu(mv.getList(), sysMenuRoleVOList);
		}
		return sysMenuVOList;
	}

}
