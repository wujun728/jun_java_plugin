package com.jun.plugin.common.entity;

import com.alibaba.fastjson2.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableField;
//import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;

import java.util.List;

/**
 * BaseEntity
 *
 * @author wujun
 * @version V1.0
 * @date 2020年3月18日
 */
@Data
public class BaseEntity {
	// -------------------------------------分页信息----------------------------------------------
    @JSONField(serialize = false)
    @TableField(exist = false)
    private int page = 1;

    @JSONField(serialize = false)
    @TableField(exist = false)
    private int limit = 10;
    
    @JSONField(serialize = false)
    @TableField(exist = false)
    private String keyword;

    // -------------------------------------数据权限----------------------------------------------
    /**
     * 数据权限：用户id
     */
    @TableField(exist = false)
    private List<String> createIds;
    
	@TableField(exist = false)
	private Integer isOwner;

//	public Page getQueryPage(){
//        Page page = new Page(getPage(), getLimit());
//        return page;
//    }
}
