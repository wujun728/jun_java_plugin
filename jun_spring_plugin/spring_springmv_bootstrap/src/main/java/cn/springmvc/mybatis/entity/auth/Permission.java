package cn.springmvc.mybatis.entity.auth;

import cn.springmvc.mybatis.entity.BaseEntity;

/**
 * @author Vincent.wang
 *
 */
public class Permission implements BaseEntity<String> {

    private static final long serialVersionUID = -7141829387338999544L;

    private String id;

    /** 菜单名称 **/
    private String name;

    /** 菜单编码 **/
    private String key;

    /** 菜单是否显示 **/
    private Integer hide;

    /** URL **/
    private String url;

    /** 显示顺序 **/
    private Integer sort;

    /** 父菜单编码 **/
    private String parentKey;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Integer getHide() {
        return hide;
    }

    public void setHide(Integer hide) {
        this.hide = hide;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getParentKey() {
        return parentKey;
    }

    public void setParentKey(String parentKey) {
        this.parentKey = parentKey;
    }

}
