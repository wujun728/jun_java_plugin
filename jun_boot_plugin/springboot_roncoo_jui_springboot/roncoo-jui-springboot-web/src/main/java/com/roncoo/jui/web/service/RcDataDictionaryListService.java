package com.roncoo.jui.web.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.roncoo.jui.common.dao.RcDataDictionaryListDao;
import com.roncoo.jui.common.entity.RcDataDictionaryList;
import com.roncoo.jui.common.entity.RcDataDictionaryListExample;
import com.roncoo.jui.common.entity.RcDataDictionaryListExample.Criteria;
import com.roncoo.jui.common.util.PageUtil;
import com.roncoo.jui.common.util.jui.Page;
import com.roncoo.jui.web.bean.qo.RcDataDictionaryListQO;
import com.roncoo.jui.web.bean.vo.RcDataDictionaryListVO;

/**
 * 数据字典明细表 
 *
 * @author Wujun
 * @since 2017-11-11
 */
@Component
public class RcDataDictionaryListService {

	@Autowired
	private RcDataDictionaryListDao dao;

	public Page<RcDataDictionaryListVO> listForPage(int pageCurrent, int pageSize, RcDataDictionaryListQO qo) {
	    RcDataDictionaryListExample example = new RcDataDictionaryListExample();
	    Criteria c = example.createCriteria();
	    example.setOrderByClause(" id desc ");
        Page<RcDataDictionaryList> page = dao.listForPage(pageCurrent, pageSize, example);
        return PageUtil.transform(page, RcDataDictionaryListVO.class);
	}

	public int save(RcDataDictionaryListQO qo) {
	    RcDataDictionaryList record = new RcDataDictionaryList();
        BeanUtils.copyProperties(qo, record);
		return dao.save(record);
	}

	public int deleteById(Long id) {
		return dao.deleteById(id);
	}

	public RcDataDictionaryListVO getById(Long id) {
	    RcDataDictionaryListVO vo = new RcDataDictionaryListVO();
	    RcDataDictionaryList record = dao.getById(id);
        BeanUtils.copyProperties(record, vo);
		return vo;
	}

	public int updateById(RcDataDictionaryListQO qo) {
	    RcDataDictionaryList record = new RcDataDictionaryList();
        BeanUtils.copyProperties(qo, record);
		return dao.updateById(record);
	}
	
}
