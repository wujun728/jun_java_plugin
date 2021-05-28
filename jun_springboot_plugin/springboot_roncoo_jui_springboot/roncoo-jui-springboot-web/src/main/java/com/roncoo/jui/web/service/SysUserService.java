package com.roncoo.jui.web.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.roncoo.jui.common.dao.SysUserDao;
import com.roncoo.jui.common.entity.SysUser;
import com.roncoo.jui.common.entity.SysUserExample;
import com.roncoo.jui.common.entity.SysUserExample.Criteria;
import com.roncoo.jui.common.enums.UserStatusEnum;
import com.roncoo.jui.common.util.PageUtil;
import com.roncoo.jui.common.util.SqlUtil;
import com.roncoo.jui.common.util.jui.Page;
import com.roncoo.jui.web.bean.qo.SysUserQO;
import com.roncoo.jui.web.bean.vo.SysUserVO;
import com.xiaoleilu.hutool.crypto.SecureUtil;

/**
 * 后台用户信息
 *
 * @author Wujun
 * @since 2017-10-25
 */
@Component
public class SysUserService {

	@Autowired
	private SysUserDao dao;

	public Page<SysUserVO> listForPage(int pageCurrent, int pageSize, SysUserQO qo) {
		SysUserExample example = new SysUserExample();
		Criteria c = example.createCriteria();
		if (StringUtils.hasText(qo.getUserPhone())) {
			c.andUserPhoneLike(SqlUtil.like(qo.getUserPhone()));
		}
		example.setOrderByClause(" id desc ");
		Page<SysUser> page = dao.listForPage(pageCurrent, pageSize, example);
		return PageUtil.transform(page, SysUserVO.class);
	}

	public int save(SysUserQO qo) {
		SysUser record = new SysUser();
		BeanUtils.copyProperties(qo, record);
		record.setUserStatus(UserStatusEnum.NORMAL.getCode());
		record.setSalt(SecureUtil.simpleUUID());
		record.setPwd(SecureUtil.md5(record.getSalt() + qo.getPwd()));
		return dao.save(record);
	}

	public int deleteById(Long id) {
		return dao.deleteById(id);
	}

	public SysUserVO getById(Long id) {
		SysUserVO vo = new SysUserVO();
		SysUser record = dao.getById(id);
		BeanUtils.copyProperties(record, vo);
		return vo;
	}

	public int updateById(SysUserQO qo) {
		SysUser record = new SysUser();
		BeanUtils.copyProperties(qo, record);
		return dao.updateById(record);
	}

}
