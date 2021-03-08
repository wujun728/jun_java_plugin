package com.roncoo.jui.web.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.roncoo.jui.common.dao.RcDataDictionaryDao;
import com.roncoo.jui.common.entity.RcDataDictionary;
import com.roncoo.jui.common.entity.RcDataDictionaryExample;
import com.roncoo.jui.common.entity.RcDataDictionaryExample.Criteria;
import com.roncoo.jui.common.util.PageUtil;
import com.roncoo.jui.common.util.jui.Page;
import com.roncoo.jui.web.bean.qo.RcDataDictionaryQO;
import com.roncoo.jui.web.bean.vo.RcDataDictionaryVO;

/**
 * 数据字典
 *
 * @author Wujun
 * @since 2017-11-11
 */
@Component
public class RcDataDictionaryService {

	@Autowired
	private RcDataDictionaryDao dao;

	public Page<RcDataDictionaryVO> listForPage(int pageCurrent, int pageSize, String orderField, String orderDirection, RcDataDictionaryQO qo) {
		RcDataDictionaryExample example = new RcDataDictionaryExample();
		Criteria c = example.createCriteria();
		// 字段排序
		StringBuilder orderByClause = new StringBuilder();
		if (StringUtils.hasText(orderField)) {
			orderByClause.append(orderField).append(" ").append(orderDirection).append(", ");
		}
		example.setOrderByClause(orderByClause.append(" id desc ").toString());
		Page<RcDataDictionary> page = dao.listForPage(pageCurrent, pageSize, example);
		page.setOrderField(orderField);
		page.setOrderDirection(orderDirection);
		return PageUtil.transform(page, RcDataDictionaryVO.class);
	}

	public int save(RcDataDictionaryQO qo) {
		RcDataDictionary record = new RcDataDictionary();
		BeanUtils.copyProperties(qo, record);
		return dao.save(record);
	}

	public int deleteById(Long id) {
		return dao.deleteById(id);
	}

	public RcDataDictionaryVO getById(Long id) {
		RcDataDictionaryVO vo = new RcDataDictionaryVO();
		RcDataDictionary record = dao.getById(id);
		BeanUtils.copyProperties(record, vo);
		return vo;
	}

	public int updateById(RcDataDictionaryQO qo) {
		RcDataDictionary record = new RcDataDictionary();
		BeanUtils.copyProperties(qo, record);
		return dao.updateById(record);
	}

}
