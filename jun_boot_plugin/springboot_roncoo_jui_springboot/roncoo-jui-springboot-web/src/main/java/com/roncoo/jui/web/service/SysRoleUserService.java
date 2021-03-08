package com.roncoo.jui.web.service;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.roncoo.jui.common.dao.SysRoleUserDao;
import com.roncoo.jui.common.entity.SysRoleUser;
import com.roncoo.jui.common.entity.SysRoleUserExample;
import com.roncoo.jui.common.entity.SysRoleUserExample.Criteria;
import com.roncoo.jui.common.util.ArrayListUtil;
import com.roncoo.jui.common.util.PageUtil;
import com.roncoo.jui.common.util.jui.Page;
import com.roncoo.jui.web.bean.qo.SysRoleUserQO;
import com.roncoo.jui.web.bean.vo.SysRoleUserVO;

/**
 * 角色用户关联表
 *
 * @author Wujun
 * @since 2017-10-20
 */
@Component
public class SysRoleUserService {

	@Autowired
	private SysRoleUserDao dao;

	public Page<SysRoleUserVO> listForPage(int pageCurrent, int pageSize, SysRoleUserQO qo) {
		SysRoleUserExample example = new SysRoleUserExample();
		Criteria c = example.createCriteria();
		example.setOrderByClause(" id desc ");
		Page<SysRoleUser> page = dao.listForPage(pageCurrent, pageSize, example);
		return PageUtil.transform(page, SysRoleUserVO.class);
	}

	public int save(SysRoleUserQO qo) {
		SysRoleUser record = new SysRoleUser();
		BeanUtils.copyProperties(qo, record);
		return dao.save(record);
	}

	public int deleteById(Long id) {
		return dao.deleteById(id);
	}

	public SysRoleUserVO getById(Long id) {
		SysRoleUserVO vo = new SysRoleUserVO();
		SysRoleUser record = dao.getById(id);
		BeanUtils.copyProperties(record, vo);
		return vo;
	}

	public int updateById(SysRoleUserQO qo) {
		SysRoleUser record = new SysRoleUser();
		BeanUtils.copyProperties(qo, record);
		return dao.updateById(record);
	}

	public List<SysRoleUserVO> listByUserId(Long userId) {
		List<SysRoleUser> list = dao.listByUserId(userId);
		return ArrayListUtil.copy(list, SysRoleUserVO.class);
	}

	public int save(Long userId, String ids) {
		if (StringUtils.hasText(ids)) {
			// 先删除旧的，再添加新的
			dao.deleteByUserId(userId);
			// 拆分角色和平台拼接ID
			String[] idStrings = ids.split(",");
			for (String id : idStrings) {
				SysRoleUser sysRoleUser = new SysRoleUser();
				sysRoleUser.setUserId(userId);
				sysRoleUser.setRoleId(Long.parseLong(id));
				dao.save(sysRoleUser);
			}
		}
		return 1;
	}

}
