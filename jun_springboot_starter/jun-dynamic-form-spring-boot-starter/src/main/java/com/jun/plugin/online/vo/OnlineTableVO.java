/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFormat
 *  io.swagger.v3.oas.annotations.media.Schema
 */
package com.jun.plugin.online.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import java.util.List;

import lombok.Data;

//@Schema(description="Online\u8868\u5355\u5f00\u53d1")
@Data
public class OnlineTableVO {
    private static /* synthetic */ int[] Nd;
    //@Schema(description="\u521b\u5efa\u65f6\u95f4")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private /* synthetic */ Date createTime;
    //@Schema(description="id")
    private /* synthetic */ String id;
    //@Schema(description="\u662f\u5426\u6811  0\uff1a\u5426   1\uff1a\u662f")
    private /* synthetic */ Integer tree;
    //@Schema(description="\u8868\u7c7b\u578b  0\uff1a\u5355\u8868")
    private /* synthetic */ Integer tableType;
    //@Schema(description="\u8868\u5355\u5e03\u5c40  1\uff1a\u4e00\u5217   2\uff1a\u4e24\u5217   3\uff1a\u4e09\u5217    4\uff1a\u56db\u5217")
    private /* synthetic */ Integer formLayout;
    //@Schema(description="\u6811\u7236id")
    private /* synthetic */ String treePid;
    //@Schema(description="\u6811\u5c55\u793a\u5217")
    private /* synthetic */ String treeLabel;
    //@Schema(description="\u7248\u672c\u53f7")
    private /* synthetic */ Integer version;
    //@Schema(description="\u8868\u540d")
    private /* synthetic */ String name;
    //@Schema(description="\u8868\u5b57\u6bb5")
    private /* synthetic */ List<OnlineTableColumnVO> columnList;
    //@Schema(description="\u8868\u63cf\u8ff0")
    private /* synthetic */ String comments;

//    public List<OnlineTableColumnVO> getColumnList() {
//        return fZub.columnList;
//    }

}

