package com.roncoo.jui.web.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.roncoo.jui.common.dao.WebSiteUrlDao;
import com.roncoo.jui.common.entity.WebSiteUrl;
import com.roncoo.jui.common.entity.WebSiteUrlExample;
import com.roncoo.jui.common.entity.WebSiteUrlExample.Criteria;
import com.roncoo.jui.common.util.PageUtil;
import com.roncoo.jui.common.util.SqlUtil;
import com.roncoo.jui.common.util.jui.Page;
import com.roncoo.jui.web.bean.qo.WebSiteUrlQO;
import com.roncoo.jui.web.bean.vo.WebSiteUrlVO;

/**
 * 网址汇总地址
 *
 * @author Wujun
 * @since 2017-11-22
 */
@Component
public class WebSiteUrlService {

	@Autowired
	private WebSiteUrlDao dao;

	public Page<WebSiteUrlVO> listForPage(int pageCurrent, int pageSize, WebSiteUrlQO qo) {
		WebSiteUrlExample example = new WebSiteUrlExample();
		Criteria c = example.createCriteria();
		c.andSiteIdEqualTo(qo.getSiteId());
		if (StringUtils.hasText(qo.getUrlName())) {
			c.andUrlNameLike(SqlUtil.like(qo.getUrlName()));
		}
		example.setOrderByClause(" status_id desc, sort desc, id desc ");
		Page<WebSiteUrl> page = dao.listForPage(pageCurrent, pageSize, example);
		return PageUtil.transform(page, WebSiteUrlVO.class);
	}

	public int save(WebSiteUrlQO qo) {
		WebSiteUrl record = new WebSiteUrl();
		BeanUtils.copyProperties(qo, record);
		return dao.save(record);
	}

	public int deleteById(Long id) {
		return dao.deleteById(id);
	}

	public WebSiteUrlVO getById(Long id) {
		WebSiteUrlVO vo = new WebSiteUrlVO();
		WebSiteUrl record = dao.getById(id);
		BeanUtils.copyProperties(record, vo);
		return vo;
	}

	public int updateById(WebSiteUrlQO qo) {
		WebSiteUrl record = new WebSiteUrl();
		BeanUtils.copyProperties(qo, record);
		return dao.updateById(record);
	}

}
