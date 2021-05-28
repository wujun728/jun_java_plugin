package com.roncoo.jui.common.dao.impl;

import com.roncoo.jui.common.dao.WebSiteDao;
import com.roncoo.jui.common.entity.WebSite;
import com.roncoo.jui.common.entity.WebSiteExample;
import com.roncoo.jui.common.mapper.WebSiteMapper;
import com.roncoo.jui.common.util.PageUtil;
import com.roncoo.jui.common.util.jui.Page;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class WebSiteDaoImpl implements WebSiteDao {
    @Autowired
    private WebSiteMapper webSiteMapper;

    public int save(WebSite record) {
        return this.webSiteMapper.insertSelective(record);
    }

    public int deleteById(Long id) {
        return this.webSiteMapper.deleteByPrimaryKey(id);
    }

    public int updateById(WebSite record) {
        return this.webSiteMapper.updateByPrimaryKeySelective(record);
    }

    public List<WebSite> listByExample(WebSiteExample example) {
        return this.webSiteMapper.selectByExample(example);
    }

    public WebSite getById(Long id) {
        return this.webSiteMapper.selectByPrimaryKey(id);
    }

    public Page<WebSite> listForPage(int pageCurrent, int pageSize, WebSiteExample example) {
        int count = this.webSiteMapper.countByExample(example);
        pageSize = PageUtil.checkPageSize(pageSize);
        pageCurrent = PageUtil.checkPageCurrent(count, pageSize, pageCurrent);
        int totalPage = PageUtil.countTotalPage(count, pageSize);
        example.setLimitStart(PageUtil.countOffset(pageCurrent, pageSize));
        example.setPageSize(pageSize);
        return new Page<WebSite>(count, totalPage, pageCurrent, pageSize, this.webSiteMapper.selectByExample(example));
    }
}