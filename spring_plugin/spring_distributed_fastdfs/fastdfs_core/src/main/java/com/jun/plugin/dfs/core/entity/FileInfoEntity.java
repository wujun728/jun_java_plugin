package com.jun.plugin.dfs.core.entity;

import lombok.Data;

import java.io.Serializable;

import com.jun.plugin.dfs.base.BaseEntity;

/**
 * 文件信息
 */
@Data
public class FileInfoEntity extends BaseEntity implements Serializable {
    /**
     * 1:所属应用 + 鉴权访问
     */
    public static final int FILE_ACCESS_TYPE_BELONGS_AUTH = 1;
    /**
     * 2:鉴权访问
     */
    public static final int FILE_ACCESS_TYPE_AUTH = 2;

    /**
     * 3:无鉴权访问
     */
    public static final int FILE_ACCESS_TYPE_NO_AUTH = 3;

    /**
     * 1:创建成功
     */
    public static final int FILE_STATUS_CREATED = 1;

    /**
     * 2:上传成功
     */
    public static final int FILE_STATUS_UPLOADED = 2;

    /**
     * 3:已删除
     */
    public static final int FILE_STATUS_DELETED = 3;

    private Integer id;

    /**
     * fastdfs返回的fileId
     */
    private String fileId;

    /**
     * 原始文件名
     */
    private String name;

    /**
     * 文件字节数
     */
    private long bytes;

    /**
     * 所存放的组编号与fastdfs的组名对应
     */
    private String groupName;

    /**
     * 访问类型 1:所属应用鉴权访问,2:所有应用鉴权访问,3:无鉴权访问
     */
    private Integer accessType;

    /**
     * 所属应用的编码
     */
    private String belongsApp;

    /**
     * 状态 1:创建成功,2:上传成功,3:已删除
     */
    private Integer status;
}
