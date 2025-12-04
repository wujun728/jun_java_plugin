package com.company.project.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * BaseEntity
 *
 * @author wenbin
 * @version V1.0
 * @date 2020年3月18日
 */
@Data
@JsonIgnoreProperties(value = { "getQueryPage"})
public class BasePageEntity {
    @TableField(exist = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Integer page;

    @TableField(exist = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Integer limit;

    /**
     * page条件
     *
     * @param <T>
     * @return
     */
    @JSONField(serialize = false)
    @JsonIgnore
    public <T> Page getQueryPage() {
        return new Page<T>(page == null ? 1 : page, limit == null ? 10 : limit);
    }
}
