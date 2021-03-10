package cn.springmvc.jpa.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * @author Wujun
 *
 */
@Entity
@Table(name = "t_sys_permission")
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

    @Id
    @Column(name = "ID", length = 32)
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Column(name = "NAME")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "SKEY")
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Column(name = "HIDE")
    public Integer getHide() {
        return hide;
    }

    public void setHide(Integer hide) {
        this.hide = hide;
    }

    @Column(name = "URL")
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Column(name = "SORT")
    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    @Column(name = "PARENT_KEY")
    public String getParentKey() {
        return parentKey;
    }

    public void setParentKey(String parentKey) {
        this.parentKey = parentKey;
    }

}
