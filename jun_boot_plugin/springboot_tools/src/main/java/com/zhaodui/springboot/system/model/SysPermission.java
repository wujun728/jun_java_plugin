package com.zhaodui.springboot.system.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "sys_permission")
public class SysPermission {
    /**
     * 主键id
     */
    @Id
    private String id;

    /**
     * 父id
     */
    @Column(name = "parent_id")
    private String parentId;

    /**
     * 菜单标题
     */
    private String name;

    /**
     * 路径
     */
    private String url;

    /**
     * 组件
     */
    private String component;

    /**
     * 组件名字
     */
    @Column(name = "component_name")
    private String componentName;

    /**
     * 一级菜单跳转地址
     */
    private String redirect;

    /**
     * 菜单类型(0:一级菜单; 1:子菜单:2:按钮权限)
     */
    @Column(name = "menu_type")
    private Integer menuType;

    /**
     * 菜单权限编码
     */
    private String perms;

    /**
     * 权限策略1显示2禁用
     */
    @Column(name = "perms_type")
    private String permsType;

    /**
     * 菜单排序
     */
    @Column(name = "sort_no")
    private Double sortNo;

    /**
     * 聚合子路由: 1是0否
     */
    @Column(name = "always_show")
    private Boolean alwaysShow;

    /**
     * 菜单图标
     */
    private String icon;

    /**
     * 是否路由菜单: 0:不是  1:是（默认值1）
     */
    @Column(name = "is_route")
    private Boolean isRoute;

    /**
     * 是否叶子节点:    1:是   0:不是
     */
    @Column(name = "is_leaf")
    private Boolean isLeaf;

    /**
     * 是否缓存该页面:    1:是   0:不是
     */
    @Column(name = "keep_alive")
    private Boolean keepAlive;

    /**
     * 是否隐藏路由: 0否,1是
     */
    private Integer hidden;

    /**
     * 描述
     */
    private String description;

    /**
     * 创建人
     */
    @Column(name = "create_by")
    private String createBy;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 更新人
     */
    @Column(name = "update_by")
    private String updateBy;

    /**
     * 更新时间
     */
    @Column(name = "update_time")
    private Date updateTime;

    /**
     * 删除状态 0正常 1已删除
     */
    @Column(name = "del_flag")
    private Integer delFlag;

    /**
     * 是否添加数据权限1是0否
     */
    @Column(name = "rule_flag")
    private Integer ruleFlag;

    /**
     * 按钮权限状态(0无效1有效)
     */
    private String status;

    /**
     * 外链菜单打开方式 0/内部打开 1/外部打开
     */
    @Column(name = "internal_or_external")
    private Boolean internalOrExternal;

    /**
     * 获取主键id
     *
     * @return id - 主键id
     */
    public String getId() {
        return id;
    }

    /**
     * 设置主键id
     *
     * @param id 主键id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 获取父id
     *
     * @return parent_id - 父id
     */
    public String getParentId() {
        return parentId;
    }

    /**
     * 设置父id
     *
     * @param parentId 父id
     */
    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    /**
     * 获取菜单标题
     *
     * @return name - 菜单标题
     */
    public String getName() {
        return name;
    }

    /**
     * 设置菜单标题
     *
     * @param name 菜单标题
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取路径
     *
     * @return url - 路径
     */
    public String getUrl() {
        return url;
    }

    /**
     * 设置路径
     *
     * @param url 路径
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * 获取组件
     *
     * @return component - 组件
     */
    public String getComponent() {
        return component;
    }

    /**
     * 设置组件
     *
     * @param component 组件
     */
    public void setComponent(String component) {
        this.component = component;
    }

    /**
     * 获取组件名字
     *
     * @return component_name - 组件名字
     */
    public String getComponentName() {
        return componentName;
    }

    /**
     * 设置组件名字
     *
     * @param componentName 组件名字
     */
    public void setComponentName(String componentName) {
        this.componentName = componentName;
    }

    /**
     * 获取一级菜单跳转地址
     *
     * @return redirect - 一级菜单跳转地址
     */
    public String getRedirect() {
        return redirect;
    }

    /**
     * 设置一级菜单跳转地址
     *
     * @param redirect 一级菜单跳转地址
     */
    public void setRedirect(String redirect) {
        this.redirect = redirect;
    }

    /**
     * 获取菜单类型(0:一级菜单; 1:子菜单:2:按钮权限)
     *
     * @return menu_type - 菜单类型(0:一级菜单; 1:子菜单:2:按钮权限)
     */
    public Integer getMenuType() {
        return menuType;
    }

    /**
     * 设置菜单类型(0:一级菜单; 1:子菜单:2:按钮权限)
     *
     * @param menuType 菜单类型(0:一级菜单; 1:子菜单:2:按钮权限)
     */
    public void setMenuType(Integer menuType) {
        this.menuType = menuType;
    }

    /**
     * 获取菜单权限编码
     *
     * @return perms - 菜单权限编码
     */
    public String getPerms() {
        return perms;
    }

    /**
     * 设置菜单权限编码
     *
     * @param perms 菜单权限编码
     */
    public void setPerms(String perms) {
        this.perms = perms;
    }

    /**
     * 获取权限策略1显示2禁用
     *
     * @return perms_type - 权限策略1显示2禁用
     */
    public String getPermsType() {
        return permsType;
    }

    /**
     * 设置权限策略1显示2禁用
     *
     * @param permsType 权限策略1显示2禁用
     */
    public void setPermsType(String permsType) {
        this.permsType = permsType;
    }

    /**
     * 获取菜单排序
     *
     * @return sort_no - 菜单排序
     */
    public Double getSortNo() {
        return sortNo;
    }

    /**
     * 设置菜单排序
     *
     * @param sortNo 菜单排序
     */
    public void setSortNo(Double sortNo) {
        this.sortNo = sortNo;
    }

    /**
     * 获取聚合子路由: 1是0否
     *
     * @return always_show - 聚合子路由: 1是0否
     */
    public Boolean getAlwaysShow() {
        return alwaysShow;
    }

    /**
     * 设置聚合子路由: 1是0否
     *
     * @param alwaysShow 聚合子路由: 1是0否
     */
    public void setAlwaysShow(Boolean alwaysShow) {
        this.alwaysShow = alwaysShow;
    }

    /**
     * 获取菜单图标
     *
     * @return icon - 菜单图标
     */
    public String getIcon() {
        return icon;
    }

    /**
     * 设置菜单图标
     *
     * @param icon 菜单图标
     */
    public void setIcon(String icon) {
        this.icon = icon;
    }

    /**
     * 获取是否路由菜单: 0:不是  1:是（默认值1）
     *
     * @return is_route - 是否路由菜单: 0:不是  1:是（默认值1）
     */
    public Boolean getIsRoute() {
        return isRoute;
    }

    /**
     * 设置是否路由菜单: 0:不是  1:是（默认值1）
     *
     * @param isRoute 是否路由菜单: 0:不是  1:是（默认值1）
     */
    public void setIsRoute(Boolean isRoute) {
        this.isRoute = isRoute;
    }

    /**
     * 获取是否叶子节点:    1:是   0:不是
     *
     * @return is_leaf - 是否叶子节点:    1:是   0:不是
     */
    public Boolean getIsLeaf() {
        return isLeaf;
    }

    /**
     * 设置是否叶子节点:    1:是   0:不是
     *
     * @param isLeaf 是否叶子节点:    1:是   0:不是
     */
    public void setIsLeaf(Boolean isLeaf) {
        this.isLeaf = isLeaf;
    }

    /**
     * 获取是否缓存该页面:    1:是   0:不是
     *
     * @return keep_alive - 是否缓存该页面:    1:是   0:不是
     */
    public Boolean getKeepAlive() {
        return keepAlive;
    }

    /**
     * 设置是否缓存该页面:    1:是   0:不是
     *
     * @param keepAlive 是否缓存该页面:    1:是   0:不是
     */
    public void setKeepAlive(Boolean keepAlive) {
        this.keepAlive = keepAlive;
    }

    /**
     * 获取是否隐藏路由: 0否,1是
     *
     * @return hidden - 是否隐藏路由: 0否,1是
     */
    public Integer getHidden() {
        return hidden;
    }

    /**
     * 设置是否隐藏路由: 0否,1是
     *
     * @param hidden 是否隐藏路由: 0否,1是
     */
    public void setHidden(Integer hidden) {
        this.hidden = hidden;
    }

    /**
     * 获取描述
     *
     * @return description - 描述
     */
    public String getDescription() {
        return description;
    }

    /**
     * 设置描述
     *
     * @param description 描述
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * 获取创建人
     *
     * @return create_by - 创建人
     */
    public String getCreateBy() {
        return createBy;
    }

    /**
     * 设置创建人
     *
     * @param createBy 创建人
     */
    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    /**
     * 获取创建时间
     *
     * @return create_time - 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建时间
     *
     * @param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取更新人
     *
     * @return update_by - 更新人
     */
    public String getUpdateBy() {
        return updateBy;
    }

    /**
     * 设置更新人
     *
     * @param updateBy 更新人
     */
    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    /**
     * 获取更新时间
     *
     * @return update_time - 更新时间
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 设置更新时间
     *
     * @param updateTime 更新时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 获取删除状态 0正常 1已删除
     *
     * @return del_flag - 删除状态 0正常 1已删除
     */
    public Integer getDelFlag() {
        return delFlag;
    }

    /**
     * 设置删除状态 0正常 1已删除
     *
     * @param delFlag 删除状态 0正常 1已删除
     */
    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    /**
     * 获取是否添加数据权限1是0否
     *
     * @return rule_flag - 是否添加数据权限1是0否
     */
    public Integer getRuleFlag() {
        return ruleFlag;
    }

    /**
     * 设置是否添加数据权限1是0否
     *
     * @param ruleFlag 是否添加数据权限1是0否
     */
    public void setRuleFlag(Integer ruleFlag) {
        this.ruleFlag = ruleFlag;
    }

    /**
     * 获取按钮权限状态(0无效1有效)
     *
     * @return status - 按钮权限状态(0无效1有效)
     */
    public String getStatus() {
        return status;
    }

    /**
     * 设置按钮权限状态(0无效1有效)
     *
     * @param status 按钮权限状态(0无效1有效)
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * 获取外链菜单打开方式 0/内部打开 1/外部打开
     *
     * @return internal_or_external - 外链菜单打开方式 0/内部打开 1/外部打开
     */
    public Boolean getInternalOrExternal() {
        return internalOrExternal;
    }

    /**
     * 设置外链菜单打开方式 0/内部打开 1/外部打开
     *
     * @param internalOrExternal 外链菜单打开方式 0/内部打开 1/外部打开
     */
    public void setInternalOrExternal(Boolean internalOrExternal) {
        this.internalOrExternal = internalOrExternal;
    }
}