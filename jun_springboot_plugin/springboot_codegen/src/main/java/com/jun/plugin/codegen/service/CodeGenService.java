package com.jun.plugin.codegen.service;

import com.jun.plugin.codegen.common.PageResult;
import com.jun.plugin.codegen.entity.GenConfig;
import com.jun.plugin.codegen.entity.TableRequest;

import cn.hutool.db.Entity;

/**
 * <p>
 * 代码生成器
 * </p>
 *
 * @package: com.xkcoding.codegen.service
 * @description: 代码生成器
 * @author: yangkai.shen
 * @date: Created in 2019-03-22 10:15
 * @copyright: Copyright (c) 2019
 * @version: V1.0
 * @modified: yangkai.shen
 */
public interface CodeGenService {
    /**
     * 生成代码
     *
     * @param genConfig 生成配置
     * @return 代码压缩文件
     */
    byte[] generatorCode(GenConfig genConfig);

    /**
     * 分页查询表信息
     *
     * @param request 请求参数
     * @return 表名分页信息
     */
    PageResult<Entity> listTables(TableRequest request);
}
