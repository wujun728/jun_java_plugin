package com.zhaodui.springboot.system.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "sys_dict_item")
public class SysDictItem {
    @Id
    private String id;

    /**
     * 字典id
     */
    @Column(name = "dict_id")
    private String dictId;

    /**
     * 字典项文本
     */
    @Column(name = "item_text")
    private String itemText;

    /**
     * 字典项值
     */
    @Column(name = "item_value")
    private String itemValue;

    /**
     * 描述
     */
    private String description;

    /**
     * 排序
     */
    @Column(name = "sort_order")
    private Integer sortOrder;

    /**
     * 状态（1启用 0不启用）
     */
    private Integer status;

    @Column(name = "create_by")
    private String createBy;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_by")
    private String updateBy;

    @Column(name = "update_time")
    private Date updateTime;

    /**
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 获取字典id
     *
     * @return dict_id - 字典id
     */
    public String getDictId() {
        return dictId;
    }

    /**
     * 设置字典id
     *
     * @param dictId 字典id
     */
    public void setDictId(String dictId) {
        this.dictId = dictId;
    }

    /**
     * 获取字典项文本
     *
     * @return item_text - 字典项文本
     */
    public String getItemText() {
        return itemText;
    }

    /**
     * 设置字典项文本
     *
     * @param itemText 字典项文本
     */
    public void setItemText(String itemText) {
        this.itemText = itemText;
    }

    /**
     * 获取字典项值
     *
     * @return item_value - 字典项值
     */
    public String getItemValue() {
        return itemValue;
    }

    /**
     * 设置字典项值
     *
     * @param itemValue 字典项值
     */
    public void setItemValue(String itemValue) {
        this.itemValue = itemValue;
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
     * 获取排序
     *
     * @return sort_order - 排序
     */
    public Integer getSortOrder() {
        return sortOrder;
    }

    /**
     * 设置排序
     *
     * @param sortOrder 排序
     */
    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    /**
     * 获取状态（1启用 0不启用）
     *
     * @return status - 状态（1启用 0不启用）
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置状态（1启用 0不启用）
     *
     * @param status 状态（1启用 0不启用）
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * @return create_by
     */
    public String getCreateBy() {
        return createBy;
    }

    /**
     * @param createBy
     */
    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    /**
     * @return create_time
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * @return update_by
     */
    public String getUpdateBy() {
        return updateBy;
    }

    /**
     * @param updateBy
     */
    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    /**
     * @return update_time
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * @param updateTime
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}