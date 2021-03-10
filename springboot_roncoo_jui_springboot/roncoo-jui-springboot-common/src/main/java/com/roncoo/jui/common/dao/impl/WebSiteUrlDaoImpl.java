package com.roncoo.jui.common.dao.impl;

import com.roncoo.jui.common.dao.WebSiteUrlDao;
import com.roncoo.jui.common.entity.WebSiteUrl;
import com.roncoo.jui.common.entity.WebSiteUrlExample;
import com.roncoo.jui.common.mapper.WebSiteUrlMapper;
import com.roncoo.jui.common.util.PageUtil;
import com.roncoo.jui.common.util.jui.Page;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class WebSiteUrlDaoImpl implements WebSiteUrlDao {
    @Autowired
    private WebSiteUrlMapper webSiteUrlMapper;

    public int save(WebSiteUrl record) {
        return this.webSiteUrlMapper.insertSelective(record);
    }

    public int deleteById(Long id) {
        return this.webSiteUrlMapper.deleteByPrimaryKey(id);
    }

    public int updateById(WebSiteUrl record) {
        return this.webSiteUrlMapper.updateByPrimaryKeySelective(record);
    }

    public List<WebSiteUrl> listByExample(WebSiteUrlExample example) {
        return this.webSiteUrlMapper.selectByExample(example);
    }

    public WebSiteUrl getById(Long id) {
        return this.webSiteUrlMapper.selectByPrimaryKey(id);
    }

    public Page<WebSiteUrl> listForPage(int pageCurrent, int pageSize, WebSiteUrlExample example) {
        int count = this.webSiteUrlMapper.countByExample(example);
        pageSize = PageUtil.checkPageSize(pageSize);
        pageCurrent = PageUtil.checkPageCurrent(count, pageSize, pageCurrent);
        int totalPage = PageUtil.countTotalPage(count, pageSize);
        example.setLimitStart(PageUtil.countOffset(pageCurrent, pageSize));
        example.setPageSize(pageSize);
        return new Page<WebSiteUrl>(count, totalPage, pageCurrent, pageSize, this.webSiteUrlMapper.selectByExample(example));
    }
}