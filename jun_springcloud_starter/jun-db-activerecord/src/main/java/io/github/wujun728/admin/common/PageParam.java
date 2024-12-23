package io.github.wujun728.admin.common;

import java.util.HashMap;

/***
 * 分页参数
 */
public class PageParam extends HashMap<String,Object> {
    public Integer getPage() {
        return Integer.parseInt(this.getStr("page"));
    }

    public int getPerPage() {
        return Integer.parseInt(this.getStr("perPage"));
    }

    public String getStr(String key){
        Object o = this.get(key);
        return o == null ? null : o.toString();
    }

    public PageParam(){
        super();
        super.put("page","1");
        super.put("perPage","10");
    }
}
