package com.ibeetl.admin.core.dao;

import org.beetl.sql.core.annotatoin.Sql;
import org.beetl.sql.core.mapper.BaseMapper;

import com.ibeetl.admin.core.entity.CoreFile;

public interface  CoreFileDao extends BaseMapper<CoreFile> {
    @Sql("update core_file set biz_type=?,biz_id=? where file_batch_id=?")
    public void updateBatchIdInfo( String bizType, String bizId,String fileBatchId);
}
