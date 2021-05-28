package com.ibeetl.admin.core.web.query;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ibeetl.admin.core.annotation.Query;
import org.beetl.sql.core.engine.PageQuery;

import java.lang.reflect.Field;

/**
 * 子类继承此类获得翻页功能
 * @author Wujun
 */
public class PageParam {
    private Integer page = null;
    private Integer limit = null;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    @JsonIgnore
    public PageQuery getPageQuery() {
        Field[] fs =this.getClass().getDeclaredFields();
        for(Field f:fs){
            Query query = f.getAnnotation(Query.class);
            if(query==null){
                continue ;
            }
            if (query.fuzzy()) {
                try {
                    if ( f.getType() == String.class) {
                        f.setAccessible(true);
                        Object o = f.get(this);
                        if (o != null && !o.toString().isEmpty()) {
                            f.set(this,"%"+o.toString()+"%");
                        }
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        PageQuery query = new PageQuery();
        query.setParas(this);
        if (page != null) {
            query.setPageNumber(page);
            query.setPageSize(limit);
        }
        return query;
    }

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}


}
