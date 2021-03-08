package com.roncoo.jui.common.dao;

import com.roncoo.jui.common.entity.WebSiteUrl;
import com.roncoo.jui.common.entity.WebSiteUrlExample;
import com.roncoo.jui.common.util.jui.Page;
import java.util.List;

public interface WebSiteUrlDao {
    int save(WebSiteUrl record);

    int deleteById(Long id);

    int updateById(WebSiteUrl record);

    WebSiteUrl getById(Long id);

    List<WebSiteUrl> listByExample(WebSiteUrlExample example);

    Page<WebSiteUrl> listForPage(int pageCurrent, int pageSize, WebSiteUrlExample example);
}