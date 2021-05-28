package com.roncoo.jui.common.dao;

import com.roncoo.jui.common.entity.WebSite;
import com.roncoo.jui.common.entity.WebSiteExample;
import com.roncoo.jui.common.util.jui.Page;
import java.util.List;

public interface WebSiteDao {
    int save(WebSite record);

    int deleteById(Long id);

    int updateById(WebSite record);

    WebSite getById(Long id);

    List<WebSite> listByExample(WebSiteExample example);

    Page<WebSite> listForPage(int pageCurrent, int pageSize, WebSiteExample example);
}